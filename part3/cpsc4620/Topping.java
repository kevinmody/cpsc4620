package cpsc4620;

public class Topping {
	/*
	 *
	 * Standard Java object class.
	 *
	 * This file can be modified to match your design, or you can keep it as-is.
	 *
	 *
	 * */

	private int TopID;
	private String TopName;
	private double TopCost;
	private double TopPrice;
	private int TopMinInventory;
	private int TopCurrentInventory;
	private static int TopStaticCounter;

	public Topping(int _TopID, String _TopName, double _TopCost, double _TopPrice, int _TopMinInventory, int _TopCurrentInventory) {
		TopID = _TopID;
		TopName = _TopName;
		TopCost = _TopCost;
		TopPrice = _TopPrice;
		TopMinInventory = _TopMinInventory;
		TopCurrentInventory = _TopCurrentInventory;
		TopStaticCounter = 0;
	}

	public int getTopID() {
		return TopID;
	}

	public void setTopID(int topID) {
		TopID = topID;
	}

	public String getTopName() {
		return TopName;
	}

	public void setTopName(String topName) {
		TopName = topName;
	}

	public double getTopCost() {
		return TopCost;
	}

	public void setTopCost(double topCost) {
		TopCost = topCost;
	}

	public double getTopPrice() {
		return TopPrice;
	}

	public void setTopPrice(double topPrice) {
		TopPrice = topPrice;
	}

	public int getTopMinInventory() {
		return TopMinInventory;
	}

	public void setTopMinInventory(int topMinInventory) {
		TopMinInventory = topMinInventory;
	}

	public int getTopCurrentInventory() {
		return TopCurrentInventory;
	}

	public void setTopCurrentInventory(int topCurrentInventory) {
		TopCurrentInventory = topCurrentInventory;
	}

	public int getTopStaticCounter() {
		return TopStaticCounter;
	}

	public void updateTopStaticCounter(boolean isDouble) {
		if (isDouble) TopStaticCounter += 2;
		else TopStaticCounter += 1;
	}

	@Override
	public String toString() {
		return "Topping Table" +
				"\nTopID = " + TopID +
				"\nTopName = " + TopName +
				"\nTopCost = " + TopCost +
				"\nTopPrice = " + TopPrice +
				"\nTopMinInventory = " + TopMinInventory +
				"\nTopCurrentInventory = " + TopCurrentInventory +
				"\nTopStaticCounter = " + TopStaticCounter;
	}


}
