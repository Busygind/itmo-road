CREATE TYPE size_description AS ENUM (
 'Огромный',
 'Большой',
 'Средний',
 'Маленький',
 'Крошечный',
 'Не определен'
);

CREATE TABLE if not exists sizes
(
    id   UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    x INTEGER NOT NULL CHECK (x > 0),
	y INTEGER NOT NULL CHECK (y > 0),
	z INTEGER NOT NULL CHECK (z > 0),
	description size_description
);

CREATE TABLE if not exists planets
(
    id   UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
	weight DECIMAL CHECK (weight > 0)
);

CREATE TABLE if not exists emotions
(
	id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
	name VARCHAR(255) UNIQUE
);

CREATE TABLE if not exists symptoms
(
	id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
	name VARCHAR(255) UNIQUE
);

CREATE TABLE if not exists facilities
(
    id   UUID DEFAULT gen_random_uuid() PRIMARY KEY,
	name VARCHAR(255),
    size_id UUID REFERENCES sizes(id) ON DELETE RESTRICT,
	color VARCHAR(255),
	is_magic BOOLEAN,
	planet_id UUID REFERENCES planets(id) ON DELETE RESTRICT
);

CREATE TABLE if not exists persons
(
    id   UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    first_name VARCHAR(255),
	last_name VARCHAR(255),
	home_planet_id UUID REFERENCES planets(id) ON DELETE RESTRICT,
	birth_date TIMESTAMP,
	current_emotion_id UUID REFERENCES emotions(id) ON DELETE SET NULL
);

CREATE TABLE if not exists aircrafts
(
    id   UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
	speed INTEGER,
	pilot_id UUID
);

CREATE TABLE if not exists emotions_symptoms (
	emotion_id UUID NOT NULL REFERENCES emotions ON DELETE CASCADE,
	symptom_id UUID NOT NULL REFERENCES symptoms ON DELETE CASCADE,
	PRIMARY KEY (emotion_id, symptom_id)
);

ALTER TABLE aircrafts
    ADD CONSTRAINT FK_aircraft_pilot
        FOREIGN KEY (pilot_id) REFERENCES persons(id)
		ON DELETE SET NULL;

