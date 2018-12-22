--Randyll Bearer: Recitation handout 5
--1.
SELECT SID, Course_No
FROM COURSE_TAKEN
WHERE Term = 'Fall 10' AND Grade IS NULL;

--2.
SELECT SID, AVG(grade)
FROM COURSE_TAKEN
GROUP BY SID
HAVING AVG(Grade) > 3.7
ORDER BY AVG(Grade) DESC;

--3.
SELECT SID, Count(Course_No)
FROM Course_Taken
GROUP BY SID;

--4.
insert into STUDENT values (130, 'Peter', 1, 'CS', '123456789');

SELECT S.SID, Count(CT.Course_No)
FROM STUDENT S LEFT JOIN COURSE_TAKEN CT ON S.SID = CT.SID
GROUP BY S.SID;
