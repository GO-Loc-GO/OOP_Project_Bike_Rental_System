package DataStructure;

import Customer.Customer;

public interface Map_C {
	public int size();
	public boolean isEmpty();
	public Customer get(String k);
	public Customer put(String k, String pin);
	public Customer remove(String k);
	public Position find(String k);
	public DoubleLinkedList getList();
}
