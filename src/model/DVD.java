package model;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class DVD implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The date the DVD was rented */
	protected GregorianCalendar bought;

	/** The date the DVD is due back */
	protected GregorianCalendar dueBack;

	/** The title of the DVD */
	protected String title;

	/** The name of the person who is renting the DVD */
	protected String nameOfRenter; 

	// add constructor
	// add getter, setter methods


	public DVD() {
		// TODO Auto-generated constructor stub
	}

}
