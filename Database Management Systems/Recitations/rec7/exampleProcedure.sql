--Function + stored procedure

--FUNCTION
--Must drop functions just like triggers
--IN in first line defines a read only, cannot give size constraint to varchar2
--IS in second line begins our function/procedure
--:= used to assign a value to a variable
CREATE OR REPLACE FUNCTION compute_balance(c_ssn IN varchar2) return number
IS
balance number;
total_acc_balance number;
total_loan number;
BEGIN
	--update total balance for all accounts that share same ssn
	SELECT SUM(balance) INTO total_acc_balance
	FROM Account
	WHERE ssn = c_ssn;

	--Total amount loaned by all accounts that share same ssn
	SELECT sum(amount) INTO total_loan
	FROM Loan
	WHERE ssn = c_ssn;

	balance := total_acc_balance - total_loan;
	return balance;
END;
/

--STOPPED PROCEDURE
--For parameters, stopped procedures can do in,out,inout.    functions can only do in
	--out allows one to write ouput to same variable
CREATE OR REPLACE PROCEDURE transfer_fund(from_account IN varchar2, to_account IN varchar2, amount IN number)
IS

BEGIN

END;
/  














