package Customer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class BillIssue extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel label1,label2,label3,label4;
	JTextField field_start,field_end;
	JButton confirm_Button;
	Box box1,box2,box3,box4,box5,box6,box7,box8,BigBox1,BigBox2,FinalBox;
	JPanel p1,p2,p3;
	String[] labels = {"No.","Type","Start time","Expected returning time","Within limit","Time exceeded","Rent(RMB)"};
	JTable t;
	JScrollPane s;
	int i;
	private Object display[][];
	
	public BillIssue(String username, Object display[][]) {
		setDisplay(display);
		setBounds(600, 200, 750,580);
		setTitle("Bill(current user: "+username+")");
		Draw();
		setVisible(true);
	}
	
	void Draw() {
		label1 = new JLabel("Billing Details");
		label2 = new JLabel("You've successfull returned the bikes!");
		label3 = new JLabel("Please pay the bill according to the shown details. Thanks for using our bikes!");
		confirm_Button = new JButton("Confirm");
		confirm_Button.addActionListener(this);
		
		
		box1 = Box.createHorizontalBox();
		box1.add(Box.createHorizontalStrut(280));
		box1.add(label1);
		
		box2 = Box.createVerticalBox();
		t = new JTable(this.getDisplay(), labels);
		t.setEnabled(false);
		t.getColumnModel().getColumn(6).setPreferredWidth(40);
		t.getColumnModel().getColumn(5).setPreferredWidth(80);
		t.getColumnModel().getColumn(4).setPreferredWidth(40);
		t.getColumnModel().getColumn(3).setPreferredWidth(90);
		t.getColumnModel().getColumn(2).setPreferredWidth(70);
		t.getColumnModel().getColumn(1).setPreferredWidth(80);
		t.getColumnModel().getColumn(0).setPreferredWidth(10);
		s = new JScrollPane(t);
		box2.add(s);
		
		box3 = Box.createHorizontalBox();
		box3.add(label2);
		
		box4 = Box.createHorizontalBox();
		box4.add(label3);
		
		box5 = Box.createHorizontalBox();
		box5.add(confirm_Button);
		
		box6 = Box.createVerticalBox();
		box6.add(box3);
		box6.add(Box.createVerticalStrut(5));
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == confirm_Button) {
			this.dispose();
		}

	}

	private Object[][] getDisplay() {
		return display;
	}

	private void setDisplay(Object display[][]) {
		this.display = display;
	}

}
