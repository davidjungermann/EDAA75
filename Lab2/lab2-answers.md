4. 
a) 
Theater has a natural key, in form of the unique name of the theater. 

Movie has a compound key, the combination of title and production_year, which is a natural key, since both of these
attributes exist in the real world. 

b) 
The name of the theater could be subject to change. For now I guess it works, since it wont change often, but you could also implement
a theater_id to keep track of theaters. 

c) 
No we don't have any weak sets in our database, since all entitites have a primary key which can be used to uniquely define them. 

d) 
Compound keys are not convenient to use, and therefore we can use the imdb_key as an invented key instead. 
Since names are not considered unique, we use the invented usernames as keys for a customer instead. 
A ticket has nothing but the ticket_id that describes, and since it's unique we can use it as an invented key.
The same goes for performance, which has an ID to define it. It's not specified wheter it's possible to show the same movie
at the same time in two different theaters, which could make it impossible to separate two different performances.  


6. 
theaters(_theater_name_, capacity)
movies(_imdb_key_, title, production_year, running_time)
performances(_performance_id_, start_time, date, /_ticket_id_/, /_imdb_key_/, /_theater_name_/)
tickets(_ticket_id_, /_user_name_/, /_performance_id_/)
customers(_user_name_, name, password)

7. 
The first option is to subtract the amount of tickets sold from the capacity in Theater by making a new attribute in performances.
You could call it remaining_seats, and it can calculate remaining_seats = (capacity - 1) every time a new ticket is sold.
This means implementing a counter that increments every time a new ticket is sold for each performance. 

The second option is to join theaters, performances and tickets, group them by performance id, and write out remaining_seats as (capacity - count). 

Something like this: 

SELECT theater_name, capacity, performance_id, count(), (capacity - count()) AS remaining_seats
FROM theaters
JOIN performances
USING(theater_name)
JOIN tickets
USING (performance_id)
GROUP BY performance_id

UTVECKLA LITE HÄR! 

8. 