package cpsc4620;

public class PickupOrder extends Order {

	/*
	 *
	 * Standard Java object class.
	 *
	 * Since PickupOrder is a subtype of order, this class extends Order.
	 *
	 * This file can be modified to match your design, or you can keep it as-is.
	 * However, be careful about removing the extends or calls to super.
	 * Remember that changes to Order will also affect this class
	 *
	 *
	 * */

	private int isPickedUp;
	private String pickupTime;

	public PickupOrder(int orderID, int custID, String date, double custPrice, double busPrice, int isPickedUp, boolean isComplete) {
		super(orderID, custID, DBNinja.pickup, date, custPrice, busPrice, isComplete);
		this.isPickedUp = isPickedUp;
	}

	public int getIsPickedUp() {
		return isPickedUp;
	}

	public void setIsPickedUp(int isPickedUp) {
		this.isPickedUp = isPickedUp;
	}

	public String getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}

	@Override
	public String toString() {
		return super.toString() + " | is the order picked up? (yes=1): " + isPickedUp;
	}
}
