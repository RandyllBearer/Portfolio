--Randyll Bearer 		rlb97@pitt.edu

---------------------------------------------------
--Question 1

--Need to make sure none of our tables already exist, that would be awkward.
DROP TABLE CONTRACT;	
DROP TABLE SELL;
DROP TABLE PRESCRIPTION;
DROP TABLE DRUG;
DROP TABLE PHARMACEUTICAL_CO;
DROP TABLE PHARMACY;
DROP TABLE PATIENT;
DROP TABLE DOCTOR;

------------------------------------------
--Question 1: Initialization of all tables
CREATE TABLE DOCTOR(	
	Doctor_Lic_No number(10),
	Doctor_Lic_State varchar2(10),
	FirstName varchar2(15),
	MiddleName varchar2(15),
	LastName varchar2(20),
	Speciality varchar2(30),
	YearofExperience number(2),
	CONSTRAINT DOCTOR_PK
		PRIMARY KEY (Doctor_Lic_No, Doctor_Lic_State)
);

CREATE TABLE PATIENT(
	SSN number(9),
	FirstName varchar2(15),
	MiddleName varchar2(15),
	LastName varchar2(20),
	Address varchar2(64),
	DateOfBirth date,
	PCP_Lic_No number(10),
	PCP_Lic_State varchar2(10),
	CONSTRAINT PATIENT_PK
		PRIMARY KEY (SSN),
	CONSTRAINT PATIENT_FK
		FOREIGN KEY (PCP_Lic_No, PCP_Lic_State) REFERENCES DOCTOR(Doctor_Lic_No, Doctor_Lic_State)
);

CREATE TABLE PHARMACEUTICAL_CO(
	PCM_Registration varchar2(20),
	Name varchar2(20),
	Phone number(10),
	CONSTRAINT PHARMACEUTICAL_CO_PK
		PRIMARY KEY (PCM_REGISTRATION)
);

CREATE TABLE PHARMACY(
	Name varchar2(20),
	Address varchar2(64),
	Phone number(10),
	CONSTRAINT PHARMACY_PK
		PRIMARY KEY(Name),
	CONSTRAINT PHARMACY_UN1
		UNIQUE(Address),
	CONSTRAINT PHARMACY_UN2
		UNIQUE(Phone)
);

CREATE TABLE DRUG(
	Name varchar2(20),
	Formula varchar2(30),
	Manufacturer_Registration varchar2(20),
	CONSTRAINT DRUG_PK
		PRIMARY KEY(Name),
	CONSTRAINT DRUG_FK
		FOREIGN KEY (Manufacturer_Registration) REFERENCES PHARMACEUTICAL_CO(PCM_REGISTRATION)
);

CREATE TABLE PRESCRIPTION(
	Doctor_Lic_No number(10),
	Doctor_Lic_State varchar2(10),
	Patient number(9),
	Drug varchar2(20),
	Quantity number(2),
	P_Date date,
	CONSTRAINT PRESCRIPTION_PK
		PRIMARY KEY (Doctor_Lic_No, Doctor_Lic_State, Patient, Drug, P_Date),
	CONSTRAINT PRESCRIPTION_FK1
		FOREIGN KEY (Doctor_Lic_No, Doctor_Lic_State) REFERENCES DOCTOR(Doctor_Lic_No, Doctor_Lic_State),
	CONSTRAINT PRESCRIPTION_FK2
		FOREIGN KEY (Patient) REFERENCES PATIENT(SSN),
	CONSTRAINT PRESCRIPTION_FK3
		FOREIGN KEY (Drug) REFERENCES DRUG(Name)
);

CREATE TABLE SELL(
	Store varchar2(20),
	Drug varchar2(20),
	Price number(6,2),
	CONSTRAINT SELL_PK
		PRIMARY KEY (Store, Drug),
	CONSTRAINT SELL_FK1
		FOREIGN KEY (Store) REFERENCES PHARMACY(Name),
	CONSTRAINT SELL_FK2
		FOREIGN KEY (Drug) REFERENCES DRUG(Name)
);

CREATE TABLE CONTRACT(
	Store varchar2(20),
	Manufacturer_Registration varchar2(20),
	StartDate date,
	EndDate date,
	CONSTRAINT CONTRACT_PK
		PRIMARY KEY (Store, Manufacturer_Registration),
	CONSTRAINT CONTRACT_FK1
		FOREIGN KEY (Store) REFERENCES PHARMACY(Name),
	CONSTRAINT CONTRACT_FK2
		FOREIGN KEY (Manufacturer_Registration) REFERENCES PHARMACEUTICAL_CO(PCM_REGISTRATION)
);



----------------------------------------------------------------------------
--Question 2: Altering of all tables POSSIBLY NEED THIS ALL IN A TRANSACTION
--A. Specify Not Null Constraints
ALTER TABLE PATIENT
	MODIFY DateOfBirth date NOT NULL;

ALTER TABLE DRUG
	MODIFY Formula varchar2(30) NOT NULL;
	
ALTER TABLE PRESCRIPTION
	MODIFY Quantity number(2) NOT NULL;
	
ALTER TABLE SELL
	MODIFY Price number(6,2) NOT NULL;

ALTER TABLE CONTRACT
	MODIFY StartDate date NOT NULL;

COMMIT;	
	
--B. Add Refill to PRESCRIPTION, NOT NULL and >0, default = 1
ALTER TABLE PRESCRIPTION
	ADD Refill number(2) DEFAULT 1 NOT NULL;
	
ALTER TABLE PRESCRIPTION
	ADD CONSTRAINT PRESCRIPTION_IC_Refill
		CHECK(Refill > 0);

--C. Set default value of PRESCRIPTION(Quantity) to 1.		
ALTER TABLE PRESCRIPTION
	MODIFY Quantity number(2) DEFAULT 1;
		
--D. Add DRUG(SuggestMinPrice)
ALTER TABLE DRUG
	ADD SuggestMinPrice number(6,2);
--I don't think this is required, so leave it possible to be null



--------------------------------------------
--Question 3: Insert data using SQL INSERT
--DOCTOR
INSERT INTO DOCTOR(Doctor_Lic_No, Doctor_Lic_State, FirstName, MiddleName, LastName, Speciality, YearofExperience)
VALUES (1200, 'PA', 'John', 'K.', 'Steward', 'Internal Medicine', 10);

INSERT INTO DOCTOR
VALUES (1201, 'PA', 'Richard', 'T.', 'Calvin', 'Dermatology', 5);

INSERT INTO DOCTOR
VALUES (1202, 'OH', 'Alicia', 'A.', 'Johnson', 'Immunology', 20);

INSERT INTO DOCTOR
VALUES (1203, 'PA', 'Thomas', 'J.', 'Kurt', 'Opthalmology', 15);

INSERT INTO DOCTOR
VALUES (1204, 'PA', 'DANIEL', NULL, 'Robinson', 'Pediatrics', 9);

--PATIENT
INSERT INTO PATIENT(SSN, FirstName, MiddleName, LastName, Address, DateOfBirth, PCP_Lic_No, PCP_Lic_State)
VALUES (765765666, 'Michael', 'F', 'Cao', '320 Fifth Avenue', '01-JAN-1940', 1203, 'PA');

INSERT INTO PATIENT
VALUES (888424247, 'Tom', 'K', 'Louis', '310 Fifth Avenue', '05-FEB-1921', 1203, 'PA');

INSERT INTO PATIENT
VALUES (546746535, 'Bill', 'A', 'Newton', '330 Fifth Avenue', '11-DEC-1920', NULL, NULL);

INSERT INTO PATIENT
VALUES (246577767, 'Richard', 'F', 'Kates', '340 Fifth Avenue', '03-DEC-1928', 1201, 'PA');

INSERT INTO PATIENT
VALUES (246345645, 'Mary', 'M', 'Missy', '350 Fifth Avenue', '04-MAR-1988', NULL, NULL);

--PHARMACEUTICAL_CO
INSERT INTO PHARMACEUTICAL_CO(PCM_REGISTRATION, Name, Phone)
--Need to change Johnson and Johnson to avoid use of ampersand
VALUES('LIC1234', 'Johnson and Johnson', 4122217889);

INSERT INTO PHARMACEUTICAL_CO
VALUES('LIC2019', 'Pfizer', 6518900071);

INSERT INTO PHARMACEUTICAL_CO
VALUES('LIC1904', 'Roche', 4518764902);

--PHARMACY
INSERT INTO PHARMACY(Name, Address, Phone)
VALUES('Rite Aid', '318 5th Ave', 4124346603);

INSERT INTO PHARMACY
VALUES('Revco', '242 5th Ave', 4125662619);

INSERT INTO PHARMACY
VALUES('Mercy Health Center', '412 E.Commons', 4123234500);


--DRUG
INSERT INTO DRUG(Name, Formula, Manufacturer_Registration, SuggestMinPrice)
--Not provided with SuggestMinPrice
VALUES('Tylenol', 'Tylenol Formula', 'LIC1234', NULL);

INSERT INTO DRUG
VALUES('Aspirin', 'Aspirin Formula', 'LIC2019', NULL);

INSERT INTO DRUG
VALUES('AspirinB', 'AspirinB Formula', 'LIC2019', NULL);

INSERT INTO DRUG
VALUES('Motrin', 'Motrin Formula', 'LIC1904', NULL);

INSERT INTO DRUG
VALUES('Allegra', 'Allegra Formula', 'LIC2019', NULL);

--PRESCRIPTION
INSERT INTO PRESCRIPTION(Doctor_Lic_No, Doctor_Lic_State, Patient, Drug, Quantity, P_Date, Refill)
VALUES(1203, 'PA', 888424247, 'Tylenol', 20, '01-JAN-16', 3);

INSERT INTO PRESCRIPTION
VALUES(1200, 'PA', 765765666, 'Aspirin', 20, '03-JAN-16', 2);

INSERT INTO PRESCRIPTION
VALUES(1203, 'PA', 246577767, 'AspirinB', 20, '11-JAN-16', 1);

INSERT INTO PRESCRIPTION
VALUES(1202, 'OH', 546746535, 'Motrin', 10, '12-JAN-16', 1);

INSERT INTO PRESCRIPTION
VALUES(1201, 'PA', 765765666, 'Tylenol', 15, '14-JAN-16', 1);

INSERT INTO PRESCRIPTION
VALUES(1203, 'PA', 246577767, 'Allegra', 5, '23-JAN-16', 1);

INSERT INTO PRESCRIPTION
VALUES(1204, 'PA', 246345645, 'Motrin', 5, '13-JAN-16', 1);

INSERT INTO PRESCRIPTION
VALUES(1202, 'OH', 546746535, 'Aspirin', 10, '15-JAN-16', 1);

INSERT INTO PRESCRIPTION
VALUES(1200, 'PA', 246345645, 'Aspirin', 20, '09-JAN-16', 1);

INSERT INTO PRESCRIPTION
VALUES(1200, 'PA', 765765666, 'Aspirin', 10, '11-JAN-16', 1);

--SELL
INSERT INTO SELL(Store, Drug, Price)
VALUES('Rite Aid', 'Tylenol', 8.29);

INSERT INTO SELL
VALUES('Rite Aid', 'AspirinB', 3.00);

--CONTRACT
INSERT INTO CONTRACT(Store, Manufacturer_Registration, StartDate, EndDate)
VALUES('Rite Aid', 'LIC1234', '01-JAN-1999', '10-JAN-2018');

INSERT INTO CONTRACT
VALUES('Revco', 'LIC1904', '02-JUL-2010', '01-JAN-2020');


--House Cleaning
COMMIT;
PURGE RECYCLEBIN;