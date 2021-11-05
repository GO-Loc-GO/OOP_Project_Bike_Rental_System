package Customer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
import DataStructure.DLListMap_C;
import DataStructure.DoubleLinkedList;
import DataStructure.Position;
import Indexes.Login;
import Shop.Bike;

public class Customer_Interface extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel label1,label2,label3,label4;
	JTextField field_Account;
	JButton allBikes_Button,availableBikes_Button,myRent_Button, rent_Button,returnBike_Button,logOut_Button,closeAccount_Button;
	Box box1,box2,box3,box4,box5,box6,box7,box8,BigBox1,BigBox2,FinalBox;
	JPanel p1,p2,p3;
	Object display[][] = new Object[200][3];
	String[] labels = {"Serial Number","Current Status","Estimated returning time"};
	JTable t;
	JScrollPane s;
	private String user;
	
	public Customer_Interface(String username) {
		setBounds(300, 200, 696, 400);
		setTitle("Customer interface(current user: "+username+")");
		Draw();
		this.setUser(username);
		setVisible(true);
	}
	
	void Draw() {
		label1 = new JLabel("Bike Rental Information");
		label2 = new JLabel("Information Browsing Options:");
		label3 = new JLabel("Rental Options:");
		label4 = new JLabel("Account Options:");
		allBikes_Button = new JButton("See all bikes");
		allBikes_Button.addActionListener(this);
		availableBikes_Button = new JButton("See available bikes");
		availableBikes_Button.addActionListener(this);
		myRent_Button = new JButton("See Bikes I'm Renting");
		myRent_Button.addActionListener(this);
		rent_Button = new JButton("Rent bikes");
		rent_Button.addActionListener(this);
		returnBike_Button = new JButton("Return bikes");
		returnBike_Button.addActionListener(this);
		closeAccount_Button = new JButton("Close Account");
		closeAccount_Button.addActionListener(this);
		logOut_Button = new JButton("Sign out");
		logOut_Button.addActionListener(this);
		
		box1=Box.createVerticalBox();
		box1.add(label2);
		box1.add(allBikes_Button);
		box1.add(Box.createVerticalStrut(5));
		box1.add(availableBikes_Button);
		box1.add(Box.createVerticalStrut(5));
		box1.add(myRent_Button);
		
		box2=Box.createVerticalBox();
		box2.add(label3);
		box2.add(Box.createVerticalStrut(5));
		box2.add(rent_Button);
		box2.add(Box.createVerticalStrut(5));
		box2.add(returnBike_Button);
		
		box3=Box.createVerticalBox();
		box3.add(label4);
		box3.add(Box.createVerticalStrut(5));
		box3.add(closeAccount_Button);
		box3.add(Box.createVerticalStrut(5));
		box3.add(logOut_Button);
		
		t = new JTable(display, labels);
		t.setEnabled(false);
		s = new JScrollPane(t);
		
		BigBox1 = Box.createVerticalBox();
		BigBox1.add(Box.createVerticalStrut(10));
        BigBox1.add(box1);
        BigBox1.add(Box.createVerticalStrut(30));
        BigBox1.add(box2);
        BigBox1.add(Box.createVerticalStrut(30));
        BigBox1.add(box3);	
		
        this.setLayout(null);
		label1.setBounds(175,0,150,20);
		s.setBounds(0, 20, 500, 339);
		BigBox1.setBounds(505,8,200,400);
		add(label1);
		add(s);
		add(BigBox1);
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
	
	DLListMap_C loadCustomerData() {
		File f = new File("customer_info.txt");
		DLListMap_C customer_info = new DLListMap_C();
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String temp = "";
			while((temp = br.readLine()) != null) {
				customer_info.put(temp,br.readLine());
			}
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return customer_info;
		
	}
	
	void updateCustomerData(DLListMap_C customer_info) {
		DoubleLinkedList list = customer_info.getList();
		File f = new File("customer_info.txt");
		
		try {
			FileWriter fw = new FileWriter(f.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			if(customer_info.size() == 0) {
				bw.write("");
			}else {
				Position p = list.first();
				while(p!=null) {
					Customer c = (Customer) p.element();
					bw.write(c.getUsername()+"\n");
					bw.write(c.getPin()+"\n");
					p = list.after(p);
				}
			}
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		Object src = a.getSource();
		if(src == allBikes_Button) {
			File f = new File("bike_info.txt");
			try {
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				int i = 0;              
				while((display[i][0] = br.readLine())!=null) {
					display[i][1] = br.readLine();
					br.readLine();
					br.readLine();
					br.readLine();
					display[i][2] = br.readLine();
					br.readLine();
					i++;
				}
				br.close();
				repaint();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}else if(src == availableBikes_Button) {
			File f = new File("bike_info.txt");
			try {
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String temp = null;
				int i = 0;      
				Object temp_display[][] = new Object[200][3];
				while((temp_display[i][0] = br.readLine())!=null) {
					temp_display[i][1] = br.readLine();
					temp = br.readLine();
					temp = br.readLine();
					temp = br.readLine();
					temp_display[i][2] = br.readLine();
					temp = br.readLine();
					i++;
				}
				br.close();
				int k=0;
				for(i=0;i<display.length;i++) {
					for(int j=0;j<display[i].length;j++) {
						display[i][j]=" ";
					}
				}
				i=0;
				while(i<display.length && temp_display[i][1]!=null) {
					temp = ((String)temp_display[i][1]).trim();
					if(temp.equals("Available")) {
						for(int j=0;j<3;j++) {
							display[k][j] = temp_display[i][j];
						}
						k++;
					}
					i++;
				}
				repaint();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}else if(src == myRent_Button) {
			new BikesIRent(this.getUser());
		}else if(src == rent_Button) {
			new RentBike(this.getUser());
		}else if(src == returnBike_Button) {
			DLListMap_B bike_info = loadBikeData();
			Bike rented_bikes[] = bike_info.getByUser(getUser());
			if(rented_bikes.length == 0) {
				JOptionPane.showMessageDialog(null, "You have no bike need to be returned!");
			}else {
				new ReturnBike(this.getUser());
			}
		}else if(src == closeAccount_Button) {
			DLListMap_B bike_info = loadBikeData();
			if(bike_info.getByUser(this.getUser()).length != 0) {
				JOptionPane.showMessageDialog(null, "You have to return all the bikes you rent before closing your account!");
			}else {
				int user_Option = JOptionPane.showConfirmDialog(null, "Are you sure you want to close this account?", "Confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(user_Option == JOptionPane.OK_OPTION) {
					DLListMap_C customer_info = loadCustomerData();
					customer_info.remove(getUser());
					updateCustomerData(customer_info);
					this.dispose();
					new Login();
				}else if(user_Option == JOptionPane.CANCEL_OPTION) {
					repaint();
				}
				
			}
		}else if(src == logOut_Button) {
			this.dispose();
			new Login();
		}
		
	}

	public String getUser() {
		return this.user;
	}

	private void setUser(String user) {
		this.user = user;
	}
}
