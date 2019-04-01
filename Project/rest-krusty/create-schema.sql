PRAGMA foreign_keys = OFF; 

DROP TABLE IF EXISTS cookies; 
DROP TABLE IF EXISTS pallets;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS order_sizes;
DROP TABLE IF EXISTS ingredients;
DROP TABLE IF EXISTS materials;

PRAGMA foreign_keys = ON; 

CREATE TABLE cookies(
cookie_name TEXT,
PRIMARY KEY (cookie_name)
);

CREATE TABLE pallets(
pallet_id DEFAULT (lower(hex(randomblob(16)))),
cookie_name TEXT,
order_nbr  INT,
production_date DATE,
location TEXT,
delivery_time TIME, 
delivery_date DATE, 
blocked INT DEFAULT 0,
PRIMARY KEY (pallet_id),
FOREIGN KEY (order_nbr) REFERENCES orders(order_nbr),
FOREIGN KEY (cookie_name) REFERENCES cookies(cookie_name) 
);

CREATE TABLE orders(
order_nbr INT,
delivery_time TIME,
customer_id DEFAULT (lower(hex(randomblob(16)))),
PRIMARY KEY (order_nbr),
FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE order_sizes(
cookie_name TEXT,
order_nbr INT,
pallet_amount INT CHECK(pallet_amount >= 1),
FOREIGN KEY(cookie_name) REFERENCES cookies(cookie_name),
FOREIGN KEY(order_nbr) REFERENCES orders(order_nbr)
);

CREATE TABLE customers(
customer_id DEFAULT (lower(hex(randomblob(16)))),
customer_name TEXT,
address TEXT,
PRIMARY KEY (customer_id)
);

CREATE TABLE materials(
material_name TEXT,
material_amount INT CHECK(material_amount >= 0), 
unit TEXT,
last_delivery_date DATE,
last_delivery_amount INT,
PRIMARY KEY (material_name)
);

CREATE TABLE ingredients(
cookie_name TEXT,
material_name TEXT,
ingredient_amount INT,
FOREIGN KEY (cookie_name) REFERENCES cookies(cookie_name),
FOREIGN KEY (material_name) REFERENCES materials(material_name),
PRIMARY KEY(material_name, cookie_name)
);







