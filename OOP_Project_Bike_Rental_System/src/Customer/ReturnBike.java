package Customer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import DataStructure.DLListMap_B;
import DataStructure.DoubleLinkedList;
import DataStructure.Position;
import Shop.Bike;

public class ReturnBike extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel label1,label2,label3,label4;
	JTextField field_start,field_end;
	JButton cancel_Button,returnBike_Button;
	Box box1,box2,box3,box4,box5,box6,box7,box8,BigBox1,BigBox2,FinalBox;
	JPanel p1,p2,p3;
	Object display[][] = new Object[200][5];
	Object temp_display[][] = new Object[200][7];
	String[] labels = {"No.","Type","Start time","Expected returning time","Time exceeded"};
	JTable t;
	JScrollPane s;
	private String user;
	int i;


	public ReturnBike(String username) {
		setUser(username);
		setBounds(600, 200, 666,580);
		setTitle("Return Bike(current user: "+username+")");
		Draw();
		setVisible(true);
	}
	
	DLListMap_B loadBikeData() {
		DLListMap_B bike_info = new DLListMap_B();
		File f = new File("bike_info.txt");
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String temp = null;
			while((temp = br.readLine())!=null) {
				bike_info.put(Integer.parseInt(temp), br.readLine(), br.readLine(), 
						br.readLine(), br.readLine(), br.readLine(), br.readLine());
			}
			br.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return bike_info;
	}
	
	void updateBikeData(DLListMap_B bike_info) {
		DLListMap_B sorted_bike_info = new DLListMap_B();
		for(int i = 1;i<bike_info.getMaxSerial()+1;i++) {
			if(bike_info.getBySerial(i)!=null) {
				Bike bike_i = bike_info.getBySerial(i);
				sorted_bike_info.put(i, bike_i.getStatus(), bike_i.getCurrentUser(), 
				bike_i.getRentalType(), bike_i.getStartTime(),bike_i.getReturnTime(), bike_i.getRemark());
			}
		}
		DoubleLinkedList list = sorted_bike_info.getList();
		File f = new File("bike_info.txt");
		try {
			FileWriter fw = new FileWriter(f.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			if(sorted_bike_info.size()==0) {
				bw.write("");
			}else {
				Position p = list.first();
				while(p!=null) {
					Bike b = (Bike) p.element();
					bw.write(b.getSerial()+"\n");
					bw.write(b.getStatus()+"\n");
					bw.write(b.getCurrentUser()+"\n");
					bw.write(b.getRentalType()+"\n");
					bw.write(b.getStartTime()+"\n");
					bw.write(b.getReturnTime()+"\n");
					bw.write(b.getRemark()+"\n");
					p = list.after(p);
				}
			}
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private int[] calculateTimeDifference(Date d1,Date d2) throws ParseException {
	    int result[] = new int[3];
		
	    long from1 = d1.getTime();  
	    long to1 = d2.getTime();  
	    int days = (int) ((to1 - from1) / (1000 * 60 * 60 * 24));
	    result[0] = days;

	      
	    long from2 = d1.getTime();  
	    long to2 = d2.getTime();  
	    int hours =  (int) ((to2 - from2) / (1000 * 60 * 60));
	    result[1] = hours - days * 24;

	    
	    long from3 = d1.getTime();  
	    long to3 = d2.getTime();  
	    int minutes =  (int) ((to3 - from3) / (1000 * 60));  
	    result[2] = minutes - hours * 60;
	     
	    return result;
	}
	
	private int[] calculateUsingTime(Date startTime, Date expectedReturnTime, Bike bike) {
		int[] totalTimeUsed, topUsingTime, timeExceeded;
		int result[] = new int[2];
		long total, top;
		int inLimit=0, outLimit=-1;
		Date now = new Date();
		try {
			total = now.getTime()-startTime.getTime();
			top = expectedReturnTime.getTime()- startTime.getTime();
			totalTimeUsed = calculateTimeDifference(startTime, new Date());
			topUsingTime = calculateTimeDifference(startTime,expectedReturnTime);
			if(total>top) {
				timeExceeded = calculateTimeDifference(expectedReturnTime, now);
				if(bike.getRentalType().equals("Hourly")||bike.getRentalType().equals("Hourly Family Rental")) {
					inLimit = topUsingTime[0]*24+topUsingTime[1];
					if(timeExceeded[2]>30) {
						outLimit = timeExceeded[0]*24+timeExceeded[1]+1;
					}else {
						outLimit = timeExceeded[0]*24+timeExceeded[1];
					}
				}else if(bike.getRentalType().equals("Daily")||bike.getRentalType().equals("Daily Family Rental")) {
					inLimit = topUsingTime[0];
					if(timeExceeded[2]>30) {
						outLimit = timeExceeded[0]*24+timeExceeded[1]+1;
					}else {
						outLimit = timeExceeded[0]*24+timeExceeded[1];
					}
				}else if(bike.getRentalType().equals("Weekly")||bike.getRentalType().equals("Weekly Family Rental")) {
					inLimit = topUsingTime[0]/7;
					if(timeExceeded[2]>30) {
						outLimit = timeExceeded[0]*24+timeExceeded[1]+1;
					}else {
						outLimit = timeExceeded[0]*24+timeExceeded[1];
					}
				}
			}else {
				outLimit = 0;
				if(bike.getRentalType().equals("Hourly")||bike.getRentalType().equals("Hourly Family Rental")) {
					if(totalTimeUsed[2]>30 && totalTimeUsed[0] + totalTimeUsed[1] != 0) {
						inLimit = totalTimeUsed[0]*24+totalTimeUsed[1]+1;
					}else if(totalTimeUsed[2] != 0 && totalTimeUsed[0] + totalTimeUsed[1] == 0) {
						inLimit = 1;
					}else {
						inLimit = totalTimeUsed[0]*24+totalTimeUsed[1];
					}
				}else if(bike.getRentalType().equals("Daily")||bike.getRentalType().equals("Daily Family Rental")) {
					if(totalTimeUsed[1]>12 && totalTimeUsed[0] != 0) {
						inLimit = totalTimeUsed[0]+1;
					}else if(totalTimeUsed[1]+totalTimeUsed[2] != 0 && totalTimeUsed[0] == 0) {
						inLimit = 1;
					}else {
						inLimit = totalTimeUsed[0];
					}
				}else if(bike.getRentalType().equals("Weekly")||bike.getRentalType().equals("Weekly Family Rental")) {
					if(totalTimeUsed[0]%7>3) {
						inLimit = totalTimeUsed[0]/7+1;
					}else if(totalTimeUsed[0]+totalTimeUsed[1]+totalTimeUsed[2] != 0) {
						inLimit = 1;
					}else {
						inLimit = totalTimeUsed[0]/7;
					}
				}
			}
			result[0]=inLimit;
			result[1]=outLimit;
			
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	void Draw() {
		label1 = new JLabel("Bikes I Rent");
		label2 = new JLabel("Please enter the serial number range of the bikes you want to return: ");
		label3 = new JLabel("from");
		label4 = new JLabel("to");
		field_start = new JTextField(10);
		field_end = new JTextField(10);
		cancel_Button = new JButton("Cancel");
		cancel_Button.addActionListener(this);
		returnBike_Button = new JButton("Return bike(s)");
		returnBike_Button.addActionListener(this);
		
		DLListMap_B bike_info = loadBikeData();
		Bike bikes_rent[] = bike_info.getByUser(this.getUser());
		for(i=0;i<bikes_rent.length;i++) {
			display[i][0] = bikes_rent[i].getSerial();
			display[i][1] = bikes_rent[i].getRentalType();
			display[i][2] = bikes_rent[i].getStartTime();
			display[i][3] = bikes_rent[i].getReturnTime();
		    Date return_time = bikes_rent[i].getReturnDate();
		    Date now = new Date();
		    try {
				int result[] = calculateTimeDifference(return_time, now);
				if (return_time.before(now)){ 
			    	display[i][4] = Integer.toString(result[0])+"d-"+Integer.toString(result[1])+"h-"+Integer.toString(result[2])+"m";	
			    }else{ 
			       display[i][4] = "N/M";
			    }
		    } catch (ParseException e) {
				e.printStackTrace();
			} 
		}
		
		box1 = Box.createHorizontalBox();
		box1.add(Box.createHorizontalStrut(280));
		box1.add(label1);
		
		box2 = Box.createVerticalBox();
		t = new JTable(display, labels);
		t.setEnabled(false);
		t.getColumnModel().getColumn(4).setPreferredWidth(145);
		t.getColumnModel().getColumn(3).setPreferredWidth(135);
		t.getColumnModel().getColumn(2).setPreferredWidth(135);
		t.getColumnModel().getColumn(1).setPreferredWidth(100);
		t.getColumnModel().getColumn(0).setPreferredWidth(50);
		s = new JScrollPane(t);
		box2.add(s);
		
		box3 = Box.createHorizontalBox();
		box3.add(label2);
		
		box4 = Box.createHorizontalBox();
		box4.add(Box.createHorizontalStrut(200));
		box4.add(label3);
		box4.add(field_start);
		box4.add(label4);
		box4.add(field_end);
		box4.add(Box.createHorizontalStrut(200));
		
		box5 = Box.createHorizontalBox();
		box5.add(returnBike_Button);
		box5.add(Box.createHorizontalStrut(50));
		box5.add(cancel_Button);
		
		box6 = Box.createVerticalBox();
		box6.add(box3);
		box6.add(box4);
		box6.add(Box.createVerticalStrut(5));
		box6.add(box5);
		
		p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(box1,BorderLayout.NORTH);
		p1.add(box2,BorderLayout.SOUTH);
		
		p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.add(box6,BorderLayout.CENTER);
		p2.add(Box.createVerticalStrut(20),BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		add(p1,BorderLayout.NORTH);
		add(p2,BorderLayout.SOUTH);
	}
	
	int calculateTotalFee(int inLimit, int outLimit, String type) {
		int total=0;
		if(type.equals("Hourly")) {
			total = inLimit*50+outLimit*50;
		}else if(type.equals("Daily")){
			total = inLimit*200+outLimit*50;
		}else if(type.equals("Weekly")){
			total = inLimit*600+outLimit*50;
		}else if(type.equals("Hourly Family Rental")){
			total = (inLimit*5+outLimit*5)*7;
		}else if(type.equals("Daily Family Rental")){
			total = (inLimit*20+outLimit*5)*7;
		}else if(type.equals("Weekly Family Rental")){
			total = (inLimit*60+outLimit*5)*7;
		}
		return total;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		int start=0, end=0, max, min,totalFee=0,j=0,i=0,k=0,n=0;
		DLListMap_B bike_info = loadBikeData();
		Bike rented_bikes[] = bike_info.getByUser(this.getUser());
		if(src == returnBike_Button) {
			if(field_start.getText().equals("")||field_end.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Please fill in all the information!");
			}else {
				try {
					start = Integer.parseInt(field_start.getText());
					end = Integer.parseInt(field_end.getText());
				} catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Please enter integer numbers!");
					j=-1;
				}
				
				max = rented_bikes[0].getSerial();
				min = rented_bikes[0].getSerial();
				for(i=1; i<rented_bikes.length; i++) {
					if(rented_bikes[i].getSerial()>max) {
						max = rented_bikes[i].getSerial();
					}else if(rented_bikes[i].getSerial()<min) {
						min = rented_bikes[i].getSerial();
					}
				}
				if(start<min || start>max || end<min || end > max ||start > end) {
					if(j==0) {
					JOptionPane.showMessageDialog(null, "Invalid numbers! Number in left blank must be smaller than the number in the right one, also the numbers need to be within range!");
					}
				}else {
					while(n<rented_bikes.length) {
						if(start<=rented_bikes[n].getSerial()&&rented_bikes[n].getSerial()<=end) {
							temp_display[k][0] = rented_bikes[n].getSerial();
							temp_display[k][1] = rented_bikes[n].getRentalType();
							temp_display[k][2] = rented_bikes[n].getStartTime();
							temp_display[k][3] = rented_bikes[n].getReturnTime();
							int rent_times[] = calculateUsingTime(rented_bikes[n].getStartDate(),rented_bikes[n].getReturnDate(), rented_bikes[n]);
							if(temp_display[k][1].equals("Hourly")||temp_display[k][1].equals("Hourly Family Rental")) {
								temp_display[k][4] = Integer.toString(rent_times[0])+" hours";
							}else if(temp_display[k][1].equals("Daily")||temp_display[k][1].equals("Daily Family Rental")) {
								temp_display[k][4] = Integer.toString(rent_times[0])+" days";
							}else if(temp_display[k][1].equals("Weekly")||temp_display[k][1].equals("Weekly Family Rental")) {
								temp_display[k][4] = Integer.toString(rent_times[0])+" weeks";
							}
							temp_display[k][5] = Integer.toString(rent_times[1])+" hours";
							int fee = calculateTotalFee(rent_times[0],rent_times[1],rented_bikes[n].getRentalType());
							temp_display[k][6] = fee;
							totalFee = totalFee + fee;
							rented_bikes[n].resetCurrentUser();
							rented_bikes[n].resetRentalType();
							rented_bikes[n].resetStartTime();
							rented_bikes[n].resetReturnTime();
							rented_bikes[n].resetStatus();
							bike_info.put(rented_bikes[n].getSerial(), rented_bikes[n].getStatus(), rented_bikes[n].getCurrentUser(), rented_bikes[n].getRentalType(), rented_bikes[n].getStartTime(), rented_bikes[n].getReturnTime(), rented_bikes[n].getRemark());
							k++;
						}
						n++;
					}
					temp_display[k+1][0] = "Total";
					temp_display[k+1][6] = totalFee;
					updateBikeData(bike_info);
					new BillIssue(this.getUser(),temp_display);
					this.dispose();
				}
			}
		}else if(src == cancel_Button) {
			this.dispose();
		}
		
	}

	public String getUser() {
		return user;
	}
	
	private void setUser(String username) {
		this.user = username;	
	}

}
