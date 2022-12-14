// Sahil Patel and Kevin Mody

package cpsc4620;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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

	public final static String order_notComplete = "Not-Complete";
	public final static String order_complete = "Complete";

	public final static DateTimeFormatter Date_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public final static SimpleDateFormat Date_Simple_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * This function will handle the connection to the database
	 * 
	 * @return true if the connection was successfully made
	 */
	private static boolean connect_to_db() {

		try {
			conn = DBConnector.make_connection();
			return true;
		} catch (SQLException | IOException e) {
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
		int newID = -1;

		try {
			String maxID = "select max(CustomerOrderID) from customerOrder";

			PreparedStatement prepStatement = conn.prepareStatement(maxID);
			ResultSet returnValue = prepStatement.executeQuery();


			while (returnValue.next()) {
				newID = returnValue.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("Error getting max order id");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		//conn.close();
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
		//String order = "insert into customerOrder(CustomerOrderID, CustomerOrderCustomerID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost, CustomerOrderIsComplete) values (?, ?, ?, (STR_TO_DATE('?', '%Y-%m-%d %H:%i:%s')), ?, ?, ?);";
		String order = "insert into customerOrder(CustomerOrderID, CustomerOrderCustomerID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost, CustomerOrderIsComplete) values (?, ?, ?, ?, ?, ?, ?);";
		String dineIn_stmt = "insert into dineIn(DineInCustomerOrderID, DineInTableNumber) values (?, ?);";
		//String pickUp_stmt = "insert into pickup(PickupCustomerOrderID, PickupTimestamp) values(?, (STR_TO_DATE('?', '%Y-%m-%d %H:%i:%s')));";
		String pickUp_stmt = "insert into pickup(PickupCustomerOrderID, PickupTimestamp) values(?, ?);";
		String delivery_stmt = "insert into delivery(DeliveryCustomerOrderID, DeliveryStreet, DeliveryCity, DeliveryState, DeliveryZip) values (?, ?, ?, ?, ?)";

		try{

			// Getting max
			int oID = getMaxOrderID() + 1;
			o.setOrderID(oID);

			//Date oDate = DBNinja.Date_formatter.parse(o.getDate());
			Date oDate = DBNinja.Date_Simple_format.parse(o.getDate());
			Timestamp oSQLDate =  new java.sql.Timestamp(oDate.getTime());


			// int, int, String, String,
			// (CustomerOrderID, CustomerOrderCustomerID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost, CustomerOrderIsComplete)
			PreparedStatement prepStatement = conn.prepareStatement(order);

			prepStatement.setInt(1, oID);
			prepStatement.setInt(2, o.getCustID());

			prepStatement.setString(3, o.getOrderType());
			prepStatement.setTimestamp(4, oSQLDate);

			prepStatement.setDouble(5, o.getPrice());
			prepStatement.setDouble(6, o.getCost());

			prepStatement.setBoolean(7, o.getIsComplete());


			int flag = prepStatement.executeUpdate();
			if(flag == 0) {
				System.out.println("Error adding order to customerOrder toable");
			}
			else {
				//System.out.println("Adding Order to customerOrder table successful");

			}

			if(o instanceof DineinOrder){
				// "insert into dineIn(DineInCustomerOrderID, DineInTableNumber) values (?, ?);"
				PreparedStatement prepDinein = conn.prepareStatement(dineIn_stmt);
				prepDinein.setInt(1, oID);
				prepDinein.setInt(2, ((DineinOrder) o).getTableNum() );

				flag = prepDinein.executeUpdate();
				if(flag == 0) {
					System.out.println("Error adding Dine-In Order");
				}
			}
			else if(o instanceof PickupOrder){
				// "insert into pickup(PickupCustomerOrderID, PickupTimestamp) values(?, ?);";

				Date pDate = DBNinja.Date_Simple_format.parse(((PickupOrder) o).getPickupTime());
				Timestamp pSQLDate =  new java.sql.Timestamp(pDate.getTime());

				PreparedStatement prepPickup = conn.prepareStatement(pickUp_stmt);
				prepPickup.setInt(1, oID);
				prepPickup.setTimestamp(2, pSQLDate);

				flag = prepPickup.executeUpdate();
				if(flag == 0) {
					System.out.println("Error adding Pickup Order");
				}

			}
			else {
				//if(o instanceof DeliveryOrder){
				// "insert into delivery(DeliveryCustomerOrderID, DeliveryStreet, DeliveryCity, DeliveryState, DeliveryZip) values (?, ?, ?, ?, ?)"
				PreparedStatement prepdelivery = conn.prepareStatement(delivery_stmt);
				prepdelivery.setInt(1, oID);
				prepdelivery.setString(2, ((DeliveryOrder) o).getStreet());
				prepdelivery.setString(3, ((DeliveryOrder) o).getCity());
				prepdelivery.setString(4, ((DeliveryOrder) o).getState());
				prepdelivery.setString(5, ((DeliveryOrder) o).getZip());

				flag = prepdelivery.executeUpdate();
				if(flag == 0) {
					System.out.println("Error adding Delivery Order");
				}
			}





			if(flag != 0) {
				System.out.println("Adding DineIn/Pick/Delivery Successful");

				// Adding all pizza
				for (Pizza p : o.getPizzaList()) {
					DBNinja.addPizza(p, oID);
				}

				// Adding all discounts
				for (Discount d : o.getDiscountList()) {
					DBNinja.addOrderDiscount(oID, d.getDiscountID());
				}
			}

		}
		catch (SQLException e) {
			System.out.println("Error adding Order - addOrder");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		} catch (ParseException e) {
			e.printStackTrace();
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
		int newID = -1;
		try {
			String maxID = "select max(PizzaID) from pizza";

			PreparedStatement prepStatement = conn.prepareStatement(maxID);
			ResultSet returnValue = prepStatement.executeQuery();


			while (returnValue.next()) {
				newID = returnValue.getInt(1);
			}


		} catch (SQLException e) {
			System.out.println("Error getting max pizza id");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return newID;
	}

	public static void addPizza(Pizza p, int orderID) throws SQLException, IOException {

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

			int pID = getMaxPizzaID() + 1;
			p.setPizzaID(pID);
			p.setOrderID(orderID);

			//System.out.println("Is connection Closed (addPizza): " + conn.isClosed());

			if (conn.isClosed()) {
				connect_to_db();
			}

			//System.out.println("Is connection Closed (addPizza): " + conn.isClosed());

			prepStatement = conn.prepareStatement(pizza_stmt);


			prepStatement.setInt(1, pID);
			prepStatement.setInt(2, p.getCrustID());
			prepStatement.setInt(3, p.getSizeID());
			prepStatement.setInt(4, p.getOrderID());
			prepStatement.setString(5, p.getPizzaState());
			prepStatement.setDouble(6, p.getPrice());
			prepStatement.setDouble(7, p.getCost());

			int flag = prepStatement.executeUpdate();

			//System.out.println("Is connection Closed (addPizza): " + conn.isClosed());

			if (flag == 0) {
				System.out.println("Error adding pizza");
			} else {
				System.out.println("Adding pizza Successful");

				// Adding pizza topping
				int i = 0;
				for (Topping t : p.getToppings()) {
					DBNinja.addPizzaTopping(pID, p.getSizeID(), t.getTopID(), p.getIsToppingDoubled(i));
					i += 1;
				}

				//Adding pizza discount
				for (Discount d : p.getDiscounts()) {
					DBNinja.addPizzaDiscount(pID, d.getDiscountID());
				}
			}


		} catch (SQLException e) {
			System.out.println("Error adding Pizza");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void addPizzaTopping(int pizzaID, int sizeID, int toppingID, boolean isDouble) throws IOException, SQLException {
		connect_to_db();

		try {
			String topCurr_stmt = "insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter) values(?, ?, ?)";
			int ctr = isDouble ? 2 : 1;
			int basetopID = ((toppingID-1)*4) + sizeID;

			PreparedStatement prepStmt = conn.prepareStatement(topCurr_stmt);
			prepStmt.setInt(1, pizzaID);
			prepStmt.setInt(2, basetopID);
			prepStmt.setInt(3, ctr);

			int flag = prepStmt.executeUpdate();
			if (flag == 0) {
				System.out.println("Error adding toppingCurrent");
			} else {
				System.out.println("Adding toppingCurrent Successful");
			}


		} catch (SQLException e) {
			System.out.println("Error adding toppingCurrent");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void addPizzaDiscount(int pizzaID, int discountID) throws IOException, SQLException {
		connect_to_db();

		try {
			String topCurr_stmt = "insert into pizzaDiscount(PizzaDiscountPizzaID, PizzaDiscountDiscountID) values(?, ?)";

			PreparedStatement prepStmt = conn.prepareStatement(topCurr_stmt);
			prepStmt.setInt(1, pizzaID);
			prepStmt.setInt(2, discountID);

			int flag = prepStmt.executeUpdate();
			if (flag == 0) {
				System.out.println("Error adding pizzaDiscount");
			} else {
				System.out.println("Adding pizzaDiscount Successful");
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

	public static void addOrderDiscount(int orderID, int discountID) throws IOException, SQLException {
		connect_to_db();

		try {
			String topCurr_stmt = "insert into orderDiscount(OrderDiscountOrderID, OrderDiscountDiscountID) values (?, ?)";

			PreparedStatement prepStmt = conn.prepareStatement(topCurr_stmt);
			prepStmt.setInt(1, orderID);
			prepStmt.setInt(2, discountID);

			int flag = prepStmt.executeUpdate();
			if (flag == 0) {
				System.out.println("Error adding orderDiscount");
			} else {
				System.out.println("Adding orderDiscount Successful");
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

	public static int getMaxCustID() throws SQLException, IOException {
		connect_to_db();
		/*
		 * A function I needed because I forgot to make my pizzas auto increment in my DB.
		 * It goes and fetches the largest PizzaID in the pizza table.
		 * You wont need this function if you didn't forget to do that
		 */
		int newID = -1;
		try {
			String maxID = "select max(CustomerID) from customer;";

			PreparedStatement prepStatement = conn.prepareStatement(maxID);
			ResultSet returnValue = prepStatement.executeQuery();


			while (returnValue.next()) {
				newID = returnValue.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Error getting max customer id");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return newID;
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

	public static void addCustomer(Customer c) throws SQLException, IOException {
		connect_to_db();
		/*
		 * This should add a customer to the database
		 */
		try {

			String cust_stmt = "insert into customer(CustomerFname, CustomerLname, CustomerPhone, CustomerID) values (?, ?, ?, ?)";
			PreparedStatement prepStatement = conn.prepareStatement(cust_stmt);

			prepStatement.setString(1, c.getFName());
			prepStatement.setString(2, c.getLName());
			prepStatement.setString(3, c.getPhone());
			prepStatement.setInt(4, c.getCustID());

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
			String order_stmt = "update customerOrder set CustomerOrderIsComplete = ? where CustomerOrderID = ?";
			PreparedStatement prepStatement = conn.prepareStatement(order_stmt);

			prepStatement.setBoolean(1, true);
			prepStatement.setInt(2, o.getOrderID());

			int flag = prepStatement.executeUpdate();
			if (flag == 0) {
				System.out.println("Error marking order as complete");
			} else {
				System.out.println("Order Marked as complete");
			}
		} catch (SQLException e) {
			System.out.println("Error marking order as complete");
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
				System.out.println("Error updating topping inventory - AddToInventory");
			} else {
				//System.out.println("Updating topping inventory Successful");
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

	public static void updateInventory(ArrayList<Topping> allToppings) throws SQLException {
		connect_to_db();

		String updateTop = "update topping set ToppingStaticCounter = ? where ToppingID = ?;";

		try {

			for (Topping t : allToppings) {

				PreparedStatement prepStatement = conn.prepareStatement(updateTop);

				prepStatement.setInt(1, t.getTopStaticCounter());
				prepStatement.setInt(2, t.getTopID());

				int flag = prepStatement.executeUpdate();
				if (flag == 0) {
					System.out.println("Error updating topping inventory - updateInventory");
				} else {
					//System.out.println("Updating topping inventory Successful");
				}
			}


		} catch (SQLException e) {
			System.out.println("Error updating topping inventory");
			while (e != null) {
				System.out.println("Message     : " + e.getMessage());
				e = e.getNextException();
			}
		}

		conn.close();
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
	 *
	 * // assumes date format 'YYYY-MM-DD HH:mm:ss'
	 */
	private static int getYear(String date) {
		return Integer.parseInt(date.substring(0, 4));
	}
	// assumes date format 'YYYY-MM-DD HH:mm:ss'
	private static int getMonth(String date) {
		return Integer.parseInt(date.substring(5, 7));
	}
	// assumes date format 'YYYY-MM-DD HH:mm:ss'
	private static int getDay(String date) {
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

			String cust_stmt = "Select CustomerID, CustomerFname, CustomerLname, CustomerPhone From customer where CustomerID != 0;";
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
