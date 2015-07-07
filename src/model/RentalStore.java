package model;

import java.util.LinkedList;

import javax.swing.AbstractListModel;

public class RentalStore extends AbstractListModel {	
	private LinkedList<DVD> listDVDs;

	// constructor method that initializes the Linkedlist
	public RentalStore() {
		listDVDs = new LinkedList<DVD>();
	}
	// override these two methods from AbstractListModel class

	public Object getElementAt(int arg0) {
		return null;
	}
	
	public int getSize() {
		return listDVDs.size();
	}

	// add methods to add, delete, and update.
	// add methods to load/save accounts from/to a binary file
	// add other methods as needed
}