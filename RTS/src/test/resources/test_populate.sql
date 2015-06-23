DELETE FROM rts_confirm;
DELETE FROM rts_transaction;
DELETE FROM rts_credit;
DELETE FROM rts_ticket;
DELETE FROM rts_user;

INSERT INTO rts_user(userid, password, email, enable) values (1, '123', '1@1.com', 1);
INSERT INTO rts_user(userid, password, email, enable) values (2, '234', '2@2.com', 1);

INSERT INTO rts_credit(cnum, cvc, cdate, userid) values (100, 100, 100, 1);
INSERT INTO rts_credit(cnum, cvc, cdate, userid) values (200, 200, 200, 1);

INSERT INTO rts_ticket(ticketid, total, sold, available, enable, price) values (1, 1, 1, 1, 1, 1);
INSERT INTO rts_ticket(ticketid, total, sold, available, enable, price) values (2, 1, 1, 1, 1, 2);

INSERT INTO rts_transaction(tid, qt, userid, ticketid) values (1, 1, 2, 1);
INSERT INTO rts_transaction(tid, qt, userid, ticketid) values (2, 1, 2, 2);

INSERT INTO rts_confirm(code, userid) values ('123confirmationcode', 1);

-- select * FROM RTS_USER;
-- select * FROM RTS_CREDIT;
-- select * FROM RTS_TICKET;
-- select * FROM RTS_TRANSACTION;