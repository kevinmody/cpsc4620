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

		Street = null;
		City = null;
		State = null;
		Zip = null;
	}

	public Customer(int custID, String fName, String lName, String phone, String street, String city, String state, String zip) {
		CustID = custID;
		FName = fName;
		LName = lName;
		Phone = phone;
		Street = street;
		City = city;
		State = state;
		Zip = zip;
	}

	public Customer() {
		CustID = -1;
		FName = null;
		LName = null;
		Phone = null;

		Street = null;
		City = null;
		State = null;
		Zip = null;

	}

	public int getCustID() {
		return CustID;
	}

	public String getFName() {
		return FName;
	}

	public String getLName() {
		return LName;
	}

	public String getPhone() {
		return Phone;
	}

	public void setCustID(int custID) {
		CustID = custID;
	}

	public void setFName(String fName) {
		FName = fName;
	}

	public void setLName(String lName) {
		LName = lName;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public void setStreet(String street) {
		Street = street;
	}

	public String getStreet() {
		return Street;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getCity() {
		return City;
	}

	public void setState(String state) {
		State = state;
	}

	public String getState() {
		return State;
	}

	public void setZip(String zip) {
		Zip = zip;
	}

	public String getZip() {
		return Zip;
	}

	@Override
	public String toString() {
		return "CustID = " + CustID + " | Name = " + FName +  " " + LName + ", Phone = " + Phone;
	}
	
	
}
