DELETE mysql2.loyalty_card
FROM mysql2.loyalty_card, mysql1.customer, postgres2.place
JOIN (mysql1.customer.customer_id, mysql2.loyalty_card.ms1_customer_id)
JOIN (postgres2.place.place_id, mysql1.customer.pg2_place_id)
WHERE postgres2.place.place_name='Zagreb';