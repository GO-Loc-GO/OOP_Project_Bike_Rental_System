package Shop;

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
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Customer.Customer;
import DataStructure.DLListMap_B;
import DataStructure.DLListMap_C;
import DataStructure.DoubleLinkedList;
import DataStructure.Position;

public class EditBike extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField field_serial,field_currentUser,field_rentalType,field_startTime,field_returnTime,field_remark;
	JButton submit_Button,cancel_Button;
	JRadioButton Available_Button,Rented_Button,Maintainence_Button;
	JLabel label1,label2,label3,label4,label5,label6,label7;
	Box box1,box2,box3,box4,box5,box6,box7,box8,BigBox,BigBox2,FinalBox;
	private Bike temp_bike;
	DLListMap_B bike_info;
	
	public EditBike() {
		String str = JOptionPane.showInputDialog("Please enter the serial number of the bike you want to edit:");
		int num=0;
		if(str!=null) {
			if(str.matches("\\d+")) {
				num = Integer.parseInt(str);
				bike_info = loadBikeData();
				if(bike_info.findBySerial(num)==null) {
					JOptionPane.showMessageDialog(null,"Bike does not exits!");
					new EditBike();
				}else {
					this.setBike(bike_info.getBySerial(num));
					setBounds(300, 200, 480, 380);
					setTitle("Bike Information Edit");
					Draw();
					setVisible(true);
				}
			}else {
				JOptionPane.showMessageDialog(null,"Please enter an integer!");
			}
		}
	}
	
	void Draw() {
		field_serial = new JTextField(Integer.toString(this.getBike().getSerial()));
	    field_currentUser = new JTextField(this.getBike().getCurrentUser());
	    field_rentalType = new JTextField(this.getBike().getRentalType());
	    field_startTime = new JTextField(this.getBike().getStartTime());
	    field_returnTime = new JTextField(this.getBike().getReturnTime());
	    field_remark = new JTextField(this.getBike().getRemark());
	    label1 = new JLabel("Serial number:");
	    label2 = new JLabel("Current status:");
	    label3 = new JLabel("Current user:");
	    label4 = new JLabel("Rental type:");
	    label5 = new JLabel("Rental start time:");
	    label6 = new JLabel("Expected return time:");
	    label7 = new JLabel("Remark:");
	    submit_Button = new JButton("Submit");
	    submit_Button.addActionListener(this);
	    cancel_Button = new JButton("Cancel");
	    cancel_Button.addActionListener(this);
	    Available_Button = new JRadioButton("Available");
	    Available_Button.addActionListener(this);
	    Rented_Button = new JRadioButton("Rented");
	    Rented_Button.addActionListener(this);
	    Maintainence_Button = new JRadioButton("On Maintainence");
	    Maintainence_Button.addActionListener(this);
	    ButtonGroup grp = new ButtonGroup();
	    grp.add(Available_Button);
	    grp.add(Rented_Button);
	    grp.add(Maintainence_Button);
	    if(this.getBike().getStatus().equals("Available")) {
	    	Available_Button.setSelected(true);
	    }else if(this.getBike().getStatus().equals("Rented")) {
	    	Rented_Button.setSelected(true);
	    }else if(this.getBike().getStatus().equals("On Maintainence")) {
	    	Maintainence_Button.setSelected(true);
	    }
	    
	    box1 = Box.createHorizontalBox();
	    box1.add(Box.createHorizontalStrut(40));
	    box1.add(label1);
	    box1.add(field_serial);
	    
	    box2 = Box.createHorizontalBox();
	    box2.add(Box.createHorizontalStrut(37));
	    box2.add(label2);
	    box2.add(Available_Button);
	    box2.add(Rented_Button);
	    box2.add(Maintainence_Button);
	    
	    box3 = Box.createHorizontalBox();
	    box3.add(Box.createHorizontalStrut(46));
	    box3.add(label3);
	    box3.add(field_currentUser);
	    
	    box4 = Box.createHorizontalBox();
	    box4.add(Box.createHorizontalStrut(55));
	    box4.add(label4);
	    box4.add(field_rentalType);
	    
	    box5 = Box.createHorizontalBox();
	    box5.add(Box.createHorizontalStrut(26));
	    box5.add(label5);
	    box5.add(field_startTime);
	    
	    box6 = Box.createHorizontalBox();
	    box6.add(Box.createHorizontalStrut(0));
	    box6.add(label6);
	    box6.add(field_returnTime);
	    
	    box7 = Box.createHorizontalBox();
	    box7.add(Box.createHorizontalStrut(76));
	    box7.add(label7);
	    box7.add(field_remark);
	    
	    box8 = Box.createHorizontalBox();
	    box8.add(submit_Button);
	    box8.add(Box.createHorizontalStrut(50));
	    box8.add(cancel_Button);
	    
	    BigBox = Box.createVerticalBox();
	    BigBox.add(box1);
	    BigBox.add(box2);
	    BigBox.add(box3);
	    BigBox.add(box4);
	    BigBox.add(box5);
	    BigBox.add(box6);
	    BigBox.add(box7);
	    BigBox.add(box8);
	    
	    this.setLayout(new BorderLayout());
	    add(BigBox,BorderLayout.CENTER);
	    add(Box.createHorizontalStrut(40),BorderLayout.WEST);
	    add(Box.createHorizontalStrut(40),BorderLayout.EAST);
	    add(Box.createVerticalStrut(40),BorderLayout.NORTH);
	    add(Box.createVerticalStrut(40),BorderLayout.SOUTH);
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
	
	public static boolean isValidDate(String str) {
		   boolean convertSuccess = true;
		   if(str.equals("N/M")) {
			   return convertSuccess;
		   }
		   SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		   try {
		      formatDateTime.parse(str);
		      convertSuccess = true;
		      return convertSuccess;
		   } catch (ParseException e) {
		      convertSuccess = false;
		   }
		   return convertSuccess;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == submit_Button) {
			if(!field_serial.getText().matches("\\d+")) {
				JOptionPane.showMessageDialog(null, "Please enter an integer for serial number!");
				field_serial.setText(Integer.toString(this.getBike().getSerial()));
			}else if(!field_rentalType.getText().equals("Hourly")&&!field_rentalType.getText().equals("Daily")&&!field_rentalType.getText().equals("Weekly")&&!field_rentalType.getText().equals("Hourly Family Rental")&&!field_rentalType.getText().equals("Daily Family Rental")&&!field_rentalType.getText().equals("Weekly Family Rental")&&!field_rentalType.getText().equals("N/M")) {
				JOptionPane.showMessageDialog(null, "Invalid rental type!");
				field_currentUser.setText(this.getBike().getRentalType());
			}else if(field_remark.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter N/M if there is no remark!");
				field_remark.setText(this.getBike().getRemark());
			}else {
				DLListMap_C customer_info = loadCustomerData();
				if(customer_info.get(field_currentUser.getText())==null&&!field_currentUser.getText().equals("N/M")) {
					JOptionPane.showMessageDialog(null, "Altered user does not exist!");
					field_currentUser.setText(this.getBike().getCurrentUser());
				}else if(bike_info.getBySerial(Integer.parseInt(field_serial.getText()))!=null&&Integer.parseInt(field_serial.getText())!=this.getBike().getSerial()) {
					JOptionPane.showMessageDialog(null, "Altered serial number already exist");
					field_serial.setText(Integer.toString(this.getBike().getSerial()));
				}else if(!isValidDate(field_startTime.getText())||!isValidDate(field_returnTime.getText())) {
					JOptionPane.showMessageDialog(null, "Invalid starting or returning time!");
					field_startTime.setText(this.getBike().getStartTime());
					field_returnTime.setText(this.getBike().getReturnTime());
				}else {
					if(Available_Button.isSelected()) {
						if(!field_currentUser.getText().equals("N/M")||!field_rentalType.getText().equals("N/M")||!field_returnTime.getText().equals("N/M")||!field_startTime.getText().equals("N/M")) {
							JOptionPane.showMessageDialog(null, "Illogical information!");
						}else {
							bike_info.put(Integer.parseInt(field_serial.getText()), "Available", field_currentUser.getText(), field_rentalType.getText(), field_startTime.getText(), field_returnTime.getText(), field_remark.getText());
							updateBikeData(bike_info);
							JOptionPane.showMessageDialog(null, "Bike info successfullt changed!");
							this.dispose();
						}
					}else if(Rented_Button.isSelected()) {
						if(field_currentUser.getText().equals("N/M")||field_rentalType.getText().equals("N/M")||field_returnTime.getText().equals("N/M")||field_startTime.getText().equals("N/M")) {
							JOptionPane.showMessageDialog(null, "Illogical information!");
						}else {
							bike_info.put(Integer.parseInt(field_serial.getText()), "Rented", field_currentUser.getText(), field_rentalType.getText(), field_startTime.getText(), field_returnTime.getText(), field_remark.getText());
							updateBikeData(bike_info);
							JOptionPane.showMessageDialog(null, "Bike info successfullt changed!");
							this.dispose();
						}
					}else if(Maintainence_Button.isSelected()) {
						if(!field_currentUser.getText().equals("N/M")||!field_rentalType.getText().equals("N/M")||!field_returnTime.getText().equals("N/M")||!field_startTime.getText().equals("N/M")) {
							JOptionPane.showMessageDialog(null, "Illogical information!");
						}else {
							bike_info.put(Integer.parseInt(field_serial.getText()), "On Maintainence", field_currentUser.getText(), field_rentalType.getText(), field_startTime.getText(), field_returnTime.getText(), field_remark.getText());
							updateBikeData(bike_info);
							JOptionPane.showMessageDialog(null, "Bike info successfullt changed!");
							this.dispose();
						}
					}
				}
				
			}
		}else if(src == cancel_Button) {
			this.dispose();
		}

	}

	private Bike getBike() {
		return temp_bike;
	}

	private void setBike(Bike bike) {
		this.temp_bike = bike;
	}

}
