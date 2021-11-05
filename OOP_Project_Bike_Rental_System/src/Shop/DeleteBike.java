package Shop;

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

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import DataStructure.DLListMap_B;
import DataStructure.DoubleLinkedList;
import DataStructure.Position;

public class DeleteBike extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField field_start,field_end;
	JLabel label1,label2,label3;
	JButton delete_Button,cancel_Button;
	Box box1,box2,box3,box4,box5,box6,box7,box8,BigBox1,BigBox2,BigBox3,FinalBox;
	
	
	
	public DeleteBike() {
		setBounds(500, 300, 410, 130);
		Draw();
		setVisible(true);
	}
	
	void Draw() {
		label1 = new JLabel("Please enter the serial number range of the bikes you want to delete:");
		label2 = new JLabel("from");
		label3 = new JLabel("to");
		field_start = new JTextField(10);
		field_end = new JTextField(10);
		delete_Button = new JButton("Submit and delete");
		delete_Button.addActionListener(this);
		cancel_Button = new JButton("Cancel");
		cancel_Button.addActionListener(this);
		
		
		box1 = Box.createHorizontalBox();
		box1.add(label1);
		box2 = Box.createHorizontalBox();
		box2.add(Box.createHorizontalStrut(150));
		box2.add(label2);
		box2.add(field_start);
		box2.add(label3);
		box2.add(field_end);
		box2.add(Box.createHorizontalStrut(150));
		box3 = Box.createHorizontalBox();
		box3.add(delete_Button);
		box3.add(Box.createHorizontalStrut(20));
		box3.add(cancel_Button);
		
		BigBox1 = Box.createVerticalBox();
		BigBox1.add(box1);
		BigBox1.add(box2);
		BigBox1.add(Box.createVerticalStrut(10));
		BigBox1.add(box3);
		
		this.setLayout(new FlowLayout());
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == delete_Button) {
			String start = field_start.getText();
			String end = field_end.getText();
			DLListMap_B	bike_info = loadBikeData();
			if(start.matches("\\d+") && end.matches("\\d+")) {
				int st = Integer.parseInt(start);
				int en = Integer.parseInt(end);
				if(st<bike_info.getMinSerial()||st>bike_info.getMaxSerial()||en>bike_info.getMaxSerial()||en<bike_info.getMinSerial()||st>en) {
					JOptionPane.showMessageDialog(null, "Invalid numbers! Number in left blank must be smaller than the number in the right one, also the numbers need to be within range!");
				}else {
					int i;
					for(i=st;i<en+1;i++) {
						if(bike_info.findBySerial(i)!=null) {
							if(!bike_info.getBySerial(i).getCurrentUser().equals("N/M")) {
								int user_Option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete thses bikes? Some of them haven't been returned!", "Confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
								if(user_Option == JOptionPane.OK_OPTION) {
									break;
								}else if(user_Option == JOptionPane.CANCEL_OPTION) {
									repaint();
								}
							}
						}
					}
					for(i=st;i<en+1;i++) {
						if(bike_info.findBySerial(i)!=null) {
							bike_info.remove(i);
						}
					}
					updateBikeData(bike_info);
					JOptionPane.showMessageDialog(null,"Bike(s) successfully deleted");
					this.dispose();
				}
				
			}else {
				JOptionPane.showMessageDialog(null,"Please enter integer numbers!");
			}
		}else if(src == cancel_Button) {
			this.dispose();
		}

	}

}
