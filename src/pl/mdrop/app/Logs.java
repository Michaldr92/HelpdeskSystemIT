package pl.mdrop.app;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logs {
	
	public static void createLogsDb(String errDB){
		
		Logger logger = Logger.getLogger("MyLog");  
		File createFolder = new File("logs");
	    FileHandler fh;  
	
	    try {  	    	
	    	createFolder.mkdir();
	        fh = new FileHandler("logs/DB_err.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
	
	        logger.info(errDB);  
	
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}	
}

/*	
private static void createLogs(String errDB){
	File createFolder = new File("logs");
	File logsFile = new File("logs/DB_error.txt");
	
	FileWriter createLogs;
	try {
		createFolder.mkdir();
		logsFile.createNewFile();	
		
		createLogs = new FileWriter(logsFile);
		createLogs.write(errDB);
	} catch (IOException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Cannot create log file!");
	}
}
*/
