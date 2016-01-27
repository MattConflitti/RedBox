package model;

import java.io.Serializable;

/***********************************************************************
 * @author Matt Conflitti
 * @version 1.0
 * 
 * DNode stores a generic object as data and provides pointers to
 * the previous DNode and the next one.  Used for a doubly linked list.
 **********************************************************************/
public class DNode<E> implements Serializable,Comparable<Object> {
	
	/** serial id */
	private static final long serialVersionUID = 1L;

	/** generic object stored */
	public E data;
	
	/** points to next DNode */
	public DNode<E> next;
	
	/** points to previous DNode */
	public DNode<E> prev;
	
	/*******************************************************************
	 * Constructor sets all fields to specified values
	 *  
	 * @param date set to generic object provided
	 * @param next points to next dnode
	 * @param prev points to previous dnode
	 ******************************************************************/
	public DNode(E data, DNode<E> next, DNode<E> prev) {
		super();
		this.data = data;
		this.next = next;
		this.prev = prev;
	}

	/*******************************************************************
	 * Default constructor of DNode with no initial values
	 ******************************************************************/
	public DNode() {
		super();
	}

	/*******************************************************************
	 * Getter method to access object stored in node
	 * 
	 * @return data of type E
	 ******************************************************************/
	public E getData() {
		return data;
	}
	
	/*******************************************************************
	 * Setter method to set the data in the node
	 * 
	 * @param data object to be stored in node
	 ******************************************************************/
	public void setData(E data) {
		this.data = data;
	}

	/*******************************************************************
	 * Getter method to access next node
	 * 
	 * @return pointer to next node
	 ******************************************************************/
	public DNode<E> getNext() {
		return next;
	}
	
	/*******************************************************************
	 * Setter method to set pointer to next node
	 * 
	 * @param next pointer to next node
	 ******************************************************************/
	public void setNext(DNode<E> next) {
		this.next = next;
	}

	/*******************************************************************
	 * Getter method to acces previous node
	 * 
	 * @return pointer to previous node
	 ******************************************************************/
	public DNode<E> getPrev() {
		return prev;
	}

	/*******************************************************************
	 * Setter method to set pointer to previous node
	 * 
	 * @param prev pointer to previous node
	 ******************************************************************/
	public void setPrev(DNode<E> prev) {
		this.prev = prev;
	}

	/*******************************************************************
	 * Compares this object to the one is parameter. It returns the
	 * value returned by the data's compareTo method from inside the 
	 * node.
	 * 
	 * @param o Object to compare against
	 ******************************************************************/
	public int compareTo(Object o) {
		DNode d = (DNode) o;
		return ((Comparable) d.data).compareTo(this.data);
	}
	
	
}
