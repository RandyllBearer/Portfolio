--create the table student
drop table student cascade constraints;
create table  student (
	sid	varchar2(5) not null,
	name	varchar2(15) not null,
	class	number(2),
	major	varchar2(10),
	ssn 	varchar2(16) not null, 
	constraint pk_student primary key(sid));


--create table student_dir

drop table student_dir cascade constraints;
create table student_dir (
	sid 	varchar2(5) not null,
	address varchar2(30),
	phone	varchar2(15),
	constraint pk_student_dir primary key(sid),
	constraint fk_sd_student foreign key (sid) references student(SID));

--create table course
drop table course cascade constraints;
create table course (
	course_no	varchar2(10) not null,
	name		varchar2(50),
	course_level		varchar2(10),
	constraint pk_course primary key(course_no));

--create table course_taken

drop table course_taken cascade constraints;
create table course_taken (
	course_no	varchar2(10) not null,
	sid		varchar2(5) not null,
	term		varchar2(15),
	grade		number(3,2),
	constraint pk_course_taken primary key(course_no, sid, term),
	constraint fk_ct_course foreign key(course_no) references course(course_no),
	constraint fk_ct_student foreign key(sid) references student(sid));

--insert data into student table 
insert into student(sid, name, class, major, ssn)
	values('123', 'John', 3, 'CS', '123-456-1987');

insert into student(sid, name, class, major, ssn)
	values('124', 'Mary', 3, 'CS', '276-287-1091');

insert into student(sid, name, class, major, ssn)
	values('126', 'Sam', 2, 'CS', '987-283-0987');

insert into student(sid, name, class, major, ssn)
	values('129', 'Julie', 2, 'Math', '123-098-2091');


--insert data into student_dir table

insert into student_dir(sid, address, phone)
	values('123', '333 Library St', '555-535-5263');

insert into student_dir(sid, address, phone)
	values('124', '219 Library St', '555-963-9653');

insert into student_dir(sid, address, phone)
	values('129', '555 Library St', '555-123-4567');

--insert data into course

insert into course(course_no, name, course_level)
	values('CS1520', 'Web Applications', 'UGrad');

insert into course(course_no, name, course_level)
	values('CS1555', 'Database Management Systems', 'UGrad');

insert into course(course_no, name, course_level)
	values('CS1550', 'Operating Systems', 'UGrad');

insert into course(course_no, name, course_level)
	values('CS2550', 'Database Management Systems', 'Grad');

insert into course(course_no, name, course_level)
	values('CS1655', 'Secure Data Management and Web Applications', 'UGrad');


--insert into course_taken
insert into course_taken(course_no, sid, term, grade)
	values('CS1520', '123', 'Fall 10', 3.75);

insert into course_taken(course_no, sid, term, grade)
	values('CS1520', '124', 'Fall 10', 4);

insert into course_taken(course_no, sid, term, grade)
	values('CS1520', '126', 'Fall 10', 3);

insert into course_taken(course_no, sid, term, grade)
	values('CS1555', '123', 'Fall 10', 4);

insert into course_taken(course_no, sid, term, grade)
	values('CS1555', '124', 'Fall 10', null);

insert into course_taken(course_no, sid, term, grade)
	values('CS1550', '123', 'Spring 11', null );

insert into course_taken(course_no, sid, term, grade)
	values('CS1550', '124', 'Spring 11', null );

insert into course_taken(course_no, sid, term, grade)
	values('CS1550', '126', 'Spring 11', null );

insert into course_taken(course_no, sid, term, grade)
	values('CS1550', '129', 'Spring 11', null );

insert into course_taken(course_no, sid, term, grade)
	values('CS2550', '124', 'Spring 11', null );

insert into course_taken(course_no, sid, term, grade)
	values('CS1520', '126', 'Spring 11', null );

commit

--display a message
prompt finish creating tables and inserting data;






	




