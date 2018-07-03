--Randyll Bearer 		rlb97@pitt.edu
--Questions 3-7
---------------------------------------------------
--Question 3: Populate the database
start hw5-db-insert.sql
COMMIT;


--------------
--Question 4:
--A. Find doctors of all patients born before 1980 that have been issued a prescription in the past years. (No amount of years given) 
SELECT DISTINCT PCP_Lic_No, PCP_Lic_State
FROM PATIENT P JOIN PRESCRIPTION PR ON (P.SSN = PR.Patient);

--B. Find # of prescriptions and average quantity prescribed in 2016
SELECT Doctor_Lic_No, Doctor_Lic_State, COUNT(*) AS NumPrescriptions, AVG(Quantity) as AVG_Quantity
FROM PRESCRIPTION
WHERE prescDate  BETWEEN '01-JAN-2016' AND '31-DEC-2016'
GROUP BY Doctor_Lic_No, Doctor_Lic_State;

--C. Find the last names of doctors that have the top three highest number of prescriptions
SELECT D.LastName, COUNT(*) AS NumPrescriptions
FROM DOCTOR D JOIN PRESCRIPTION P ON (D.Doctor_Lic_No = P.Doctor_Lic_No AND D.Doctor_Lic_State = P.Doctor_Lic_State)
GROUP BY D.LastName
ORDER BY COUNT(*) DESC
FETCH FIRST 3 ROWS ONLY;


-----------------------------
--Question 5: NESTED QUERIES
--A.
SELECT D.LastName, 		(SELECT MAX(P.Quantity)
						FROM PRESCRIPTION P
						WHERE (D.Doctor_Lic_No = P.Doctor_Lic_No AND D.Doctor_Lic_State = P.Doctor_Lic_State)) AS MaxQuantity
FROM DOCTOR D
ORDER BY MaxQuantity DESC;

--B.
SELECT DISTINCT D.LastName
FROM DOCTOR D
WHERE D.Doctor_Lic_No NOT IN 	(SELECT P.Doctor_Lic_No
								FROM PRESCRIPTION P
								WHERE P.Drug = 'Aspirin'
								);

--C.
--I know this query is a little redundant, but it does use a nested query
SELECT DISTINCT LastName
FROM DOCTOR
WHERE LastName IN 	(SELECT D.LastName
					FROM DOCTOR D JOIN PATIENT P ON (D.Doctor_Lic_No = P.PCP_Lic_No AND D.Doctor_Lic_State = P.PCP_Lic_State)
					WHERE D.LastName = P.LastName
					);

					
---------------------------
--Question 6: TOP-K QUERIES
--A.
SELECT *
FROM PRESCRIPTION
ORDER BY Quantity DESC
FETCH FIRST 5 ROWS ONLY;

--B.
SELECT *
FROM PRESCRIPTION
ORDER BY Quantity DESC
OFFSET 6 ROWS
FETCH NEXT 5 ROWS ONLY;

--C.
SELECT *
FROM DOCTOR
ORDER BY YearOfExperience DESC
FETCH FIRST 3 ROWS ONLY;


-------------------------------------------------
--Question 7: NESTED QUERIES WITH SET COMPARISON
--A.
--This is finding the drugs that are not being sold
SELECT DISTINCT D.Name
FROM DRUG D
WHERE NOT EXISTS 	(SELECT S.Drug
					FROM SELL S
					WHERE S.Drug = D.Name);
				
--B.
--So this is finding the companies which have not been contracted
SELECT DISTINCT PH.Name
FROM PHARMACEUTICAL_CO PH
WHERE NOT EXISTS	(SELECT C.Manufacturer_Registration
					FROM CONTRACT C
					WHERE C.Manufacturer_Registration = PH.PCM_Registration
					);

COMMIT;
PURGE RECYCLEBIN;