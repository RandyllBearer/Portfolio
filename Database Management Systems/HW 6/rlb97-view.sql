--Randyll Bearer	rlb97@pitt.edu
--Question 3-4
------------------------------------
--Question 3: Create Views
--A.
CREATE OR REPLACE VIEW DOCTOR_PRESCRIPTION(Doctor_Lic_No,Doctor_Lic_State,FirstName, LastName ,PrescriptionNum)
	AS SELECT D.Doctor_Lic_No, D.Doctor_Lic_State,D.FirstName,D.LastName, COUNT(*)
	FROM DOCTOR D JOIN PRESCRIPTION P ON D.Doctor_Lic_No = P.Doctor_Lic_No AND D.Doctor_Lic_State = P.Doctor_Lic_State
	GROUP BY D.Doctor_Lic_No, D.Doctor_Lic_State, D.FirstName, D.LastName;

COMMIT;
	
--B.
DROP MATERIALIZED VIEW DOCTOR_PRESCRIPTION_MV;

CREATE MATERIALIZED VIEW DOCTOR_PRESCRIPTION_MV(Doctor_Lic_No, Doctor_Lic_state, FirstName, LastName, PrescriptionNum)
	BUILD IMMEDIATE
	REFRESH COMPLETE ON DEMAND
	AS SELECT Doctor_Lic_No, Doctor_Lic_State, FirstName, LastName, PrescriptionNum
	FROM DOCTOR_PRESCRIPTION;
	
COMMIT;

--C.
CREATE OR REPLACE VIEW PHARMACEUTICAL_DRUG(PCM_Registration, PCM_Name, Drug)
	AS SELECT P.PCM_Registration, P.Name, D.Name
	FROM PHARMACEUTICAL_CO P JOIN DRUG D ON P.PCM_Registration = D.Manufacturer_Registration
	ORDER BY P.PCM_Registration;

COMMIT;

--D.
DROP MATERIALIZED VIEW PHARMACEUTICAL_DRUG_MV;

CREATE MATERIALIZED VIEW PHARMACEUTICAL_DRUG_MV(PCM_Registration, PCM_Name, Drug)
	BUILD IMMEDIATE
	REFRESH COMPLETE ON DEMAND
	AS SELECT P.PCM_Registration, P.Name, D.Name
	FROM PHARMACEUTICAL_CO P JOIN DRUG D ON P.PCM_Registration = D.Manufacturer_Registration
	ORDER BY P.PCM_Registration;


------------------------------------------------------------------------------------
--Question 4: Express Queries Using both regular and materialized views created in 3
--A.	 DOCTOR_PRESCRIPTION
SELECT LastName AS LastNameMaxPrescription
FROM DOCTOR_PRESCRIPTION
WHERE PrescriptionNum = (SELECT PrescriptionNum
						FROM DOCTOR_PRESCRIPTION
						ORDER BY PrescriptionNum DESC
						FETCH FIRST 1 ROWS ONLY
						);

--A_MV.  DOCTOR_PRESCRIPTION_MV
SELECT LastName AS LastNameMaxPrescription
FROM DOCTOR_PRESCRIPTION_MV
WHERE PrescriptionNum = (SELECT PrescriptionNum
						FROM DOCTOR_PRESCRIPTION_MV
						ORDER BY PrescriptionNum DESC
						FETCH FIRST 1 ROWS ONLY
						);

						
--B.	DOCTOR_PRESCRIPTION
SELECT DISTINCT P.PCM_Name AS PCMSellingMoreThanFiveDollars
FROM PHARMACEUTICAL_DRUG P JOIN SELL S ON P.Drug = S.Drug
WHERE S.Price > 5.00;

--B_MV.	DOCTOR_PRESCRIPTION_MV

SELECT DISTINCT P.PCM_Name AS PCMSellingMoreThanFiveDollars
FROM PHARMACEUTICAL_DRUG_MV P JOIN SELL S ON P.Drug = S.Drug
WHERE S.Price > 5.00;


--C.
--RUN A
SET TIMING ON;
SELECT LastName AS LastNameMaxPrescription
FROM DOCTOR_PRESCRIPTION
WHERE PrescriptionNum = (SELECT PrescriptionNum
						FROM DOCTOR_PRESCRIPTION
						ORDER BY PrescriptionNum DESC
						FETCH FIRST 1 ROWS ONLY
						);
SET TIMING OFF;

SET TIMING ON;
SELECT LastName AS LastNameMaxPrescription
FROM DOCTOR_PRESCRIPTION_MV
WHERE PrescriptionNum = (SELECT PrescriptionNum
						FROM DOCTOR_PRESCRIPTION_MV
						ORDER BY PrescriptionNum DESC
						FETCH FIRST 1 ROWS ONLY
						);
SET TIMING OFF;

--Compare results of A
--For the most part, the elapsed time of both the standard and materialized view for A was 00:00:00.00
--SOMETIMES, the standard view for A took 00:00:00.01, but its fair to say that there was no practical
--Difference between the two views for Part A.

--RUN B
SET TIMING ON;
SELECT DISTINCT P.PCM_Name AS PCMSellingMoreThanFiveDollars
FROM PHARMACEUTICAL_DRUG P JOIN SELL S ON P.Drug = S.Drug
WHERE S.Price > 5.00;
SET TIMING OFF;

SET TIMING ON;
SELECT DISTINCT P.PCM_Name AS PCMSellingMoreThanFiveDollars
FROM PHARMACEUTICAL_DRUG_MV P JOIN SELL S ON P.Drug = S.Drug
WHERE S.Price > 5.00;
SET TIMING OFF;

--Compare results of B
--Similar results to the standad/materialized views of part A. No practical difference between the standard and
--materialized views for part B. Both had an average elapsed time of 00:00:00.00.


--D. INSERT AND TEST
START doctor_prescription_inserts.sql
EXECUTE DBMS_MVIEW.REFRESH('DOCTOR_PRESCRIPTION_MV')
EXECUTE DBMS_MVIEW.REFRESH('PHARMACEUTICAL_DRUG_MV')


--RUN A
SET TIMING ON;
SELECT LastName AS LastNameMaxPrescription
FROM DOCTOR_PRESCRIPTION
WHERE PrescriptionNum = (SELECT PrescriptionNum
						FROM DOCTOR_PRESCRIPTION
						ORDER BY PrescriptionNum DESC
						FETCH FIRST 1 ROWS ONLY
						);
SET TIMING OFF;

SET TIMING ON;
SELECT LastName AS LastNameMaxPrescription
FROM DOCTOR_PRESCRIPTION_MV
WHERE PrescriptionNum = (SELECT PrescriptionNum
						FROM DOCTOR_PRESCRIPTION_MV
						ORDER BY PrescriptionNum DESC
						FETCH FIRST 1 ROWS ONLY
						);
SET TIMING OFF;

--Compare Results of A
--So the inserting of the new tuples takes several seconds, but the actual time elasped didn't change TOO much
--Standard View Time Elapsed Average: 00:00:00.02
--Materialized View Time Elapsed Average: 00:00:00.00

--RUN B
SET TIMING ON;
SELECT DISTINCT P.PCM_Name AS PCMSellingMoreThanFiveDollars
FROM PHARMACEUTICAL_DRUG P JOIN SELL S ON P.Drug = S.Drug
WHERE S.Price > 5.00;
SET TIMING OFF;

SET TIMING ON;
SELECT DISTINCT P.PCM_Name AS PCMSellingMoreThanFiveDollars
FROM PHARMACEUTICAL_DRUG_MV P JOIN SELL S ON P.Drug = S.Drug
WHERE S.Price > 5.00;
SET TIMING OFF;

--Compare Results of B
--Standard View Time Elapsed Average: 00:00:00.01
--Materialized View Time Elapsed Average: 00:00:00.00

--CONCLUSION: Materialized Views are faster than Standard Views, but we didn't record how long it took to update it.














