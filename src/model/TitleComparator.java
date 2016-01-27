package model;

import java.util.Comparator;

/***********************************************************************
 * @author Matt Conflitti
 * @version 1.0
 * 
 * TitleComparator is used in Collections.sort() to sort the DVDs
 * by title in alphabetical order
 **********************************************************************/
public class TitleComparator implements Comparator<DVD> {

	/*******************************************************************
	 * Method compares two DVD objects and returns a value based on
	 * comparison of titles
	 * 
	 * @return positive int, negative int, or 0 
	 ******************************************************************/
	public int compare(DVD o1, DVD o2) {
		int result = o1.getTitle().compareToIgnoreCase(o2.getTitle());
		return result;
	}

}
