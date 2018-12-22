//Zachary White: ZTW9 & Randyll Bearer: RLB97
//Milestone 2: Java Application to utilize SQL Backend
/**
*	Consists of a High-Level Menu and Low-Level Menu:
*		High-Level Menu: select whether you want to login as administrator or customer. This login is verified by the database
*		Low-Level Menu: According to whether you logged in as administrator or customer, you will be presented with the appropriate functions.
**/
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

//Main Driver Program
public class BetterFutures {
	private static Connection connection;	//Connection to class oracle server
	private static Scanner keyboard;		//Read in input from user
	private static PreparedStatement ps;	//Channel to execute SQL query
	private static ResultSet rs;			//Stores results from queries
	private static String userName;			//currently active user's login

	//Main Driver Application
	//Handles the High-Level menu, according to user input splits into either customer or administrator low-level menus
	public static void main(String[] args) throws SQLException{
		connection = null;
		keyboard = null;
		ps = null;
		rs = null;
		
		try
		{
			//Attempt to Connect to Database
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");	//Provided in JavaDemo.java on class website
			} catch (ClassNotFoundException e) {
				//e.printStackTrace();
				System.out.println("ERROR: Could not register class oracle.jdbc.driver.OracleDriver");
				System.exit(1);
			}
			connection = DriverManager.getConnection("jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass", "rlb97", "3938666"); //Provided in JavaDemo.java on class website
			
			//Set connection isolation level & commit preference
			connection.setAutoCommit(false);	//Allow us to decide what gets committed
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);	//Don't allow transactions to interact with each other until they are committed
		
			//Insert admin account so we will always have at least 'admin' 'root'
			String query = "SELECT * FROM ADMINISTRATOR WHERE login = 'admin'";
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			if(rs.next()){	//if an admin account already exists, we're okay
				//Do Nothing
			}else{			//if no 'admin' exists, create it
				query = "INSERT INTO ADMINISTRATOR VALUES ('admin', 'Administrator', 'admin@betterfuture.com', '5th Ave, Pitt', 'root' ) ";
				ps = connection.prepareStatement(query);
				ps.executeUpdate();
			}
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
			System.out.println("ERROR: could not connect to BetterFutures database");
			System.exit(1);
		}
		
		//High-Level Menu Select Admin or Customer 
		boolean isAdmin = false;
		
		System.out.println("Welcome to the BetterFutures database program! Login as:\n");
		System.out.println("1) Admin\n2) Customer\n");
		
		String input = null;
		System.out.print("Please enter an option: ");
		keyboard = new Scanner(System.in);
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
		
		//Get user's login and password
		System.out.print("\nPlease enter username: ");
		userName = keyboard.nextLine();
		System.out.print("Please enter password: ");
		password = keyboard.nextLine();
		
		while(!checkLogin(userName, password, isAdmin))	//verify that customer/administrator exists in database
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
		
		//Close all open channels before finishing program
		connection.close();
		keyboard.close();
		ps.close();
		rs.close();
	}
	
	//-------------------
	//VARIOUS FUNCTIONS
	//-------------------
	
	//Called by High-Level Menu
	//Accept user-inputted username, password, and isAdmin. Check to see if such a user exists in the database
	private static boolean checkLogin(String userName, String password, boolean isAdmin)
	{
		try
		{
			if(isAdmin)	//if user wants to login as administrator, verify login from administrator table
			{
				ps = connection.prepareStatement("SELECT * FROM ADMINISTRATOR");
			}
			else		//if user wants to login as customer, verify login from customer table
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
			//e.printStackTrace();
			System.out.println("ERROR: Could not verify user login/password");
			System.exit(1);
		}
		return false;
	}
	
	//Called throughout Low-Level Menu
	//Print out the entire contents of the result set
	private static void printRS(ResultSet rs)
	{
		try
		{
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();	//used in for Loop

			while (rs.next()) {
				for(int i = 1; i <= columnsNumber; i++)
					System.out.print(rs.getString(i) + " ");
				System.out.println();
			}
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
			System.out.println("ERROR: Could not printRS...");
		}
	}
	
	//Return the current Date type c_date from MUTUALDATE
	private static Date getCDate(){
		String query = "SELECT MAX(c_date) FROM MUTUALDATE";
		Date date = null;
		
		try{
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			
			rs.next();
			date = rs.getDate(1);	//yyyy-mm-dd
			return date;
			
		}catch(SQLException e){
			//e.printStackTrace();
			System.out.println("ERROR: Could not getCDate()");
		}
		return null;
	}
	
	//return current date from MUTUALDATE as a string
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
			}
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
			System.out.println("ERROR: Could not return TodayMutualDate");
		}
		return null;
	}
	
	//Return the highestNumbered transaction ID currently existing in trxlog
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
			System.out.print("Please enter an option: ");
			
			String input = null;
			input = keyboard.nextLine();
			
			while(Integer.parseInt(input) < 1 || Integer.parseInt(input) > 9)
			{
				System.out.print("Sorry, please select a valid option [1-6]: ");
				input = keyboard.nextLine();
			}
			
			if(input.equals("1")){			//Register new customer
				registerCustomer();
			}else if(input.equals("2")){	//Update Share Quotas
				updateShares();
			}else if(input.equals("3")){	//Add Mutual Fund
				addMutualFund();
			}else if(input.equals("4")){	//Update Time and Date
				updateDate();
			}else if(input.equals("5")){	//Display Statistics
				displayStatistics();
			}else if(input.equals("6")){	//Exit Program
				return;
			}
		}
	}
	
	//Admin Option 1
	//Insert a new customer into the CUSTOMER table, make sure login is not a duplicate
	private static void registerCustomer(){
			String login = "";
			int validLogin = 0;
			String name = "";
			String email = "";
			String address = "";
			String password = "";
			
			//Validate Login
			System.out.print("Please enter desired customer login: ");
			login = keyboard.nextLine();
			while(validLogin == 0 ){
				if(login.equals("")){
					System.out.print("Sorry but please enter a non-empty login: ");
					login = keyboard.nextLine();
				}else{	//check to see if this login already exists in database
					try{
						String query = "select * from CUSTOMER where login = ?";
						ResultSet rs = null;
						
						ps = connection.prepareStatement(query);
						ps.setString(1, login);
					
						rs = ps.executeQuery();
						
						if(rs.next()){
							System.out.print("Sorry but that login already exists in the database, please enter a new login: ");
							login = keyboard.nextLine();
						}else{
							validLogin = 1;
						}
					}catch(SQLException e){
						e.printStackTrace();
						System.out.println("ERROR: Could not check to see if login already exists in database");
					}
				}
			}
			
			//Validate Name
			System.out.print("Please enter customer name: ");
			name = keyboard.nextLine();
			while(name.equals("")){
				System.out.print("Sorry, but please enter a non-empty name: ");
				name = keyboard.nextLine();
			}
			
			//Validate Email
			System.out.print("Please enter customer email: ");
			email = keyboard.nextLine();
			while(email.equals("")){
				System.out.print("Sorry, but please enter a non-empty email: ");
				email = keyboard.nextLine();
			}
			
			//Validate Address
			System.out.print("Please enter customer address: ");
			address = keyboard.nextLine();
			while(address.equals("")){
				System.out.print("Sorry, but please enter a non-empty address: ");
				address = keyboard.nextLine();
			}
			
			//Validate Password
			System.out.print("Please enter customer password: ");
			password = keyboard.nextLine();
			while(password.equals("")){
				System.out.print("Sorry, but please enter a non-empty password: ");
				password = keyboard.nextLine();
			}
			try{
				connection.commit();
				connection.setAutoCommit(false);
				
				//Insert tuple into CUSTOMER table
				String query = "INSERT INTO CUSTOMER VALUES (?, ?, ?, ?, ?, 0)";
				ps = connection.prepareStatement(query);
				ps.setString(1, login);
				ps.setString(2, name);
				ps.setString(3, email);
				ps.setString(4, address);
				ps.setString(5, password);
				
				ps.executeUpdate();
				
				connection.commit();
			}catch(SQLException e){
				//e.printStackTrace();
				System.out.println("ERROR: Could not insert into CUSTOMER table...");
			}
			
	}
	
	//Admin Option 2
	//Iterate through all mutualfunds currently existing in MUTUALFUND, ask user for new updated price
	//If a closingPrice has already been updated this day, update the tuple instead of inserting a new one
	private static void updateShares(){
		try{
			connection.commit();
			connection.setAutoCommit(false);
			String symbol;
			
			int needToUpdate = 0;	//if 1, we have to update a tuple and not insert a new one
			
			//Check To See if we already have prices for today
			String query = "SELECT * FROM CLOSINGPRICE,MUTUALDATE WHERE p_date = c_date";
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			
			if(rs.next()){	//there exists a closingPrice for today
				needToUpdate = 1;
			}
			
			//Display all symbols and ask for new price
			query = "SELECT DISTINCT SYMBOL FROM CLOSINGPRICE";
			ResultSet distinctSymbols = null;
			ps = connection.prepareStatement(query);
			distinctSymbols = ps.executeQuery();
			
			while(distinctSymbols.next()){
				symbol = distinctSymbols.getString(1);
				String currentDate = getTodayMutualDate();
				
				System.out.print("Enter New Price For " + symbol + ":  ");
				float newPrice = keyboard.nextFloat();
				keyboard.nextLine();
				while(newPrice <= 0){	//Cannot allow a negative price input
					System.out.print("Please enter a valid postive price: ");
					newPrice = keyboard.nextFloat();
					keyboard.nextLine();
				}
				
				//Insert new closingPrice or update existing closingPrice for currentDate
				if(needToUpdate == 1){	//Run Update
					query = "UPDATE CLOSINGPRICE SET symbol = ?, price = ?, p_date = to_date(?, 'YYYY-MM-DD') WHERE symbol = ? AND p_date = to_date(?, 'YYYY-MM-DD') ";
					ps = connection.prepareStatement(query);
					ps.setString(1, symbol);
					ps.setFloat(2, newPrice);
					ps.setString(3, currentDate);
					ps.setString(4, symbol);
					ps.setString(5, currentDate);
					
				}else{					//Run Insert
					query = "INSERT INTO CLOSINGPRICE VALUES(?, ?, to_date(?, 'YYYY-MM-DD') )";
					ps = connection.prepareStatement(query);
					ps.setString(1, symbol);
					ps.setFloat(2, newPrice);
					ps.setString(3, currentDate);
				}
				
				ps.executeUpdate();
			}
			
			connection.commit();
		}catch(SQLException e){
			//e.printStackTrace();
			System.out.println("ERROR: Could not updateShares()...");
		}
		
	}
	
	//Admin option 3
	//Insert a new mutualFund tuple into MUTUALFUND, guarantee to symbol conflict
	private static void addMutualFund(){
		String symbol;
		int validSymbol = 0;	//if 1, we can simply insert, no update necessary
		String name;
		String description;
		String category;
		String date = getTodayMutualDate();	//retrieved from MUTUALDATE
		
		try{
			connection.commit();
			connection.setAutoCommit(false);
			
			//get symbol + verify it doesn't already exist in MUTUALFUND
			System.out.print("Please enter symbol for new MutualFund: ");
			symbol = keyboard.nextLine();
			while(validSymbol == 0){
				if(symbol.equals("")){
					System.out.print("Please enter valid non-empty symbol: ");
					symbol = keyboard.nextLine();
				}else{
					try{
						String query = "select * from MUTUALFUND where symbol = ?";
							
						ps = connection.prepareStatement(query);
						ps.setString(1, symbol);
					
						rs = ps.executeQuery();
						
						if(rs.next()){	//Symbol already taken
							System.out.print("Sorry but that symbol already exists in the database, please enter a new symbol: ");
							symbol = keyboard.nextLine();
						}else{	//Symbol available
							validSymbol = 1;
						}
					}catch(SQLException e){
						//e.printStackTrace();
						System.out.println("ERROR: Could not check to see if login already exists in database");
					}
				}
			}
			
			//get MutualFund name
			System.out.print("Please enter name for new MutualFund: ");
			name = keyboard.nextLine();
			while(name.equals("")){
				System.out.print("Please enter a valid non-empty name: ");
				name = keyboard.nextLine();
			}
			
			//get MutualFund description
			System.out.print("Please enter description for new MutualFund: ");
			description = keyboard.nextLine();
			while(description.equals("")){
				System.out.print("Please enter a valid non-empty description for new MutualFund: ");
				description = keyboard.nextLine();
			}
			
			//get category for new mutual fund
			System.out.print("Please enter category for new MutualFund [fixed, bonds, stocks, or mixed]: ");
			category = keyboard.nextLine();
			while(!category.equals("fixed") && !category.equals("bonds") && !category.equals("stocks") && !category.equals("mixed")){
				System.out.print("Please enter a valid category type: ");
				category = keyboard.nextLine();
			}
			
			//Insert new mutualFund
			String query = "INSERT INTO MUTUALFUND VALUES (?, ?, ?, ?, to_date(?,'YYYY-MM-DD') )";
			ps = connection.prepareStatement(query);
			ps.setString(1, symbol);
			ps.setString(2, name);
			ps.setString(3, description);
			ps.setString(4, category);
			ps.setString(5, date);
			
			ps.executeUpdate();
			
			connection.commit();
		}catch(SQLException e){
			//e.printStackTrace();
			System.out.println("ERROR: Could not insert new mutualfund...");
		}
	
	}
	
	//Admin Option 4
	//Allow administrator to update the current entry in MutualDate
	public static void updateDate(){
		String newDate;
		String currentDate = getTodayMutualDate();
		
		//get newDate from user
		System.out.print("Please enter new date [in format YYYY-MM-DD]: ");
		newDate = keyboard.nextLine();
		while(newDate.length() !=10){
			System.out.print("Sorry, please enter a valid new date [Example: '2017-04-01']: ");
			newDate = keyboard.nextLine();
		}
		
		//update mutualdate
		try{
			connection.commit();
			connection.setAutoCommit(false);
			
			String query = "UPDATE MUTUALDATE SET c_date = to_date(?, 'YYYY-MM-DD') WHERE c_date = to_date(?, 'YYYY-MM-DD')";
			ps = connection.prepareStatement(query);
			ps.setString(1, newDate);
			ps.setString(2, currentDate);
			
			ps.executeUpdate();
			
			connection.commit();
		}catch(SQLException e){
			//e.printStackTrace();
			System.out.println("ERROR: Could not update mutualdate...");
		}
		
	}
	
	//Admin Option 5
	//Display top kRows tuples from the past xMonths for most sold stock category and customers who have invested the most
	public static void displayStatistics(){
		int xMonths = 0;
		int kRows = 0;
		int day;
		int month;	//starts at 0
		int year;
		String dayString = null;	//used in forming the full string to be used in query
		String monthString = null;	//used in forming the full string to be used in query
		String startDate;	//current - x, only consider transactions after this date
		Date currentDate = getCDate();	//YYYY-MM-DD
	
		//We want to get x from user
		System.out.print("Grab Statistics from the past X months, X = ");
		xMonths = keyboard.nextInt();
		keyboard.nextLine();
		while(xMonths <= 0){
			System.out.print("Sorry, but please enter a valid positive amount of months: ");
			xMonths = keyboard.nextInt();
			keyboard.nextLine();
		}
	
		/**
		* Figure out startDate from xMonths
		* Create a calendar positioned at current date from MUTUALFUND
		* Subtract xMonths months from that calendar date
		* Format the resulting date into a string to be inserted into query
		**/
		Calendar next = new GregorianCalendar();
		next.setTime(currentDate);	
		next.add(Calendar.MONTH, (xMonths*-1));	//subtract x months from our current date, only consider transactions after this
		
		day = next.get(Calendar.DAY_OF_MONTH);
		if(day < 10){
			dayString = ("0"+day);	//we need a leading 0 for our string->date format
		}else{
			dayString = ""+day;
		}
		month = next.get(Calendar.MONTH) + 1;	//Month starts from 0, add +1 to it
		System.out.println("Month+1: " + month);
		if( month < 10){
			monthString = ("0"+month);	//we need a leading 0 for our string->date format
		}else{
			monthString = ""+month;
		}
		year = next.get(Calendar.YEAR);
		startDate = (year+"-"+monthString+"-"+dayString);	//we can use this string in our query to_date()
		
		//We want to get k from user
		System.out.print("Return the top K tuples, k = ");
		kRows = keyboard.nextInt();
		keyboard.nextLine();
		while(kRows <= 0){
			System.out.print("Sorry, but please enter a valid positive amount of tuples: ");
			kRows = keyboard.nextInt();
			keyboard.nextLine();
		}
		
		try{
			connection.commit();
			connection.setAutoCommit(false);
			
			//fetch results for top k highest volume category shares sold
			String query = "SELECT m.category, sum(t.num_shares) FROM TRXLOG t JOIN MUTUALFUND m ON t.symbol = m.symbol WHERE t_date >= to_date(?, 'YYYY-MM-DD') AND action = 'sell' GROUP BY m.category ORDER BY sum(num_shares) DESC FETCH FIRST ? ROWS ONLY ";
			ps = connection.prepareStatement(query);
			ps.setString(1, startDate);
			ps.setInt(2, kRows);
			rs = ps.executeQuery();
			
			while(rs.next()){
				System.out.println("Category: " + rs.getString(1) + "    NumOfShares: " + rs.getInt(2) );
			}
			
			//fetch results for top k highest invested amount	
			query = "SELECT c.name, sum(t.amount) FROM CUSTOMER c JOIN TRXLOG t ON c.login = t.login WHERE t_date >= to_date(?, 'YYYY-MM-DD') AND action = 'buy' GROUP BY c.name ORDER BY sum(t.amount) DESC FETCH FIRST ? ROWS ONLY  ";
			ps = connection.prepareStatement(query);
			ps.setString(1, startDate);
			ps.setInt(2, kRows);
			rs = ps.executeQuery();
			
			while(rs.next()){
				System.out.println("Customer: " + rs.getString(1) + "    AmountInvested: " + rs.getInt(2) );
			}
			
			connection.commit();
		}catch(SQLException e){
			//e.printStackTrace();
			System.out.println("ERROR: Could not retrieve statistics for display...");
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
			
			if(input.equals("1")){			//Browse mutual funds
				browseMutualFunds();
			}else if(input.equals("2")){	//Search Mutual Funds
				searchMutualFunds();
			}else if(input.equals("3")){	//Deposit + Invest
				invest();
			}else if(input.equals("4")){	//Sell Shares
				sellShares();
			}else if(input.equals("5")){	//Buy Shares
				buyShares();
			}else if(input.equals("6")){	//Conditional Invest
				System.out.println("\nSorry, could not finish this function\n");
			}else if(input.equals("7")){	//Change Allocation Preference
				changeAllocationPreferences();
			}else if(input.equals("8")){	//Customer Portfolio
				customerPortfolio();
			}else if(input.equals("9")){	//Exit Program
				return;
			}
		}
	}
	
	//Customer Option 8
	//Display history of prices for currently owned stocks and present statistics
	private static void customerPortfolio()
	{
		//Recieve Date from User and check if it is valid and exists in database
		String dateString = "";
		System.out.print("\nPlease enter a date (in format YYYY-MM-DD): ");
		dateString = keyboard.nextLine();

		String query = "SELECT * FROM CLOSINGPRICE WHERE p_date = to_date(?, 'YYYY-MM-DD')";
		try
		{
			ps = connection.prepareStatement(query);
			ps.setString(1, dateString);
			
			rs = ps.executeQuery();
			
			if(!rs.next())
			{
				System.out.println("\nDate does not exist in database");
				return;
			}
		}
		catch(SQLException e)
		{
			//e.printStackTrace();
			System.out.println("ERROR: Could not validate date... ");
			return;
		}
		
		//Begin Transaction (Don't want to read changed data)
		try
		{
			connection.commit();
			connection.setAutoCommit(false);
		}
		catch(SQLException e)
		{
			//e.printStackTrace();
			System.out.println("ERROR: Line 686...");
		}
		
		//Declare Variables
		float totalPortfolioValue = 0;	//Cumulative value of ownedStocks
		
		//Store Symbol and Price
		ArrayList<String> symbols1 = new ArrayList<String>();
		ArrayList<Float> prices = new ArrayList<Float>();
		
		query = "SELECT * FROM CLOSINGPRICE WHERE p_date = to_date(?, 'YYYY-MM-DD')";
		ResultSet rs = null;
		
		try
		{
			ps = connection.prepareStatement(query);
			ps.setString(1, dateString);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				symbols1.add(rs.getString(1));
				prices.add(rs.getFloat(2));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		//Store Symbol and NumShares
		ArrayList<String> symbols = new ArrayList<String>();
		ArrayList<Integer> shares = new ArrayList<Integer>();
		
		query = "SELECT * FROM OWNS WHERE login = ?";
		
		try
		{
			ps = connection.prepareStatement(query);
			ps.setString(1, userName);
			
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				symbols.add(rs.getString(2));
				shares.add(rs.getInt(3));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		//Loop Through the currently owned stocks
		for(int i = 0; i < symbols.size(); i++)
		{
			String symbol = symbols.get(i);	//Our first owned symbol
			System.out.println("\nSymbol: " + symbol);
			
			float price = 0;
			
			for(int j=0; j<symbols1.size(); j++)		//Loop through the share prices of the specified date
			{
				if(symbols1.get(j).equals(symbol))	//if our history symbol matches our owned symbol
				{
					price = prices.get(j);
					System.out.println("Price: " + price);
					break;
				}
			}
			
			int share = shares.get(i);
			System.out.println("Shares: " + share);

			//Loop through history of purchases/sells in trxlog
			query = "SELECT * FROM TRXLOG WHERE login = ? AND t_date <= to_date(?, 'YYYY-MM-DD')"; //And Date < dateString
			float costValue = 0;	//Cumulative Total
			float sellValue = 0;	//Cumulative Total
			try
			{
				ps = connection.prepareStatement(query);
				ps.setString(1, userName);
				ps.setString(2, dateString);
				rs = ps.executeQuery();
				
				while(rs.next())
				{
					if(symbol.equals(rs.getString(3)))
					{
						if(rs.getString(5).equals("buy"))
						{
							costValue += rs.getFloat("amount");
						}
						else if(rs.getString(5).equals("sell"))
						{
							sellValue += rs.getFloat("amount");
						}
					}
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
			//Calculate currentValue + update totalPortfolio Value
			float currentValue = price * share;
			System.out.println("Current Value: " + currentValue);
			
			System.out.println("Cost Value: " + costValue);
			
			float adjustedCost = costValue - sellValue;
			System.out.println("Adjusted Cost: " + adjustedCost);
		
			float yield = currentValue - adjustedCost;
			System.out.println("Yield: " + yield);
			
			totalPortfolioValue += currentValue;
		}
		
		System.out.println("\nTotal Portfolio Value: " + totalPortfolioValue);
	}
	
	//Customer Option 7
	//If a user has not already changed their allocation prefs in the last month, allow them to change their prefs.
	private static void changeAllocationPreferences()
	{
		Date mostRecentDate = null;	//last time user updated their allocationPreferences
		int maxAllNo = 0;			//how many allocations does the user have?
		
		//Check to see the last time the user has changed their allocationPrefs.
		String query = "SELECT max(p_date) FROM ALLOCATION WHERE login = ?";
		try
		{
			ps = connection.prepareStatement(query);
			ps.setString(1, userName);
			
			rs = ps.executeQuery();
			while(rs.next())
			{
				mostRecentDate = rs.getDate(1);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		//Check to see what we should make our new allocation_no
		query = "SELECT max(allocation_no) FROM ALLOCATION";
		try
		{
			ps = connection.prepareStatement(query);
			
			rs = ps.executeQuery();
			while(rs.next())
			{
				maxAllNo = rs.getInt(1);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		String todayDate = getTodayMutualDate();
		String allDate = mostRecentDate.toString();
		
		String[] tdArray = todayDate.split("-");
		String[] adArray = allDate.split("-");
		
		if(tdArray[1].equals(adArray[1]) && tdArray[0].equals(adArray[0]))
		{
			System.out.println("\nAllocation has already been changed within the month");
			return;
		}
		
		System.out.println("Enter DONE when done entering preferences");
		
		//Use array lists to store user-desired symbol/percentages. We don't know how many in advance
		ArrayList<String> symbols = new ArrayList<String>();
		ArrayList<Float> percentages = new ArrayList<Float>();
		
		String symbol = "";
		float percent = 0;
		float percTotal = 0;
		
		
		//Allow the user to dicate their new allocationPrefs. Make sure total percentage does not surpass 1.0 and symbol exists in MUTUALFUND
		while(true)
		{
			System.out.print("\nEnter symbol: ");
			symbol = keyboard.nextLine();
			if(symbol.toUpperCase().equals("DONE"))
				break;
			
			System.out.print("Enter percentage of allocation (0-100): ");
			percent = keyboard.nextFloat();
			keyboard.nextLine(); 
			
			if(percent < 0 || percent > 100)	//if the total percentage has not yet reached 1.0
			{
				System.out.print("Enter percentage of allocation (0-100): ");
				percent = keyboard.nextFloat();
				keyboard.nextLine();
			}
			
			percent /= 100;
			percTotal += percent;
			
			symbols.add(symbol);
			percentages.add(percent);
		}
		
		if(percTotal == 0)
		{
			return;
		}
		else if(percTotal > 1 || percTotal < 1)
		{
			System.out.println("Percentages do not total to 100. Cannot change preferences");
			return;
		}
		
		//Insert the new allocationPreferences
		try
		{
			connection.commit();
			connection.setAutoCommit(false);
			
			//insert into ALLOCATION
			query = "INSERT INTO ALLOCATION VALUES (?, ?, to_date(?, 'YYYY-MM-DD'))";
			ps = connection.prepareStatement(query);
			ps.setInt(1, maxAllNo + 1);
			ps.setString(2, userName);
			ps.setString(3, todayDate);
			ps.executeUpdate();
			
			//Isert into PREFERS
			for(int i = 0; i < symbols.size(); i++)
			{
				symbol = symbols.get(i);
				percent = percentages.get(i);
				
				query = "INSERT INTO PREFERS VALUES (?, ?, ?)";
				ps = connection.prepareStatement(query);
				ps.setInt(1, maxAllNo + 1);
				ps.setString(2, symbol);
				ps.setFloat(3, percent);
				
				ps.executeUpdate();
			}
			
			connection.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	//Customer Option 4
	//Allow a user to sell shares, most of the work handled by SQL trigger
	private static void sellShares()
	{
		int shares;
		String symbol;
		
		//Get the symbol and #Shares user desires to sell
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
		
		//Insert into trxlog
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
			//e.printStackTrace();
			System.out.println("ERROR: Could not sell requested shares");
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
			//e.printStackTrace();
			System.out.println("ERROR: Could not sell requested shares");
		}
	}
	
	//Customer Option 5
	//Allow customer to buy shares using the amount in their CUSTOMER.balance, most work done by SQL trigger
	private static void buyShares()
	{
		int shares;
		String symbol;
		
		//Get desired symbol and #shares to buy
		System.out.print("\nEnter symbol to buy: ");
		symbol = keyboard.nextLine();
		
		System.out.print("Enter # of shares to buy: ");
		shares = keyboard.nextInt();
		keyboard.nextLine();
		
		if(shares < 0)
		{
			System.out.println("Cannot be less less than 0.");
			return;
		}
		else if(shares == 0)
		{
			return;
		}
		
		//Calculate how much this purchase will cost
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
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		//Figure out which # transaction this will be
		int maxTransID = getMaxTransID();
		String dateString = getTodayMutualDate();
		
		try
		{
			query = "INSERT INTO TRXLOG (trans_id, login, symbol, t_date, action, num_shares, price, amount) VALUES (?,?,?,to_date(?, 'YYYY-MM-DD'),?,?,?,?)";
			ps = connection.prepareStatement(query);
			ps.setInt(1, maxTransID + 1);
			ps.setString(2, userName);
			ps.setString(3, symbol);
			ps.setString(4, dateString);
			ps.setString(5, "buy");
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
			System.out.println("Could not buy requested shares");
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
			//e.printStackTrace();
			System.out.println("Could not buy requested shares");
		}
	}
	
	//Customer Option 3
	//Allow customer to deposit an amount, allocate that amount according to their preferences.
	public static void invest()
	{
		int maxTransID = getMaxTransID();
		String dateString = getTodayMutualDate();
		
		//Get the amount the user would like to deposit
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
		
		//Insert appropriate transaction into database
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
	
	//Customer Option 2
	//Allow customer to enter up to two keywords and then search the MUTUALFUND.description for any tuple containing ALL keywords
	private static void searchMutualFunds()
	{
		String keyword1 = null;
		String keyword2 = null;
		
		//Retrieve keywords
		System.out.print("\nEnter keyword 1: ");
		keyword1 = keyboard.nextLine();
		
		System.out.print("Enter keyword 2 (Optional): ");
		keyword2 = keyboard.nextLine();
		
		//Search MUTUALFUND.description for keywords
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
