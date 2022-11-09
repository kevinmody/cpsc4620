package cpsc4620;
import java.util.ArrayList;

public class Pizza 
{
	
	/*
	 * 
	 * Standard Java object class. 
	 *  
	 * This file can be modified to match your design, or you can keep it as-is.
	 * 
	 * */
	
	private int PizzaID;
	private String CrustType;
	private String Size;
	private int OrderID;
	private String PizzaState;
	private String PizzaDate;
	private double CustPrice;
	private double BusPrice;
	
	//Like order, we store the associated toppings and discounts into arraylists since we need a way in java to associate them with pizzas.
	private ArrayList<Topping> Toppings;
	private ArrayList<Discount> Discounts;
	
	//each index in this array will represent whether the topping at Toppings.get(index) is doubled.
	private boolean[] isToppingDoubled;//There's definitely a better way to do this, so you're welcome to try and find it.
	
	
	public Pizza(int pizzaID, String size, String crustType, int orderID, String pizzaState, String pizzaDate,
			double custPrice, double busPrice) {
		PizzaID = pizzaID;
		CrustType = crustType;
		Size = size;
		OrderID = orderID;
		PizzaState = pizzaState;
		PizzaDate = pizzaDate;
		CustPrice = custPrice;
		BusPrice = busPrice;
		Toppings = new ArrayList<Topping>();
		isToppingDoubled = new boolean[17];//We have 17 toppings, the array needs to be size 17. A good programmer wouldn't hard code this.
		Discounts = new ArrayList<Discount>();
	}

	public int getPizzaID() {
		return PizzaID;
	}



	public String getCrustType() {
		return CrustType;
	}



	public String getSize() {
		return Size;
	}



	public int getOrderID() {
		return OrderID;
	}



	public String getPizzaState() {
		return PizzaState;
	}



	public String getPizzaDate() {
		return PizzaDate;
	}



	public double getCustPrice() {
		return CustPrice;
	}



	public double getBusPrice() {
		return BusPrice;
	}



	public ArrayList<Topping> getToppings() {
		return Toppings;
	}



	public ArrayList<Discount> getDiscounts() {
		return Discounts;
	}



	public void setPizzaID(int pizzaID) {
		PizzaID = pizzaID;
	}



	public void setCrustType(String crustType) {
		CrustType = crustType;
	}



	public void setSize(String size) {
		Size = size;
	}



	public void setOrderID(int orderID) {
		OrderID = orderID;
	}



	public void setPizzaState(String pizzaState) {
		PizzaState = pizzaState;
	}



	public void setPizzaDate(String pizzaDate) {
		PizzaDate = pizzaDate;
	}



	public void setCustPrice(double custPrice) {
		CustPrice = custPrice;
	}



	public void setBusPrice(double busPrice) {
		BusPrice = busPrice;
	}



	public void setToppings(ArrayList<Topping> toppings) {
		Toppings = toppings;
	}



	public void setDiscounts(ArrayList<Discount> discounts) {
		Discounts = discounts;
	}

	public void addToppings(Topping t, boolean isExtra)
	{
		Toppings.add(t);
		//also add to the prices of the pizza
		if(isExtra)
		{
			this.BusPrice += t.getBusPrice()*2;
			this.CustPrice += t.getCustPrice()*2;
		}
		else
		{
			this.BusPrice += t.getBusPrice();
			this.CustPrice += t.getCustPrice();
		}

	}
	
	public void addDiscounts(Discount d)
	{
		Discounts.add(d);
		if(d.isPercent())
		{
			this.CustPrice = (this.CustPrice*(1-(d.getAmount() / 100)));
		}
		else
		{
			this.CustPrice -= d.getAmount();
		}
	}

	
	//whenever we add a topping to a pizza, we should also modify the respective index isDoubled array if it has extra topping on it.
	//i.e., if bacon, toppingID=17 is used with double, we would go in and modify the 16 (yes sixteen, java starts at 0, IDs start at 1) to be true.
	public void modifyDoubledArray(int index, boolean b)
	{
		isToppingDoubled[index] = b;
	}
	
	//we'll need to get this array whenever we're about to add to our pizza-topping bridge table so we know if we need to mark isDoubled as true or not.
	public boolean[] getIsDoubleArray()
	{
		return isToppingDoubled;
	}
	
	@Override
	public String toString() {
		return "PizzaID=" + PizzaID + " | CrustType= " + CrustType + ", Size= " + Size + " | For order " + OrderID
				+ " | Pizza Status: " + PizzaState + ", as of " + PizzaDate + " | Customer Price= " + CustPrice + " | Business Price= "
				+ BusPrice;
	}
	
	
}
