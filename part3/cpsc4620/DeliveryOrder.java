package cpsc4620;

public class DeliveryOrder extends Order
{
	/*
	 * 
	 * Standard Java object class. 
	 * 
	 * Since DeliveryOrder is a subtype of order, this class extends Order.
	 * 
	 * This file can be modified to match your design, or you can keep it as-is.
	 * However, be careful about removing the extends or calls to super.
	 * Remember that changes to Order will also affect this class
	 *
	 * 
	 * */
	
	private String Address;
	
	public DeliveryOrder(int orderID, int custID, String date, double custPrice, double busPrice, int isComplete, String address) 
	{
		super(orderID, custID, DBNinja.delivery, date, custPrice, busPrice, isComplete);
		this.Address = address;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	@Override
	public String toString() {
		return super.toString() + " | Delivered to: " + Address;
	}
	
	
}
