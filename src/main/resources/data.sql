INSERT INTO account(id, name, username, password, create_dt)
VALUES (1,'John','objgogo1', '1234','2022-03-01 12:00:00'),
       (2,'Bob','objgogo2', '1234','2022-03-01 12:00:00'),
       (3,'Peter','objgogo3', '1234','2022-03-01 12:00:00');

INSERT INTO account_roles(account_id,role)
VALUES (1,'USER'),
       (2,'DELIVERY'),
       (1,'USER');

INSERT INTO order_mst(account_id,order_from, order_to, city, gu, dong, subject, demand, order_dt)
VALUES (1,  'Seoul Gangnam Nonhyeon','Seoul Gangnam Nonhyeon2' , 'Seoul', 'Gangnam', 'Nonhyeon', 'Order Subject 1', 'Order Demand 1', '2022-03-01 12:00:00'),
       (1,  'Seoul Gangnam Nonhyeon','Seoul Gangnam Nonhyeon2' , 'Seoul', 'Gangnam', 'Cheongdam', 'Order Subject 2', 'Order Demand 2', '2022-03-02 13:00:00'),
       (1,  'Seoul Gangnam Nonhyeon','Seoul Gangnam Nonhyeon2' , 'Seoul', 'Jongno', 'Sajik', 'Order Subject 3', 'Order Demand 3', '2022-03-03 14:00:00');