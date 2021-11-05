package DataStructure;

import Shop.Bike;

public class DLListMap_B implements Map_B{
	private DoubleLinkedList list = new DoubleLinkedList();
	private int maxSerial=0,minSerial=0;
	
	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.size()==0;
	}

	@Override
	public Bike getBySerial(int k) {
		Position p = findBySerial(k);
		if(p==null) {
			return null;
		}
		return ((Bike)p.element());
	}

	@Override
	public Bike put(int k, String status, String currentUser,String rentalType, String startTime,String returnTime, String remark) {
		Position p = findBySerial(k);
		if(p==null) {
			Bike b = new Bike(k, status, currentUser, rentalType, startTime, returnTime, remark);
			list.insertLast(b);
			if(k>maxSerial) {
				maxSerial = k;
			}else if(k<minSerial) {
				minSerial = k;
			}
			return null;
		}else {
			Bike b = new Bike(k, status, currentUser, rentalType, startTime, returnTime, remark);
//			System.out.println("test");
			list.insertAfter(p, b);
			list.remove(p);
			return ((Bike)p.element());
		}
	}

	@Override
	public Bike remove(int k) {
		if(isEmpty()) {
			return null;
		}
		Position p=findBySerial(k);
		if(p==null) {
			return null;
		}else {
			list.remove(p);
		}
		return ((Bike)p.element());
	}

	@Override
	public Position findBySerial(int k) {
		Position p = list.first();
		Position last = list.last();
		while(p != last) {
			if(((Bike)p.element()).getSerial() == k) {
//				System.out.println("test2");
				return p;
			}else {
				p=list.after(p);
			}
		}
		if(!list.isEmpty() && ((Bike)p.element()).getSerial() == k) {
			return p;
		}else {
			return null;
		}
	}
	
	@Override
	public Bike[] getByUser(String username) {
		Position p = list.first();
		int i=0;
		int size=0;
		while(p != null) {
			if(((Bike)p.element()).getCurrentUser().equals(username)) {
				size++;
			}
			p=list.after(p);
		}
		Bike bikes[] = new Bike[size];
		p=list.first();
		while(p != null) {
			if(((Bike)p.element()).getCurrentUser().equals(username)) {
				bikes[i] = (Bike) p.element();
				i++;
			}
			p=list.after(p);
		}
		return bikes;
	}
	
	@Override
	public Bike[] getByStatus(String status) {
		Position p = list.first();
		int i=0;
		int size=0;
		while(p != null) {
			if(((Bike)p.element()).getStatus().equals(status)) {
				size++;
			}
			p=list.after(p);
		}
		Bike bikes[] = new Bike[size];
		p=list.first();
		while(p != null) {
			if(((Bike)p.element()).getStatus().equals(status)) {
				bikes[i] = (Bike) p.element();
				i++;
			}
			p=list.after(p);
		}
		return bikes;
	}
	
	@Override
	public Bike[] getByRentalType(String type) {
		Position p = list.first();
		int i=0;
		int size=0;
		while(p != null) {
			if(((Bike)p.element()).getRentalType().equals(type)) {
				size++;
			}
			p=list.after(p);
		}
		Bike bikes[] = new Bike[size];
		p=list.first();
		while(p != null) {
			if(((Bike)p.element()).getRentalType().equals(type)) {
				bikes[i] = (Bike) p.element();
				i++;
			}
			p=list.after(p);
		}
		return bikes;
	}
	
	@Override
	public DoubleLinkedList getList() {
		return this.list;
	}

	public int getMaxSerial() {
		return maxSerial;
	}
	
	public int getMinSerial() {
		return minSerial;
	}
	
	
}
