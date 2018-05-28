UPDATE mysql2.loyalty_card(mysql2.loyalty_card.points) VALUES(100)
FROM mysql2.loyalty_card, mysql1.customer
JOIN(mysql1.customer.customer_id, mysql2.loyalty_card.ms1_customer_id)
WHERE mysql1.customer.customer_name='ivo';