package cpsc4620;

public class Customer 
{
	
	/*
	 * 
	 * Standard Java object class. 
	 *  
	 * This file can be modified to match your design, or you can keep it as-is.
	 * 
	 * */
	
	private int CustID;
	private String FName;
	private String LName;
	private String Phone;
	private String Street;
	private String City;
	private String State;
	private String Zip;
	
	
	public Customer(int custID, String fName, String lName, String phone) {
		CustID = custID;
		FName = fName;
		LName = lName;
		Phone = phone;
	}

	public int getCustID() {
		return CustID;
	}
	public void setCustID(int custID) {
		CustID = custID;
	}

	public String getFName() {
		return FName;
	}
	public void setFName(String fName) {
		FName = fName;
	}

	public String getLName() {
		return LName;
	}
	public void setLName(String lName) {
		LName = lName;
	}

	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}

	/*

	public String getStreet() { return Street;}
	public void setStreet(String street) { Street = street; }

	public String getCity() { return City;}
	public void setCity(String city) { City = city; }

	public String getState() { return State; }
	public void setState(String state) { State = state; }

	public String getZip() { return Zip; }
	public void setZip(String zip) { Zip = zip; }

	*/

	@Override
	public String toString() {
		return  "CustID= " + CustID +
				"\nName= " + FName +  " " + LName +
				"\nPhone= " + Phone;
	}
	
	
}
