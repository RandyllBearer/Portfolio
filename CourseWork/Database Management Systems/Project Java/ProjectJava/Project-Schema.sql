--Randyll Bearer & Zachary White
--CS 1555 Project
--Milestone 1: Database Schema

-----------------------------
--Drop all tables to be safe
----------------------------
DROP TABLE MUTUALFUND CASCADE CONSTRAINTS;
DROP TABLE CLOSINGPRICE CASCADE CONSTRAINTS;
DROP TABLE CUSTOMER CASCADE CONSTRAINTS;
DROP TABLE ADMINISTRATOR CASCADE CONSTRAINTS;
DROP TABLE ALLOCATION CASCADE CONSTRAINTS;
DROP TABLE PREFERS CASCADE CONSTRAINTS;
DROP TABLE TRXLOG CASCADE CONSTRAINTS;
DROP TABLE OWNS CASCADE CONSTRAINTS;
DROP TABLE MUTUALDATE CASCADE CONSTRAINTS;

----------------
--Create Tables
----------------
--MUTUALFUND
CREATE TABLE MUTUALFUND(
symbol 		varchar2(20),
name 		varchar2(30) CONSTRAINT nn_mutualfund_name NOT NULL,
description	varchar2(100),
category	varchar2(10),
c_date		date CONSTRAINT nn_mutualfund_c_date NOT NULL,
CONSTRAINT pk_mutualfund PRIMARY KEY(symbol)
	DEFERRABLE INITIALLY IMMEDIATE,
CONSTRAINT ic_mutualfund_category CHECK
	(category in ('fixed', 'bonds', 'stocks', 'mixed'))
);

--CLOSINGPRICE
CREATE TABLE CLOSINGPRICE(
symbol	varchar2(20),
price	float CONSTRAINT nn_closingprice_price NOT NULL,
p_date	date,
CONSTRAINT pk_closingprice PRIMARY KEY(symbol, p_date)
	DEFERRABLE INITIALLY IMMEDIATE,
CONSTRAINT fk_closingprice_mutualfund FOREIGN KEY(symbol)
	REFERENCES MUTUALFUND(symbol)
	DEFERRABLE INITIALLY IMMEDIATE
);

--CUSTOMER
CREATE TABLE CUSTOMER( 
login		varchar2(10),
name		varchar2(20) CONSTRAINT nn_customer_name NOT NULL,
email		varchar2(30) CONSTRAINT nn_customer_email NOT NULL,
address		varchar2(30) CONSTRAINT nn_customer_address NOT NULL,
password	varchar2(10) CONSTRAINT nn_customer_password NOT NULL,
balance		float,
CONSTRAINT pk_customer PRIMARY KEY(login)
	DEFERRABLE INITIALLY IMMEDIATE,
CONSTRAINT ic_customer_balance CHECK
	(balance >= 0)
);

--ADMINISTRATOR
CREATE TABLE ADMINISTRATOR(
login		varchar2(10),
name		varchar2(20) CONSTRAINT nn_administrator_name NOT NULL,
email		varchar2(30) CONSTRAINT nn_administrator_email NOT NULL,
address		varchar2(30) CONSTRAINT nn_administrator_address NOT NULL,
password	varchar2(10) CONSTRAINT nn_administrator_password NOT NULL,
CONSTRAINT pk_administrator PRIMARY KEY(login)
	DEFERRABLE INITIALLY IMMEDIATE
);

--ALLOCATION
CREATE TABLE ALLOCATION(
allocation_no	int,
login			varchar2(10),
p_date			date CONSTRAINT nn_allocation_p_date NOT NULL,
CONSTRAINT pk_allocation PRIMARY KEY(allocation_no)
	DEFERRABLE INITIALLY IMMEDIATE,
CONSTRAINT fk_allocation_customer FOREIGN KEY(login)
	REFERENCES CUSTOMER(login)
	DEFERRABLE INITIALLY IMMEDIATE
);

--PREFERS
CREATE TABLE PREFERS(
allocation_no	int,
symbol			varchar2(20),
percentage		float,
CONSTRAINT pk_prefers PRIMARY KEY(allocation_no, symbol)
	DEFERRABLE INITIALLY IMMEDIATE,
CONSTRAINT fk_prefers_allocation FOREIGN KEY(allocation_no)
	REFERENCES ALLOCATION(allocation_no)
	DEFERRABLE INITIALLY IMMEDIATE,
CONSTRAINT fk_prefers_mutualfund FOREIGN KEY(symbol)
	REFERENCES MUTUALFUND(symbol)
	DEFERRABLE INITIALLY IMMEDIATE
);

--TRXLOG
CREATE TABLE TRXLOG(
trans_id	int,
login		varchar2(10),
symbol		varchar2(20),
t_date		date CONSTRAINT nn_trxlog_t_date NOT NULL,
action		varchar2(10),
num_shares	int,
price		float,
amount		float CONSTRAINT nn_trxlog_amount NOT NULL,
CONSTRAINT pk_trxlog PRIMARY KEY(trans_id)
	DEFERRABLE INITIALLY IMMEDIATE,
CONSTRAINT fk_trxlog_customer FOREIGN KEY(login)
	REFERENCES CUSTOMER(login)
	DEFERRABLE INITIALLY IMMEDIATE,
CONSTRAINT fk_trxlog_mutualfund FOREIGN KEY(symbol)
	REFERENCES MUTUALFUND(symbol)
	DEFERRABLE INITIALLY IMMEDIATE,
CONSTRAINT ic_trxlog_action CHECK
	(action in ('deposit', 'sell', 'buy'))
);
	
--OWNS
CREATE TABLE OWNS(
login	varchar2(10),
symbol	varchar2(20),
shares	int,
CONSTRAINT pk_owns PRIMARY KEY(login, symbol)
	DEFERRABLE INITIALLY IMMEDIATE,
CONSTRAINT fk_owns_customer FOREIGN KEY(login)
	REFERENCES CUSTOMER(login)
	DEFERRABLE INITIALLY IMMEDIATE,
CONSTRAINT fk_owns_mutualfund FOREIGN KEY(symbol)
	REFERENCES MUTUALFUND(symbol)
	DEFERRABLE INITIALLY IMMEDIATE
);

--MUTUALDATE
CREATE TABLE MUTUALDATE(
c_date	date,
CONSTRAINT pk_mutualdate PRIMARY KEY(c_date)
	DEFERRABLE INITIALLY IMMEDIATE
);

---------------
--PROCEDURES
---------------
--PROCEDURE getPrice(), returns the most recent CLOSINGPRICE of the requested MUTUALFUND symbol
CREATE OR REPLACE FUNCTION getPrice(requestedSymbol VARCHAR2)
	RETURN FLOAT IS
	returnPrice FLOAT := 0;
	transactionDate DATE;
	t_date DATE;
	BEGIN
		--Get the current date
		SELECT c_date INTO transactionDate FROM MUTUALDATE WHERE ROWNUM = 1;
		
		--Get the most recent date
		SELECT MAX(p_date) INTO t_date FROM CLOSINGPRICE WHERE p_date < transactionDate;
		
		--Get the correct symbol price from the most recent day
		SELECT price INTO returnPrice FROM CLOSINGPRICE
		WHERE (symbol = requestedSymbol AND p_date = t_date );
		
		RETURN returnPrice;
	END;
/

--Ran by the 'deposit trigger'. Inserts 'buy' transactions into TRXLOG according to the user's preferences 
CREATE OR REPLACE PROCEDURE allocateDeposit(login_name VARCHAR2, totalDeposit FLOAT)
	IS
	--Establish a cursor to iterate through PREFERS
	CURSOR prefers_cursor IS SELECT * FROM PREFERS
		WHERE ( allocation_no = ( SELECT allocation_no 
								FROM 	(SELECT *
										FROM ALLOCATION
										ORDER BY allocation_no DESC
										)
								WHERE rownum = 1
								) 
				);
	
	prefers_row PREFERS%ROWTYPE;
	
	--VARIABLES
	partialDeposit FLOAT := 0.0;
	num_shares INT := 0;
	transactionDate DATE;
	trxlogCount INT := 0;
	iteration INT := 0;
	
	BEGIN
		--Open the cursor
		IF NOT prefers_cursor%ISOPEN
			THEN OPEN prefers_cursor;
		END IF;
		
		--Get the current date so we can add it to TRXLOG
		SELECT MAX(c_date) INTO transactionDate FROM MUTUALDATE;
		
		
		
		LOOP
			
			FETCH prefers_cursor INTO prefers_row;
			EXIT WHEN prefers_cursor%NOTFOUND;
			
			--Calculate the amount of money we have to spend on this specific allocation
			partialDeposit := prefers_row.percentage * totalDeposit;
			
			--The amount of shares we are able to purchase for this preference, trunc() to preserve whole number INT.
			SELECT trunc( partialDeposit / getPrice(prefers_row.symbol) ) INTO num_shares FROM DUAL;
			
			--Insert the 'buy' transaction into TRXLOG to activate the 'buy trigger'. 'Buy trigger' handles updating OWNS and BALANCE
			SELECT COUNT(*) into trxlogCount --An iterative value
			FROM TRXLOG;
			
			IF trxlogCount = 0 THEN --if this is our first transaction ever, then we need to spawn a 'buy' transaction with transaction #=1
				INSERT INTO TRXLOG VALUES ( 1, login_name, prefers_row.symbol, transactionDate, 'buy', num_shares, getPrice(prefers_row.symbol), getPrice(prefers_row.symbol) * num_shares); 
			ELSE
				--Update the iterative value trxlogCount
				SELECT (trxlogCount + 1) INTO trxlogCount FROM DUAL;
				--Insert the new 'buy' transaction.
				INSERT INTO TRXLOG VALUES ( trxlogCount, login_name, prefers_row.symbol, transactionDate, 'buy', num_shares, getPrice(prefers_row.symbol), getPrice(prefers_row.symbol) * num_shares);
			END IF;
			
			
			
		END LOOP;
		
		CLOSE prefers_cursor; --Don't forget to close cursor
		
	END;
/

---------------
--TRIGGERS
---------------
--TRXLOG
--DEPOSIT: Add the deposited amount to customer balance, then insert purchase transactions for each allocation preference
CREATE OR REPLACE TRIGGER onInsert_trxlog_deposit
BEFORE INSERT
ON TRXLOG
FOR EACH ROW
WHEN(NEW.action = 'deposit')

DECLARE
toAllocate FLOAT := 0;

BEGIN

	UPDATE CUSTOMER SET balance = balance + :NEW.amount
	WHERE CUSTOMER.login = :NEW.login;
	
	SELECT balance INTO toAllocate
	FROM CUSTOMER
	WHERE CUSTOMER.login = :NEW.login;
	
	allocateDeposit(:NEW.login, toAllocate);
	
END;
/

--CUSTOMER

--BUY
CREATE OR REPLACE TRIGGER onInsert_trxlog_buy
BEFORE INSERT
ON TRXLOG
FOR EACH ROW
WHEN(NEW.action = 'buy')
BEGIN
	UPDATE CUSTOMER
	SET CUSTOMER.balance = (CUSTOMER.balance - :NEW.amount)
	WHERE CUSTOMER.login = :NEW.login;
	
	UPDATE OWNS
	SET OWNS.shares = OWNS.shares + :NEW.num_shares
	WHERE OWNS.login = :NEW.login AND OWNS.symbol = :NEW.symbol;
	
	IF SQL%rowcount = 0 THEN
		INSERT INTO OWNS VALUES(:NEW.login, :NEW.symbol, :NEW.num_shares);
	END IF;
END;
/

--SELLS
CREATE OR REPLACE TRIGGER onUpdate_trxlog_sell
BEFORE INSERT
ON TRXLOG
FOR EACH ROW
WHEN(NEW.action = 'sell')
BEGIN
	UPDATE CUSTOMER
	SET CUSTOMER.balance = (CUSTOMER.balance + :NEW.amount)
	WHERE CUSTOMER.login = :NEW.login;
	
	UPDATE OWNS
	SET OWNS.shares = (OWNS.shares - :NEW.num_shares)
	WHERE OWNS.login = :NEW.login AND OWNS.symbol = :NEW.symbol;

END;
/

--OWNS
--SHARES: If someone sells their shares and it results in them owning no more shares, just remove that tuple from the database.
CREATE OR REPLACE TRIGGER onUpdate_owns_shares
AFTER UPDATE
ON OWNS
BEGIN
	DELETE FROM OWNS
	WHERE OWNS.shares = 0;
END;
/

commit;















