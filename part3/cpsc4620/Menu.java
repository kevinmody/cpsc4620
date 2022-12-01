package cpsc4620;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
/*
 * This file is where the front end magic happens.
 * 
 * You will have to write the functionality of each of these menu options' respective functions.
 * 
 * This file should need to access your DB at all, it should make calls to the DBNinja that will do all the connections.
 * 
 * You can add and remove functions as you see necessary. But you MUST have all 8 menu functions (9 including exit)
 * 
 * Simply removing menu functions because you don't know how to implement it will result in a major error penalty (akin to your program crashing)
 * 
 * Speaking of crashing. Your program shouldn't do it. Use exceptions, or if statements, or whatever it is you need to do to keep your program from breaking.
 * 
 * 
 */

public class Menu {

	public static Scanner reader = new Scanner(System.in);


	public static void main(String[] args) throws SQLException, IOException {
		//BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Welcome to Kevin and Sahil Bro's Pizzeria!");

		int menu_option = 0;

		// present a menu of options and take their selection
		PrintMenu();
		String option = reader.nextLine();
		menu_option = Integer.parseInt(option);

		while (menu_option != 9) {
			switch (menu_option) {
				case 1:// enter order
					EnterOrder();
					break;
				case 2:// view customers
					viewCustomers();
					break;
				case 3:// enter customer
					EnterCustomer();
					break;
				case 4:// view order
					// open/closed/date
					ViewOrders();
					break;
				case 5:// mark order as complete
					MarkOrderAsComplete();
					break;
				case 6:// view inventory levels
					ViewInventoryLevels();
					break;
				case 7:// add to inventory
					AddInventory();
					break;
				case 8:// view reports
					PrintReports();
					break;
				default:
					System.out.println("Invalid option selected, please select from 1 - 9.");
					break;
			}
			PrintMenu();
			//option = reader.nextLine();
			//menu_option = Integer.parseInt(option);
			menu_option = Integer.parseInt(reader.nextLine());
		}

	}

	public static void PrintMenu() {
		System.out.println("\n\nPlease enter a menu option:");
		System.out.println("1. Enter a new order");
		System.out.println("2. View Customers ");
		System.out.println("3. Enter a new Customer ");
		System.out.println("4. View orders");
		System.out.println("5. Mark an order as completed");
		System.out.println("6. View Inventory Levels");
		System.out.println("7. Add Inventory");
		System.out.println("8. View Reports");
		System.out.println("9. Exit\n\n");
		System.out.println("Enter your option: ");
	}

	// allow for a new order to be placed
	public static void EnterOrder() throws SQLException, IOException {
		//BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		/*
		 * EnterOrder should do the following:
		 * Ask if the order is for an existing customer -> If yes, select the customer. If no -> create the customer (as if the menu option 2 was selected).
		 *
		 * Ask if the order is delivery, pickup, or dinein (ask for orderType specific information when needed)
		 *
		 * Build the pizza (there's a function for this)
		 *
		 * ask if more pizzas should be be created. if yes, go back to building your pizza.
		 *
		 * Apply order discounts as needed (including to the DB)
		 *
		 * apply the pizza to the order (including to the DB)
		 *
		 * return to menu
		 */

		Customer customer = new Customer();
		boolean customer_found = false;
		//Scanner reader = new Scanner(System.in);

		while (!customer_found) {
			System.out.println("Are You an Existing Customer or a New Customer?");
			System.out.println("1. Existing Customer");
			System.out.println("2. New Customer");
			System.out.println("Enter the Corresponding Number.");
			int newcustomer = Integer.parseInt(reader.nextLine());

			ArrayList<Customer> customer_list;
			switch (newcustomer) {
				//Existing Customer
				case 1:
					boolean customer_number_found = false;

					customer_list = DBNinja.getCustomerList();

					while (!customer_number_found) {

						System.out.println("List of Customers:");
						viewCustomers();

						System.out.println("Enter Your Customer ID");
						int CustID = Integer.parseInt(reader.nextLine());
						//check if customer exists

						for (Customer cus : customer_list) {
							if (cus.getCustID() == CustID) {
								customer = cus;
								customer_number_found = true;
								customer_found = true;
							}
						}
						if (!customer_number_found) {
							System.out.println("Invalid Selection. Try Again.");
						}
					}
					break;

				//New Customer
				case 2:
					customer = EnterCustomer();
					customer_found = true;
					break;
				default:
					System.out.println("Invalid Selection. Try Again.");
					customer_found = false;
			}
		}

		//Setting up an order

		System.out.println("What Type of Order is This?");
		boolean got_order_type = false;
		Order order = new Order();

		while (!got_order_type) {
			System.out.println("1. Dine-in");
			System.out.println("2. Pickup");
			System.out.println("3. Delivery");
			System.out.println("Enter the Corresponding Number.");
			int order_type = Integer.parseInt(reader.nextLine());

			/*
			//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			date = formatter.format(date);

			 */

			switch (order_type) {
				case 1:
					order = addDinein(customer);
					order.setOrderType(DBNinja.dine_in);
					got_order_type = true;
					break;
				case 2:
					order = addPickup(customer);
					order.setOrderType(DBNinja.pickup);
					got_order_type = true;
					break;
				case 3:
					order = addDelivery(customer);
					order.setOrderType(DBNinja.delivery);
					got_order_type = true;
					break;
				default:
					System.out.println("Invalid Option for Type of order");
					break;
			}
		}


		//creating a pizza
		buildPizza(order);

		boolean all_pizza_added = false;
		while (!all_pizza_added) {
			//pizza_list.add(buildPizza(order));

			boolean add_another_answer = false;
			while (!add_another_answer) {
				System.out.println("Would You Like To Add Another Pizza?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				System.out.println("Enter the Corresponding Number.");
				int response = Integer.parseInt(reader.nextLine());
				switch (response) {
					case 1:
						add_another_answer = true;

						buildPizza(order);
						break;
					case 2:
						add_another_answer = true;
						all_pizza_added = true;
						break;
					default:
						System.out.println("Invalid Selection. Try Again.");
				}
			}
		}

		DBNinja.addOrder(order);

		System.out.println("Finished adding order...Returning to menu...");
	}

	public static Order addDinein(Customer c) {
		System.out.println("You have selected Dine-in order.");

		System.out.println("Enter order placed date (in format yyyy-MM-dd HH:mm:ss - 24 hour format): ");
		String date = reader.nextLine();

		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		System.out.println("Please enter table number: ");
		int tableNum = Integer.parseInt(reader.nextLine());

		Order o = new DineinOrder(-1, c.getCustID(), date, 0, 0, false, tableNum);
		return o;
	}

	public static Order addDelivery(Customer c) {
		System.out.println("You have selected Delivery order.");

		System.out.println("Enter order placed date (in format yyyy-MM-dd HH:mm:ss - 24 hour format): ");
		String date = reader.nextLine();

		if(c.getStreet().isBlank() || c.getCity().isBlank() || c.getState().isBlank() || c.getZip().isBlank()) {
			addCustomerAddress(c);
		}


		Order o = new DeliveryOrder(-1, c.getCustID(), date, 0, 0, false, c.getStreet(), c.getCity(), c.getState(), c.getZip());
		return o;
	}

	public static Order addPickup(Customer c) {
		System.out.println("You have selected Pickup order.");

		System.out.println("Enter order placed date (in format yyyy-MM-dd HH:mm:ss - 24 hour format): ");
		String date = reader.nextLine();

		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		System.out.println("Please enter the time you will pick it up (in format yyyy-MM-dd HH:mm:ss - 24 hour format): ");
		String pickup_d = reader.nextLine();

		Order o = new PickupOrder(-1, c.getCustID(), date, 0, 0, 0, false);
		return o;
	}


	public static void viewCustomers() throws IOException, SQLException {
		/*
		 * Simply print out all of the customers from the database.
		 */
		ArrayList<Customer> allCust = DBNinja.getCustomerList();

		System.out.println("Customer ID | Customer Name");
		for (Customer c : allCust) {
			System.out.println(c.toString());
		}
	}


	// Enter a new customer in the database
	public static Customer EnterCustomer() throws SQLException, IOException {
		/*
		 * Ask what the name of the customer is. YOU MUST TELL ME (the grader) HOW TO FORMAT THE FIRST NAME, LAST NAME, AND PHONE NUMBER.
		 * If you ask for first and last name one at a time, tell me to insert First name <enter> Last Name (or separate them by different print statements)
		 * If you want them in the same line, tell me (First Name <space> Last Name).
		 *
		 * same with phone number. If there's hyphens, tell me XXX-XXX-XXXX. For spaces, XXX XXX XXXX. For nothing XXXXXXXXXXXX.
		 *
		 * I don't care what the format is as long as you tell me what it is, but if I have to guess what your input is I will not be a happy grader
		 *
		 * Once you get the name and phone number (and anything else your design might have) add it to the DB
		 */
		//BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Please Enter the Customer First name: ");
		String fname = reader.nextLine();

		System.out.println("Please Enter the Customer Last name: ");
		String lname = reader.nextLine();

		System.out.println("Please Enter 10-digit Customer phone number (without any spaces or special character in between): ");
		String phone = reader.nextLine();


		int cID = DBNinja.getMaxCustID() + 1;
		Customer c = new Customer(cID, fname, lname, phone);


		System.out.println("Is this a customer who wants delivery? Enter Y/N");
		String option = reader.nextLine().toLowerCase(Locale.ROOT);

		if (option.equals("y")) {
			addCustomerAddress(c);
		}

		DBNinja.addCustomer(c);
		return c;

	}

	public static void addCustomerAddress(Customer c) {

		String street = "";
		String city = "";
		String state = "";
		String zip = "";

		System.out.println("Please enter the street and house humber: ");
		street = reader.nextLine();

		System.out.println("Please enter the city: ");
		city = reader.nextLine();

		System.out.println("Please enter the state (two letter abbreviation): ");
		state = reader.nextLine();

		System.out.println("Please enter the 5 digit zip code: ");
		zip = reader.nextLine();

		c.setZip(zip);
		c.setState(state);
		c.setCity(city);
		c.setStreet(street);
	}

	// View any orders that are not marked as completed
	public static void ViewOrders() throws SQLException, IOException {
		/*
		 * This should be subdivided into two options: print all orders (using simplified view) and print all orders (using simplified view) since a specific date.
		 *
		 * Once you print the orders (using either sub option) you should then ask which order I want to see in detail
		 *
		 * When I enter the order, print out all the information about that order, not just the simplified view.
		 *
		 */
		ArrayList<Order> currOrders = DBNinja.getCurrentOrders();

		//Print off high level information about the order
		int o_count = 1;
		for (Order o : currOrders) {
			System.out.println(o_count + ": " + o.toSimplePrint());
			o_count++;
		}

		// User can now select an order and get the full detail
		System.out.println("Which order would you like to see in detail? Enter the number: ");
		//BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int chosen_order = Integer.parseInt(reader.nextLine());
		if (chosen_order <= currOrders.size()) {
			System.out.println(currOrders.get(chosen_order - 1).toString());
		} else {
			System.out.println("Incorrect entry, not an option");
		}
	}


	// When an order is completed, we need to make sure it is marked as complete
	public static void MarkOrderAsComplete() throws SQLException, IOException {
		/*All orders that are created through java (part 3, not the 7 orders from part 2) should start as incomplete
		 *
		 * When this function is called, you should print all of the orders marked as complete
		 * and allow the user to choose which of the incomplete orders they wish to mark as complete
		 *
		 */
		ArrayList<Order> currOrders = DBNinja.getCurrentOrders();
		int o_count = 1;

		//see all open orders
		for (Order o : currOrders) {
			System.out.print(o_count + ": " + o.toSimplePrint());
			o_count++;
		}

		//pick the order to mark as completed
		System.out.println("Which order would you like mark as complete? Enter the number: ");

		int chosen_order = Integer.parseInt(reader.nextLine());

		if (chosen_order <= currOrders.size()) {
			DBNinja.CompleteOrder(currOrders.get(chosen_order - 1));
		} else {
			System.out.println("Incorrect entry, not an option");
		}

	}

	// See the list of inventory and it's current level
	public static void ViewInventoryLevels() throws SQLException, IOException {
		//print the inventory. I am really just concerned with the ID, the name, and the current inventory

		ArrayList<Topping> curInventory = DBNinja.getInventory();
		for (Topping t : curInventory) {
			//System.out.println(t.getTopID() + " : " + t.getTopName() + " : " + t.getTopCurrentInventory());
			System.out.printf("ID: %-2d | %-20s - %f\n", t.getTopID(), t.getTopName(), t.getTopCurrentInventory());
		}
	}

	// Select an inventory item and add more to the inventory level to re-stock the
	// inventory
	public static void AddInventory() throws SQLException, IOException {
		/*
		 * This should print the current inventory and then ask the user which topping they want to add more to and how much to add
		 */


		ArrayList<Topping> curInventory = DBNinja.getInventory();
		for (Topping t : curInventory) {
			//System.out.println(t.getTopID() + " : " + t.getTopName() + " : " + t.getTopCurrentInventory());
			System.out.printf("ID: %-2d | %-20s - %f\n", t.getTopID(), t.getTopName(), t.getTopCurrentInventory());
		}

		System.out.println("Which topping's inventory's would you like to update (Use number) : ");
		int topID = Integer.parseInt(reader.nextLine());
		while (topID < 1 || topID > curInventory.size()) {
			System.out.println("Invalid topping index chosen. Select again");
			System.out.println("Which topping's inventory's would you like to update : ");
			topID = Integer.parseInt(reader.nextLine());
		}
		System.out.println("How much you want to update : ");
		double add = Double.parseDouble(reader.nextLine());
		while (add <= 0) {
			System.out.println("You cannot update inventory in negative or zero.\nHow much you want to update : ");
			add = Double.parseDouble(reader.nextLine());
		}

		Topping t = curInventory.get(topID - 1);
		DBNinja.AddToInventory(t, add);
	}

	// A function that builds a pizza. Used in our add new order function
	public static Pizza buildPizza(Order o) throws SQLException, IOException {

		/*
		 * This is a helper function for first menu option.
		 *
		 * It should ask which size pizza the user wants and the crustType.
		 *
		 * Once the pizza is created, it should be added to the DB.
		 *
		 * We also need to add toppings to the pizza. (Which means we not only need to add toppings here, but also our bridge table)
		 *
		 * We then need to add pizza discounts (again, to here and to the database)
		 *
		 * Once the discounts are added, we can return the pizza
		 */
		//BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


		//select size
		System.out.println("What size is the pizza? \n1.) Small \n2.) Medium\n3.) Large\n4.) X-Large \n Enter the corresponding number: ");
		int size = Integer.parseInt(reader.nextLine());

		while (size < DBNinja.size_s || size > DBNinja.size_xl) {
			System.out.println("Invalid value selected. Please select from the below options.");
			System.out.println("What size is the pizza? \n1.) Small \n2.) Medium\n3.) Large\n4.) X-Large \n Enter the corresponding number: ");
			size = Integer.parseInt(reader.nextLine());
		}


		//select crust
		System.out.println("What crust for this pizza? \n1.) Thin \n2.) Original\n3.) Pan\n4.) Gluten-Free \n Enter the corresponding number: ");
		int crust = Integer.parseInt(reader.nextLine());

		while (crust < DBNinja.crust_thin || crust > DBNinja.crust_gf) {
			System.out.println("Invalid value selected. Please select from the below options.");
			System.out.println("What crust for this pizza? \n1.) Thin \n2.) Original\n3.) Pan\n4.) Gluten-Free \n Enter the corresponding number: ");
			crust = Integer.parseInt(reader.nextLine());
		}

		//get the base price ??
		double base_price = DBNinja.getBasePrice(size, crust);
		double base_cost = DBNinja.getBaseCost(size, crust);

		Pizza newPizza = new Pizza(-1, size, crust, o.getOrderID(), DBNinja.order_notComplete, o.getDate(), base_price, base_cost);

		//add toppings to the pizza
		int chosen_t = 0;
		ArrayList<Topping> curInventory = DBNinja.getInventory();

		while (chosen_t != -1) {
			for (Topping t : curInventory) {
				System.out.println(t.getTopID() + " - " + t.getTopName());
			}
			System.out.println("Which topping do you want to add? Enter the number. Enter -1 to stop adding toppings: ");
			chosen_t = Integer.parseInt(reader.nextLine());

			if (chosen_t != -1) {
				if (chosen_t > 0 && chosen_t <= curInventory.size() && curInventory.get(chosen_t-1).canUseTopping(size)) {

					System.out.println("Would you like to add extra of this topping? (Y/N) : ");
					String yn = reader.nextLine().toLowerCase(Locale.ROOT);
					boolean isExtra = yn.equals("y");

					newPizza.addToppings(curInventory.get(chosen_t - 1), isExtra);

				} else {
					System.out.println("Incorrect entry, not an option");
					chosen_t = Integer.parseInt(reader.nextLine());
				}
			}
		}

		System.out.println("Should any discounts be added for this pizza? Enter Y or N: ");
		String yn = reader.nextLine().toLowerCase(Locale.ROOT);
		if (yn.equals("y")) {
			// add discounts
			int chosen_d = 0;
			ArrayList<Discount> discs = DBNinja.getDiscountList();
			while (chosen_d != -1) {
				for (Discount d : discs) {
					System.out.println(d.getDiscountID() + " - " + d.getDiscountName() + " | " + d.getAmount());
				}

				System.out.println("Which discount do you want to add? Enter the number. Enter -1 to stop adding discounts: ");

				chosen_d = Integer.parseInt(reader.nextLine());
				if (chosen_d != -1) {
					if (chosen_d > 0 && chosen_d <= discs.size()) {
						newPizza.addDiscounts(discs.get(chosen_d - 1));
					} else {
						System.out.println("Incorrect entry, not an option");
					}
				}
			}
		}
		o.addPizza(newPizza);
		DBNinja.updateInventory(curInventory);
		return newPizza;
	}


	private static int getTopIndexFromList(int TopID, ArrayList<Topping> tops) {
		/*
		 * This is a helper function I used to get a topping index from a list of toppings
		 * It's very possible you never need a function like this
		 *
		 */
		int ret = -1;


		return ret;
	}


	public static void PrintReports() throws SQLException, NumberFormatException, IOException {
		/*
		 * This function calls the DBNinja functions to print the three reports.
		 *
		 * You should ask the user which report to print
		 */
		System.out.println("Which report you want to print:");
		System.out.println("1. Profit by order type");
		System.out.println("2. Profit by Pizza");
		System.out.println("3. Topping popularity");

		int report_c = Integer.parseInt(reader.nextLine());

		while (report_c < 1 || report_c > 3) {
			System.out.println("invalid Selection");
			System.out.println("Which report you want to print:");
			System.out.println("1. Profit by order type");
			System.out.println("2. Profit by Pizza");
			System.out.println("3. Topping popularity");
			report_c = Integer.parseInt(reader.nextLine());
		}

		switch (report_c) {
			case 1:
				DBNinja.printProfitByOrderType();
				break;
			case 2:
				DBNinja.printProfitByPizzaReport();
				break;
			case 3:
				DBNinja.printToppingPopReport();
				break;
			default:
				System.out.println("Invalid report option selected");
				break;
		}
	}

}
