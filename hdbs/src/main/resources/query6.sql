DELETE mysql1.customer
FROM mysql1.customer, postgres2.place
JOIN (postgres2.place.place_id, mysql1.customer.pg2_place_id)
WHERE postgres2.place.place_name='Zagreb';