package Indexes;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Customer.Customer_Interface;
import DataStructure.DLListMap_A;
import DataStructure.DLListMap_C;
import Shop.Administrator_Interface;

public class Login extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JRadioButton customer_Button,admin_Button;
	JButton login_Button,register_Button;
	Box FinalBox,Bigbox1,Bigbox2,Bigbox3,box1,box2,box3,box4,box5,box6;
	JTextField field_Account;
	JPasswordField field_Pin;
	
	public Login() {
		FlowLayout LayoutManner_Login = new FlowLayout();
		setLayout(LayoutManner_Login);
		setBounds(600, 200, 400, 400);
		setTitle("Login");
		Draw();
		setVisible(true);
	}
	
	void Draw() {
		box1=Box.createHorizontalBox();
		box1.add(new JLabel("Username:"));
		field_Account = new JTextField(10);
		box1.add(field_Account);
		
		box2=Box.createHorizontalBox();
		box2.add(new JLabel("Password:"));
		box2.add(Box.createHorizontalStrut(4));
		field_Pin = new JPasswordField(10);
		box2.add(field_Pin);
		
		box3 = Box.createHorizontalBox();
		box3.add(new JLabel("Your identity:"));
		
		box4 = Box.createVerticalBox();
		ButtonGroup bg = new ButtonGroup();
		customer_Button = new JRadioButton("Customer");
		admin_Button = new JRadioButton("Administrator");
		bg.add(customer_Button);
		bg.add(admin_Button);
		customer_Button.addActionListener(this);
		admin_Button.addActionListener(this);
		box4.add(Box.createVerticalStrut(28));
		box4.add(customer_Button);
		box4.add(admin_Button);
		
		box5 = Box.createHorizontalBox();
		login_Button = new JButton("Login");
		login_Button.addActionListener(this);
		box5.add(login_Button);
		
		box6 = Box.createHorizontalBox();
		register_Button = new JButton("Register a new account");
		register_Button.addActionListener(this);
		box6.add(register_Button);
		
		Bigbox1=Box.createVerticalBox();
		Bigbox2 = Box.createHorizontalBox();
		Bigbox3 = Box.createVerticalBox();
		
		Bigbox1.add(box1);
		Bigbox1.add(Box.createVerticalStrut(5));
		Bigbox1.add(box2);
		Bigbox2.add(box3);
		Bigbox2.add(box4);
		Bigbox3.add(box5);
		Bigbox3.add(Box.createVerticalStrut(5));
		Bigbox3.add(box6);
		
		
		FinalBox = Box.createVerticalBox();
		FinalBox.add(Box.createVerticalStrut(30));
		FinalBox.add(Bigbox1);
		FinalBox.add(Bigbox2);
		FinalBox.add(Box.createVerticalStrut(75));
		FinalBox.add(Bigbox3);
		
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
		String temp_username= null;
		String temp_pin = null;
		if (src == login_Button) {     //if login_Button is clicked, then action would be taken according to the selected identity 
			if(admin_Button.isSelected()==false&&customer_Button.isSelected()==false) {
				JOptionPane.showMessageDialog(null, "Please select your identity");
			}else if (field_Account.getText().equals(null)) {
				JOptionPane.showMessageDialog(null, "Username can't be blank!");
			}else if(new String(field_Pin.getPassword()).equals("")) {  // tells if password is entered
				JOptionPane.showMessageDialog(null, "Password can not be blank!");
			}else {
				if(admin_Button.isSelected()) {       //if administrator is selected
					try {
						File data = new File("admin_info.txt");
						if(!data.exists()) {
							data.createNewFile();
							JOptionPane.showMessageDialog(null, "Account does not exist");
							field_Account.setText("");
							field_Pin.setText("");
						}else {
							DLListMap_A user_info = loadAdminData(data);
							
							temp_username = field_Account.getText();
							temp_pin = new String(field_Pin.getPassword());
							if(user_info.find(temp_username)!=null && user_info.get(temp_username).getPin().equals(temp_pin)) {
								this.dispose();
								new Administrator_Interface(temp_username);
							}else if(user_info.find(temp_username)==null) {
								JOptionPane.showMessageDialog(null, "Account does not exist!");
								field_Account.setText("");
								field_Pin.setText("");
							}else if(user_info.find(temp_username)!=null && !user_info.get(temp_username).getPin().equals(temp_pin)) {
								JOptionPane.showMessageDialog(null, "Access denied, wrong password!");
								field_Pin.setText("");
							}
						}
					}catch (IOException e1) {
						e1.printStackTrace();
					}	
				}else {                   //if customer_Button is selected
					try {
						File data = new File("customer_info.txt");
						if(!data.exists()) {
							data.createNewFile();
							JOptionPane.showMessageDialog(null, "Account does not exist");
							field_Account.setText("");
							field_Pin.setText("");
						}else {
							DLListMap_C user_info = loadCustomerData(data);
							
							temp_username = field_Account.getText();
							temp_pin = new String(field_Pin.getPassword());
							if(user_info.find(temp_username)!=null && user_info.get(temp_username).getPin().equals(temp_pin)) {
								this.dispose();
								new Customer_Interface(temp_username);
							}else if(user_info.find(temp_username)==null) {
								JOptionPane.showMessageDialog(null, "Account does not exist!");
								field_Account.setText("");
								field_Pin.setText("");
							}else if(user_info.find(temp_username)!=null && !user_info.get(temp_username).getPin().equals(temp_pin)) {
								JOptionPane.showMessageDialog(null, "Access denied, wrong password!");
								field_Pin.setText("");
							}
						}							
					}catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}else if(src == register_Button){         //if registration button is clicked
			this.dispose();
			new Registration();
		}
	}
}

