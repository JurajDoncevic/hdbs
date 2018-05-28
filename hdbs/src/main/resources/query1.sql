SELECT *
FROM mysql1.customer, postgres2.place
JOIN(postgres2.place.place_id, mysql1.customer.pg2_place_id)
JOIN(postgres2.place.place_id, mysql1.customer.pg2_place_id)
WHERE mysql1.customer.customer_id = 1;
/*FAILURE:SEMANTIC ERROR IN JOIN CLAUSE: All joins must be unique. This one is not: postgres2.place.place_id-mysql1.customer.pg2_place_id*/