--Randyll Bearer		rlb97@pitt.edu
----------
--Question 1: Create a trigger
CREATE OR REPLACE TRIGGER Account_Trig_AddNumAccount
AFTER INSERT
ON Account
FOR EACH ROW
BEGIN
	UPDATE Customer
	SET num_accounts = num_accounts + 1
	WHERE SSN = :NEW.SSN;		
END;
/

---------------
--Question 2
INSERT INTO Account VALUES (125, 123456789, 1234, TO_DATE('02/17/17', 'MM/DD/YY'), 1500, NULL);

SELECT *
FROM Customer;

---------------
--Question 3
CREATE OR REPLACE TRIGGER Account_Trig_ReduceNumAccount
AFTER DELETE
ON Account
FOR EACH ROW
BEGIN
	UPDATE Customer
	SET num_accounts = num_accounts - 1
	WHERE SSN = :NEW.SSN;
END;
/

--------------
--Question 4
CREATE OR REPLACE TRIGGER Account_Trig_BalanceUpdate
AFTER UPDATE
On Account
FOR EACH ROW
WHEN(Balance < 0)
BEGIN
	UPDATE 
END;
/
