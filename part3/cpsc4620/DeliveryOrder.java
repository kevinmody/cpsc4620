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

	private String street;
	private String city;
	private String state;
	private String zip;
	
	public DeliveryOrder(int orderID, int custID, String date, double custPrice, double busPrice, int isComplete,
						 String street, String city, String state, String zip)
	{
		super(orderID, custID, DBNinja.delivery, date, custPrice, busPrice, isComplete);
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;

	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet() {
		return street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getZip() {
		return zip;
	}

	@Override
	public String toString() {
		return super.toString() +
				"\nDelivered to: " + street + ", " + city + ", " + state + ", " + zip + ".";
	}
	
	
}
