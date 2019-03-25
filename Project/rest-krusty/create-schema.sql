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
cookie_id DEFAULT (lower(hex(randomblob(16)))),
cookie_name TEXT,
PRIMARY KEY (cookie_id)
);

CREATE TABLE pallets(
pallet_nbr DEFAULT (lower(hex(randomblob(16)))),
cookie_id  DEFAULT (lower(hex(randomblob(16)))),
order_nbr  DEFAULT (lower(hex(randomblob(16)))),
production_date DATE,
location TEXT,
delivery_time TIME, 
delivery_date DATE, 
blocked TEXT,
PRIMARY KEY (pallet_nbr),
FOREIGN KEY (cookie_id) REFERENCES cookies(cookie_id), 
FOREIGN KEY (order_nbr) REFERENCES orders(order_id)
);

CREATE TABLE orders(
order_nbr DEFAULT (lower(hex(randomblob(16)))),
customer_id DEFAULT (lower(hex(randomblob(16)))),
customer_name TEXT, 
address TEXT,
PRIMARY KEY (order_nbr),
FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE order_sizes(
cookie_id DEFAULT (lower(hex(randomblob(16)))),
order_nbr DEFAULT (lower(hex(randomblob(16)))),
pallet_amount INT,
FOREIGN KEY(cookie_id) REFERENCES cookies(cookie_id),
FOREIGN KEY(order_nbr) REFERENCES orders(order_nbr)
);

CREATE TABLE customers(
customer_id DEFAULT (lower(hex(randomblob(16)))),
customer_name TEXT,
address TEXT,
PRIMARY KEY (customer_id)
);

CREATE TABLE materials(
material_id DEFAULT (lower(hex(randomblob(16)))),
material_name TEXT,
amount INT, 
unit TEXT,
last_delivery_date DATE,
last_delivery_amount INT,
PRIMARY KEY (material_id)
);

CREATE TABLE ingredients(
cookie_id DEFAULT (lower(hex(randomblob(16)))),
material_id DEFAULT (lower(hex(randomblob(16)))),
amount INT,
FOREIGN KEY (cookie_id) REFERENCES cookies(cookie_id),
FOREIGN KEY (material_id) REFERENCES materials(material_id)
);




