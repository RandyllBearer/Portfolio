--Randyll Bearer	rlb97@pitt.edu
--Question 5
------------------------------------
--Question 5: Create Triggers
--A.
CREATE OR REPLACE TRIGGER OnDeleteDoctor
BEFORE DELETE
ON DOCTOR
FOR EACH ROW
BEGIN

	UPDATE PATIENT
	SET PCP_Lic_No = -1, PCP_Lic_State = 'XX'
	WHERE PCP_Lic_No = :NEW.Doctor_Lic_No AND PCP_Lic_State = :NEW.Doctor_Lic_State;
	
	UPDATE PRESCRIPTION
	SET Doctor_Lic_No = -1, Doctor_Lic_State = 'XX'
	WHERE Doctor_Lic_No = :NEW.Doctor_Lic_No AND Doctor_Lic_State = :NEW.Doctor_Lic_State;
	
END;
/

--This solution DOES NOT WORK, the trigger compiles fine but I keep getting referential constraint errors, will need to ask TA

--B.
DROP TABLE SUSPICIOUS_PRESCRIPTION;

create table SUSPICIOUS_PRESCRIPTION (
Doctor_Lic_No number(10),
Doctor_Lic_State varchar2(10),
Patient number(9),
Drug varchar2(20),
Quantity number(2) not null,
prescDate date,
constraint pk_FishyPrescription primary key( Doctor_Lic_No, Doctor_Lic_State, Patient, Drug, prescDate)
	DEFERRABLE INITIALLY IMMEDIATE
);

CREATE OR REPLACE TRIGGER OnInsertPrescription
AFTER INSERT
ON PRESCRIPTION
FOR EACH ROW
	When(NEW.Quantity > 50)
BEGIN
	INSERT INTO SUSPICIOUS_PRESCRIPTION VALUES(:NEW.Doctor_Lic_No, :NEW.Doctor_Lic_State, :NEW.Patient, :NEW.Drug, :NEW.Quantity, :NEW.prescDate);
END;
/