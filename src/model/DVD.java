package model;

import java.io.Serializable;
import java.util.GregorianCalendar;

/***********************************************************************
 * @author Matt Conflitti
 * @version 1.0
 * 
 * DVD stores data concerning a rented DVD such as due date, rented
 * date, title, and name of renter.
 **********************************************************************/

public class DVD implements Serializable,Comparable<Object> {

	/** serial id */
	private static final long serialVersionUID = 1L;

	/** The date the DVD was rented */
	protected GregorianCalendar bought;

	/** The date the DVD is due back */
	protected GregorianCalendar dueBack;

	/** The title of the DVD */
	protected String title;

	/** The name of the person who is renting the DVD */
	protected String nameOfRenter;

	/** number of days late */
	protected int daysLate;

	/** number of days rented */
	protected int daysRented;

	/*******************************************************************
	 * Default DVD constructor sets primitives to default values
	 ******************************************************************/
	public DVD() {
		this.daysLate = 0;
		this.daysRented = 0;
	}

	/*******************************************************************
	 * Constructor that sets all fields to specs
	 * 
	 * @param bought GregorianCalendar object for rented date
	 * @param dueBack GregorianCalendar object for due back date
	 * @param title name of DVD
	 * @param nameOfRenter name of renter
	 ******************************************************************/
	public DVD(GregorianCalendar bought, GregorianCalendar dueBack,
			String title, String nameOfRenter) {
		this();
		this.bought = bought;
		this.dueBack = dueBack;
		this.title = title;
		this.nameOfRenter = nameOfRenter;

	}

	/*******************************************************************
	 * Getter method for the rented date GregorianCalendar object
	 * 
	 * @return rented date GregorianCalendar object
	 ******************************************************************/
	public GregorianCalendar getBought() {
		return bought;
	}

	/*******************************************************************
	 * Setter method for the rented date GregorianCalendar object
	 * 
	 * @param bought GregorianCalendar date object
	 ******************************************************************/
	public void setBought(GregorianCalendar bought) {
		this.bought = bought;
	}

	/*******************************************************************
	 * Getter method for the due back date GregorianCalendar object
	 * 
	 * @return due back date GregorianCalendar object
	 ******************************************************************/
	public GregorianCalendar getDueBack() {
		return dueBack;
	}

	/*******************************************************************
	 * Setter method for the due back date GregorianCalendar object
	 * 
	 * @param dueBack GregorianCalendar date object
	 ******************************************************************/
	public void setDueBack(GregorianCalendar dueBack) {
		this.dueBack = dueBack;
	}

	/*******************************************************************
	 * Getter method for the title of DVD
	 * 
	 * @return title of DVD
	 ******************************************************************/
	public String getTitle() {
		return title;
	}

	/*******************************************************************
	 * Setter method for the title of DVD
	 * 
	 * @param title title of DVD
	 ******************************************************************/
	public void setTitle(String title) {
		this.title = title;
	}

	/*******************************************************************
	 * Getter method for renter's name
	 * 
	 * @return name of renter
	 ******************************************************************/
	public String getNameOfRenter() {
		return nameOfRenter;
	}

	/*******************************************************************
	 * Setter method for name of renter
	 * 
	 * @param nameOfRenter name of renter
	 ******************************************************************/
	public void setNameOfRenter(String nameOfRenter) {
		this.nameOfRenter = nameOfRenter;
	}

	/*******************************************************************
	 * Setter method for daysLate, calculates how many days based on
	 * input date
	 * 
	 * @param unit GregorianCalendar object of specified date
	 ******************************************************************/
	public void setDaysLate(GregorianCalendar unit) {

		//set initial count
		int count = 0;

		//get clone of dvd's due date object
		GregorianCalendar temp =(GregorianCalendar)getDueBack().clone();

		//keep adding days until dates are equal
		while(unit.after(temp)) {
			temp.add(GregorianCalendar.DAY_OF_MONTH, 1);
			count++;
		}

		//set daysLate to count
		daysLate = count;
	}

	/*******************************************************************
	 * Getter method to get number of days late
	 * 
	 * @return number of days late
	 ******************************************************************/
	public int getDaysLate() {
		return daysLate;
	}

	/*******************************************************************
	 * Setter method for daysRented by calculating num of days between
	 * rented and due date
	 * 
	 * @param rented GregorianCalendar object of specified rented date
	 * @param due GregorianCalendar object of specified due date
	 ******************************************************************/
	public void setDaysRented(GregorianCalendar rented, 
			GregorianCalendar due) {

		//set counter
		int count = 0;

		//increment days until dates are equal
		GregorianCalendar temp = (GregorianCalendar) rented.clone();
		while(temp.before(due)) {
			temp.add(GregorianCalendar.DAY_OF_MONTH, 1);
			count++;
		}

		//update daysRented count
		daysRented = count;
	} 

	/*******************************************************************
	 * Getter method for daysRented
	 * 
	 * @return number of days rented
	 ******************************************************************/
	public int getDaysRented() {
		return daysRented;
	}

	/*******************************************************************
	 * Calculates cost of DVD based on daysRented and if it is late
	 * 
	 * @return cost of dvd
	 ******************************************************************/
	public double getCost() {
		return (.50 * daysRented) + (.25 * daysLate);
	}

	/*******************************************************************
	 * Compares this object to the one given.  This method is what
	 * Collections.sort defaults to if no comparator is given. It
	 * is recommended to always use a comparator for more flexible
	 * sorting so this method is not a huge deal. It currently sorts
	 * by due back date.
	 * 
	 *  @param o object to compare against
	 ******************************************************************/
	public int compareTo(Object o) {
		DVD d = (DVD) o;
		if(this.getDueBack().after(d.getDueBack()))
			return 1;
		if(this.getDueBack().before(d.getDueBack()))
			return -1;
		return 0;
	}



}
