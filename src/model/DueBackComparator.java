package model;

import java.util.Comparator;

/***********************************************************************
 * @author Matt Conflitti
 * @version 1.0
 * 
 * DueBackComparator is used in Collections.sort() to sort the DVDs
 * by due date to list them in descending order.
 **********************************************************************/
public class DueBackComparator implements Comparator<DVD> {

	/*******************************************************************
	 * Method compares two DVD objects and returns a value based on
	 * comparison of due dates
	 * 
	 * @return positive int, negative int, or 0 
	 ******************************************************************/
	public int compare(DVD o1, DVD o2) {
		if(o1.getDueBack().after(o2.getDueBack()))
			return 1;
		if(o1.getDueBack().before(o2.getDueBack()))
			return -1;
		return 0;
	}

}
