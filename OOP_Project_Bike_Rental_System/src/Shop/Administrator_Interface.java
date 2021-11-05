package Shop;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;

import DataStructure.DLListMap_A;
import DataStructure.DLListMap_B;
import DataStructure.DoubleLinkedList;
import DataStructure.Position;
import Indexes.Login;

public class Administrator_Interface extends JFrame implements ActionListener {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel label1,label2,label3,label4;
	JButton allBikes_Button,addBike_Button,editBike_Button, deleteBike_Button,searchBike_Button,logOut_Button,closeAccount_Button;
	Box box1,box2,box3,box4,box5,box6,box7,box8,BigBox1,BigBox2,FinalBox;
	Object display[][];
	String[] labels = {"Serial Number","Current Status","Current User","Rental type","Start time","Expected return time","Remark"};
	JTable t;
	JScrollPane s;
	private String user;
	
	
	public Administrator_Interface(String username) {
		setBounds(300, 200, 970, 426);
		setTitle("Administrator interface(current user: "+username+")");
		Draw();
		this.setUser(username);
		setVisible(true);
	}


	void Draw() {
		label1 = new JLabel("Bike Rental Information");
		label2 = new JLabel("Administrator Options:");
		label3 = new JLabel("Account Options:");
		allBikes_Button = new JButton("See all bikes");
		allBikes_Button.addActionListener(this);
		addBike_Button = new JButton("Create new bike");
		addBike_Button.addActionListener(this);
		editBike_Button = new JButton("Edit bike info");
		editBike_Button.addActionListener(this);
		deleteBike_Button = new JButton("Delete bike");
		deleteBike_Button.addActionListener(this);
		searchBike_Button = new JButton("Search for bikes");
		searchBike_Button.addActionListener(this);
		closeAccount_Button = new JButton("Close Account");
		closeAccount_Button.addActionListener(this);
		logOut_Button = new JButton("Sign out");
		logOut_Button.addActionListener(this);
		
		box1=Box.createVerticalBox();
		box1.add(label2);
		box1.add(allBikes_Button);
		box1.add(Box.createVerticalStrut(5));
		box1.add(addBike_Button);
		box1.add(Box.createVerticalStrut(5));
		box1.add(editBike_Button);
		box1.add(Box.createVerticalStrut(5));
		box1.add(deleteBike_Button);
		box1.add(Box.createVerticalStrut(5));
		box1.add(searchBike_Button);
		
		box2=Box.createVerticalBox();
		box2.add(label3);
		box2.add(Box.createVerticalStrut(5));
		box2.add(closeAccount_Button);
		box2.add(Box.createVerticalStrut(5));
		box2.add(logOut_Button);
		
		display = new Object[200][7];
		t = new JTable(display, labels);
		t.setEnabled(false);
		s = new JScrollPane(t);
		
		BigBox1 = Box.createVerticalBox();
        BigBox1.add(box1);
        BigBox1.add(Box.createVerticalStrut(100));
        BigBox1.add(box2);
		
		this.setLayout(null);
		label1.setBounds(320,0,150,20);
		s.setBounds(0, 20, 800, 369);
		BigBox1.setBounds(805,20,200,400);
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
	
	void updateBikeData(DLListMap_B bike_info) {
		DLListMap_B sorted_bike_info = new DLListMap_B();
		for(int i = 1;i<bike_info.size()+1;i++) {
			Bike bike_i = bike_info.getBySerial(i);
			sorted_bike_info.put(i, bike_i.getStatus(), bike_i.getCurrentUser(), bike_i.getRentalType(), bike_i.getStartTime(),bike_i.getReturnTime(), bike_i.getRemark());
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

	DLListMap_A loadAdminData() {
		File f = new File("admin_info.txt");
		DLListMap_A admin_info = new DLListMap_A();
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String temp = "";
			while((temp = br.readLine()) != null) {
				admin_info.put(temp,br.readLine());
			}
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return admin_info;
		
	}
	
	void updateAdminData(DLListMap_A admin_info) {
		DoubleLinkedList list = admin_info.getList();
		File f = new File("admin_info.txt");
		try {
			FileWriter fw = new FileWriter(f.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			if(admin_info.size()==0) {
				bw.write("");
			}else {
				Position p = list.first();
				while(p!=null) {
					Administrator c = (Administrator) p.element();
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
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == allBikes_Button) {
			File f = new File("bike_info.txt");
			try {
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				int i = 0;      
				int initial_size = display.length;
				while((display[i][0] = br.readLine())!=null) {
					display[i][1] = br.readLine();
					display[i][2] = br.readLine();
					display[i][3] = br.readLine();
					display[i][4] = br.readLine();
					display[i][5] = br.readLine();
					display[i][6] = br.readLine();
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
				br.close();
				repaint();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}else if(src == addBike_Button) {
			String str = JOptionPane.showInputDialog("Please enter the number of new bikes you want to create:");
			if(str!=null) {
				if(str.matches("\\d+")) {
					int num = Integer.parseInt(str);
					DLListMap_B	bike_info = loadBikeData();
					int initial_size = bike_info.size();
					if(initial_size + num > 200) {
						JOptionPane.showMessageDialog(null,"Not enough space for new bikes!");
					}else {
						for(int i=1;i<initial_size+num+1;i++) {
							if(bike_info.findBySerial(i)==null) {
								bike_info.put(i, "Available", "N/M", "N/M", "N/M", "N/M", "N/M");
							}
						}
						updateBikeData(bike_info);
						JOptionPane.showMessageDialog(null,"New bike(s) successfully added");
					}
				}else {
					JOptionPane.showMessageDialog(null,"Please enter an integer!");
				}
			}	
			
		}else if(src == editBike_Button) {
			new EditBike();	
		}else if(src == deleteBike_Button) {
			new DeleteBike();
		}else if(src == searchBike_Button) {
			this.dispose();
			new SearchBike(this.getUser());
		}else if (src == closeAccount_Button) {
			int user_Option = JOptionPane.showConfirmDialog(null, "Are you sure you want to close this account?", "Confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(user_Option == JOptionPane.OK_OPTION) {
				DLListMap_A admin_info = loadAdminData();
				admin_info.remove(getUser());
				updateAdminData(admin_info);
				this.dispose();
				new Login();
			}
		}else if(src == logOut_Button) {
			this.dispose();
			new Login();
		}
		
	}

	private String getUser() {
		return user;
	}


	private void setUser(String user) {
		this.user = user;
	}
	
}
