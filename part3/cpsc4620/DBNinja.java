package cpsc4620;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

/*
 * This file is where most of your code changes will occur You will write the code to retrieve
 * information from the database, or save information to the database
 * 
 * The class has several hard coded static variables used for the connection, you will need to
 * change those to your connection information
 * 
 * This class also has static string variables for pickup, delivery and dine-in. If your database
 * stores the strings differently (i.e "pick-up" vs "pickup") changing these static variables will
 * ensure that the comparison is checking for the right string in other places in the program. You
 * will also need to use these strings if you store this as boolean fields or an integer.
 * 
 * 
 */

/**
 * A utility class to help add and retrieve information from the database
 */

public final class DBNinja {
	private static Connection conn;

	// Change these variables to however you record dine-in, pick-up and delivery,
	// and sizes and
	// crusts
	public final static String pickup = "Pickup";
	public final static String delivery = "Delivery";
	public final static String dine_in = "DineIn";

	public final static int size_s = 1;
	public final static int size_m = 2;
	public final static int size_l = 3;
	public final static int size_xl = 4;
	private final static int size_max_int = 4;

	public final static int crust_thin = 1;
	public final static int crust_orig = 2;
	public final static int crust_pan = 3;
	public final static int crust_gf = 4;
	private final static int crust_max_int = 4;

	public final static String order_notComplete = "Not Complete";
	public final static String order_complete = "Complete";

	/**
	 * This function will handle the connection to the database
	 * 
	 * @return true if the connection was successfully made
	 * @throws SQLException
	 * @throws IOException
	 */
	private static boolean connect_to_db() throws SQLException, IOException {

		try {
			conn = DBConnector.make_connection();
			return true;
		} catch (SQLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

	}



	public static int getMaxOrderID() throws SQLException, IOException {
		connect_to_db();
		/*
		 * A function I needed because I forgot to make my pizzas auto increment in my DB.
		 * It goes and fetches the largest PizzaID in the pizza table.
		 * You wont need this function if you didn't forget to do that
		 */

		String maxID = "select max(CustomerOrderID) from customerOrder";

		PreparedStatement prepStatement = conn.prepareStatement(maxID);
		ResultSet returnValue = prepStatement.executeQuery();

		int newID = -1;
		while (returnValue.next()) {
			newID = returnValue.getInt(1);
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return newID;
	}

	/**
	 *
	 * @param o order that needs to be saved to the database
	 * @throws SQLException
	 * @throws IOException
	 * @requires o is not NULL. o's ID is -1, as it has not been assigned yet. The
	 *           pizzas do not exist in the database yet, and the topping inventory
	 *           will allow for these pizzas to be made
	 * @ensures o will be assigned an id and added to the database, along with all
	 *          of it's pizzas. Inventory levels will be updated appropriately
	 */
	public static void addOrder(Order o) throws SQLException, IOException {
		connect_to_db();
		/*
		 * add code to add the order to the DB. Remember that we're not just
		 * adding the order to the order DB table, but we're also recording
		 * the necessary data for the delivery, dinein, and pickup tables
		 */
		String order = "insert into customerOrder(CustomerOrderID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost) values (?, ?, (STR_TO_DATE(?, '%Y-%m-%d %H:%i')), ?, ?);";
		String dineIn_stmt = "insert into dineIn(DineInCustomerOrderID, DineInTableNumber) values (?, ?);";
		String pickUp_stmt = "insert into pickup(PickupCustomerOrderID, PickupCustomerID, PickupTimestamp) values(?, ?, (STR_TO_DATE(?, '%Y-%m-%d %H:%i')));";
		String delivery_stmt = "insert into delivery(DeliveryCustomerOrderID, DeliveryCustomerID, DeliveryStreet, DeliveryCity, DeliveryState, DeliveryZip) values (?, ?, ?, ?, ?, ?)";
		PreparedStatement prepStatement;

		try{

			// Getting max
			int newID = getMaxOrderID() + 1;


			// insert into customerOrder(CustomerOrderID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost)
			// values (?, ?, (STR_TO_DATE(?, '%Y-%m-%d %H:%i')), ?, ?);"
			prepStatement = conn.prepareStatement(order);
			prepStatement.setInt(1, newID);
			prepStatement.setString(2, o.getOrderType());
			prepStatement.setString(3, o.getDate());
			prepStatement.setDouble(4, o.getPrice());
			prepStatement.setDouble(5, o.getCost());

			int flag = prepStatement.executeUpdate();
			if(flag == 0) {
				System.out.println("Error adding order");
			}
			else {
				System.out.println("Adding Order to customerOrder table successful");
			}

			if(o instanceof DineinOrder){
				// "insert into dineIn(DineInCustomerOrderID, DineInTableNumber) values (?, ?);"
				prepStatement = conn.prepareStatement(dineIn_stmt);
				prepStatement.setInt(1, newID);
				prepStatement.setInt(2, ((DineinOrder) o).getTableNum() );
			}
			else if(o instanceof PickupOrder){
				// "insert into pickup(PickupCustomerOrderID, PickupCustomerID, PickupTimestamp) values(?, ?, (STR_TO_DATE(?, '%Y-%m-%d %H:%i')))"
				prepStatement = conn.prepareStatement(pickUp_stmt);
				prepStatement.setInt(1, newID);
				prepStatement.setInt(2, ((PickupOrder) o).getCustID() );
				prepStatement.setString(3, ((PickupOrder) o).getPickupTime());
			}
			else {//if(o instanceof DineinOrder){
				// "insert into delivery(DeliveryCustomerOrderID, DeliveryCustomerID, DeliveryStreet, DeliveryCity, DeliveryState, DeliveryZip) values (?, ?, ?, ?, ?, ?)"
				prepStatement = conn.prepareStatement(delivery_stmt);
				prepStatement.setInt(1, newID);
				prepStatement.setInt(2, ((DeliveryOrder) o).getCustID() );
				prepStatement.setString(3, ((DeliveryOrder) o).getStreet());
				prepStatement.setString(4, ((DeliveryOrder) o).getCity());
				prepStatement.setString(5, ((DeliveryOrder) o).getState());
				prepStatement.setString(6, ((DeliveryOrder) o).getZip());
			}

			flag = prepStatement.executeUpdate();
			if(flag == 0) {
				System.out.println("Error adding order");
			}
			else {
				System.out.println("Adding DineIn/Pick/Delivery Successful");
			}

		}
		catch (SQLException e) {
			System.out.println("Error adding Order");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}



		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();

	}

	public static int getMaxPizzaID() throws SQLException, IOException {
		connect_to_db();
		/*
		 * A function I needed because I forgot to make my pizzas auto increment in my DB.
		 * It goes and fetches the largest PizzaID in the pizza table.
		 * You wont need this function if you didn't forget to do that
		 */

		String maxID = "select max(PizzaID) from pizza";

		PreparedStatement prepStatement = conn.prepareStatement(maxID);
		ResultSet returnValue = prepStatement.executeQuery();

		int newID = -1;
		while(returnValue.next()){
			newID = returnValue.getInt(1);
		}





		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return newID;
	}

	public static void addPizza(Pizza p) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Add the code needed to insert the pizza into into the database.
		 * Keep in mind adding pizza discounts to that bridge table and 
		 * instance of topping usage to that bridge table if you have't accounted
		 * for that somewhere else.
		 */

		String pizza_stmt = "insert into pizza(PizzaID, PizzaCrustID, PizzaSizeID, PizzaOrderID, PizzaState, PizzaTotalPrice, PizzaTotalCost) values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement prepStatement;


		
		try {

			int newID = getMaxPizzaID() + 1;

			prepStatement = conn.prepareStatement(pizza_stmt);

			prepStatement.setInt(1, newID);
			prepStatement.setInt(2, p.getCrustID());
			prepStatement.setInt(3, p.getSizeID());
			prepStatement.setInt(4, p.getOrderID());
			prepStatement.setString(5, p.getPizzaState());
			prepStatement.setDouble(6, p.getPrice());
			prepStatement.setDouble(7, p.getCost());

			int flag = prepStatement.executeUpdate();
			if (flag == 0) {
				System.out.println("Error adding pizza");
			} else {
				System.out.println("Adding pizza Successful");
			}

		}
		catch (SQLException e) {
			System.out.println("Error adding Pizza");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}


	//this function will update toppings inventory in SQL and add entities to the Pizzatops table. Pass in the p pizza that is using t topping
	public static void useTopping(Pizza p, Topping t, boolean isDoubled) throws SQLException, IOException {
		connect_to_db();
		/*
		 * This function should 2 two things.
		 * We need to update the topping inventory every time we use t topping (accounting for extra toppings as well)
		 * and we need to add that instance of topping usage to the pizza-topping bridge if we haven't done that elsewhere
		 * Ideally, you should't let toppings go negative. If someone tries to use toppings that you don't have, just print
		 * that you've run out of that topping.
		 */

		try {

			String topCurrent_stmt = "insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter) values (?, ?, ?);";
			int ToppingCurrentBaseToppingID = ((t.getTopID() - 1) * size_max_int) + p.getSizeID();

			String topUpdate_stmt = "update topping set ToppingCurrentInventory = ? ToppingStaticCounter = ? where ToppingID = ?;";

			String basetopSelect_stmt = "select BaseToppingID, BaseToppingUnit from baseTopping where BaseToppingSizeID = ? and BaseToppingToppingID = ?";
			int BaseToppingID = -1;
			double BaseToppingUnit = -1.0;

			// Get the unit/portion size of topping
			PreparedStatement prepStatement = conn.prepareStatement(basetopSelect_stmt);
			prepStatement.setInt(1, p.getSizeID());
			prepStatement.setInt(2, t.getTopID());

			ResultSet queryReturn = prepStatement.executeQuery();
			while (queryReturn.next()) {
				BaseToppingID = queryReturn.getInt(1);
				BaseToppingUnit = queryReturn.getDouble(2);
			}

			double newinventory = t.getTopCurrentInventory() - BaseToppingUnit;
			if (isDoubled) {
				newinventory -= BaseToppingUnit;
			}

			if (newinventory > 0) {
				t.setTopCurrentInventory(newinventory);

				// "insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter) values (?, ?, ?);"
				prepStatement = conn.prepareStatement(topCurrent_stmt);

				int counter = isDoubled ? 2 : 1;

				prepStatement.setInt(1, p.getPizzaID());
				prepStatement.setInt(2, BaseToppingID);
				prepStatement.setInt(3, counter);

				int flag = prepStatement.executeUpdate();
				if (flag == 0) {
					System.out.println("Error adding Topping current");
				} else {
					System.out.println("Adding currentTopping Successful");
				}

				// "update topping set ToppingCurrentInventory = ? ToppingStaticCounter = ? where ToppingID = ?;"
				prepStatement = conn.prepareStatement(topUpdate_stmt);

				prepStatement.setInt(1, (int) newinventory);
				prepStatement.setInt(1, t.getTopStaticCounter() + counter);
				prepStatement.setInt(1, t.getTopID());

				flag = prepStatement.executeUpdate();
				if (flag == 0) {
					System.out.println("Error updating topping");
				} else {
					System.out.println("Updating Topping Successful");
				}

			} else {
				System.out.println("Cannot add selected topping, we are out of it");
				return;
			}
		} catch (SQLException e) {
			System.out.println("Error adding Current Topping on pizza");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void usePizzaDiscount(Pizza p, Discount d) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Helper function I used to update the pizza-discount bridge table.
		 * You might use this, you might not depending on where / how to want to update
		 * this table
		 */

		try {

			String pizzaDiscount_stmt = "insert into pizzaDiscount(PizzaDiscountPizzaID, PizzaDiscountDiscountID) values (?, ?);";
			PreparedStatement prepStatement = conn.prepareStatement(pizzaDiscount_stmt);

			prepStatement.setInt(1, p.getPizzaID());
			prepStatement.setInt(2, d.getDiscountID());

			int flag = prepStatement.executeUpdate();
			if (flag == 0) {
				System.out.println("Error inserting pizza Discount");
			} else {
				System.out.println("Inserting pizzaDiscount Successful");
			}
		} catch (SQLException e) {
			System.out.println("Error adding pizzaDiscount");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}
	
	public static void useOrderDiscount(Order o, Discount d) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Helper function I used to update the pizza-discount bridge table.
		 * You might use this, you might not depending on where / how to want to update
		 * this table
		 */
		try {

			String orderDiscount_stmt = "insert into orderDiscount(OrderDiscountOrderID, OrderDiscountDiscountID) values (?, ?);";
			PreparedStatement prepStatement = conn.prepareStatement(orderDiscount_stmt);

			prepStatement.setInt(1, o.getOrderID());
			prepStatement.setInt(2, d.getDiscountID());

			int flag = prepStatement.executeUpdate();
			if (flag == 0) {
				System.out.println("Error inserting order Discount");
			} else {
				System.out.println("Inserting orderDiscount Successful");
			}
		} catch (SQLException e) {
			System.out.println("Error adding orderDiscount");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}
	
	public static void addCustomer(Customer c) throws SQLException, IOException {
		connect_to_db();
		/*
		 * This should add a customer to the database
		 */
		try {

			String cust_stmt = "insert into customer(CustomerFname, CustomerLname, CustomerPhone) values (?, ?, ?)";
			PreparedStatement prepStatement = conn.prepareStatement(cust_stmt);

			prepStatement.setString(1, c.getFName());
			prepStatement.setString(2, c.getLName());
			prepStatement.setString(3, c.getPhone());

			int flag = prepStatement.executeUpdate();
			if (flag == 0) {
				System.out.println("Error inserting customer");
			} else {
				System.out.println("Inserting customer Successful");
			}
		} catch (SQLException e) {
			System.out.println("Error inserting customer");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void CompleteOrder(Order o) throws SQLException, IOException {
		connect_to_db();
		/*
		 * add code to mark an order as complete in the DB. You may have a boolean field
		 * for this, or maybe a completed time timestamp. However you have it.
		 */

		try {
			String order_stmt = "update pizza set PizzaState = ? where PizzaID = ?";
			PreparedStatement prepStatement = conn.prepareStatement(order_stmt);

			prepStatement.setString(1, DBNinja.order_complete);
			prepStatement.setInt(2, o.getOrderID());

			int flag = prepStatement.executeUpdate();
			if (flag == 0) {
				System.out.println("Error updating pizza state");
			} else {
				System.out.println("Updating pizza state Successful");
			}
		} catch (SQLException e) {
			System.out.println("Error updating pizza state");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void AddToInventory(Topping t, double toAdd) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Adds toAdd amount of topping to topping t.
		 */
		String updateTop = "update topping set ToppingCurrentInventory = ? where ToppingID = ?;";
		t.setTopCurrentInventory(t.getTopCurrentInventory() + toAdd);

		try {

			PreparedStatement prepStatement = conn.prepareStatement(updateTop);

			prepStatement.setDouble(1, t.getTopCurrentInventory());
			prepStatement.setInt(2, t.getTopID());

			int flag = prepStatement.executeUpdate();
			if (flag == 0) {
				System.out.println("Error updating topping inventory");
			} else {
				System.out.println("Updating topping inventory Successful");
			}


		} catch (SQLException e) {
			System.out.println("Error updating topping inventory");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}


		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void printInventory() throws SQLException, IOException {
		connect_to_db();

		/*
		 * I used this function to PRINT (not return) the inventory list.
		 * When you print the inventory (either here or somewhere else)
		 * be sure that you print it in a way that is readable.
		 *
		 *
		 *
		 * The topping list should also print in alphabetical order
		 */
		try {
			String selectInv_stmt = "select ToppingID, ToppingName, ToppingCurrentInventory from topping order by ToppingName;";
			PreparedStatement prepStatement = conn.prepareStatement(selectInv_stmt);
			ResultSet queryReturn = prepStatement.executeQuery();

			int topID = -1;
			String topName = "";
			Double currInv = -1.0;


			System.out.println("Topping ID | Topping Name | Current Inventory");

			while (queryReturn.next()) {
				topID = queryReturn.getInt(1);
				topName = queryReturn.getNString(2);
				currInv = queryReturn.getDouble(3);

				System.out.println(topID + " | " + topName + " | " + currInv);

			}

		} catch (SQLException e) {
			System.out.println("Error printing inventory");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}


		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}
	
	
	public static ArrayList<Topping> getInventory() throws SQLException, IOException {
		connect_to_db();
		/*
		 * This function actually returns the toppings. The toppings
		 * should be returned in alphabetical order if you don't
		 * plan on using a printInventory function
		 */


		ArrayList<Topping> allTopping = new ArrayList<>();

		try {

			String selectInv_stmt = "select ToppingID, ToppingName, ToppingCost, ToppingPrice, ToppingCurrentInventory, ToppingMinInventory, ToppingStaticCounter from topping order by ToppingID;";
			String topUnits_stmt = "select BaseToppingUnit from baseTopping where BaseToppingToppingID = ? order by BaseToppingSizeID;";

			PreparedStatement prep_selInv = conn.prepareStatement(selectInv_stmt);
			ResultSet queryReturn = prep_selInv.executeQuery();
			ResultSet topUnit;

			while (queryReturn.next()) {
				int topID = queryReturn.getInt(1);
				String topName = queryReturn.getNString(2);
				Double topCost = queryReturn.getDouble(3);
				Double topPrice = queryReturn.getDouble(4);
				Double currInv = queryReturn.getDouble(5);
				Double minInv = queryReturn.getDouble(6);
				int sticCntr = queryReturn.getInt(7);

				ArrayList<Double> unitSize = new ArrayList<>();


				PreparedStatement topUnit_prep = conn.prepareStatement(topUnits_stmt);
				topUnit_prep.setInt(1, topID);
				topUnit = topUnit_prep.executeQuery();
				while (topUnit.next()) {
					unitSize.add(topUnit.getDouble(1));
				}

				allTopping.add(new Topping(topID, topName, topCost, topPrice, minInv, currInv, unitSize));

			}

		} catch (SQLException e) {
			System.out.println("Error getting all Toppings");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return allTopping;
	}


	public static ArrayList<Order> getCurrentOrders() throws SQLException, IOException {
		connect_to_db();
		/*
		 * This function should return an arraylist of all of the orders.
		 * Remember that in Java, we account for supertypes and subtypes
		 * which means that when we create an arrayList of orders, that really
		 * means we have an arrayList of dineinOrders, deliveryOrders, and pickupOrders.
		 * 
		 * Also, like toppings, whenever we print out the orders using menu function 4 and 5
		 * these orders should print in order from newest to oldest.
		 */
		ArrayList<Order> allOrders = new ArrayList<>();

		try {

			String selectInv_stmt = "select CustomerOrderID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalCost, CustomerOrderTotalPrice, CustomerOrderCustomerID, CustomerOrderIsComplete from customerOrder order by CustomerOrderTimeStamp desc;";
			PreparedStatement prepStatement = conn.prepareStatement(selectInv_stmt);
			ResultSet queryReturn = prepStatement.executeQuery();

			while (queryReturn.next()) {
				int orderID = queryReturn.getInt(1);
				String orderType = queryReturn.getNString(2);
				String time = queryReturn.getTimestamp(3).toString();
				Double totalCost = queryReturn.getDouble(4);
				Double totalPrice = queryReturn.getDouble(5);
				int custID = queryReturn.getInt(6);
				Boolean isComp = queryReturn.getBoolean(7);

				//allOrders.add(new Order(orderID, orderType, time, totalPrice, totalCost));
				allOrders.add(new Order(orderID, custID, orderType, time, totalPrice, totalCost, isComp));
			}

		} catch (SQLException e) {
			System.out.println("Error getting all orders");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}



		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return allOrders;
	}
	
	public static ArrayList<Order> sortOrders(ArrayList<Order> list)
	{
		/*
		 * This was a function that I used to sort my arraylist based on date.
		 * You may or may not need this function depending on how you fetch
		 * your orders from the DB in the getCurrentOrders function.
		 */
		
		
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return null;
		
	}
	
	public static boolean checkDate(int year, int month, int day, String dateOfOrder)
	{
		//Helper function I used to help sort my dates. You likely wont need these
		
		
		
		
		
		
		
		
		return false;
	}
	
	
	/*
	 * The next 3 private functions help get the individual components of a SQL datetime object. 
	 * You're welcome to keep them or remove them.
	 */
	private static int getYear(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(0,4));
	}
	private static int getMonth(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(5, 7));
	}
	private static int getDay(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(8, 10));
	}



	
	
	
	public static double getBasePrice(int size, int crust) throws SQLException, IOException {
		connect_to_db();
		double bp = 0.0;
		// add code to get the base price (for the customer) for that size and crust pizza Depending on how
		// you store size & crust in your database, you may have to do a conversion

		try {

			// "select BaseCostPrize, BaseCostCost from baseCost where BaseCostSizeID = ? and BaseCostCrustID = ?;"
			String baseCost_stmt = "select BaseCostPrize from baseCost where BaseCostSizeID = ? and BaseCostCrustID = ?;";

			PreparedStatement prepStatement = conn.prepareStatement(baseCost_stmt);
			prepStatement.setInt(1, size);
			prepStatement.setInt(2, crust);

			ResultSet queryReturn = prepStatement.executeQuery();

			while (queryReturn.next()) {
				bp = queryReturn.getDouble(1);

			}

		} catch (SQLException e) {
			System.out.println("Error getting BasePrice");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return bp;
	}

	public static double getBaseCost(int size, int crust) throws SQLException, IOException {
		connect_to_db();
		double bp = 0.0;
		// add code to get the base price (for the customer) for that size and crust pizza Depending on how
		// you store size & crust in your database, you may have to do a conversion

		try {

			// "select BaseCostPrize, BaseCostCost from baseCost where BaseCostSizeID = ? and BaseCostCrustID = ?;"
			String baseCost_stmt = "select BaseCostCost from baseCost where BaseCostSizeID = ? and BaseCostCrustID = ?;";

			PreparedStatement prepStatement = conn.prepareStatement(baseCost_stmt);
			prepStatement.setInt(1, size);
			prepStatement.setInt(2, crust);

			ResultSet queryReturn = prepStatement.executeQuery();

			while (queryReturn.next()) {
				bp = queryReturn.getDouble(1);

			}

		} catch (SQLException e) {
			System.out.println("Error getting BaseCost");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return bp;
	}


	public static String getCustomerName(int CustID) throws SQLException, IOException {
		/*
		 *This is a helper function I used to fetch the name of a customer
		 *based on a customer ID. It actually gets called in the Order class
		 *so I'll keep the implementation here. You're welcome to change
		 *how the order print statements work so that you don't need this function.
		 */
		connect_to_db();
		String ret = "";

		try {

			String query = "Select CustomerFname, CustomerLname From customer WHERE CustomerID = " + CustID + ";";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);

			while (rset.next()) {
				ret = rset.getString(1) + " " + rset.getString(2);
			}
		} catch (SQLException e) {
			System.out.println("Error getting customer name");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		conn.close();
		return ret;
	}
	


	
	public static ArrayList<Discount> getDiscountList() throws SQLException, IOException {
		ArrayList<Discount> discs = new ArrayList<Discount>();
		connect_to_db();
		//returns a list of all the discounts.

		try {

			String disc_stmt = "Select DiscountID, DiscountName, DiscountValue, DiscountIsPercent From discount;";
			Statement stmt = conn.createStatement();
			PreparedStatement prepStatement = conn.prepareStatement(disc_stmt);

			ResultSet queryReturn = prepStatement.executeQuery();

			while (queryReturn.next()) {
				int id = queryReturn.getInt(1);
				String name = queryReturn.getString(2);
				double value = queryReturn.getDouble(3);
				boolean isPer = queryReturn.getBoolean(4);

				discs.add(new Discount(id, name, value, isPer));
			}
		} catch (SQLException e) {
			System.out.println("Error getting all discount");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return discs;
	}


	public static ArrayList<Customer> getCustomerList() throws SQLException, IOException {
		ArrayList<Customer> custs = new ArrayList<Customer>();
		connect_to_db();
		/*
		 * return an arrayList of all the customers. These customers should
		 *print in alphabetical order, so account for that as you see fit.
		*/
		try {

			String cust_stmt = "Select CustomerID, CustomerFname, CustomerLname, CustomerPhone From customer;";
			PreparedStatement stmt = conn.prepareStatement(cust_stmt);
			ResultSet queryReturn = stmt.executeQuery(cust_stmt);

			while (queryReturn.next()) {
				custs.add(new Customer(queryReturn.getInt(1), queryReturn.getString(2), queryReturn.getString(3), queryReturn.getString(4)));
			}
		} catch (SQLException e) {
			System.out.println("Error getting all customer");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return custs;
	}
	
	public static int getNextOrderID() throws SQLException, IOException
	{
		/*
		 * A helper function I had to use because I forgot to make
		 * my OrderID auto increment...You can remove it if you
		 * did not forget to auto increment your orderID.
		 */
		
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return -1;
	}
	
	public static void printToppingPopReport() throws SQLException, IOException {
		connect_to_db();
		/*
		 * Prints the ToppingPopularity view. Remember that these views
		 * need to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 *
		 * I'm not picky about how they print (other than that it should
		 * be in alphabetical order by name), just make sure it's readable.
		 */

		try {

			String toprept_stmt = "select Topping, ToppingCount from ToppingPopularity order by Topping;";
			PreparedStatement stmt = conn.prepareStatement(toprept_stmt);
			ResultSet queryReturn = stmt.executeQuery(toprept_stmt);
			System.out.println("Topping \t | Topping Count");
			while (queryReturn.next()) {
				System.out.println(queryReturn.getString(1) + " \t | " + queryReturn.getInt(2));
			}
		} catch (SQLException e) {
			System.out.println("Error getting topping popularity report");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}


		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}
	
	public static void printProfitByPizzaReport() throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Prints the ProfitByPizza view. Remember that these views
		 * need to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 * 
		 * I'm not picky about how they print, just make sure it's readable.
		 */
		try {

			//String profitrept_stmt = "select 'Pizza Size', 'Pizza Crust', Profit, LastOrderDate from ProfitByPizza;";
			String profitrept_stmt = "select * from ProfitByPizza;";

			PreparedStatement stmt = conn.prepareStatement(profitrept_stmt);
			ResultSet queryReturn = stmt.executeQuery(profitrept_stmt);
			System.out.println("Size | Crust | Profit | Last Order Date");
			while (queryReturn.next()) {
				System.out.println(
						queryReturn.getString(1) + " | " +
						queryReturn.getString(2) + " | " +
						queryReturn.getDouble(3) + " | " +
						queryReturn.getString(4));
			}

		} catch (SQLException e) {
			System.out.println("Error getting Profit by Pizza report");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}
	
	public static void printProfitByOrderType() throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Prints the ProfitByOrderType view. Remember that these views
		 * need to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 * 
		 * I'm not picky about how they print, just make sure it's readable.
		 */

		try {

			String profitrept_stmt = "select CustomerType, OrderDate, TotalOrderPrice, TotalOrderCost, Profit from ProfitByOrderType;";

			PreparedStatement stmt = conn.prepareStatement(profitrept_stmt);
			ResultSet queryReturn = stmt.executeQuery(profitrept_stmt);
			System.out.println("CustomerType | OrderDate | TotalOrderPrice | TotalOrderCost | Profit");
			while (queryReturn.next()) {
				System.out.println(
						queryReturn.getString(1) + " | " +
								queryReturn.getString(2) + " | " +
								queryReturn.getDouble(3) + " | " +
								queryReturn.getDouble(4) + " | " +
								queryReturn.getDouble(5));
			}

		} catch (SQLException e) {
			System.out.println("Error getting Profit by order type report");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}
}
