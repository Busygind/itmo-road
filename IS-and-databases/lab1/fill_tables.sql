insert into sizes (x, y, z, description) values (10000, 5000, 200, 'Огромный');
insert into sizes (x, y, z, description) values (100, 200, 40, 'Маленький');

insert into planets (name, weight) values ('Земля', 70000300);
insert into planets (name, weight) values ('Япет', 30000065);

insert into facilities (name, size_id, color, is_magic, planet_id) 
values ('Монолит', (select distinct (id) from sizes where (x=10000)), 'Черный', 'true', (select (id) from planets where (name='Япет')));

insert into emotions (name) values ('Ошеломление');
insert into emotions (name) values ('Грусть');
insert into emotions (name) values ('Ярость');
insert into emotions (name) values ('Удивление');

insert into symptoms (name) values ('Головокружение');
insert into symptoms (name) values ('Слезы');
insert into symptoms (name) values ('Покраснение лица');

insert into emotions_symptoms (emotion_id, symptom_id) values 
((select id from emotions where (name='Ошеломление')), (select id from symptoms where (name='Головокружение')));
insert into emotions_symptoms (emotion_id, symptom_id) values 
((select id from emotions where (name='Грусть')), (select id from symptoms where (name='Слезы')));

insert into persons (first_name, last_name, home_planet_id, birth_date, current_emotion_id)
values ('Дэвид', 'Боумен', (select id from planets where (name='Земля')), '04.03.1973', (select id from emotions where (name='Ошеломление')));

insert into aircrafts (name, speed, pilot_id) values
('Дискавери', 400, (select id from persons where (first_name='Дэвид' and last_name='Боумен'))); 
