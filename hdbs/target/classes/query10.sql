SUM(mysql2.loyalty_card.points)
FROM mysql2.loyalty_card, mysql1.customer, mysql1.place
JOIN(mysql1.customer.customer_id, mysql2.loyalty_card.ms1_customer_id)
JOIN(mysql1.place.place_id, mysql1.customer.place_id)
WHERE mysql1.place.place_name='Zagreb';