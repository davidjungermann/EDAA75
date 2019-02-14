4. 
a) 
Theater has a natural key, in form of the unique name of the theater. 

Movie has a compound key, the combination of title and production_year, which is a natural key, since both of these
attributes exist in the real world. 

b) 
The name of the theater could be subject to change. For now I guess it works, since it wont change often, but you could also implement a theater_id to keep track of theaters. 

c) 
Weak entity sets are entity sets without natural keys, which are: Ticket, Performance and Customer. These entity sets have invented keys that define them instead, which makes them normal entity sets. 

Sales is weak however, since it only has a foreign key. 

d) 
Compound keys are not convenient to use, and therefore we can use the imdb_key as an invented key instead. 
Since names are not considered unique, we use the invented usernames as keys for a customer instead. 
A ticket has nothing but the ticket_id that describes, and since it's unique we can use it as an invented key.
The same goes for performance, which has an ID to define it. It's not specified wheter it's possible to show the same movie
at the same time in two different theaters, which could make it impossible to separate two different performances.  


6. 
theaters(_theater_name_, capacity)
movies(_imdb_key_, title, production_year, running_time)
performances(_performance_id_, start_time, date, /_imdb_key_/, /_theater_name_/)
tickets(_ticket_id_, /_user_name_/, /_performance_id_/)
customers(_user_name_, name, password)
sales(remaining_seats, /_performance_id_/)

7. 
The first way of doing it is implementing a new attribute in performances called remaining_seats. 
Another way of doing this is by making a new table, sales, which keeps track of the number of seats remaining in each performance. 

Using any of these, you can either lower the value of remaining_seats every time a new ticket is purchased, meaning each time
a new row is added to tickets, or you can search up the amount of tickets left by joining performances and theaters. 

The first solution is nicer and works better,  but is harder to implement, since you have to use triggers. The second method is easier, but requires explicit UPDATE-calls to remaining_seats value each time a new ticket is added. 
SELECT performance_id, count(), (capacity - count()) AS remaining_seats
FROM theaters
JOIN performances
USING(theater_name)
GROUP BY performance_id

CREATE TRIGGER remaining_seats 
   AFTER INSERT
   ON tickets
   WHEN  new.performance_id = ((SELECT performance_id
                           FROM tickets))
BEGIN
  UPDATE sales
  SET remaining_seats = (remaining_seats - 1)
END;


CREATE TRIGGER update_remaining_seats INSERT on tickets
BEGIN 
  UPDATE sales SET remaining_seats = (remaining_seats - 1) WHERE new.tickets.performance_id = sales.performance_id
END;

CREATE TRIGGER remaining_seats BEFORE INSERT ON tickets
BEGIN
 SELECT
 CASE
 WHEN (remaining_seats < 0) THEN
 RAISE (
 ABORT,
 'The tickets are out for this performance'
 )
 END;
END;

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

