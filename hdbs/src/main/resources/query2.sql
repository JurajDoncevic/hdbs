SELECT mysql1.customer.customer_name, postgres2.place.place_name, postgres2.county.county_name
FROM mysql1.customer, postgres2.place, postgres2.county
JOIN(postgres2.place.place_id, mysql1.customer.pg2_place_id)
JOIN(postgres2.county.county_id, postgres2.place.county_id)
WHERE mysql1.customer.customer_id = 1;