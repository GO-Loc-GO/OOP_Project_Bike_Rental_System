package Shop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bike {
	private int serialNumber;
	private String status;
	private String currentUser;
	private String rentalType;
    private String rentalStartTime;
    private String estimatedReturnTime;
	private String remark;
	public Bike(int serial, String status,String currentUser, String rentalType,String startTime,String returnTime, String remark) {
		setSerial(serial);
		setCurrentUser(currentUser);
		this.rentalStartTime = startTime;
		this.estimatedReturnTime = returnTime;
		setRentalType(rentalType);
		setStatus(status);
		remark(remark);
	}
	public int getSerial() {
		return serialNumber;
	}
	public String getStatus() {
		return status; 
	}
	public String getRemark() {
		return remark;
	}
	public void setSerial(int srl) {
		this.serialNumber = srl;
	}
	public void setStatus(String str) {
		this.status = str;
	}
	public void resetStatus() {
		this.status = "Available";
	}
	public void remark(String str) {
		this.remark = str;
	}
	
	
	public String getStartTime() {
		return rentalStartTime;
	}
	public void setStartTime(String str) {
		this.rentalStartTime = str;
	}
	public void resetStartTime() {
		this.rentalStartTime = "N/M";
	}
	
	
	public String getReturnTime() {
		return estimatedReturnTime;
	}
	public void setReturnTime(String str) {
		this.estimatedReturnTime = str;
	}
	public void resetReturnTime() {
		this.estimatedReturnTime = "N/M";
	}
	
	public Date getStartDate() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date time=null;
		try {
		   time= sdf.parse(this.getStartTime());
		   } catch (ParseException e) {
		   e.printStackTrace();
		}
		return time;
	}
	
	public Date getReturnDate() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date time=null;
		try {
		   time= sdf.parse(this.getReturnTime());
		   } catch (ParseException e) {
		   e.printStackTrace();
		}
		return time;
	}
	
	
	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	public void resetCurrentUser() {
		this.currentUser = "N/M";
	}
	
	public String getRentalType() {
		return rentalType;
	}
	public void setRentalType(String rentalType) {
		this.rentalType = rentalType;
	}
	public void resetRentalType() {
		this.rentalType = "N/M";
	}
	
		
}
