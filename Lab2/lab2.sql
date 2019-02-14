PRAGMA foreign_keys = OFF; 

DROP TABLE IF EXISTS theaters;
DROP TABLE IF EXISTS movies;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS performances;
DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS sales;

PRAGMA foreign_keys = ON;

CREATE TABLE theaters(
  theater_name TEXT,
  capacity  INT,
  PRIMARY KEY (theater_name)
);

CREATE TABLE movies(
  imdb_key TEXT,
  title  TEXT,
  production_year INT,
  running_time INT,
  PRIMARY KEY (imdb_key)
);

CREATE TABLE customers(
  user_name TEXT,
  name TEXT,
  password TEXT,
  PRIMARY KEY (user_name)
);

CREATE TABLE performances(
  performance_id TEXT,
  start_time TIME,
  date DATE, 
  remaining_seats INT, 
  imdb_key TEXT,
  theater_name TEXT,
  PRIMARY KEY(performance_id),
  FOREIGN KEY (imdb_key) REFERENCES movies(imdb_key),
  FOREIGN KEY (theater_name) REFERENCES theaters(theater_name)
);

CREATE TABLE tickets(
  ticket_id TEXT DEFAULT (lower(hex(randomblob(16)))),
  user_name TEXT,
  performance_id TEXT,
  PRIMARY KEY (ticket_id),
  FOREIGN KEY (user_name) REFERENCES customers(user_name),
  FOREIGN KEY (performance_id) REFERENCES performances(performance_id)
); 

INSERT
INTO   theaters(theater_name, capacity)
VALUES ('Spegeln', 150),
       ('Luna', 1500),
       ('Le arte de biographie', 50);

INSERT
INTO   movies(imdb_key, title, production_year, running_time)
VALUES ('tt0368226', 'The Room', 2003, 99),
       ('tt4954522', 'Raw', 2016, 99),
       ('tt3152098', 'Mega Shark vs. Mecha Shark', 2014, 85);

INSERT
INTO   performances(performance_id, start_time, date, imdb_key, theater_name)
VALUES ('123', '19:30', '2019-04-20','tt0368226', 'Spegeln'),
       ('124', '21:40', '2019-04-21','tt4954522', 'Luna'),
       ('125', '15:00', '2019-04-19', 'tt3152098', 'Le arte de biographie');

INSERT 
INTO   customers(user_name, name, password)
VALUES ('ravedave', 'David Jungermann', 'qwerty'),  
       ('bdd', 'David Blomberg', 'hello123'),
       ('pettsson','Alexander Pettersson', 'securepassword'); 

INSERT
INTO   tickets(ticket_id, user_name, performance_id)
VALUES (lower(hex(randomblob(16))), 'ravedave', '123'),
       (lower(hex(randomblob(16))), 'bdd', '124'),
       (lower(hex(randomblob(16))), 'pettsson', '125');  

CREATE TABLE sales AS
  SELECT performance_id, capacity AS remaining_seats
  FROM theaters
  JOIN performances
  USING(theater_name)
  GROUP BY performance_id
  ;

UPDATE sales 
SET remaining_seats = (remaining_seats - 1)
WHERE performance_id LIKE "123%";

UPDATE sales 
SET remaining_seats = (remaining_seats - 1)
WHERE performance_id LIKE "124%";

UPDATE sales 
SET remaining_seats = (remaining_seats - 1)
WHERE performance_id LIKE "125%";





