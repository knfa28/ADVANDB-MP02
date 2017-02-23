# Base Query
SELECT L.location_id, HT.house_type_desc,
	COUNT(DISTINCT(H.household_id)) AS 'hh_count',
	AVG(H.roof_id) AS 'roof_avg',
    AVG (H.wall_id) AS 'wall_avg',
    AVG(H.water_id) AS 'water_avg',
    2 - AVG(H.welec_status) AS 'welec_avg'
FROM (fact_household H)

INNER JOIN dim_location AS L ON H.location_id = L.location_id
LEFT JOIN dim_house_type AS HT ON H.house_type_id = HT.house_type_id

GROUP BY L.location_id, HT.house_type_desc
ORDER BY L.location_id ASC, HT.house_type_desc ASC;

# Rollup
    SELECT L.location_id,
        COUNT(DISTINCT(H.household_id)) AS 'hh_count',
        AVG(H.roof_id) AS 'roof_avg',
        AVG (H.wall_id) AS 'wall_avg',
        AVG(H.water_id) AS 'water_avg',
        2 - AVG(H.welec_status) AS 'welec_avg'
    FROM (fact_household H)
    INNER JOIN dim_location AS L ON H.location_id = L.location_id
    GROUP BY L.location_id

# Drill Down
SELECT L.location_id, HT.house_type_desc, T.tenur_desc,
    COUNT(DISTINCT(H.household_id)) AS 'hh_count',
    AVG(H.roof_id) AS 'roof_avg',
    AVG (H.wall_id) AS 'wall_avg',
    AVG(H.water_id) AS 'water_avg',
    2 - AVG(H.welec_status) AS 'welec_avg'
FROM (fact_household H)
INNER JOIN dim_location AS L ON H.location_id = L.location_id
LEFT JOIN dim_house_type AS HT ON H.house_type_id = HT.house_type_id
LEFT JOIN dim_tenur AS T ON H.tenur_id = T.tenur_id
GROUP BY L.location_id, HT.house_type_desc, T.tenur_desc
ORDER BY L.location_id ASC, HT.house_type_desc ASC, T.tenur_desc ASC;

SELECT L.location_id, HT.house_type_desc, T.tenur_desc, H.household_id, R.roof_desc, W.wall_desc, WA.water_desc, H.welec_status
FROM (fact_household H)
INNER JOIN dim_location AS L ON H.location_id = L.location_id
LEFT JOIN dim_house_type AS HT ON H.house_type_id = HT.house_type_id
LEFT JOIN dim_tenur AS T ON H.tenur_id = T.tenur_id
LEFT JOIN dim_roof AS R ON H.roof_id = R.roof_id
LEFT JOIN dim_wall AS W ON H.wall_id = W.wall_id
LEFT JOIN dim_water AS WA ON H.water_id = WA.water_id
GROUP BY L.location_id, HT.house_type_desc, T.tenur_desc, H.household_id
ORDER BY L.location_id ASC, HT.house_type_desc ASC, T.tenur_desc ASC, H.household_id ASC;

# Slice Dice
SELECT L.location_id,
	COUNT(DISTINCT(H.household_id)) AS 'hh_count',
	AVG(H.roof_id) AS 'roof_avg',
    AVG (H.wall_id) AS 'wall_avg',
    AVG(H.water_id) AS 'water_avg',
    2 - AVG(H.welec_status) AS 'welec_avg'
FROM (fact_household H)
INNER JOIN dim_location AS C ON H.location_id = C.location_id
INNER JOIN dim_location AS L ON H.location_id = L.location_id
WHERE C.calamity_id = 1
AND C.avg_aid = 0
AND L.mun = 1
GROUP BY L.location_id