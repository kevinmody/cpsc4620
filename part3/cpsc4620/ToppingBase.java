package cpsc4620;

public class ToppingBase {
	/*
	 *
	 * Standard Java object class.
	 *
	 * This file can be modified to match your design, or you can keep it as-is.
	 *
	 *
	 * */

	private int TopBaseID;
	private int TopBaseSizeID;
	private int TopBaseUnit;

	private String TopName;
	private double TopCostPerUnit;
	private double TopPrice;
	private int TopMinInventory;
	private int TopCurrentInventory;
	private static int TopStaticCounter;

	public ToppingBase(int _TopID, String _TopName, double _TopCost, double _TopPrice, int _TopMinInventory, int _TopCurrentInventory) {
		TopBaseID = _TopID;
		TopName = _TopName;
		TopCostPerUnit = _TopCost;
		TopPrice = _TopPrice;
		TopMinInventory = _TopMinInventory;
		TopCurrentInventory = _TopCurrentInventory;
		TopStaticCounter = 0;
	}

	public int getTopID() {
		return TopBaseID;
	}

	public void setTopID(int topID) {
		TopBaseID = topID;
	}

	public String getTopName() {
		return TopName;
	}

	public void setTopName(String topName) {
		TopName = topName;
	}

	public double getTopCostPerUnit() {
		return TopCostPerUnit;
	}

	public void setTopCostPerUnit(double topCostPerUnit) {
		TopCostPerUnit = topCostPerUnit;
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
				"\nTopBaseID = " + TopBaseID +
				"\nTopName = " + TopName +
				"\nTopCost = " + TopCostPerUnit +
				"\nTopPrice = " + TopPrice +
				"\nTopMinInventory = " + TopMinInventory +
				"\nTopCurrentInventory = " + TopCurrentInventory +
				"\nTopStaticCounter = " + TopStaticCounter;
	}


}
