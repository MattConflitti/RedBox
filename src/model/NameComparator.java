package model;

import java.util.Comparator;

/***********************************************************************
 * @author Matt Conflitti
 * @version 1.0
 * 
 * NameComparator is used in Collections.sort() to sort the DVDs
 * by name in alphabetical order
 **********************************************************************/
public class NameComparator implements Comparator<DVD> {

	/*******************************************************************
	 * Method compares two DVD objects and returns a value based on
	 * comparison of names
	 * 
	 * @return positive int, negative int, or 0 
	 ******************************************************************/
	public int compare(DVD o1, DVD o2) {
		int result = o1.getNameOfRenter().
				compareToIgnoreCase(o2.getNameOfRenter());
		return result;
	}

}
