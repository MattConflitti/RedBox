package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

import javax.swing.AbstractListModel;

/***********************************************************************
 * RentalStore uses LinkedLists to store DVD units and then is
 * processed to be displayed in JList.
 * 
 * @author Matt Conflitti
 * @version 1.0
 **********************************************************************/
public class RentalStore extends AbstractListModel<String> {	

	/** master list of DVDs */
	private MyDoubleLinkedList<DVD> listDVDs;

	/** filtered list of DVDs */
	private MyDoubleLinkedList<DVD> filteredList;

	/** points to list to use */
	private MyDoubleLinkedList<DVD> usedList;

	/** is list filtered */
	private boolean filtered;

	/** stores comparator for sort */
	private Comparator<DVD> comparator;

	/*******************************************************************
	 * Default constructor instantiates all lists
	 ******************************************************************/
	public RentalStore() {
		listDVDs = new MyDoubleLinkedList<DVD>();
		filteredList = new MyDoubleLinkedList<DVD>();
		usedList = listDVDs;
		filtered = false;
		comparator = new NameComparator();
	}

	/*******************************************************************
	 * Gets all information from DVD objects to be displayed in JList
	 * 
	 * @param arg0 index of item in list
	 * @return string of DVD information
	 ******************************************************************/
	public String getElementAt(int arg0) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String result = "";
		if(filtered)
			result += "Days Late: " + 
					usedList.get(arg0).getDaysLate() + ", ";
		result += "Renter Name: " + 
				usedList.get(arg0).getNameOfRenter();
		result += ", Title: " + usedList.get(arg0).getTitle();
		result += ", Rented On: " + 
				df.format(usedList.get(arg0).getBought().getTime());
		result += ", Due Back: " + 
				df.format(usedList.get(arg0).getDueBack().getTime());
		if(usedList.get(arg0) instanceof Game)
			result += ", Game System: " + 
					((Game) usedList.get(arg0)).getPlayer().getText();
		return result;


	}

	/*******************************************************************
	 * Get size of the currently used list
	 * 
	 * @return size of list
	 ******************************************************************/
	public int getSize() {
		return usedList.size();
	}

	/*******************************************************************
	 * Add DVD to master list, update JList, and sort list
	 * 
	 * @param d DVD to be added
	 ******************************************************************/
	public void add(DVD d) {
		listDVDs.add(d);
		fireIntervalAdded(this,0,listDVDs.size());
		sort(null);
	}

	/*******************************************************************
	 * Update/Refresh JList
	 ******************************************************************/
	public void update() {
		fireContentsChanged(this, 0, listDVDs.size());
	}

	/*******************************************************************
	 * Remove DVD at given index, update filtered and master list.
	 * Update list and refire JList.
	 * 
	 * @return removed item
	 ******************************************************************/
	public DVD remove(int index) {
		DVD temp = listDVDs.remove(index);
		if(filteredList.contains(temp))
			filteredList.remove(index);
		fireIntervalRemoved(this,0,listDVDs.size());
		
		
		sort(null);
		return temp;
	}

	/*******************************************************************
	 * Get DVD at a given index
	 * @param index location of DVD
	 * @return DVD at given index
	 ******************************************************************/
	public DVD get(int index) {
		return listDVDs.get(index);
	}

	/*******************************************************************
	 * Set filter field
	 * 
	 * @param b boolean to set filtered
	 ******************************************************************/
	public void setFilter(boolean b) {
		filtered = b;
	}

	/*******************************************************************
	 * Set used list of rental store to be displayed
	 * 
	 * @param used LinkedList to be used
	 ******************************************************************/
	public void setUsedList(MyDoubleLinkedList<DVD> used) {
		usedList = used;
		update();
	}

	/*******************************************************************
	 * Reset list to master list and turn off filter
	 ******************************************************************/
	public void resetList() {
		usedList = listDVDs;
		setFilter(false);
		sort(null);
		update();
	}

	/*******************************************************************
	 * Sort used list using Collections class and custom comparator
	 * 
	 * @param c comparator to use
	 ******************************************************************/
	public void sort(Comparator<DVD> c) {
		if(c != null)
			comparator = c;
		if(listDVDs.size() > 0)
			Collections.sort(usedList, comparator);

		update();
	}


	/*******************************************************************
	 * Filter the master list into a filtered list and set used list to
	 * that filtered list and update JList.
	 * 
	 * @param date date to filter by
	 * @return filtered list
	 ******************************************************************/
	public MyDoubleLinkedList<DVD> filter(GregorianCalendar date) {
		setFilter(true);
		filteredList.clear();
		for(int i=0; i<listDVDs.size(); i++) {
			listDVDs.get(i).setDaysLate(date);
			if(listDVDs.get(i).getDaysLate() > 0)
				filteredList.add(get(i));
		}
		update();
		return filteredList;
	}

	/*******************************************************************
	 * Load database and set it to the master list.
	 * 
	 * @param filename
	 * @throws Exception if input stream throws exception
	 * @return the loaded db
	 ******************************************************************/
	public MyDoubleLinkedList<DVD> loadDB(String filename) 
			throws Exception {
		FileInputStream f_in = new 
				FileInputStream(filename);

		ObjectInputStream obj_in = 
				new ObjectInputStream (f_in);

		//cast read object to MyDoubleLinkedList and store it in master
		MyDoubleLinkedList<DVD> temp = 
				(MyDoubleLinkedList<DVD>)obj_in.readObject();
		listDVDs = temp;

		//set usedlist to master which updates JList as well
		setUsedList(listDVDs);

		obj_in.close();
		return temp;
	}

	/*******************************************************************
	 * Save database to file
	 * 
	 * @param filename
	 * @throws Exception if output stream throws exception
	 ******************************************************************/
	public void saveDB(String filename) throws Exception {
		FileOutputStream f_out = new 
				FileOutputStream(filename);
		ObjectOutputStream obj_out = new
				ObjectOutputStream (f_out);
		obj_out.writeObject(listDVDs);
		obj_out.flush();
		obj_out.close();

	}
}