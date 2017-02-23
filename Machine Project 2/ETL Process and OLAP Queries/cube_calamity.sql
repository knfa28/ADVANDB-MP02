SELECT L.location_id, DC.calamity_desc,
    SUM(C.avg_frequency) AS 'frequency_sum',
    AVG(C.avg_aid) AS 'aid_avg',
    IF(STRONG.count IS NULL, 0, STRONG.count/TOTAL.count) AS 'strong_avg',
	IF(WEAK.count IS NULL, 0, WEAK.count/TOTAL.count) AS 'weak_avg',
    CASE
		WHEN STRONG.count IS NULL AND WEAK.count IS NOT NULL THEN (TOTAL.count - WEAK.count)/TOTAL.count 
        WHEN STRONG.count IS NOT NULL AND WEAK.count IS NULL THEN (TOTAL.count - STRONG.count)/TOTAL.count
        WHEN STRONG.count IS NOT NULL AND WEAK.count IS NOT NULL THEN (TOTAL.count - (STRONG.count + WEAK.count))/TOTAL.count
	END AS 'others_avg'
FROM (dim_location L, fact_calamity C,
	(SELECT location_id, COUNT(*) as 'count'
	FROM fact_household
	GROUP BY location_id) AS TOTAL)

INNER JOIN dim_calamity AS DC ON C.calamity_id = DC.calamity_id

LEFT JOIN (SELECT location_id, COUNT(*) as 'count'
	FROM fact_household
	WHERE (roof_id = 1 OR roof_id = 2)
	AND (wall_id = 1 OR wall_id = 2)
	GROUP BY location_id) AS STRONG
ON L.location_id = STRONG.location_id

LEFT JOIN (SELECT location_id, COUNT(*) as 'count'
	FROM fact_household
	WHERE (roof_id = 3 OR roof_id = 4 OR roof_id = 5 OR roof_id = 6 OR roof_id = 7)
	AND (wall_id = 3 OR wall_id = 4 OR wall_id = 5 OR wall_id = 6 OR wall_id = 7)
	GROUP BY location_id) AS WEAK
ON L.location_id = WEAK.location_id

WHERE L.location_id = C.location_id
AND L.location_id = TOTAL.location_id
GROUP BY L.location_id, DC.calamity_desc WITH ROLLUP

UNION

SELECT L.location_id, DC.calamity_desc,
    SUM(C.avg_frequency) AS 'frequency_sum',
    AVG(C.avg_aid) AS 'aid_avg',
    IF(STRONG.count IS NULL, 0, STRONG.count/TOTAL.count) AS 'strong_avg',
	IF(WEAK.count IS NULL, 0, WEAK.count/TOTAL.count) AS 'weak_avg',
    CASE
		WHEN STRONG.count IS NULL AND WEAK.count IS NOT NULL THEN (TOTAL.count - WEAK.count)/TOTAL.count 
        WHEN STRONG.count IS NOT NULL AND WEAK.count IS NULL THEN (TOTAL.count - STRONG.count)/TOTAL.count
        WHEN STRONG.count IS NOT NULL AND WEAK.count IS NOT NULL THEN (TOTAL.count - (STRONG.count + WEAK.count))/TOTAL.count
	END AS 'others_avg'
FROM (dim_location L, fact_calamity C,
	(SELECT location_id, COUNT(*) as 'count'
	FROM fact_household
	GROUP BY location_id) AS TOTAL)

INNER JOIN dim_calamity AS DC ON C.calamity_id = DC.calamity_id

LEFT JOIN (SELECT location_id, COUNT(*) as 'count'
	FROM fact_household
	WHERE (roof_id = 1 OR roof_id = 2)
	AND (wall_id = 1 OR wall_id = 2)
	GROUP BY location_id) AS STRONG
ON L.location_id = STRONG.location_id

LEFT JOIN (SELECT location_id, COUNT(*) as 'count'
	FROM fact_household
	WHERE (roof_id = 3 OR roof_id = 4 OR roof_id = 5 OR roof_id = 6 OR roof_id = 7)
	AND (wall_id = 3 OR wall_id = 4 OR wall_id = 5 OR wall_id = 6 OR wall_id = 7)
	GROUP BY location_id) AS WEAK
ON L.location_id = WEAK.location_id

WHERE L.location_id = C.location_id
AND L.location_id = TOTAL.location_id
GROUP BY DC.calamity_desc, L.location_id  WITH ROLLUP
