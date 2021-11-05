package Customer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

import DataStructure.DLListMap_B;
import Shop.Bike;

public class BikesIRent extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel label1,label2,label3;
	JButton return_Button,returnBike_Button,rent_Button;
	Box box1,box2,box3,box4,box5,box6,box7,box8,BigBox1,BigBox2,FinalBox;
	JPanel p1,p2,p3;
	Object display[][] = new Object[200][5];
	String[] labels = {"No.","Type","Start time","Expected returning time","Time exceeded"};
	JTable t;
	JScrollPane s;
	private String temp_user;
	int i;
	
	public BikesIRent(String username) {
		setUser(username);
		setLayout(new FlowLayout());
		setBounds(600, 200, 666,520);
		setTitle("Customer interface(current user: "+username+")");
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
	
	public int[] calculateTimeDifference(Date d1,Date d2) throws ParseException {
	    int result[] = new int[3];
		
	    long from1 = d1.getTime();  
	    long to1 = d2.getTime();  
	    int days = (int) ((to1 - from1) / (1000 * 60 * 60 * 24));
	    result[0] = days;

	      
	    long from2 = d1.getTime();  
	    long to2 = d2.getTime();  
	    int hours = (int) ((to2 - from2) / (1000 * 60 * 60));
	    result[1] = hours - days * 24;

	    
	    long from3 = d1.getTime();  
	    long to3 = d2.getTime();  
	    int minutes = (int) ((to3 - from3) / (1000 * 60));  
	    result[2] = minutes - hours * 60;
	    
	    return result;
	}
	
	void Draw() {
		label1 = new JLabel("Bikes I Rented");
		return_Button = new JButton("Back");
		return_Button.addActionListener(this);
		returnBike_Button = new JButton("Return bike");
		returnBike_Button.addActionListener(this);
		rent_Button = new JButton("Rent more");
		rent_Button.addActionListener(this);
		
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
			       display[i][4] = "None";
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
		t.getColumnModel().getColumn(1).setPreferredWidth(130);
		t.getColumnModel().getColumn(0).setPreferredWidth(50);
		s = new JScrollPane(t);
		box2.add(s);
		
		box3 = Box.createHorizontalBox();
		box3.add(Box.createHorizontalStrut(140));
		box3.add(return_Button);
		box3.add(Box.createHorizontalStrut(50));
		box3.add(returnBike_Button);
		box3.add(Box.createHorizontalStrut(50));
		box3.add(rent_Button);
		
		p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(box1,BorderLayout.NORTH);
		p1.add(box2,BorderLayout.SOUTH);
		
		p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.add(box3,BorderLayout.CENTER);
		p2.add(Box.createVerticalStrut(5),BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		add(p1,BorderLayout.NORTH);
		add(p2,BorderLayout.SOUTH);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == return_Button) {
			this.dispose();
		}else if(src == returnBike_Button) {
			DLListMap_B bike_info = loadBikeData();
			Bike rented_bikes[] = bike_info.getByUser(getUser());
			if(rented_bikes.length == 0) {
				JOptionPane.showMessageDialog(null, "You have no bike need to be returned!");
			}else {
				this.dispose();
				new ReturnBike(this.getUser());
			}
		}else if(src == rent_Button) {
			this.dispose();
			new RentBike(this.getUser());
		}
	}

	public String getUser() {
		return this.temp_user;
	}
	private void setUser(String username) {
		this.temp_user = username;
	}

}
