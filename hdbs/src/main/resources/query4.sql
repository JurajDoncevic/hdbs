SELECT mysql1.customer.customer_name, postgres2.place.place_name
FROM mysql1.customer, postgres2.place
JOIN(postgres2.place.place_id, mysql1.customer.pg2_place_id)
WHERE postgres2.place.place_name = 'Bjelovar' AND mysql1.customer.customer_id > 1;

/*
test block comment under
*/