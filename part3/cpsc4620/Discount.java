package cpsc4620;

public class Discount 
{
	
	/*
	 * 
	 * Standard Java object class. 
	 * 
	 * This file can be modified to match your design, or you can keep it as-is.
	 * 
	 * 
	 * */
	
	private int DiscountID;
	private String DiscountName;
	private double Amount;
	private boolean isPercent;
	
	public Discount(int discountID, String discountName, double amount, boolean isPercent) {
		DiscountID = discountID;
		DiscountName = discountName;
		Amount = amount;
		this.isPercent = isPercent;
	}

	public int getDiscountID() {
		return DiscountID;
	}

	public String getDiscountName() {
		return DiscountName;
	}

	public double getAmount() {
		return Amount;
	}

	public boolean isPercent() {
		return isPercent;
	}

	public void setDiscountID(int discountID) {
		DiscountID = discountID;
	}

	public void setDiscountName(String discountName) {
		DiscountName = discountName;
	}

	public void setAmount(double amount) {
		Amount = amount;
	}

	public void setPercent(boolean isPercent) {
		this.isPercent = isPercent;
	}

	@Override
	public String toString() {
		return "DiscountID=" + DiscountID + " | " + DiscountName + ", Amount= " + Amount
				+ ", isPercent= " + isPercent;
	}
	
}
