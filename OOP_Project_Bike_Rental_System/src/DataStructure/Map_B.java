package DataStructure;

import Shop.Bike;

public interface Map_B {
	public int size();
	public boolean isEmpty();
	public Bike getBySerial(int k);
	public Bike put(int k, String currentUser, String startTime,String returnTime, String rentalType,String status, String remark);
	public Bike remove(int k);
	public Position findBySerial(int k);
	public Bike[] getByUser(String username);
	public Bike[] getByStatus(String status);
	public DoubleLinkedList getList();
	public Bike[] getByRentalType(String type);
}
