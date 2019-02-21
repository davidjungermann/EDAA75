PRAGMA foreign_keys = OFF;

DROP TABLE IF EXISTS theaters;
DROP TABLE IF EXISTS movies;
DROP TABLE IF EXISTS users;
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
  title  TEXT,
  year INT,
  imdb_key TEXT,
  running_time INT,
  PRIMARY KEY (imdb_key)
);

CREATE TABLE users(
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
  FOREIGN KEY (user_name) REFERENCES users(user_name),
  FOREIGN KEY (performance_id) REFERENCES performances(performance_id)
); 


