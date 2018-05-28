SELECT mysql1.purchase.purchase_id, mysql1.customer.customer_name, postgres2.place.place_name, postgres2.county.county_name, mysql1.purchase.purchased_amount, mysql2.loyalty_card.loyalty_card_id
FROM mysql1.customer, postgres2.place, postgres2.county, mysql1.purchase, mysql1.item, mysql2.loyalty_card
JOIN(postgres2.place.place_id, mysql1.customer.pg2_place_id)
JOIN(postgres2.county.county_id, postgres2.place.county_id)
JOIN(mysql1.customer.customer_id, mysql1.purchase.customer_id)
JOIN(mysql1.item.item_id, mysql1.purchase.item_id)
JOIN(mysql1.customer.customer_id, mysql2.loyalty_card.ms1_customer_id)
WHERE postgres2.place.place_name!='Bjelovar' AND mysql1.purchase.purchased_amount > 1;