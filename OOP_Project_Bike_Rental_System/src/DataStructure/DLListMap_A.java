package DataStructure;

import Shop.Administrator;

public class DLListMap_A implements Map_A{
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
	public Administrator get(String k) {
		Position p = find(k);
		if(p==null) {
			return null;
		}
		return ((Administrator)p.element());
	}

	@Override
	public Administrator put(String k, String pin) {
		Position p = find(k);
		if(p==null) {
			Administrator c = new Administrator(k, pin);
			list.insertLast(c);
			return null;
		}else {
			Administrator c = new Administrator(k, pin);
			list.insertAfter(p, c);
			list.remove(p);
			return ((Administrator)p.element());
		}
	}

	@Override
	public Administrator remove(String k) {
		if(isEmpty()) {
			return null;
		}
		Position p=find(k);
		if(p==null) {
			return null;
		}else {
			list.remove(p);
		}
		return ((Administrator)p.element());
	}

	@Override
	public Position find(String k) {
		Position p = list.first();
		Position last = list.last();
		while(p != last) {
			if(((Administrator)p.element()).key().equals(k)) {
				return p;
			}else {
				 p=list.after(p);
			}
		}
		if(!list.isEmpty() && ((Administrator)p.element()).key().equals(k)) {
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
