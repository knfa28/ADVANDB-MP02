/*Dimensions for Household Fact Table*/
INSERT INTO `dim_tenur` VALUES (0,'Null Entry'),(1,'Owner, owner-like possession of house and lot'),(2,'Rent house/room including lot'),(3,'Own house, rent lot'),(4,'Own house, rent-free lot with consent of owner'),(5,'Own house, rent-free lot without consent of owner'),(6,'Rent-free house and lot with consent of owner'),(7,'Rent-free house and lot without consent of owner'),(8,'Living in a public space with rent'),(9,'Living in a public space without rent'),(10,'Others');
INSERT INTO `dim_house_type` VALUES (1,'Single House'),(2,'Duplex'),(3,'Multi-unit Residential'),(4,'Commercial/industrial/agricultural building/house (three units or more)'),(5,'Others (boat, cave, etc.)');
INSERT INTO `dim_roof` VALUES (1,'Strong materials (tile, concrete, brick, stone, galvanized)'), (2,'Mixed but predominantly strong materials'), (3,'Salvaged/makeshift materials'), (4,'Mixed but predominantly salvaged materials'), (5,'Light materials (bamboo, sawali, cogon, nipa)'), (6,'Mixed but predominantly light materials'), (7,'Not applicable');
INSERT INTO `dim_wall` VALUES (1,'Strong materials (tile,concrete, brick, stone, wood)'), (2,'Mixed but predominantly strong materials'), (3,'Salvaged/makeshift materials'), (4,'Mixed but predominantly salvaged materials'), (5,'Light materials (bamboo, sawali, cogon, nipa, anahaw)'), (6,'Mixed but predominantly light materials'), (7,'Not applicable');
INSERT INTO `dim_water` VALUES (1,'Own faucet, community water system'),(2,'Shared faucet, community water system'),(3,'Own use tubed/piped deep well'),(4,'Shared tubed/piped deep well'),(5,'Tubed/piped shallow well'),(6,'Dug well'),(7,'Protected Spring'),(8,'Unprotected Spring'),(9,'Lake, river, rain and others'),(10,'Peddler'),(11,'Bottled water'),(12,'Others');

/*Dimension for Calamity Fact Table*/
INSERT INTO `dim_calamity` VALUES (1,'Bagyo'),(2,'Baha'),(3,'Tagtuyot'),(4,'Lindol'),(5,'Pagsabog ng Bulcan'),(6,'Landslide'),(7,'Tsunami'),(8,'Sunog'),(9,'Forest Fire'),(10,'Armadong Digmaan'),(11,'Others');

/*Dimensions for Member Fact Table*/
INSERT INTO `dim_jobind` VALUES (0,'Null Entry'),(1,'Employed'),(2,'Unemployed');
INSERT INTO `dim_jstatus` VALUES (0,'Null Entry'),(1,'Permanent'),(2,'Short-term, seasonal, or casual'),(3,'Worked on different jobs on day to day or week to week');
INSERT INTO `dim_workcl` VALUES (0,'Null Entry'),(1,'Working for private household'),(2,'Working for private business/establishment/farm'),(3,'Working for government/corporation'),(4,'Self-employed without any employee'),(5,'Employer in own family-operated or business'),(6,'Working with pay on family-operated or business'),(7,'Working without pay on family-operated or business');

/*Load data from db_hpq for Location Dimension Table*/
INSERT INTO constellation.dim_location(mun, zone, brgy, purok, hh_count)
SELECT DISTINCT mun, zone, brgy, purok, COUNT(id) AS 'hh_count'
FROM db_hpq.hpq_hh
GROUP BY mun, zone, brgy, purok;

/*Transform and load data from db_hpq for Household Fact Table*/
INSERT INTO constellation.fact_household
SELECT H.id, L.location_id, IFNULL(H.tenur, 0), H.house_type,
	CASE 
		WHEN H.roof = 1 THEN 1
		WHEN H.roof = 2 THEN 5
		WHEN H.roof = 3 THEN 3
		WHEN H.roof = 4 THEN 2
		WHEN H.roof = 5 THEN 6
		WHEN H.roof = 6 THEN 4
		WHEN H.roof = 7 THEN 7
	END AS 'roof',
	CASE 
		WHEN H.wall = 1 THEN 1
		WHEN H.wall = 2 THEN 5
		WHEN H.wall = 3 THEN 3
		WHEN H.wall = 4 THEN 2
		WHEN H.wall = 5 THEN 6
		WHEN H.wall = 6 THEN 4
		WHEN H.wall = 7 THEN 7
	END AS 'wall',
	H.water, IFNULL(H.welec, 2), H.nofw
FROM db_hpq.hpq_hh H

INNER JOIN constellation.dim_location L 
ON H.mun = L.mun AND H.zone = L.zone AND H.brgy = L.brgy AND H.purok = L.purok

GROUP BY H.id;

/*Transform and load data from db_hpq for Member Fact Table*/
INSERT INTO constellation.fact_member
SELECT M.id, M.memno, 
	IFNULL(M.jobind, 0) AS 'jobind',
    IFNULL(M.jstatus, 0) AS 'jstatus',
    IFNULL(M.workcl, 0) AS 'workcl',
	IF(A.id IS NOT NULL, TRUE, FALSE) AS 'aquani_status',
    IF(C.id IS NOT NULL, TRUE, FALSE) AS 'crop_status'
FROM (SELECT id, memno, jobind, jstatus, workcl FROM db_hpq.hpq_mem) M
    
LEFT JOIN (SELECT hpq_hh_id, id FROM db_hpq.hpq_aquani) AS A
ON M.id = A.hpq_hh_id AND M.memno = A.id

LEFT JOIN (SELECT hpq_hh_id, id FROM db_hpq.hpq_crop) AS C
ON M.id = C.hpq_hh_id AND M.memno = C.id

WHERE M.id IN (SELECT household_id FROM constellation.fact_household)
ORDER BY M.id;

/*Transform and load data from db_hpq for Calamity Fact Table*/
INSERT INTO constellation.fact_calamity
SELECT L.location_id, 1, COUNT(L.location_id), AVG(H.calam1_hwmny), 2 - AVG(H.calam1_aid)
FROM db_hpq.hpq_hh H INNER JOIN constellation.dim_location L 
ON H.mun = L.mun AND H.zone = L.zone AND H.brgy = L.brgy AND H.purok = L.purok
WHERE H.calam1 = 1
GROUP BY L.location_id;

INSERT INTO constellation.fact_calamity
SELECT L.location_id, 2, COUNT(L.location_id), AVG(H.calam2_hwmny), 2 - AVG(H.calam2_aid)
FROM db_hpq.hpq_hh H INNER JOIN constellation.dim_location L 
ON H.mun = L.mun AND H.zone = L.zone AND H.brgy = L.brgy AND H.purok = L.purok
WHERE H.calam2 = 1
GROUP BY L.location_id;

INSERT INTO constellation.fact_calamity
SELECT L.location_id, 3, COUNT(L.location_id), AVG(H.calam3_hwmny), 2 - AVG(H.calam3_aid)
FROM db_hpq.hpq_hh H INNER JOIN constellation.dim_location L 
ON H.mun = L.mun AND H.zone = L.zone AND H.brgy = L.brgy AND H.purok = L.purok
WHERE H.calam3 = 1
GROUP BY L.location_id;

INSERT INTO constellation.fact_calamity
SELECT L.location_id, 4, COUNT(L.location_id), AVG(H.calam4_hwmny), 2 - AVG(H.calam4_aid)
FROM db_hpq.hpq_hh H INNER JOIN constellation.dim_location L 
ON H.mun = L.mun AND H.zone = L.zone AND H.brgy = L.brgy AND H.purok = L.purok
WHERE H.calam4 = 1
GROUP BY L.location_id;

INSERT INTO constellation.fact_calamity
SELECT L.location_id, 5, COUNT(L.location_id), AVG(H.calam5_hwmny), 2 - AVG(H.calam5_aid)
FROM db_hpq.hpq_hh H INNER JOIN constellation.dim_location L 
ON H.mun = L.mun AND H.zone = L.zone AND H.brgy = L.brgy AND H.purok = L.purok
WHERE H.calam5 = 1
GROUP BY L.location_id;

INSERT INTO constellation.fact_calamity
SELECT L.location_id, 6, COUNT(L.location_id), AVG(H.calam6_hwmny), 2 - AVG(H.calam6_aid)
FROM db_hpq.hpq_hh H INNER JOIN constellation.dim_location L 
ON H.mun = L.mun AND H.zone = L.zone AND H.brgy = L.brgy AND H.purok = L.purok
WHERE H.calam6 = 1
GROUP BY L.location_id;

INSERT INTO constellation.fact_calamity
SELECT L.location_id, 7, COUNT(L.location_id), AVG(H.calam7_hwmny), 2 - AVG(H.calam7_aid)
FROM db_hpq.hpq_hh H INNER JOIN constellation.dim_location L 
ON H.mun = L.mun AND H.zone = L.zone AND H.brgy = L.brgy AND H.purok = L.purok
WHERE H.calam7 = 1
GROUP BY L.location_id;

INSERT INTO constellation.fact_calamity
SELECT L.location_id, 8, COUNT(L.location_id), AVG(H.calam8_hwmny), 2 - AVG(H.calam8_aid)
FROM db_hpq.hpq_hh H INNER JOIN constellation.dim_location L 
ON H.mun = L.mun AND H.zone = L.zone AND H.brgy = L.brgy AND H.purok = L.purok
WHERE H.calam8 = 1
GROUP BY L.location_id;

INSERT INTO constellation.fact_calamity
SELECT L.location_id, 9, COUNT(L.location_id), AVG(H.calam9_hwmny), 2 - AVG(H.calam9_aid)
FROM db_hpq.hpq_hh H INNER JOIN constellation.dim_location L 
ON H.mun = L.mun AND H.zone = L.zone AND H.brgy = L.brgy AND H.purok = L.purok
WHERE H.calam9 = 1
GROUP BY L.location_id;

INSERT INTO constellation.fact_calamity
SELECT L.location_id, 10, COUNT(L.location_id), AVG(H.calam10_hwmny), 2 - AVG(H.calam10_aid)
FROM db_hpq.hpq_hh H INNER JOIN constellation.dim_location L 
ON H.mun = L.mun AND H.zone = L.zone AND H.brgy = L.brgy AND H.purok = L.purok
WHERE H.calam10 = 1
GROUP BY L.location_id;

INSERT INTO constellation.fact_calamity
SELECT L.location_id, 11, COUNT(L.location_id), AVG(H.calam11_hwmny), 2 - AVG(H.calam11_aid)
FROM db_hpq.hpq_hh H INNER JOIN constellation.dim_location L 
ON H.mun = L.mun AND H.zone = L.zone AND H.brgy = L.brgy AND H.purok = L.purok
WHERE H.calam11 = 1
GROUP BY L.location_id;

