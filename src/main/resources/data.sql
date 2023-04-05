INSERT INTO account(name, username, password, create_dt)
VALUES ('사용자','user1', '{bcrypt}$2a$10$U.ULcl4oix58W9e8.6UbVedHtbyPODQeiugNGDgcGv/3veesWLjR6','2022-03-01 12:00:00'),
       ('라이더','delivery1', '{bcrypt}$2a$10$U.ULcl4oix58W9e8.6UbVedHtbyPODQeiugNGDgcGv/3veesWLjR6','2022-03-01 12:00:00'),
       ('사용자2','user2', '{bcrypt}$2a$10$U.ULcl4oix58W9e8.6UbVedHtbyPODQeiugNGDgcGv/3veesWLjR6','2022-03-01 12:00:00');

INSERT INTO account_roles(account_id,role)
VALUES (1,'USER'),
       (2,'DELIVERY'),
       (1,'USER');

INSERT INTO order_mst(account_id,order_from, order_to, city, gu, dong, subject, demand, order_dt)
VALUES (1,  'Seoul Gangnam Nonhyeon','Seoul Gangnam Nonhyeon2' , 'Seoul', 'Gangnam', 'Nonhyeon', 'Order Subject 1', 'Order Demand 1', '2022-03-01 12:00:00'),
       (1,  'Seoul Gangnam Nonhyeon','Seoul Gangnam Nonhyeon2' , 'Seoul', 'Gangnam', 'Cheongdam', 'Order Subject 2', 'Order Demand 2', '2022-03-02 13:00:00'),
       (1,  'Seoul Gangnam Nonhyeon','Seoul Gangnam Nonhyeon2' , 'Seoul', 'Jongno', 'Sajik', 'Order Subject 3', 'Order Demand 3', '2022-03-03 14:00:00');