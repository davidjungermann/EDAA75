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
cookie_id INT,
cookie_name TEXT,
PRIMARY KEY (cookie_id)
);

CREATE TABLE pallets(
pallet_nbr DEFAULT (lower(hex(randomblob(16)))),
cookie_id  INT, 
order_nbr  INT,
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
order_nbr INT,
delivery_time TIME,
customer_id DEFAULT (lower(hex(randomblob(16)))),
PRIMARY KEY (order_nbr),
FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE order_sizes(
cookie_id INT,
order_nbr INT,
pallet_amount INT CHECK(pallet_amount >= 1),
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
material_id INT,
material_name TEXT,
material_amount INT CHECK(material_amount >= 0), 
unit TEXT,
last_delivery_date DATE,
last_delivery_amount INT,
PRIMARY KEY (material_id)
);

CREATE TABLE ingredients(
cookie_id INT,
material_id INT,
ingredient_amount INT CHECK(ingredient_amount >= 0),
FOREIGN KEY (cookie_id) REFERENCES cookies(cookie_id),
FOREIGN KEY (material_id) REFERENCES materials(material_id),
PRIMARY KEY(material_id, cookie_id)
);





