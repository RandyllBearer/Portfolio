-- CURSOR EXAMPLE
--"loop" does not require to be ended with a ;, as end loop; ends with a ;
CREATE OR REPLACE PROCEDURE show_customer_info
IS
BEGIN
	--c_customer is our implicit cursor here
	FOR c_customer in(SELECT ssn, name, address FROM customer) LOOP
		dbms_output.put_line(c_customer.ssn || ' ' ||  c_customer.name || ' ' || c_customer.address);
		
	END LOOP;
	
END;
/
