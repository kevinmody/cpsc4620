package cpsc4620;
import java.util.ArrayList;

public class Pizza {

	/*
	 *
	 * Standard Java object class.
	 *
	 * This file can be modified to match your design, or you can keep it as-is.
	 *
	 * */

	private int PizzaID;
	private int CrustID;
	private int SizeID;
	private int OrderID;
	private String PizzaState;
	private String PizzaDate;
	private double Price;
	private double Cost;

	//Like order, we store the associated toppings and discounts into arraylists since we need a way in java to associate them with pizzas.
	private ArrayList<Topping> Toppings;
	private ArrayList<Discount> Discounts;


	//each index in this array will represent whether the topping at Toppings.get(index) is doubled.
	// Parallel arraylist
	private ArrayList<Boolean> isToppingDoubled;


	public Pizza(int pizzaID, int sizeID, int crustID, int orderID, String pizzaState, String pizzaDate, double price, double cost)
	{
		PizzaID = pizzaID;
		CrustID = crustID;
		SizeID = sizeID;
		OrderID = orderID;
		PizzaState = pizzaState;
		PizzaDate = pizzaDate;
		Price = price;
		Cost = cost;
		Toppings = new ArrayList<Topping>();
		Discounts = new ArrayList<Discount>();
		isToppingDoubled = new ArrayList<Boolean>();
	}

	public int getPizzaID() {
		return PizzaID;
	}
	public void setPizzaID(int pizzaID) {
		PizzaID = pizzaID;
	}

	public int getCrustID() {
		return CrustID;
	}
	public void setCrustID(int crustID) {
		CrustID = crustID;
	}

	public int getSizeID() {
		return SizeID;
	}
	public void setSizeID(int sizeID) {
		SizeID = sizeID;
	}

	public int getOrderID() {
		return OrderID;
	}
	public void setOrderID(int orderID) {
		OrderID = orderID;
	}

	public String getPizzaState() {
		return PizzaState;
	}
	public void setPizzaState(String pizzaState) {
		PizzaState = pizzaState;
	}

	public String getPizzaDate() {
		return PizzaDate;
	}
	public void setPizzaDate(String pizzaDate) {
		PizzaDate = pizzaDate;
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

	public ArrayList<Topping> getToppings() {
		return Toppings;
	}
	public void setToppings(ArrayList<Topping> toppings) {
		Toppings = toppings;
	}


	public ArrayList<Discount> getDiscounts() {
		return Discounts;
	}
	public void setDiscounts(ArrayList<Discount> discounts) {
		Discounts = discounts;
	}


	public void addToppings(Topping t, boolean isExtra) {
		Toppings.add(t);
		isToppingDoubled.add(isExtra);

		this.Cost += t.getTopCostPerUnit();
		this.Price += t.getTopPrice();
		//also add to the prices of the pizza
		if (isExtra) {
			this.Cost *= 2;
			this.Price *= 2;
		}
	}

	public void addDiscounts(Discount d) {
		Discounts.add(d);


		if (d.isPercent()) {
			this.Price = (this.Price * (1 - (d.getAmount() / 100)));
		} else {
			this.Price -= d.getAmount();
		}
	}


	//whenever we add a topping to a pizza, we should also modify the respective index isDoubled array if it has extra topping on it.
	//i.e., if bacon, toppingID=17 is used with double, we would go in and modify the 16 (yes sixteen, java starts at 0, IDs start at 1) to be true.
	public void modifyDoubledArray(int index, boolean b) {
		isToppingDoubled.set(index, b);
	}

	//we'll need to get this array whenever we're about to add to our pizza-topping bridge table so we know if we need to mark isDoubled as true or not.
	public ArrayList<Boolean> getIsDoubleArray() {
		return isToppingDoubled;
	}

	@Override
	public String toString() {
		return "PizzaID = " + PizzaID +
				"\nCrustType = " + CrustID +
				"\nSize = " + SizeID +
				"\nFor order = " + OrderID +
				"\nPizza Status = " + PizzaState +
				"\nDate = " + PizzaDate +
				"\nPrice = " + Price +
				"\nCost = " + Cost;
	}


}