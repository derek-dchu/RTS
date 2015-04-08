delete from RTS_TRANSACTION;
delete from RTS_CREDIT;
delete from RTS_TICKET;
delete from RTS_USER;

insert into RTS_USER(userid, password, email, enable) values (1, '123', '1@1.com', 1);
insert into RTS_USER(userid, password, email, enable) values (2, '234', '2@2.com', 1);

insert into RTS_CREDIT(cnum, cid, cdate, userid) values (100, 100, 100, 1);
insert into RTS_CREDIT(cnum, cid, cdate, userid) values (200, 200, 200, 1);

insert into RTS_TICKET(ticketid, total, sold, available, enable, price) values (1, 1, 1, 1, 1, 1);
insert into RTS_TICKET(ticketid, total, sold, available, enable, price) values (2, 1, 1, 1, 1, 2);

insert into RTS_TRANSACTION(tid, qt, userid, ticketid) values (1, 1, 2, 1);
insert into RTS_TRANSACTION(tid, qt, userid, ticketid) values (2, 1, 2, 2);

insert into RTS_CONFIRM(code, userid) values ('123confirmationcode', 1);

select * from RTS_USER;
select * from RTS_CREDIT;
select * from RTS_TICKET;
select * from RTS_TRANSACTION;