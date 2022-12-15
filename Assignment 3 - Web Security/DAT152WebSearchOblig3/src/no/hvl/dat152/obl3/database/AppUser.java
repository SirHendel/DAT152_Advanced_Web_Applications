package no.hvl.dat152.obl3.database;

public class AppUser {

	private String username; 
	private String passhash; 
	private String firstname;
	private String lastname;
	private String mobilephone;
	private String role;
	private String clientID;
	private byte[] salt;
	
	public AppUser(String username, String passhash, byte[] salt, String firstname, 
			String lastname, String mobilephone, String role, String clientID) {
		this.username = username;
		this.passhash = passhash;
		this.salt = salt;
		this.firstname = firstname;
		this.lastname = lastname;
		this.mobilephone = mobilephone;
		this.role = role;
		this.clientID = clientID;
	}

	public String getUsername() {
		return username;
	}

	public String getPasshash() {
		return passhash;
	}
	
	public byte[] getSalt() {
		return this.salt;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getMobilephone() {
		return mobilephone;
	}
	
	public String getRole() {
		return role;
	}

	/**
	 * @return the clientID
	 */
	public String getClientID() {
		return clientID;
	}
	
}

