SELECT mysql1.customer.customer_name, postgres2.place.place_name
FROM mysql1.customer, postgres2.place
JOIN(postgres2.place.place_id, mysql1.customer.customer_name)
WHERE postgres2.place.population > 50000;
/*FAILURE:SEMANTIC ERROR IN JOIN CLAUSE: No such relationship exists: postgres2.place.place_id-mysql1.customer.customer_name*/