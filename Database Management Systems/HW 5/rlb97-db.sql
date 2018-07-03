--Randyll Bearer 		rlb97@pitt.edu
--Questions 1-2
---------------------------------------------------
--Question 1: Recreate the Pharmaceutical database
start hw5-db-create.sql

--Question 2: ALTER TABLE
--A.
ALTER TABLE DOCTOR
	ADD CONSTRAINT DOCTOR_IC_ExperienceAmount
		CHECK(YearofExperience >= 0 AND YearofExperience <= 100);

--B.
ALTER TABLE PRESCRIPTION
	ADD CONSTRAINT PRESCRIPTION_IC_PosQuantity
		CHECK(Quantity >= 0);

--C.
ALTER TABLE SELL
	ADD CONSTRAINT SELL_IC_PositivePrice
		CHECK(Price >= 0.00);

COMMIT;
PURGE RECYCLEBIN;
