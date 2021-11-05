package Shop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import DataStructure.DLListMap_B;
import DataStructure.DoubleLinkedList;
import DataStructure.Position;

public class SearchBike extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField field_serial,field_currentUser,field_rentalType;
	JLabel label1,label2,label3,label4,label5;
	JButton search_Button,return_Button;
	JRadioButton Available_Button,Rented_Button,Maintainence_Button;
	Box box1,box2,box3,box4,box5,box6,box7,box8,box9,box10,BigBox,BigBox2,FinalBox;
	Object display[][];
	String[] labels = {"Serial Number","Current Status","Current User","Rental type","Start time","Expected return time","Remark"};
	JTable t;
	JScrollPane s;
	private String user;
	
	public SearchBike(String username) {
		setBounds(300, 200, 1020, 426);
		setTitle("Bike Information Search(current user: "+username+")");
		this.setUser(username);
		Draw();
		setVisible(true);
	}
	
	void Draw() {
		label1 = new JLabel("Serial number:");
		label2 = new JLabel("Satus:");
		label3 = new JLabel("Current user:");
		label4 = new JLabel("Rental type:");
		label5 = new JLabel("Search Results");
		field_serial = new JTextField(5);
	    field_currentUser = new JTextField(5);
	    field_rentalType = new JTextField(5);
		search_Button = new JButton("Search");
		search_Button.addActionListener(this);
		return_Button = new JButton("Return");
		return_Button.addActionListener(this);
		Available_Button = new JRadioButton("Available");
	    Available_Button.addActionListener(this);
	    Rented_Button = new JRadioButton("Rented");
	    Rented_Button.addActionListener(this);
	    Maintainence_Button = new JRadioButton("On Maintainence");
	    Maintainence_Button.addActionListener(this);
	    Available_Button.setSelected(false);
	    Rented_Button.setSelected(false);
	    Maintainence_Button.setSelected(false);
	    
	    ButtonGroup grp = new ButtonGroup();
	    grp.add(Available_Button);
	    grp.add(Rented_Button);
	    grp.add(Maintainence_Button);
		
	    box1 = Box.createHorizontalBox();
//	    box1.add(Box.createHorizontalStrut(40));
	    box1.add(label1);
	    box1.add(field_serial);
	    
	    box2 = Box.createHorizontalBox();
	    box2.add(label2);
	    box2.add(Available_Button);
	    
	    box3 = Box.createHorizontalBox();
	    box3.add(Box.createHorizontalStrut(25));
	    box3.add(Rented_Button);
	    
	    box4 = Box.createHorizontalBox();
	    box4.add(Box.createHorizontalStrut(78));
	    box4.add(Maintainence_Button);
	    
	    box5 = Box.createVerticalBox();
	    box5.add(box2);
	    box5.add(box3);
	    box5.add(box4);
	    
	    
	    box6 = Box.createHorizontalBox();
//	    box6.add(Box.createHorizontalStrut(46));
	    box6.add(label3);
	    box6.add(field_currentUser);
	    
	    box7 = Box.createHorizontalBox();
//	    box7.add(Box.createHorizontalStrut(55));
	    box7.add(Box.createHorizontalStrut(9));
	    box7.add(label4);
	    box7.add(field_rentalType);
	    
	    box8 = Box.createVerticalBox();
	    box9 = Box.createHorizontalBox();
	    box10 = Box.createHorizontalBox();
	    box9.add(search_Button);
	    box8.add(box9);
	    box8.add(Box.createVerticalStrut(5));
	    box10.add(return_Button);
	    box8.add(box10);
	    
	    BigBox = Box.createVerticalBox();
	    BigBox.add(box1);
	    BigBox.add(box5);
	    BigBox.add(box6);
	    BigBox.add(box7);
	    BigBox.add(Box.createVerticalStrut(10));
	    BigBox.add(box8);
	    
	    display = new Object[200][7];
		t = new JTable(display, labels);
		t.setEnabled(false);
		s = new JScrollPane(t);
	    
	    this.setLayout(null);
		label5.setBounds(320,0,150,20);
		s.setBounds(0, 20, 800, 369);
		BigBox.setBounds(800,20,200,215);
//		box8.
		add(label5);
		add(s);
		add(BigBox);
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == search_Button) {
			String str = field_serial.getText();
			if(!str.matches("\\d+") && !str.equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter an integer or leave blank for serial number!");
				field_serial.setText("");
			}else if(!field_rentalType.getText().equals("Hourly")&&!field_rentalType.getText().equals("Daily")&&!field_rentalType.getText().equals("Weekly")&&!field_rentalType.getText().equals("Hourly Family Rental")&&!field_rentalType.getText().equals("Daily Family Rental")&&!field_rentalType.getText().equals("Weekly Family Rental")&&!field_rentalType.getText().equals("N/M")&&!field_rentalType.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Please enter a valid rental type or leave blank!");
				field_rentalType.setText("");
			}else {
				String currentUser = field_currentUser.getText();
				String rentalType = field_rentalType.getText();
				String status = "";
				int fail=0;
				if(Available_Button.isSelected()) {
					status = "Available";
				}else if(Rented_Button.isSelected()) {
					status = "Rented";
				}else if(Maintainence_Button.isSelected()) {
					status = "On Maintainence";
				}
				DLListMap_B bike_info = loadBikeData();
				DLListMap_B temp_results1 = new DLListMap_B();
				DLListMap_B temp_results2 = new DLListMap_B();
				DLListMap_B temp_results3 = new DLListMap_B();
				DLListMap_B temp_results4 = new DLListMap_B();
				DLListMap_B results_map = new DLListMap_B();
				if(!str.equals("")) {
					int serial = Integer.parseInt(str);
					Bike temp = bike_info.getBySerial(serial);
					if(temp != null) {
						temp_results1.put(temp.getSerial(),temp.getStatus(),temp.getCurrentUser(),temp.getRentalType(),temp.getStartTime(),temp.getReturnTime(),temp.getRemark());
						results_map = temp_results1;
					}else {
						JOptionPane.showMessageDialog(null, "No matching results!");
						fail = 1;
						this.dispose();
						new SearchBike(this.getUser());
					}
				}
				
				if(!currentUser.equals("")) {
					Bike temp[];
					if(results_map.size()!=0) {
						temp = results_map.getByUser(currentUser);
						if(temp.length!=0) {
							int i;
							for(i=0;i<temp.length;i++) {
								temp_results2.put(temp[i].getSerial(), temp[i].getStatus(), temp[i].getCurrentUser(), temp[i].getRentalType(), temp[i].getStartTime(), temp[i].getReturnTime(), temp[i].getRemark());
							}
							results_map = temp_results2;
						}else if(fail!=1){
							JOptionPane.showMessageDialog(null, "No matching results!");
							fail = 1;
							this.dispose();
							new SearchBike(this.getUser());
						}
					}else {
						temp = bike_info.getByUser(currentUser);
						if(temp.length!=0) {
							int i;
							for(i=0;i<temp.length;i++) {
								temp_results2.put(temp[i].getSerial(), temp[i].getStatus(), temp[i].getCurrentUser(), temp[i].getRentalType(), temp[i].getStartTime(), temp[i].getReturnTime(), temp[i].getRemark());
							}
							results_map = temp_results2;
						}else if(fail!=1){
							JOptionPane.showMessageDialog(null, "No matching results!");
							fail = 1;
							this.dispose();
							new SearchBike(this.getUser());
						}
					}
				}
				
				if(!rentalType.equals("")) {
					Bike temp[];
					if(results_map.size()!=0) {
						temp = results_map.getByRentalType(rentalType);
						if(temp.length!=0) {
							int i;
							for(i=0;i<temp.length;i++) {
								temp_results3.put(temp[i].getSerial(), temp[i].getStatus(), temp[i].getCurrentUser(), temp[i].getRentalType(), temp[i].getStartTime(), temp[i].getReturnTime(), temp[i].getRemark());
							}
							results_map = temp_results3;
						}else if(fail!=1){
							JOptionPane.showMessageDialog(null, "No matching results!");
							fail = 1;
							this.dispose();
							new SearchBike(this.getUser());
						}
					}else {
						temp = bike_info.getByRentalType(rentalType);
						if(temp.length!=0) {
							int i;
							for(i=0;i<temp.length;i++) {
								temp_results3.put(temp[i].getSerial(), temp[i].getStatus(), temp[i].getCurrentUser(), temp[i].getRentalType(), temp[i].getStartTime(), temp[i].getReturnTime(), temp[i].getRemark());
							}
							results_map = temp_results3;
						}else if(fail!=1){
							JOptionPane.showMessageDialog(null, "No matching results!");
							fail = 1;
							this.dispose();
							new SearchBike(this.getUser());
						}
					}
				}
				
				if(!status.equals("")) {
					Bike temp[];
					if(results_map.size()!=0) {
						temp = results_map.getByStatus(status);
						if(temp.length!=0) {
							int i;
							for(i=0;i<temp.length;i++) {
								temp_results4.put(temp[i].getSerial(), temp[i].getStatus(), temp[i].getCurrentUser(), temp[i].getRentalType(), temp[i].getStartTime(), temp[i].getReturnTime(), temp[i].getRemark());
							}
							results_map = temp_results4;
						}else if(fail!=1){
							JOptionPane.showMessageDialog(null, "No matching results!");
							fail = 1;
							this.dispose();
							new SearchBike(this.getUser());
						}
					}else {
						temp = bike_info.getByStatus(status);
						if(temp.length!=0) {
							int i;
							for(i=0;i<temp.length;i++) {
								temp_results4.put(temp[i].getSerial(), temp[i].getStatus(), temp[i].getCurrentUser(), temp[i].getRentalType(), temp[i].getStartTime(), temp[i].getReturnTime(), temp[i].getRemark());
							}
							results_map = temp_results4;
						}else if(fail!=1){
							JOptionPane.showMessageDialog(null, "No matching results!");
							fail = 1;
							this.dispose();
							new SearchBike(this.getUser());
						}
					}
				}
				
				if(results_map.size()==0&&fail!=1) {
					JOptionPane.showMessageDialog(null, "No matching results!");
					fail = 1;
					this.dispose();
					new SearchBike(this.getUser());
				}else {
					DoubleLinkedList list = results_map.getList();
					int i = 0;      
					int initial_size = display.length;
					Bike bike;
					Position p = list.first();
					while(p != null) {
						bike = (Bike) p.element();
						display[i][0] = bike.getSerial();
						display[i][1] = bike.getStatus();
						display[i][2] = bike.getCurrentUser();
						display[i][3] = bike.getRentalType();
						display[i][4] = bike.getStartTime();
						display[i][5] = bike.getReturnTime();
						display[i][6] = bike.getRemark();
						p = list.after(p);
						i++;
					}
					while(i<initial_size) {
						display[i][0] = "";
						display[i][1] = "";
						display[i][2] = "";
						display[i][3] = "";
						display[i][4] = "";
						display[i][5] = "";
						display[i][6] = "";
						i++;
					}
					repaint();
				}
				
			}
		}else if(src == return_Button) {
			this.dispose();
			new Administrator_Interface(this.getUser());
		}
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
