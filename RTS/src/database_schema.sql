create table rts_user (
	userid		number(10) primary key,
	firstname	varchar2(20),
	lastname	varchar2(20),
	email		varchar2(100),
	password	varchar2(100) not null,
	role		varchar2(20),
	enable		number(1)
);

create table rts_ticket (
	ticketid	number(10)		primary key,
	dep			varchar2(50),
	des			varchar2(50),
	dtime		varchar2(16),
	atime		varchar2(16),
	total		number(3),
	sold		number(3),
	available	number(3),
	price		varchar2(6),
	enable		number(1)
);

create table rts_transaction (
	tid			number(10)	primary key,
	status 		varchar2(1),
	userid		number(10) 	references rts_user(userid),
	ticketid	number(10)	references rts_ticket(ticketid),
	qt			number(5),
	ttime		varchar2(16)
);

create table rts_credit (
	cnum		number(16)	primary key,
	cid			number(3),
	cdate		number(4),
	userid		number(10)	references rts_user(userid)
);

create sequence seq_userid
 	start with     1000000000
	increment by   1
	nocache
	nocycle;
	
create sequence seq_ticketid
 	start with     1000000000
	increment by   1
	nocache
	nocycle;

create sequence seq_tid
 	start with     1000000000
	increment by   1
	nocache
	nocycle;