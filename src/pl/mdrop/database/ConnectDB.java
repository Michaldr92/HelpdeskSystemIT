package pl.mdrop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;
import pl.mdrop.app.Logs;

public class ConnectDB {
	
	Connection connectToDb = null;
	Statement stmt = null;
	
	public static Connection helpDeskDB(){
		
		try{
			Class.forName("org.sqlite.JDBC");
			Connection connectToDb = DriverManager.getConnection("jdbc:sqlite:Helpdesk.sqlite");
			/*if(connectToDb.getAutoCommit()){
				JOptionPane.showMessageDialog(null, "Connection to database has been established.");
			}*/
			return connectToDb;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Cannot connect to Database!\n" + e.getMessage());
			new Logs();
			Logs.createLogsDb(e.getMessage());
			return null;
		}		
	}

}
