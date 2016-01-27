package model;

import java.util.Comparator;

/***********************************************************************
 * @author Matt Conflitti
 * @version 1.0
 * 
 * RentedOnComparator is used in Collections.sort() to sort the DVDs
 * by rented date to list them in descending order.
 **********************************************************************/
public class RentedOnComparator implements Comparator<DVD> {

	/*******************************************************************
	 * Method compares two DVD objects and returns a value based on
	 * comparison of rent dates
	 * 
	 * @return positive int, negative int, or 0 
	 ******************************************************************/
	public int compare(DVD o1, DVD o2) {
		if(o1.getBought().after(o2.getBought()))
			return -1;
		if(o1.getBought().before(o2.getBought()))
			return 1;
		return 0;
	}

}