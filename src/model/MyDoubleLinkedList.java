package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/***********************************************************************
 * MyDoubleLinkedList is a custom written double linked list. Uses
 * a top and tail node pointer to keep track of nodes. Implements only
 * methods from List interface needed to be sorted.
 * 
 * @author Matt Conflitti
 * @version 1.0
 **********************************************************************/
public class MyDoubleLinkedList<E> implements Serializable,List<E> {

	/** serial id */
	private static final long serialVersionUID = 1L;

	/** DNode that points to first element in list */
	private DNode<E> top;

	/** DNode that points to last element in list */
	private DNode<E> tail;

	/** size of the list */
	public int size;

	/*******************************************************************
	 * Constructor sets nodes to null and size to zero
	 ******************************************************************/
	public MyDoubleLinkedList() {
		size = 0;
		top = null;
		tail = null;
	}

	/*******************************************************************
	 * Get the size of the list
	 * 
	 * @return size
	 ******************************************************************/
	public int size() {
		return size;
	}

	/*******************************************************************
	 * Clear the entire list
	 ******************************************************************/
	public void clear () {

		//set both top and tail to null
		top = tail = null;

		//reset size
		size = 0;
	}

	/*******************************************************************
	 * Add an element to the end of list
	 * 
	 * @param s element of type E
	 * @return true if item was added successfully
	 ******************************************************************/
	public boolean add(E s)   {

		//if list is empty
		if(top == null) {

			//set top and tail to first node
			top = tail = new DNode<E>(s,null,null);
			size++;
			return true;
		}

		//otherwise set tail to new node at bottom
		tail.setNext(new DNode<E>(s,null,tail));
		tail = tail.getNext();
		size++;
		return true;
	}  

	/*******************************************************************
	 * Add an element to the beginning of list
	 * 
	 * @param s element of type E
	 ******************************************************************/
	public void addFirst (E s) {

		//if list is empty set top and tail to first node
		if(top == null) {
			top = tail = new DNode<E>(s,null,null);
			size++;
			return;
		}

		//otherwise set top to new node before it
		top.setPrev(new DNode<E>(s,top,null));
		top = top.getPrev();
		size++;
	} 

	/*******************************************************************
	 * Remove element at desired index
	 * 
	 * @param index location of node to be removed
	 * @return element of type E
	 ******************************************************************/
	public E remove(int index) {

		//throw error if index is out of bounds
		if(index >= size)
			throw new IndexOutOfBoundsException();

		int count = 0;

		//loop through list
		DNode<E> temp = top;
		while (temp != null) {

			//if item at correct index
			if(count == index) {

				//if it is only item in list
				if(top == tail) {

					//remove it and return item
					top = tail = null;
					size--;
					return (E) temp.getData();
				}

				//if last item in list of many remove it
				if(temp == tail) {
					tail = tail.getPrev();
					tail.setNext(null);
					size--;
					return (E) temp.getData();
				}

				//if first item in list of many remove it
				if(temp == top) {
					top = top.getNext();
					top.setPrev(null);
					size--;
					return (E) temp.getData();
				}

				//anywhere in middle of list
				temp.getPrev().setNext(temp.getNext());
				temp.getNext().setPrev(temp.getPrev());
				size--;
				return (E) temp.getData();

			}

			//increment counter and through list
			count++;
			temp = temp.getNext();

		}

		return null;


	} 

	/*******************************************************************
	 * Get data from within node at desired location in list
	 * 
	 * @param index location of node
	 * @return element of type E
	 ******************************************************************/
	public E get(int index) {

		//throw error if index out of bounds
		if(index >= size)
			throw new IndexOutOfBoundsException();

		int count = 0;

		//if list empty return null
		if(top == null)
			return null;

		//increment through list until index is reached
		DNode<E> temp = top;
		while (temp != null) {

			if(count == index)
				return (E) temp.getData();
			count++;
			temp = temp.getNext();

		}

		return null;
	}

	/*******************************************************************
	 * Remove all occurrences of desired element in list
	 * 
	 * @param s element of type E
	 * @return true if at least one item was removed
	 ******************************************************************/
	public boolean removeAll(E s) {

		int removed = 0;

		//if list is empty return false
		if(top == null)
			return false;

		//increment through list finding all occurrences
		DNode<E> temp = top;
		while (temp != null) {

			//if node data is equal to desired data remove node
			//handle all possible positions of node in list
			//count how many are removed
			if(temp.getData().equals(s)) {
				if(top == tail) {
					removed++;
					top = tail = null;
				}
				else if(temp == tail) {
					tail = tail.getPrev();
					tail.setNext(null);
					removed++;
				}
				else if(temp == top) {
					top = top.getNext();
					top.setPrev(null);
					removed++;
				}
				else {
					temp.getPrev().setNext(temp.getNext());
					temp.getNext().setPrev(temp.getPrev());
					removed++;
				}
			}
			temp = temp.getNext();
		}

		//if removed at least one node update size and return true
		if(removed > 0) {
			size -= removed;
			return true;
		} else {
			return false;
		}
	}

	/*******************************************************************
	 * Get the index of node storing the matching element E
	 * 
	 * @param s element of type E
	 * @return index of element's node
	 ******************************************************************/
	public int find (E s) {
		int count = 0;

		//if list is empty return -1
		if(top == null)
			return -1;

		//iterate through the array to find the index of the data
		DNode<E> temp = top;
		while (temp != null) {

			if(temp.getData().equals(s))
				return count;

			count++;
			temp = temp.getNext();

		}

		//return -1 if not found
		return -1;
	}

	/*******************************************************************
	 * Determine whether the list contains a certain element or not
	 * 
	 * @param s element of type Object
	 * @return true if list contains Object in nodes
	 ******************************************************************/
	public boolean contains(Object s) {

		//if list is empty return false
		if(top == null)
			return false;

		//iterate through list and return true when Object s is found
		DNode<E> temp = top;
		while (temp != null) {

			if(temp.getData().equals(s))
				return true;

			temp = temp.getNext();

		}

		return false;
	}

	/*******************************************************************
	 * Loops through list and prints elements in node to console.
	 ******************************************************************/
	public void display() {

		//iterate through and printout list
		DNode<E> temp = top;
		while (temp != null) {

			System.out.println (temp.getData());
			temp = temp.getNext();

		}

	}

	/*******************************************************************
	 * Checks if list is empty
	 * 
	 * @return true if size is less than or equal to zero
	 ******************************************************************/
	public boolean isEmpty() {
		return size <= 0;
	}
	@Override
	public Object[] toArray() {

		//if list is empty return null
		if(top == null)
			return null;

		//create local array of list size
		Object[] a = new Object[size];
		int count = 0;

		//iterate through list and add node data to array
		DNode<E> temp = top;
		while (temp != null) {

			a[count] = temp.data;
			count++;
			temp = temp.getNext();

		}
		return a;

	}

	@Override
	public ListIterator listIterator() {

		// create anonymous class of type ListIterator to
		//generate an iterator for use by the sort.
		ListIterator iterator = new ListIterator<E>() {

			/** the node that is returned by next() */
			private DNode<E> current      = top;

			/** the last node to be returned by prev() or next() 
			 * reset to null upon intervening remove() or add()*/
			private DNode<E> lastAccessed = null;

			/** index of iterator */
			private int index = 0;

			//make sure list has previous and next indexes
			public boolean hasNext()      { return index < size; }
			public boolean hasPrevious()  { return index > 0; }
			public int previousIndex()    { return index - 1; }
			public int nextIndex()        { return index;     }

			//get next item
			public E next() {
				if (!hasNext()) throw new RuntimeException();
				lastAccessed = current;
				E item = current.data;
				current = current.next; 
				index++;
				return item;
			}

			//get previous item
			public E previous() {
				if (!hasPrevious()) 
					throw new RuntimeException();
				current = current.prev;
				index--;
				lastAccessed = current;
				return current.data;
			}

			// replace the item of the element that was last 
			//accessed by next() or previous() condition: no calls to 
			//remove() or add() after last call to next() or previous()
			public void set(E item) {
				if (lastAccessed == null) 
					throw new IllegalStateException();
				lastAccessed.data = item;
			}

			// remove the element that was last accessed by 
			//next() or previous() condition: no calls to remove() 
			//or add() after last call to next() or previous()
			public void remove() { 
				if (lastAccessed == null) 
					throw new IllegalStateException();
				DNode<E> x = lastAccessed.prev;
				DNode<E> y = lastAccessed.next;
				x.next = y;
				y.prev = x;
				size--;
				if (current == lastAccessed)
					current = y;
				else
					index--;
				lastAccessed = null;
			}

			// add element to list 
			public void add(E item) {
				DNode<E> x = current.prev;
				DNode<E> y = new DNode<E>();
				DNode<E> z = current;
				y.data = item;
				x.next = y;
				y.next = z;
				z.prev = y;
				y.prev = x;
				size++;
				index++;
				lastAccessed = null;
			}
		};

		return iterator;
	}

	/*******************************************************************
	 * toString method return string will all items concatenated
	 * @return concatenated string
	 ******************************************************************/
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (E item : this)
			s.append(item + " ");
		return s.toString();
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(int index, Collection c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Object set(int index, Object element) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void add(int index, Object element) {
		// TODO Auto-generated method stub

	}
	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
}