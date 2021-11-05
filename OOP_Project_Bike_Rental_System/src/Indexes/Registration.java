package Indexes;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DataStructure.DLListMap_A;
import DataStructure.DLListMap_C;

public class Registration extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField field_Account;
	JPasswordField field_Pin1,field_Pin2,field_Auth;
	JButton register_Button, return_Button;
	Box box1,box2,box3,box4,box5,box6,box7,box8,BigBox1,BigBox2,FinalBox;
	
	public Registration() {
		FlowLayout LayoutManner_Register = new FlowLayout();
		setLayout(LayoutManner_Register);
		setBounds(600, 200, 400, 400);
		setTitle("Registration");
		Draw();
		setVisible(true);
	}
	
	void Draw() {
		box1= Box.createHorizontalBox();
		box1.add(new JLabel("Create your account"));
		
		box2= Box.createHorizontalBox();
		box2.add(new JLabel("Username:"));
		box2.add(Box.createHorizontalStrut(15));
		field_Account = new JTextField(15);
		box2.add(field_Account);
		
		box3= Box.createHorizontalBox();
		box3.add(new JLabel("Password:"));
		box3.add(Box.createHorizontalStrut(18));
		field_Pin1 = new JPasswordField(15);
		box3.add(field_Pin1);
		
		box4= Box.createHorizontalBox();
		box4.add(new JLabel("Enter again:"));
		box4.add(Box.createHorizontalStrut(8));
		field_Pin2 = new JPasswordField(15);
		box4.add(field_Pin2);
		
		box5= Box.createHorizontalBox();
		box5.add(new JLabel("Auth code for administrator authority:"));
		
		box6= Box.createHorizontalBox();
		field_Auth = new JPasswordField(15);
		box6.add(field_Auth);
		
		BigBox1 = Box.createVerticalBox();
		BigBox1.add(box1);
		BigBox1.add(Box.createVerticalStrut(30));
		BigBox1.add(box2);
		BigBox1.add(box3);
		BigBox1.add(box4);
		BigBox1.add(Box.createVerticalStrut(30));
		BigBox1.add(box5);
		BigBox1.add(box6);
		BigBox1.add(new JLabel("(Leave blank if you are a customer)"));
		
		register_Button = new JButton("Register");
		register_Button.addActionListener(this);
		box7= Box.createHorizontalBox();
		box7.add(register_Button);
		
		return_Button = new JButton("Return to login");
		return_Button.addActionListener(this);
		box8= Box.createHorizontalBox();
		box8.add(return_Button);
		
		BigBox2 = Box.createVerticalBox();
		BigBox2.add(box7);
		BigBox2.add(Box.createVerticalStrut(5));
		BigBox2.add(box8);
		
		FinalBox = Box.createVerticalBox();
		FinalBox.add(BigBox1);
		FinalBox.add(Box.createVerticalStrut(40));
		FinalBox.add(BigBox2);
		
		add(FinalBox);
	}
	
	DLListMap_A loadAdminData(File f) throws IOException{
		DLListMap_A admin_info = new DLListMap_A();
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String account = "";
		String pin = "";
		//Below the codes read two lines at a time into the temporary user_info database, the first line would be 
		//an user name,then a pin till 
		//the end.During registration, each user name is required 
		//to have a corresponding pin to successfully register, 
		//so the data stored in the file will appear in pairs, 
		//each pair occupies two lines.
		while((account = br.readLine()) != null) {
			pin = br.readLine();
			admin_info.put(account,pin);
		}
		br.close();
		return admin_info;
	}
	
	DLListMap_C loadCustomerData(File f) throws IOException{
		DLListMap_C customer_info = new DLListMap_C();
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String account = "";
		String pin = "";
		//Below the codes read two lines at a time into the temporary user_info database, the first line would be 
		//an user name,then a pin till 
		//the end.During registration, each user name is required 
		//to have a corresponding pin to successfully register, 
		//so the data stored in the file will appear in pairs, 
		//each pair occupies two lines.
		while((account = br.readLine()) != null) {
			pin = br.readLine();
			customer_info.put(account, pin);
		}
		br.close();
		
		return customer_info;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		final String Authcode = "AUTH";
		String temp_username;
		String temp_pin;
		String pin1 = new String(field_Pin1.getPassword());
		String pin2 = new String(field_Pin2.getPassword());
		if (src == register_Button) {     //if login_Button is clicked, then action would be taken according to the selected identity 
			if (field_Account.getText().equals(null)) {
				JOptionPane.showMessageDialog(null, "Username can't be blank!");
				field_Account.setText("");
				field_Pin1.setText("");
				field_Pin2.setText("");
				field_Auth.setText("");
			}else if(pin1.equals("")) {  // tells if password is entered
				JOptionPane.showMessageDialog(null, "Password can not be blank!");
				field_Account.setText("");
				field_Pin1.setText("");
				field_Pin2.setText("");
				field_Auth.setText("");
			}else if(pin2.equals("")) {
				JOptionPane.showMessageDialog(null, "You need to enter the password twice to double check!");
				field_Account.setText("");
				field_Pin1.setText("");
				field_Pin2.setText("");
				field_Auth.setText("");
			}else if(!pin1.equals(pin2)) {
				JOptionPane.showMessageDialog(null, "The two passwords entered are not the same!");
				field_Account.setText("");
				field_Pin1.setText("");
				field_Pin2.setText("");
				field_Auth.setText("");
			}else {
				if(new String(field_Auth.getPassword()).equals(Authcode)) {    //if administrator authorizing code is entered
					try {
						File data = new File("admin_info.txt");
						temp_username = new String(field_Account.getText());
						temp_pin = new String(pin1);
						if(!data.exists()) {
							data.createNewFile();
							FileWriter fw = new FileWriter(data.getAbsoluteFile(),true);
							BufferedWriter bw = new BufferedWriter(fw);
							bw.write(temp_username+"\n");
							bw.write(temp_pin+"\n");		
							bw.close();
							JOptionPane.showMessageDialog(null, "You've successfully registered an account£¡");
							this.dispose();
							new Login();
						}else {
							DLListMap_A user_info = loadAdminData(data);
							
							if(user_info.find(temp_username) != null) {
								JOptionPane.showMessageDialog(null, "Administrator username already exists!");
								field_Account.setText("");
								field_Pin1.setText("");
								field_Pin2.setText("");
								field_Auth.setText("");
							}else {
								FileWriter fw = new FileWriter(data.getAbsoluteFile(),true);
								BufferedWriter bw = new BufferedWriter(fw);
								bw.write(temp_username+"\n");
								bw.write(temp_pin+"\n");
								bw.close();
								JOptionPane.showMessageDialog(null, "You've successfully registered an account£¡");
								this.dispose();
								new Login();
							}
						}
					}catch(IOException e1) {
						e1.printStackTrace();
					}
				}else if(!new String(field_Auth.getPassword()).equals(Authcode) && !new String(field_Auth.getPassword()).equals("")) {
					JOptionPane.showMessageDialog(null, "Invalid auth code for administrative authority!");
					field_Account.setText("");
					field_Pin1.setText("");
					field_Pin2.setText("");
					field_Auth.setText("");
				}else if(new String(field_Auth.getPassword()).equals("")) {
					try {
						File data = new File("customer_info.txt");
						temp_username = new String(field_Account.getText());
						temp_pin = new String(pin1);
						if(!data.exists()) {
							data.createNewFile();
							FileWriter fw = new FileWriter(data.getAbsoluteFile(),true);
							BufferedWriter bw = new BufferedWriter(fw);
							bw.write(temp_username+"\n");
							bw.write(temp_pin+"\n");
							bw.close();
							JOptionPane.showMessageDialog(null, "You've successfully registered an account£¡");
							this.dispose();
							new Login();
						}else {
							DLListMap_C user_info = loadCustomerData(data);
							
							if(user_info.find(temp_username) != null) {
								JOptionPane.showMessageDialog(null, "Customer username already exists!");
								field_Account.setText("");
								field_Pin1.setText("");
								field_Pin2.setText("");
							}else {
								FileWriter fw = new FileWriter(data.getAbsoluteFile(),true);
								BufferedWriter bw = new BufferedWriter(fw);
								bw.write(temp_username+"\n");
								bw.write(temp_pin+"\n");
								bw.close();
								JOptionPane.showMessageDialog(null, "You've successfully registered an account£¡");
								this.dispose();
								new Login();
							}
						}
					}catch(IOException e2) {
						e2.printStackTrace();
					}
				}
			}
		}else if(src == return_Button) {
			this.dispose();
			new Login();
		}
		
	}

}

