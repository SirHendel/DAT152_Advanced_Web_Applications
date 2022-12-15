package no.hvl.dat152.obl3.database;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.sql.Connection;

import no.hvl.dat152.obl3.util.Crypto;


public class AppUserDAO {

  public AppUser getAuthenticatedUser(String username, String password) {
    String sql = "SELECT * FROM SecOblig.AppUser" 
            + " WHERE username = ?";
   
    AppUser user = null;

    Connection c = null;
    Statement s = null;
    ResultSet r = null;

    try {        
      c = DatabaseHelper.getConnection();
      s = c.createStatement();
      PreparedStatement pstmt = c.prepareStatement( sql );
      pstmt.setString(1, username);
      r = pstmt.executeQuery();

      if (r.next()) {
    	  byte[] salt = Base64.getDecoder().decode(r.getString("passsalt"));
    	  String hashedPassword = Crypto.generateHashWithSalt(password, salt);
    	  String dbPassword = r.getString("passhash");
    	  if (dbPassword.equals(hashedPassword)) {
	        user = new AppUser(
	            r.getString("username"),
	            dbPassword,
	            salt,
	            r.getString("firstname"),
	            r.getString("lastname"),
	            r.getString("mobilephone"),
	            r.getString("role"),
	            r.getString("clientId")
	            );
    	  }
      }

    } catch (Exception e) {
      System.out.println(e);
    } finally {
      DatabaseHelper.closeConnection(r, s, c);
    }

    return user;
  }
  
  public String getUserClientID(String mobilephone) {

    String sql = "SELECT clientId FROM SecOblig.AppUser" 
        + " WHERE mobilephone = '" + mobilephone + "'";
    
    
    String clientID = null;

    Connection c = null;
    Statement s = null;
    ResultSet r = null;

    try {        
      c = DatabaseHelper.getConnection();
      s = c.createStatement();       
      r = s.executeQuery(sql);

      if (r.next()) {
        clientID = r.getString("clientId");
      }

    } catch (Exception e) {
      System.out.println(e);
    } finally {
      DatabaseHelper.closeConnection(r, s, c);
    }
    
    return clientID;
  }
  
  public boolean clientIDExist(String clientid) {

	    String sql = "SELECT clientId FROM SecOblig.AppUser" 
	        + " WHERE clientId = '" + clientid + "'";
	    
	    
	    String clientID = null;

	    Connection c = null;
	    Statement s = null;
	    ResultSet r = null;

	    try {        
	      c = DatabaseHelper.getConnection();
	      s = c.createStatement();       
	      r = s.executeQuery(sql);

	      if (r.next()) {
	        clientID = r.getString("clientId");
	      }

	    } catch (Exception e) {
	      System.out.println(e);
	    } finally {
	      DatabaseHelper.closeConnection(r, s, c);
	    }
	    
	    return clientID != null;
	  }

  public boolean saveUser(AppUser user) {
	  String passsalt = new String(Base64.getEncoder().encode(user.getSalt()));
    String sql = "INSERT INTO SecOblig.AppUser VALUES (" 
        + "'" + user.getUsername()  + "', "
        + "'" + user.getPasshash()  + "', "
		+ "'" + passsalt  + "', "
        + "'" + user.getFirstname() + "', "
        + "'" + user.getLastname()  + "', "
        + "'" + user.getMobilephone()  + "', "
        + "'" + user.getRole()  + "', "
        + "'" + user.getClientID() + "')";

    Connection c = null;
    Statement s = null;
    ResultSet r = null;

    try {        
      c = DatabaseHelper.getConnection();
      s = c.createStatement();       
      int row = s.executeUpdate(sql);
      if(row >= 0)
    	  return true;
    } catch (Exception e) {
    	System.out.println(e);
    	return false;
    } finally {
      DatabaseHelper.closeConnection(r, s, c);
    }
    
    return false;
  }
  
  public List<String> getUsernames() {
	  
	  List<String> usernames = new ArrayList<String>();
	  
	  String sql = "SELECT username FROM SecOblig.AppUser";

		    Connection c = null;
		    Statement s = null;
		    ResultSet r = null;

		    try {        
		      c = DatabaseHelper.getConnection();
		      s = c.createStatement();       
		      r = s.executeQuery(sql);

		      while (r.next()) {
		    	  usernames.add(r.getString("username"));
		      }

		    } catch (Exception e) {
		      System.out.println(e);
		    } finally {
		      DatabaseHelper.closeConnection(r, s, c);
		    }
	  
	  return usernames;
  }
  
  public boolean updateUserPassword(String username, String passwordnew) {
	  	byte[] salt = Crypto.getSalt();
	  	String hashedPassword;
	  	String passsalt = new String(Base64.getEncoder().encode(salt));
		try {
			hashedPassword = Crypto.generateHashWithSalt(passwordnew, salt);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			return false;
		} catch (InvalidKeySpecException e1) {
			e1.printStackTrace();
			return false;
		}
	  
	    String sql = "UPDATE SecOblig.AppUser "
	    		+ "SET passhash = '" + hashedPassword + "', passsalt = '" + passsalt + "'"
	    				+ "WHERE username = '" + username + "'";
	
	    Connection c = null;
	    Statement s = null;
	    ResultSet r = null;
	
	    try {        
	      c = DatabaseHelper.getConnection();
	      s = c.createStatement();       
	      int row = s.executeUpdate(sql);
	      System.out.println("Password update successful for "+username);
	      if(row >= 0)
	    	  return true;
	      
	    } catch (Exception e) {
	      System.out.println(e);
	      return false;
	    } finally {
	      DatabaseHelper.closeConnection(r, s, c);
	    }
	    
	    return false;
  }
  
  public boolean updateUserRole(String username, String role) {

	    String sql = "UPDATE SecOblig.AppUser "
	    		+ "SET role = '" + role + "' "
	    				+ "WHERE username = '" + username + "'";
	
	    Connection c = null;
	    Statement s = null;
	    ResultSet r = null;
	
	    try {        
	      c = DatabaseHelper.getConnection();
	      s = c.createStatement();       
	      int row = s.executeUpdate(sql);
	      System.out.println("Role update successful for "+username+" New role = "+role);
	      if(row >= 0)
	    	  return true;
	      
	    } catch (Exception e) {
	      System.out.println(e);
	      return false;
	    } finally {
	      DatabaseHelper.closeConnection(r, s, c);
	    }
	    return false;
  }

}

