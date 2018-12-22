--this is to drop the table if it has existed
drop table bank cascade constraints;

--create table bank
create table bank(
code char(4) not null,
name varchar2(20),
addr varchar2(30),
primary key(code) deferrable,
unique(name) deferrable
);

--table customer
drop table customer cascade constraints;
create table customer(
ssn char(9) not null,
name varchar2(30),
phone varchar2(15),
addr varchar2(30),
num_accounts number(2,0),
primary key (ssn) deferrable
);

--table account
drop table account cascade constraints;
create table account(
acc_no varchar2(15) not null, 
ssn  char(9),
code char(4),
open_date date,
balance number(15,2),
close_date date,
primary key(acc_no) deferrable,
foreign key (ssn) references customer(ssn) deferrable,
foreign key(code) references bank(code) deferrable,
check((close_date is null) or (close_date > open_date)) deferrable
);
 
--table loan
drop table loan cascade constraints;
create table loan(
ssn     char(9) not null,
code char(4) not null,
open_date date not null,
amount number(15,2),
close_date date,
primary key(ssn,code, open_date) deferrable,
foreign key (ssn) references customer(ssn) deferrable,
foreign key (code) references bank(code) deferrable
);


--insert data
insert into bank values('1234','Pitt Bank', '111 University St');

insert into customer values('123456789', 'John', '555-535-5263','100 University St',1);
insert into customer values('111222333', 'Mary', '555-535-3333','20 University St',1);

insert into account values('123','123456789', '1234', '10-sep-08', 500, null);  
insert into account values('124','111222333','1234','10-oct-09', 1000, null);

insert into loan values('111222333', '1234', '15-sep-2010', 100, null);

commit;
