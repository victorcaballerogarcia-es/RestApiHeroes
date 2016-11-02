INSERT INTO hero (id, name, pseudonym, publisher, first_appearance) values (1, 'Dick Grayson', 'Robin', 'DC', to_date('1940-04-01','YYYY-MM-DD'));
INSERT INTO hero (id, name, pseudonym, publisher, first_appearance) values (2, 'Bruce Wayne', 'Batman', 'DC', to_date('1939-05-01','YYYY-MM-DD'));

INSERT INTO hero_skill (id, hero_id, description) VALUES (1, 1, 'World-class acrobat and aerialist');
INSERT INTO hero_skill (id, hero_id, description) VALUES (2, 1, 'Highly skilled martial artist and hand-to-hand combatant');
INSERT INTO hero_skill (id, hero_id, description) VALUES (3, 1, 'Expert detective');
INSERT INTO hero_skill (id, hero_id, description) VALUES (4, 1, 'Utilizes high-tech equipment and weapons');
INSERT INTO hero_skill (id, hero_id, description) VALUES (5, 2, 'Genius-level intellect');
INSERT INTO hero_skill (id, hero_id, description) VALUES (6, 2, 'Peak human physical and mental conditioning');
INSERT INTO hero_skill (id, hero_id, description) VALUES (7, 2, 'Expert martial artist and hand-to-hand combatant');
INSERT INTO hero_skill (id, hero_id, description) VALUES (8, 2, 'Expert detective');
INSERT INTO hero_skill (id, hero_id, description) VALUES (9, 2, 'Utilizes high-tech equipment and weapons');

INSERT INTO hero_ally (id, hero_id) VALUES (1, 2);
INSERT INTO hero_ally (id, hero_id) VALUES (2, 1);