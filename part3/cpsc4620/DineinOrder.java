package cpsc4620;

public class DineinOrder extends Order{
	
	/*
	 * 
	 * Standard Java object class. 
	 * 
	 * Since DineinOrder is a subtype of order, this class extends Order.
	 * 
	 * This file can be modified to match your design, or you can keep it as-is.
	 * However, be careful about removing the extends or calls to super.
	 * Remember that changes to Order will also affect this class
	 * 
	 * */
	
	private int TableNum;
	
	public DineinOrder(int orderID, int custID, String date, double custPrice, double busPrice, int isComplete, int tablenum) {
		super(orderID, custID, DBNinja.dine_in, date, custPrice, busPrice, isComplete);
		this.TableNum = tablenum;
	}

	public int getTableNum() {
		return TableNum;
	}

	public void setTableNum(int tableNum) {
		TableNum = tableNum;
	}
	
	@Override
	public String toString() {
		return super.toString() + " | Customer was sat at table number " + TableNum;
	}
}
