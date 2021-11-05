package Shop;

public class Administrator {
	private final String username;
	private String pin;
	public Administrator(String username, String pin) {
		this.username = username;
		setPin(pin);
	}
	public String getUsername() {
		return username;
	}
	
	public String getPin() {
		return pin;
	}
	
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String key() {
		return this.getUsername();
	}
	

}
