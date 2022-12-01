package cpsc4620;

import java.sql.Array;
import java.util.ArrayList;

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
	private double TopCostPerUnit;
	private double TopPrice;
	private double TopMinInventory;
	private double TopCurrentInventory;
	private static int TopStaticCounter;
	private final ArrayList<Double> TopUnitSize;

	public Topping(int _TopID, String _TopName, double _TopCost, double _TopPrice, double _TopMinInventory, double _TopCurrentInventory, ArrayList<Double> _TopUnitSize) {
		TopID = _TopID;
		TopName = _TopName;
		TopCostPerUnit = _TopCost;
		TopPrice = _TopPrice;
		TopMinInventory = _TopMinInventory;
		TopCurrentInventory = _TopCurrentInventory;
		TopStaticCounter = 0;
		TopUnitSize = _TopUnitSize;
	}

	public Topping(int _TopID, String _TopName, double _TopCost, double _TopPrice, double _TopMinInventory, double _TopCurrentInventory) {
		TopID = _TopID;
		TopName = _TopName;
		TopCostPerUnit = _TopCost;
		TopPrice = _TopPrice;
		TopMinInventory = _TopMinInventory;
		TopCurrentInventory = _TopCurrentInventory;
		TopStaticCounter = 0;
		TopUnitSize = new ArrayList<>();
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

	public double getTopCostPerUnit() {
		return TopCostPerUnit;
	}

	public double getTopCost(int sizeID) {
		return TopCostPerUnit * TopUnitSize.get(sizeID - 1);
	}

	public void useTopping(int sizeID, boolean isDouble) {
		if (isDouble) {
			TopCurrentInventory -= (2 * TopUnitSize.get(sizeID - 1));
			TopStaticCounter += 2;
		} else {
			TopCurrentInventory -= (TopUnitSize.get(sizeID - 1));
			TopStaticCounter += 1;
		}
	}


	public Boolean canUseTopping(int sizeID) {
		return ((TopCurrentInventory - (2 * TopUnitSize.get(sizeID - 1))) > 0.0);
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

	public double getTopMinInventory() {
		return TopMinInventory;
	}

	public void setTopMinInventory(double topMinInventory) {
		TopMinInventory = topMinInventory;
	}

	public double getTopCurrentInventory() {
		return TopCurrentInventory;
	}

	public void setTopCurrentInventory(double topCurrentInventory) {
		TopCurrentInventory = topCurrentInventory;
	}

	public int getTopStaticCounter() {
		return TopStaticCounter;
	}

	public ArrayList<Double> getTopUnitSize() {
		return TopUnitSize;
	}

	public double getTopUnitSize(int size_idx) {
		return TopUnitSize.get(size_idx - 1);
	}

	public void updateTopCurrentInventory(int size, boolean isDouble) {
		if (isDouble) TopCurrentInventory -= (2 * TopUnitSize.get(size - 1));
		else TopCurrentInventory -= (TopUnitSize.get(size - 1));
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
				"\nTopCost = " + TopCostPerUnit +
				"\nTopPrice = " + TopPrice +
				"\nTopMinInventory = " + TopMinInventory +
				"\nTopCurrentInventory = " + TopCurrentInventory +
				"\nTopStaticCounter = " + TopStaticCounter;
	}


}