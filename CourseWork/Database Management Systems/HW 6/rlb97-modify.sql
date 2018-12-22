--Randyll Bearer	rlb97@pitt.edu
--Question 2:
------------------------------------
--Question 2: Modify the Database
--A.
DELETE FROM CONTRACT
WHERE Store = 'Mercy Health Center' AND Manufacturer_Registration = (SELECT PCM_Registration FROM PHARMACEUTICAL_CO WHERE Name = 'Roche');

INSERT INTO CONTRACT (Store, Manufacturer_Registration, StartDate, EndDate)
VALUES('Mercy Health Center', (SELECT DISTINCT PCM_Registration FROM PHARMACEUTICAL_CO WHERE Name = 'Roche'), TO_DATE('01-01-2017','MM-DD-YYYY'), NULL);

COMMIT;


--B.
--Create a temporary view
CREATE MATERIALIZED VIEW TEMP(T_SSN,T_PCP_Lic_No, T_PCP_Lic_State ) AS
SELECT SSN, PCP_Lic_No, PCP_Lic_State
FROM PATIENT;

--Make Steward the PCP of all of Calvins's old patients
UPDATE PATIENT
SET PCP_Lic_No = (SELECT Doctor_Lic_No FROM DOCTOR WHERE FirstName = 'John' AND LastName = 'Steward'), PCP_Lic_State = (SELECT Doctor_Lic_State FROM DOCTOR WHERE FirstName = 'John' AND LastName = 'Steward')
WHERE PCP_Lic_No = (SELECT Doctor_Lic_No FROM DOCTOR WHERE FirstName ='Amber' AND LastName = 'Calvin') AND PCP_Lic_State = (SELECT Doctor_Lic_State FROM DOCTOR WHERE FirstName = 'Amber' AND LastName = 'Calvin');

--Make Calvin the PCP of all of Stweard's old patients
UPDATE PATIENT
SET PCP_Lic_No = (SELECT Doctor_Lic_No FROM DOCTOR WHERE FirstName ='Amber' AND LastName = 'Calvin'), PCP_Lic_State = (SELECT Doctor_Lic_State FROM DOCTOR WHERE FirstName = 'Amber' and LastName = 'Calvin')
WHERE SSN IN(SELECT T_SSN FROM TEMP WHERE T_PCP_Lic_No = (SELECT Doctor_Lic_No FROM DOCTOR WHERE FirstName = 'John' AND LastName = 'Steward'));

--Drop TEMP as it is no longer necessary
DROP MATERIALIZED VIEW TEMP;

COMMIT;


--C.
UPDATE SELL
SET Price = (Price * 0.80)
WHERE Store = 'Rite Aid';

COMMIT;