package cpsc4620;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	public static void main(String[] args) throws SQLException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Welcome to Taylor's Pizzeria!");
		
		int menu_option = 0;

		// present a menu of options and take their selection
		PrintMenu();
		String option = reader.readLine();
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
			}
			PrintMenu();
			option = reader.readLine();
			menu_option = Integer.parseInt(option);
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
	public static void EnterOrder() throws SQLException, IOException 
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
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

	}
	
	
	public static void viewCustomers()
	{
		/*
		 * Simply print out all of the customers from the database. 
		 */
		
		
		
		
		
		
	}
	

	// Enter a new customer in the database
	public static void EnterCustomer() throws SQLException, IOException 
	{
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



	}

	// View any orders that are not marked as completed
	public static void ViewOrders() throws SQLException, IOException 
	{
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
		for (Order o : currOrders)
		{
			System.out.println(Integer.toString(o_count) + ": " + o.toSimplePrint());
			o_count++;
		}

		// User can now select an order and get the full detail
		System.out.println("Which order would you like to see in detail? Enter the number: ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int chosen_order = Integer.parseInt(reader.readLine());
		if(chosen_order <= currOrders.size())
		{
			System.out.println(currOrders.get(chosen_order-1).toString());
		}
		else
		{
			System.out.println("Incorrect entry, not an option");
		}
	}

	
	// When an order is completed, we need to make sure it is marked as complete
	public static void MarkOrderAsComplete() throws SQLException, IOException 
	{
		/*All orders that are created through java (part 3, not the 7 orders from part 2) should start as incomplete
		 * 
		 * When this function is called, you should print all of the orders marked as complete 
		 * and allow the user to choose which of the incomplete orders they wish to mark as complete
		 * 
		 */
		ArrayList<Order> currOrders = DBNinja.getCurrentOrders();
		int o_count = 1;
		//see all open orders
		for (Order o : currOrders)
		{
			System.out.println(Integer.toString(o_count) + ": " + o.toSimplePrint());
			o_count++;
		}

		//pick the order to mark as completed
		System.out.println("Which order would you like mark as complete? Enter the number: ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int chosen_order = Integer.parseInt(reader.readLine());
		if(chosen_order <= currOrders.size())
		{
			DBNinja.CompleteOrder(currOrders.get(chosen_order - 1));
		}
		else
		{
			System.out.println("Incorrect entry, not an option");
		}

	}

	// See the list of inventory and it's current level
	public static void ViewInventoryLevels() throws SQLException, IOException 
	{
		//print the inventory. I am really just concerned with the ID, the name, and the current inventory

		ArrayList<Topping> curInventory = DBNinja.getInventory();
		int t_count = 1;
		for(Topping t : curInventory)
		{
			//System.out.println(Integer.toString(t_count) + ": " + t.getName() + " Level: " + Double.toString(t.getInv()));
			t_count++;
		}
		
		
		
		
		
	}

	// Select an inventory item and add more to the inventory level to re-stock the
	// inventory
	public static void AddInventory() throws SQLException, IOException 
	{
		/*
		 * This should print the current inventory and then ask the user which topping they want to add more to and how much to add
		 */
		ArrayList<Topping> curInventory = DBNinja.getInventory();

		
		
		
		
		
	}

	// A function that builds a pizza. Used in our add new order function
	public static Pizza buildPizza(int orderID) throws SQLException, IOException {

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
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Pizza ret = null;
		//select size
		System.out.println("What size is the pizza? \n1.) Small \n2.) Medium\n3.) Large\n4.) X-Large \n Enter the corresponding number: ");
		int size_option = Integer.parseInt(reader.readLine());
		String size = "";
		if (size_option == 1) {
			size = DBNinja.size_s;
		} else if (size_option == 2) {
			size = DBNinja.size_m;
		} else if (size_option == 3) {
			size = DBNinja.size_l;
		} else {
			size = DBNinja.size_xl;
		}

		//select crust
		System.out.println("What crust for this pizza? \n1.) Thin \n2.) Original\n3.) Pan\n4.) Gluten-Free \n Enter the corresponding number: ");
		int c_option = Integer.parseInt(reader.readLine());
		String crust = "";
		if (c_option == 1) {
			crust = DBNinja.crust_thin;
		} else if (c_option == 2) {
			crust = DBNinja.crust_orig;
		} else if (c_option == 3) {
			crust = DBNinja.crust_pan;
		} else {
			crust = DBNinja.crust_gf;
		}

		//get the base price ??

		return ret;
	}
		
		
		
		
		
		


	
	private static int getTopIndexFromList(int TopID, ArrayList<Topping> tops)
	{
		/*
		 * This is a helper function I used to get a topping index from a list of toppings
		 * It's very possible you never need a function like this
		 * 
		 */
		int ret = -1;
		
		
		
		return ret;
	}
	
	
	public static void PrintReports() throws SQLException, NumberFormatException, IOException
	{
		/*
		 * This function calls the DBNinja functions to print the three reports.
		 * 
		 * You should ask the user which report to print
		 */
	}

}
