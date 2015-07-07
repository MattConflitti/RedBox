package model;

import java.io.Serializable;

public class MyDoubleLinkedList<E> implements Serializable {
	
    private DNode<E> top;    // DNode is a double linked list node.
    private DNode<E> tail;

    public int size;

    public MyDoubleLinkedList() {}
    public int size() {}
    public void clear () {}
    public void add(E s)   {}      //  add at the end. 
    public void addFirst (E s) {}   //  add at the top. 
    public E remove(int index) {}  // remove first occurrence. 
    public E get(int index) {}
    public boolean removeAll(E s) {}  // return true if at least one item is removed
    public int find (E s) {}	   // return -1 if not found, otherwise return index


}
