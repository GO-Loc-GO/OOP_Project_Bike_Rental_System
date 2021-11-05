package Customer;

import java.awt.FlowLayout;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import DataStructure.DLListMap_B;
import DataStructure.DoubleLinkedList;
import DataStructure.Position;
import Shop.Bike;

public class RentBike extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel label1,label2,label3,label4,label5,label6;
	JTextField field_NumberOfBikes,field_LengthOfTime;
	JButton allBikes_Button,availableBikes_Button, rent_Button,returnBike_Button,cancel_Button;
	JRadioButton hour_Button,day_Button, week_Button;
	JComboBox<String> c1;
	Box box1,box2,box3,box4,box5,box6,box7,box8,BigBox1,BigBox2,BigBox3,FinalBox;
	JPanel p1,p2,p3;
	Object display[][] = new Object[66][3];
	String[] labels = {"Serial Number","Current Status","Estimated returning time"};
	JTable t;
	JScrollPane s;
	private String user;
	private DLListMap_B bike_info = loadBikeData();
	
	
	public RentBike(String username) {
		setBounds(700, 400, 320, 236);
		setTitle("Rent Bike(current user: "+username+")");
		Draw();
		this.setUser(username);
		setVisible(true);
	}
	
	void Draw() {
		int numOfBikesAvailable = bike_info.getByStatus("Available").length;
		label1 = new JLabel("Please enter the number of bikes you want to rent:");
		field_NumberOfBikes = new JTextField(10);
		label2 = new JLabel("Please enter your desired time length of rental:");
		field_LengthOfTime = new JTextField(10);
		label3 = new JLabel("(Bikes currently available: "+Integer.toString(numOfBikesAvailable)+")");
		label4 = new JLabel("Tip: Renting 3-5 bikes as family rental would");
		label5 = new JLabel("be given 30% discount each at settlement.");
		rent_Button = new JButton("Rent");
		cancel_Button = new JButton("Cancel");
		rent_Button.addActionListener(this);
		cancel_Button.addActionListener(this);

		c1 = new JComboBox<String>();
		c1.addItem("-Rental Type-");
		c1.addItem("Hour(s)");
		c1.addItem("Day(s)");
		c1.addItem("Week(s)");
		
		box1 = Box.createHorizontalBox();
		box1.add(label1);
		box2 = Box.createHorizontalBox();
		box2.add(field_NumberOfBikes);
		box2.add(label3);
		
		BigBox1 = Box.createVerticalBox();
		BigBox1.add(box1);
		BigBox1.add(box2);
		
		
		box3 = Box.createHorizontalBox();
		box3.add(label2);
		box4 = Box.createHorizontalBox();
		box4.add(field_LengthOfTime);
		box4.add(c1);
		
		BigBox2 = Box.createVerticalBox();
		BigBox2.add(box3);
		BigBox2.add(box4);
		
		box7 = Box.createHorizontalBox();
		box7.add(label4);
		
		box8 = Box.createHorizontalBox();
		box8.add(label5);
		
		BigBox2.add(box7);
		BigBox2.add(box8);
		
		box5 = Box.createHorizontalBox();
		box5.add(rent_Button);
		box6 = Box.createHorizontalBox();
		box6.add(cancel_Button);
		
		BigBox3 = Box.createVerticalBox();
		BigBox3.add(box5);
		BigBox3.add(Box.createVerticalStrut(5));
		BigBox3.add(box6);
		
		FinalBox = Box.createVerticalBox();
		FinalBox.add(BigBox1);
		FinalBox.add(BigBox2);
		FinalBox.add(Box.createVerticalStrut(10));
		FinalBox.add(BigBox3);
		
		this.setLayout(new FlowLayout());
		add(FinalBox);
		
		
	}
	
	DLListMap_B loadBikeData() {
		DLListMap_B bike_info = new DLListMap_B();
		File f = new File("bike_info.txt");
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String temp = null;
			while((temp = br.readLine())!=null) {
				bike_info.put(Integer.parseInt(temp), br.readLine(), br.readLine(), br.readLine(), br.readLine(), br.readLine(), br.readLine());
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
				sorted_bike_info.put(i, bike_i.getStatus(), bike_i.getCurrentUser(), bike_i.getRentalType(), bike_i.getStartTime(),bike_i.getReturnTime(), bike_i.getRemark());
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
	
	public String calculateEstimatedReturnTime(long length, String type) throws ParseException {
		Date start_Date = new Date();
		long now = start_Date.getTime();
		long end = Long.parseUnsignedLong("0");
	    if(type.equals("Weekly")) {
	    	end = now + length * 7 * 24 * 60 * 60 *1000;
	    }else if(type.equals("Daily")) {
	    	end = now + length * 24 * 60 * 60 * 1000;
	    }else if(type.equals("Hourly")) {
	    	end = now + length * 60 * 60 *1000;
	    }
	    Date return_Date = new Date(end);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	    String result = sdf.format(return_Date);
	    
	    return result;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		int index = c1.getSelectedIndex();
		int j = 1;
		if(src == rent_Button) {
			String rentalType = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			String return_Time = null;
			String start_Time = sdf.format(new Date());
			int numOfBikesToRent=0, lengthOfTimeToRent=0;
			Bike available_Bikes[] = bike_info.getByStatus("Available");
			try {
				numOfBikesToRent = Integer.parseInt(field_NumberOfBikes.getText());
				lengthOfTimeToRent = Integer.parseInt(field_LengthOfTime.getText());
			} catch(NumberFormatException e1) {
				JOptionPane.showMessageDialog(null,"Please enter positive integer numbers in the blanks!");
				field_NumberOfBikes.setText("");
				field_LengthOfTime.setText("");
				j=0;
			}
			if(field_NumberOfBikes.getText().equals("")||field_LengthOfTime.getText().equals("")||index == 0) {
				if(j==1) {
					JOptionPane.showMessageDialog(null, "Please fill in all the information and choose your rental type!");
				}
			}else if(numOfBikesToRent <= 0 || lengthOfTimeToRent <= 0) {
				JOptionPane.showMessageDialog(null,"Please enter positive integer numbers in the blanks!");
				field_NumberOfBikes.setText("");
				field_LengthOfTime.setText("");
				c1.setSelectedIndex(0);
			}else if(numOfBikesToRent > available_Bikes.length) {
				JOptionPane.showMessageDialog(null,"Chosen number of bikes to rent can not exceed available stock!");
				field_NumberOfBikes.setText("");
			}else {
				try {
					switch(index) {
						case 1:
							return_Time = calculateEstimatedReturnTime(lengthOfTimeToRent,"Hourly");
							rentalType = "hourly";
							if(3 <= numOfBikesToRent && numOfBikesToRent <= 5) {
								for(int i = 0;i<numOfBikesToRent;i++) {
									available_Bikes[i].setCurrentUser(getUser());
									available_Bikes[i].setStatus("Rented");
									available_Bikes[i].setStartTime(start_Time);
									available_Bikes[i].setReturnTime(return_Time);
									available_Bikes[i].setRentalType("Hourly Family Rental");
									bike_info.put(available_Bikes[i].getSerial(), available_Bikes[i].getStatus(), available_Bikes[i].getCurrentUser(), available_Bikes[i].getRentalType(), available_Bikes[i].getStartTime(), available_Bikes[i].getReturnTime(), available_Bikes[i].getRemark());
								}
							}else {
								for(int i = 0;i<numOfBikesToRent;i++) {
									available_Bikes[i].setCurrentUser(getUser());
									available_Bikes[i].setStatus("Rented");
									available_Bikes[i].setStartTime(start_Time);
									available_Bikes[i].setReturnTime(return_Time);
									available_Bikes[i].setRentalType("Hourly");
									bike_info.put(available_Bikes[i].getSerial(), available_Bikes[i].getStatus(), available_Bikes[i].getCurrentUser(), available_Bikes[i].getRentalType(), available_Bikes[i].getStartTime(), available_Bikes[i].getReturnTime(), available_Bikes[i].getRemark());
								}
							}
							updateBikeData(bike_info);
							break;
						case 2:
							return_Time = calculateEstimatedReturnTime(lengthOfTimeToRent, "Daily");
							rentalType = "daily";
							if(3 <= numOfBikesToRent && numOfBikesToRent <= 5) {
								for(int i = 0;i<numOfBikesToRent;i++) {
									available_Bikes[i].setCurrentUser(getUser());
									available_Bikes[i].setStatus("Rented");
									available_Bikes[i].setStartTime(start_Time);
									available_Bikes[i].setReturnTime(return_Time);
									available_Bikes[i].setRentalType("Daily Family Rental");
									bike_info.put(available_Bikes[i].getSerial(), available_Bikes[i].getStatus(), available_Bikes[i].getCurrentUser(), available_Bikes[i].getRentalType(), available_Bikes[i].getStartTime(), available_Bikes[i].getReturnTime(), available_Bikes[i].getRemark());
								}
							}else {
								for(int i = 0;i<numOfBikesToRent;i++) {
									available_Bikes[i].setCurrentUser(getUser());
									available_Bikes[i].setStatus("Rented");
									available_Bikes[i].setStartTime(start_Time);
									available_Bikes[i].setReturnTime(return_Time);
									available_Bikes[i].setRentalType("Daily");
									bike_info.put(available_Bikes[i].getSerial(), available_Bikes[i].getStatus(), available_Bikes[i].getCurrentUser(), available_Bikes[i].getRentalType(), available_Bikes[i].getStartTime(), available_Bikes[i].getReturnTime(), available_Bikes[i].getRemark());
								}
							}
							updateBikeData(bike_info);
							break;
						case 3:
							return_Time = calculateEstimatedReturnTime(lengthOfTimeToRent, "Weekly");
							rentalType = "weekly";
							if(3 <= numOfBikesToRent && numOfBikesToRent <= 5) {
								for(int i = 0;i<numOfBikesToRent;i++) {
									available_Bikes[i].setCurrentUser(getUser());
									available_Bikes[i].setStatus("Rented");
									available_Bikes[i].setStartTime(start_Time);
									available_Bikes[i].setReturnTime(return_Time);
									available_Bikes[i].setRentalType("Weekly Family Rental");
									bike_info.put(available_Bikes[i].getSerial(), available_Bikes[i].getStatus(), available_Bikes[i].getCurrentUser(), available_Bikes[i].getRentalType(), available_Bikes[i].getStartTime(), available_Bikes[i].getReturnTime(), available_Bikes[i].getRemark());
								}
							}else {
								for(int i = 0;i<numOfBikesToRent;i++) {
									available_Bikes[i].setCurrentUser(getUser());
									available_Bikes[i].setStatus("Rented");
									available_Bikes[i].setStartTime(start_Time);
									available_Bikes[i].setReturnTime(return_Time);
									available_Bikes[i].setRentalType("Weekly");
									bike_info.put(available_Bikes[i].getSerial(), available_Bikes[i].getStatus(), available_Bikes[i].getCurrentUser(), available_Bikes[i].getRentalType(), available_Bikes[i].getStartTime(), available_Bikes[i].getReturnTime(), available_Bikes[i].getRemark());
								}
							}
							updateBikeData(bike_info);
							break;
					}
					if(3 <= numOfBikesToRent && numOfBikesToRent <= 5) {
						JOptionPane.showMessageDialog(null, "              You've successfully rent "+ Integer.toString(numOfBikesToRent)+" bike(s) charged "+rentalType+" as Family Rental\n                             Please return the bikes before " + return_Time+"\nMore info can be viewed by clicking the 'See bikes I rent' button in the customer interface");
					}else {
						JOptionPane.showMessageDialog(null, "                  You've successfully rent "+ Integer.toString(numOfBikesToRent)+" bike(s) charged "+rentalType+"\n                        Please return the bikes before " + return_Time+"\nMore info can be viewed by clicking the 'See bikes I rent' button in the customer interface");
					}
					this.dispose();
				} catch(ParseException e2) {
					e2.printStackTrace();
				}
			}
		}else if(src == cancel_Button) {
			this.dispose();
		}

	}

	public String getUser() {
		return user;
	}

	private void setUser(String user) {
		this.user = user;
	}

}
