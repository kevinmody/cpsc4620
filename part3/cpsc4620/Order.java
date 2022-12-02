// Sahil Patel and Kevin Mody

package cpsc4620;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Order {
	/*
	 *
	 * Standard Java object class.
	 *
	 * This file can be modified to match your design, or you can keep it as-is.
	 * Remember that changes to this class will affect your subtype classes.
	 *
	 * */


	private int OrderID;
	private int CustID;
	private String OrderType;
	private String Date;
	private double Price;
	private double Cost;
	private Boolean isComplete;

	//notice that these are not part of the ERD design, but we need a way to associate pizzas and discounts with a specific order
	private ArrayList<Pizza> PizzaList;
	private ArrayList<Discount> DiscountList;


	public Order(int orderID, int custID, String orderType, String date, double price, double cost, Boolean iscomplete) {
		OrderID = orderID;
		CustID = custID;
		OrderType = orderType;
		Date = date;
		Price = price;
		Cost = cost;
		this.isComplete = iscomplete;
		PizzaList = new ArrayList<Pizza>();
		DiscountList = new ArrayList<Discount>();
	}

	public Order(int orderID, String orderType, String date, double price, double cost) {
		OrderID = orderID;
		OrderType = orderType;
		Date = date;
		Price = price;
		Cost = cost;
		isComplete = false;
		PizzaList = new ArrayList<Pizza>();
		DiscountList = new ArrayList<Discount>();
	}

    public Order() {
		OrderID = -1;
		CustID = -1;
		OrderType = null;
		Date = null;
		Price = -1;
		Cost = -1;
		isComplete = false;
		PizzaList = new ArrayList<Pizza>();
		DiscountList = new ArrayList<Discount>();
    }

    public void addPizza(Pizza p) {
		PizzaList.add(p);
		Price += p.getPrice();
		Cost += p.getCost();
	}


	//When we add a discount, go ahead and update the price. Notice that this only updates the price of the variable and doesn't touch the database
	//so discount must be added before the order is sent to the DB.
	public void addDiscount(Discount d) {
		DiscountList.add(d);
		if (d.isPercent()) {
			this.Price = (this.Price * (1 - (d.getAmount() / 100)));//turn the amount into a percent
		} else {
			this.Price -= d.getAmount();
		}
	}

	public int getOrderID() {
		return OrderID;
	}

	public void setOrderID(int orderID) {
		OrderID = orderID;
	}


	public int getCustID() {
		return CustID;
	}

	public void setCustID(int custID) {
		CustID = custID;
	}


	public String getOrderType() {
		return OrderType;
	}

	public void setOrderType(String orderType) {
		OrderType = orderType;
	}


	public String getDate() {
		return Date;
	}

	// Parm date format must be "2013-12-31 20:30"
	public void setDate(String date) {
		Date = date;
	}


	public double getPrice() {
		return Price;
	}

	public void setPrice(double price) {
		Price = price;
	}


	public double getCost() {
		return Cost;
	}

	public void setCost(double cost) {
		Cost = cost;
	}


	public Boolean getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Boolean iscomplete) {
		this.isComplete = iscomplete;
	}


	public ArrayList<Pizza> getPizzaList() {
		return PizzaList;
	}

	public void setPizzaList(ArrayList<Pizza> pizzaList) {
		PizzaList = pizzaList;
	}


	public ArrayList<Discount> getDiscountList() {
		return DiscountList;
	}

	public void setDiscountList(ArrayList<Discount> discountList) {
		DiscountList = discountList;
	}



	//two print statements because one is slightly easier to read. If you can make pretty print statements, you're absolutely welcome to change these.
	@Override
	public String toString() {
		try {
			return "OrderID = " + OrderID +
					"\nDate Placed = " + this.Date +
					"\nCustomer = " + DBNinja.getCustomerName(CustID) +
					"\nOrderType = " + OrderType +
					"\nPrice = " + Price +
					"\nCost = " + Cost;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return "ERROR IN PRINT CUSTOMER";
		}
	}

	public String toSimplePrint() {
		try {

			return String.format("Order ID: %-1d || Date: %s || Customer Name: %-20s || Order Type: %-9s || IsComplete: %s \n", OrderID, Date, DBNinja.getCustomerName(CustID), OrderType, isComplete);

		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return "ERROR IN SIMPLE PRINT CUSTOMER";
		}
	}
}