package DataStructure;

import Shop.Administrator;

public interface Map_A {
	public int size();
	public boolean isEmpty();
	public Administrator get(String k);
	public Administrator put(String k, String pin);
	public Administrator remove(String k);
	public Position find(String k);
	public DoubleLinkedList getList();
}
