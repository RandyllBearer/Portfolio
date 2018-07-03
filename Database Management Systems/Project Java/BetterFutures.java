//Zachary White: ZTW9 & Randyll Bearer: RLB97
//Milestone 2: Java Application to utilize SQL Backend

import java.sql.*;
import java.util.Scanner;
public class BetterFutures {
	
	//Source on UNIXS before compiling and runninga
	//DONT FUCKING FORGET TO /SOURCE/AFS/ BEFORE TRYING TO COMPILE/RUN PROGRAM
	//connection has to be to class3
	//STEP1: Source
	//STEP2: Run project-schema and sample-data MAKE SURE IT COMMITS
	//STEP3: Compile Java
	//STEP4: Run the java
	//STEP5:???
	//STEP^: PROFIT
	
	private static Connection connection;	//Connection to class oracle server
	private static Scanner keyboard;		//Read in input from user
	private static PreparedStatement ps;	//Channel to execute SQL query
	private static ResultSet rs;			//Stores results from queries
	
	private static String userName;

	//Main Driver Application. Consists of a high and low menu
	//Asks customer or admin to login, provides appropriate options
	public static void main(String[] args) throws SQLException{
		connection = null;
		keyboard = null;
		ps = null;
		rs = null;
		
		try
		{
			//Attempt to Connect to Database
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//MUST BE CLASS3 NOT UNIXS
			connection = DriverManager.getConnection("jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass", "rlb97", "3938666");
			
			//Set connection isolation level & commit preference
			connection.setAutoCommit(false);	//Allow us to decide what gets committed
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);	//Don't allow transactions to interact with each other until they are committed
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println("Error connecting to database");
			System.exit(1);
		}
		
		//High-Level Menu Select Admin or Customer 
		boolean isAdmin = false;
		
		System.out.println("Welcome to the BetterFutures database program! Login as:\n");
		System.out.println("1) Admin\n2) Customer\n");
		
		String input = null;
		System.out.print("Please enter an option: ");
		keyboard = new Scanner(System.in); //CLOSE KEYBOARD
		input = keyboard.nextLine();
		while(!input.equals("1") && !input.equals("2"))
		{
			System.out.println("Please enter an option");
			input = keyboard.nextLine();
		}
		
		if(input.equals("1"))
		{
			isAdmin = true;
		}
		
		userName = null;
		String password = null;
		
		
		System.out.print("\nPlease enter username: ");
		userName = keyboard.nextLine();
		System.out.print("Please enter password: ");
		password = keyboard.nextLine();
		
		while(!checkLogin(userName, password, isAdmin))
		{
			System.out.print("\nCould Not Authenticate\n\nPlease enter username: ");
			userName = keyboard.nextLine();
			System.out.print("Please enter password: ");
			password = keyboard.nextLine();
		}
		
		if(isAdmin)
		{
			adminMenu();	//Present the user with appropriate admin options
		}
		else
		{
			customerMenu();	//Present the user with appropriate customer options
		}
		
		connection.close();	//User is done, close connection and exit program
	}
	
	//-------------------
	//VARIOUS FUNCTIONS
	//-------------------
	
	//High Level Menu
	//Accept user-inputted username, password, and isAdmin. Check to see if such a user exists in the database
	private static boolean checkLogin(String userName, String password, boolean isAdmin)
	{
		try
		{
			if(isAdmin)
			{
				ps = connection.prepareStatement("SELECT * FROM ADMINISTRATOR");
			}
			else
			{
					ps = connection.prepareStatement("SELECT * FROM CUSTOMER");
			}
			
			rs = ps.executeQuery();
	
			while(rs.next())
			{
				if(rs.getString(1).equals(userName) && rs.getString(5).equals(password))
				{
					return true;
				}
			}
			ps.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}
	
	//Print out the entire contents of the result set
	private static void printRS(ResultSet rs)
	{
		try
		{
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();

			while (rs.next()) {
				for(int i = 1; i <= columnsNumber; i++)
					System.out.print(rs.getString(i) + " ");
				System.out.println();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	//------------------------
	//ADMIN MENU + OPTIONS
	//------------------------
	
	//Allow the user to select from the appropriate list of admin options
	private static void adminMenu()
	{
		while(true){
			System.out.println("\n1) New Customer Registration");
			System.out.println("2) Update Share Quotas For Day");
			System.out.println("3) Add Mutual Fund");
			System.out.println("4) Update Time and Date");
			System.out.println("5) Statistics");
			System.out.println("6) Exit Program");
			
			String input = null;
			input = keyboard.nextLine();
			
			while(Integer.parseInt(input) < 1 || Integer.parseInt(input) > 6)
			{
				System.out.print("Sorry, please select a valid option [1-6]: ");
				input = keyboard.nextLine();
			}
			
			if(input.equals("1")){			//Register new customer
				
			}else if(input.equals("2")){		//Update Share Quotas
				
			}else if(input.equals("3")){		//Add Mutual Fund
				
			}else if(input.equals("4")){		//Update Time and Date
				
			}else if(input.equals("5")){	//Statistics
				
			}else if(input.equals("6")){	//Exit Program
				return;
			}
		}
	}
	
	//-------------------------
	//CUSTOMER MENU + OPTIONS
	//-------------------------
	
	//Allow the user to select from the appropriate list of customer options
	private static void customerMenu()
	{
		while(true){
			System.out.println("\n1) Browse Mutual Funds");
			System.out.println("2) Search Mutual Funds");
			System.out.println("3) Invest");
			System.out.println("4) Sell Shares");
			System.out.println("5) Buy Shares");
			System.out.println("6) Conditional Invest");
			System.out.println("7) Change Allocation Preference");
			System.out.println("8) Customer Portfolio");
			System.out.println("9) Exit Program\n");
			System.out.print("Please enter an option: ");
			
			String input = null;
			input = keyboard.nextLine();
			
			while(Integer.parseInt(input) < 1 || Integer.parseInt(input) > 9)
			{
				System.out.print("Sorry, please select a valid option [1-9]: ");
				input = keyboard.nextLine();
			}
			
			if(input.equals("1"))			//Browse mutual funds
			{
				browseMutualFunds();
			}else if(input.equals("2")){	//Search Mutual Funds
				searchMutualFunds();
			}else if(input.equals("3")){	//Deposit + Invest
				invest();
			}else if(input.equals("4")){	//Sell Shares
				sellShares();
			}else if(input.equals("5")){	//Buy Shares
				
			}else if(input.equals("6")){	//Conditional Invest
				
			}else if(input.equals("7")){	//Change Allocation Preference
				
			}else if(input.equals("8")){	//Customer Portfolio
				
			}else if(input.equals("9")){	//Exit Program
				return;
			}
		}
	}
	
	//CUSTOMER OPTION 4
	public static void sellShares()
	{
		int shares;
		String symbol;
		
		System.out.print("\nEnter symbol to sell: ");
		symbol = keyboard.nextLine();
		
		System.out.print("Enter # of shares to sell: ");
		shares = keyboard.nextInt();
		keyboard.nextLine();
		
		if(shares < 0)
		{
			System.out.println("Cannot less less than 0.");
			return;
		}
		else if(shares == 0)
		{
			return;
		}
		
		String query = "SELECT getPrice(?) from dual";
		
		float price = 0;
		try
		{
			ps = connection.prepareStatement(query);
			ps.setString(1, symbol);
			
			rs = ps.executeQuery();
			while(rs.next())
			{
				price = rs.getFloat(1);
				System.out.println(price); //TEST
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		int maxTransID = getMaxTransID();
		String dateString = getTodayMutualDate();
		
		try
		{
			query = "INSERT INTO TRXLOG (trans_id, login, symbol, t_date, action, num_shares, price, amount) VALUES (?,?,?,to_date(?, 'YYYY-MM-DD'),?,?,?,?)";
			ps = connection.prepareStatement(query);
			ps.setInt(1, maxTransID + 1);
			System.out.println(userName); //TEST
			ps.setString(2, userName);
			ps.setString(3, symbol);
			ps.setString(4, dateString);
			ps.setString(5, "sell");
			ps.setInt(6, shares);
			ps.setFloat(7, price);
			ps.setFloat(8, shares*price);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		try {
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Could not sell requested shares");
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return;
		}
		
		try {
			connection.commit();
		} catch (SQLException e) {
			System.out.println("Could not sell requested shares");
		}
	}
	
	public static void invest()
	{
		int maxTransID = getMaxTransID();
		String dateString = getTodayMutualDate();
		System.out.print("\nEnter deposit amount: ");
		float amount = keyboard.nextFloat();
		keyboard.nextLine();
		
		if(amount < 0)
		{
			System.out.println("Cannot deposit less than 0.");
			return;
		}
		else if(amount == 0)
		{
			return;
		}
		
		String query = "INSERT INTO TRXLOG (trans_id, login, t_date, action, amount) VALUES (?,?,to_date(?, 'YYYY-MM-DD'),?,?)";
		try
		{
			ps = connection.prepareStatement(query);
			ps.setInt(1, maxTransID+1);
			ps.setString(2, userName);
			ps.setString(3,  dateString);
			ps.setString(4, "deposit");
			ps.setFloat(5, amount);
			try {
				ps.executeUpdate();
			}
			catch(SQLException e) {
				e.printStackTrace();
				connection.rollback();
			}
			connection.commit();
			}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}	
	
	private static String getTodayMutualDate()
	{
		String query = "SELECT c_date FROM MUTUALDATE ORDER BY c_date DESC FETCH FIRST 1 ROWS ONLY";
		String date = null;
		
		try
		{
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				Date d = rs.getDate(1); //yyyy-mm-dd
				date = d.toString();
				return date;
				//String[] dateArray = date.split("-");
				//date = dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0].substring(2, 4);
				//return date;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static int getMaxTransID()
	{
		String query = "SELECT trans_id FROM TRXLOG ORDER BY trans_id DESC FETCH FIRST 1 ROWS ONLY";
		
		try
		{
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				return rs.getInt(1);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return -1;
	}
	
	//Customer Option 2
	//Allow...
	private static void searchMutualFunds()
	{
		String keyword1 = null;
		String keyword2 = null;
		
		System.out.print("\nEnter keyword 1: ");
		keyword1 = keyboard.nextLine();
		
		System.out.print("Enter keyword 2 (Optional): ");
		keyword2 = keyboard.nextLine();
		System.out.print("keyword 2 " + keyword2); //TEST
		
		if(!keyword2.equals("")) //If 2 keywords entered
		{
			String query = "SELECT * FROM mutualfund WHERE description LIKE ? AND description LIKE ?";
			
			try
			{
				ps = connection.prepareStatement(query);
				ps.setString(1, "%" + keyword1 + "%");
				ps.setString(2, "%" + keyword2 + "%");
				
				rs = ps.executeQuery();
				printRS(rs);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		else //If 1 keyword entered
		{
			String query = "SELECT * FROM mutualfund WHERE description LIKE ?";
			
			try
			{
				ps = connection.prepareStatement(query);
				ps.setString(1, "%" + keyword1 + "%");
				
				rs = ps.executeQuery();
				printRS(rs);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	//Customer Option 1
	//Allow the user to select desired filter, then present all valid mutual funds
	private static void browseMutualFunds()
	{
		System.out.println("\n1) List all by price");
		System.out.println("2) List all alphabetically");
		System.out.println("3) List all in category by price");
		System.out.println("4) List all in category alphabetically");
		System.out.println("5) Return to Customer Menu\n");
		System.out.print("Please enter an option: ");
		
		String input = null;
		input = keyboard.nextLine();
		
		while(Integer.parseInt(input) < 1 || Integer.parseInt(input) > 5)
		{
			System.out.print("Sorry, please select a valid option [1-5]: ");
			input = keyboard.nextLine();
		}
		
		if(input.equals("1"))			//List All by Price
		{
			System.out.print("Please enter a date (in format YYYY-MM-DD): ");
			String date = keyboard.nextLine();
			
			String query = "SELECT * FROM MUTUALFUND M JOIN CLOSINGPRICE C ON M.symbol = C.symbol WHERE C.p_date = ? ORDER BY C.price DESC";
			try {
				ps = connection.prepareStatement(query);
				ps.setDate(1, java.sql.Date.valueOf(date));
				
				rs = ps.executeQuery();
				
				printRS(rs);	//Print out the result
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(input.equals("2")){	//List all Alphabetically by fund name
			String query = "SELECT * FROM MUTUALFUND M JOIN CLOSINGPRICE C ON M.symbol = C.symbol ORDER BY M.name DESC";
			try{
				ps = connection.prepareStatement(query);
				rs = ps.executeQuery();
				
				printRS(rs);	//Print out the result
			}catch(SQLException e){
				e.printStackTrace();
			}
		}else if(input.equals("3")){	//List all in category by price
			System.out.print("Please enter a date (int format YYYY-MM-DD): ");
			String date = keyboard.nextLine();
			System.out.print("Please enter desired category symbol [E.G. 'MM' or 'BBS']: ");
			String category = keyboard.nextLine();
			
			String query = "SELECT * FROM MUTUALFUND M JOIN CLOSINGPRICE C ON M.symbol = C.symbol WHERE C.p_date = ? AND M.symbol = ? ORDER BY C.price DESC";
			try{
				ps = connection.prepareStatement(query);
				ps.setDate(1, java.sql.Date.valueOf(date));
				ps.setString(2, category);
				
				rs = ps.executeQuery();
				
				printRS(rs);	//Print out the result
			}catch(SQLException e){
				e.printStackTrace();
			}
		}else if(input.equals("4")){	//List all in category alphabetically by fund name
			System.out.print("Please enter desired category symbol [E.G. 'MM' or 'BBS']: ");
			String category = keyboard.nextLine();
			
			String query = "SELECT * FROM MUTUALFUND M JOIN CLOSINGPRICE C ON M.symbol = C.symbol WHERE M.symbol = ? ORDER BY C.price DESC";
			try{
				ps = connection.prepareStatement(query);
				ps.setString(1, category);
				
				rs = ps.executeQuery();
				
				printRS(rs);	//Print out the result
			}catch(SQLException e){
				e.printStackTrace();
			}
		}else if(input.equals("5")){	//Return to Customer Menu
			return;
		}
	}
	
	
} //end of program
