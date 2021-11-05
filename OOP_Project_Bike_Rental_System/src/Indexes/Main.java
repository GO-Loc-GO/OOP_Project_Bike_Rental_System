package Indexes;

import javax.swing.UIManager;

public class Main {
	
	public static void main(String[] args) {
		UIManager.put("OptionPane.cancelButtonText", "Cancel");
	    UIManager.put("OptionPane.noButtonText", "No");
	    UIManager.put("OptionPane.okButtonText", "Yes");
	    UIManager.put("OptionPane.yesButtonText", "Confirm");
		new Login();
//		GUI_Registration regis = new GUI_Registration();
//		Customer_Interface cus = new Customer_Interface("Loc");
//		Administrator_Interface adm = new Administrator_Interface("admin");
//		SearchBike sear = new SearchBike("admin");
	}

}
