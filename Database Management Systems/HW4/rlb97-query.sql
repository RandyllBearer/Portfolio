--Randyll Bearer			rlb97@pitt.edu
--------------------------------------------
--List the contents of the tables as specified.
SELECT *
FROM DOCTOR;

SELECT *
FROM PATIENT;

SELECT *
FROM PHARMACEUTICAL_CO;

SELECT *
FROM PHARMACY;

SELECT *
FROM DRUG;

SELECT *
FROM PRESCRIPTION;

SELECT *
FROM SELL;

SELECT *
FROM CONTRACT;

---------------------
--Question 4: Queries
--A.
SELECT DRUG
FROM SELL
WHERE Store = 'Rite Aid' AND Price > 5.00;


--B.
SELECT SSN
FROM PATIENT
WHERE PCP_Lic_No IS NULL AND PCP_Lic_State IS NULL AND DateOfBirth <= '7-FEB-1937';


--C.
SELECT D.Name
FROM (DRUG D JOIN PHARMACEUTICAL_CO P ON D.Manufacturer_Registration = P.PCM_Registration)
WHERE P.Name = 'Johnson and Johnson'
ORDER BY D.Name ASC;


--D.
SELECT SSN, COUNT(DRUG) AS NumberPrescribed
FROM (PATIENT P LEFT JOIN PRESCRIPTION PR ON P.SSN = PR.Patient)
GROUP BY P.SSN;


--E.
SELECT P.SSN AS PatientSSN, D.LastName AS Doctor_LastName
FROM (PATIENT P JOIN DOCTOR D ON P.PCP_Lic_No = D.Doctor_Lic_No)
WHERE P.FirstName = D.FirstName;


--F.
SELECT P.Name, P.Address, COUNT(S.Drug) AS NumberDrugSold
FROM (PHARMACY P LEFT JOIN SELL S ON P.Name = S.Store)
GROUP BY P.Name, P.Address;


--G.
--Cannot use P1.Drug != as that allows for duplicates, need to use P1.Drug < P2.Drug
SELECT DISTINCT P1.Drug AS Drug1, P2.Drug AS Drug2 
FROM (PRESCRIPTION P1 JOIN PRESCRIPTION P2 ON P1.Patient = P2.Patient)
WHERE P1.Drug < P2.Drug;



COMMIT;
PURGE RECYCLEBIN;


