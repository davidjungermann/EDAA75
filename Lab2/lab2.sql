PRAGMA foreign_keys = OFF; 

DROP TABLE IF EXISTS theaters;
DROP TABLE IF EXISTS movies;
DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS performances;
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
  imdb_key TEXT,
  theater_name TEXT, 
  remaining_seats INT, 
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
       ('BigBoiBioDome', 1500),
       ('KonstBiografen', 50);

INSERT
INTO   movies(imdb_key, title, production_year, running_time)
VALUES ('tt0368226', 'The Room', 2003, 99),
       ('tt4954522', 'Raw', 2016, 99),
       ('tt3152098', 'Mega Shark vs. Mecha Shark', 2014, 85);

INSERT
INTO   performances(start_time, date, performance_id, imdb_key, theater_name)
VALUES ('19:30', '2019-04-20', '123','tt0368226', 'Spegeln'),
       ('21:40', '2019-04-20', '124','tt4954522', 'BigBoiBioDome'),
       ('15:00', '2019-04-20', '125','tt3152098', 'KonstBiografen');

INSERT 
INTO   customers(user_name, name, password)
VALUES ('ravedave', 'David Jungermann', 'hello'),  
       ('bdd', 'David Blomberg', 'hello123'),
       ('pettson','Alexander Pettersson', 'hello123'); 
 