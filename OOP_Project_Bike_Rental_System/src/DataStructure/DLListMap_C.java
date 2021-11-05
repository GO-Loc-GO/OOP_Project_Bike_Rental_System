package DataStructure;

import Customer.Customer;

public class DLListMap_C implements Map_C{
	private DoubleLinkedList list = new DoubleLinkedList();
	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.size()==0;
	}

	@Override
	public Customer get(String k) {
		Position p = find(k);
		if(p==null) {
			return null;
		}
		return ((Customer)p.element());
	}

	@Override
	public Customer put(String k, String pin) {
		Position p = find(k);
		if(p==null) {
			Customer c = new Customer(k, pin);
			list.insertLast(c);
			return null;
		}else {
			Customer c = new Customer(k, pin);
			list.insertAfter(p, c);
			list.remove(p);
			return ((Customer)p.element());
		}
	}

	@Override
	public Customer remove(String k) {
		if(isEmpty()) {
			return null;
		}
		Position p=find(k);
		if(p==null) {
			return null;
		}else {
			list.remove(p);
		}
		return ((Customer)p.element());
	}

	@Override
	public Position find(String k) {
		Position p = list.first();
		Position last = list.last();
		while(p != last) {
			if(((Customer)p.element()).key().equals(k)) {
				return p;
			}else {
				p=list.after(p);
			}
		}
		if(!list.isEmpty() && ((Customer)p.element()).key().equals(k)) {
			return p;
		}else {
			return null;
		}
	}
	
	@Override
	public DoubleLinkedList getList() {
		return this.list;
	}
	

}
