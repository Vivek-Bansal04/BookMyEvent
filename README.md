Agenda:
1. Implement Transaction Isolation for BMS

Feature:
- Person wants to book a ticket for a show
- TicketController {
  - bookTicket(
    - show_id
    - list<show_seat_id>
    - user_id
  - )
- }


- TicketService {
  - bookTicket(
    - show_id
    - list<show_seat_id>
    - user_id
  - ) 
- }

- How will bookTicket() in TicketService work?
  - update show_seats
  - set status = LOCKED
  - where show_seat_id in ()


- So in my createBooking method if there are concurrent requests I can face issue if some other thread locked the seats

- So a simple solution would be to add synchronized keyword in method but the problem is if lets say I got 2 requests
- 1. book seat show1 1,2,3
  2. book seat show2 4,5
- so it will block another request also even if it is trying to book other seats.
- so solution is to do synchronized on show 
- synchronized(show){
- rest of logic
- }
- but if requests are like
- Show 1 - 1,2,3
- Show 1 - 7,8 
- It will block 
- so solution is to get a lock over a particular seat(show seat). So for that take a lock over particular id not on object 
- as object can be changed meanwhile


- T1 - 1,4,2
- T2 - 3,2,5,4
- Lets say 1 thread locked 1st and 4th and second thread locked 3,2,5 so now thread 1 is waiting for 2 to be released and 
- T2 is waiting for 4 to be released so to avoid deadlock we will first sort the seats to be booked then will acquire lock.

- 