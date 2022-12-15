package no.hvl.dat152.obl3.util;

import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import no.hvl.dat152.obl3.database.DatabaseHelper;


@WebListener
public class MyContextListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent arg0)  { 
    System.out.println("Application is started. The database will be created if it does not exist.");
    setupDB();
    TokenSingleton.Instance();
  }

  @Override
  public void contextDestroyed(ServletContextEvent arg0)  { 
    System.out.println("Application was stopped.");    
  }
  
  private void setupDB() {
    try (
        Connection conn = DatabaseHelper.getConnection();
        Statement st = conn.createStatement();
        ){
      try {
        st.executeUpdate("create schema SecOblig");
        System.out.println("Schema SecOblig was created.");
      } catch (Exception e) {
        System.out.println(e.toString());
      }

      try {
       st.executeUpdate("create table SecOblig.AppUser ("
           + "username VARCHAR(50) UNIQUE,"
           + "passhash VARCHAR(50),"
           + "passsalt VARCHAR(50),"
           + "firstname VARCHAR(50),"
           + "lastname VARCHAR(50),"
           + "mobilephone VARCHAR(20),"
           + "role VARCHAR(10),"
           + "clientId VARCHAR(50) UNIQUE,"
           + "PRIMARY KEY (username, passhash))");
        System.out.println("Table SecOblig.AppUser was created.");
      } catch (Exception e) {
        System.out.println(e.toString());
      }
      
      try {
        st.executeUpdate("CREATE TABLE SecOblig.History (datetime TIMESTAMP,"
            + "username VARCHAR(50),"
            + "searchkey VARCHAR(50),"
            + "PRIMARY KEY (datetime, username),"
            + "FOREIGN KEY (username) REFERENCES SecOblig.AppUser(username))");
     

        System.out.println("Table SecOblig.History was created.");
      } catch (Exception e) {
        System.out.println(e.toString());
      }
      
      // insert default admin user (username=admin (password="Admin22*")))
      try {
    	  String clientID = Crypto.generateRandomCryptoCode();
          st.executeUpdate("INSERT INTO SecOblig.AppUser VALUES "
          		+ "('admin', 'x2fP1HZykauf2URzbcuFyw==', 'nmqhGrn78q0/k60uc9PtGA==', 'Admin', 'SuperUser', '123456789',"
        		  +"'"+Role.ADMIN.toString()+"','"+clientID+"')");

          System.out.println("Default admin user \"admin\" created...");
          System.out.println("Default admin user clientID is: " + clientID);

        } catch (Exception e) {
          System.out.println(e.toString());
        }
      
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }
}
