/**
 * Jacob Guerrero
 *
 * Database accessor
 * This code will access a database that simulates an online store
 * database is on the same machine, accessed via MySQL
 * Many techniques inspired by tutorial from zetcode
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;


public class gameStore {

	public static void main(String[] args) {
		
		/* Connection object is how we connect to the database
		 * Statement object sends the SQL statements
		 * ResultSet object is the table that stores data we receive 
		 */
		Connection c = null;
		PreparedStatement ps  = null;
		ResultSet rs = null;
		FileInputStream fin = null;
		FileOutputStream fout = null;
		
		String url = "jdbc:mysql://localhost:3306/store?allowMultiQueries=true";
		String user = "admin";
		String password = "pass";
		
		
		try {
			//create a connection
			c = DriverManager.getConnection(url, user, password);
			
			
			//Uncomment/edit any of the following sections 
			//	to execute desired query
			
			//add new entry (uses placeholders)
			/*
			ps = c.prepareStatement("INSERT INTO item(Name, Cost, Genre, ESRB, Stock) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, "Tetris");
			ps.setInt(2, 2);
			ps.setString(3, "Puzzle");
			ps.setString(4,  "E");
			ps.setInt(5, 250);
			//ps.setString(6,  "unused");
			ps.executeUpdate();
			*/
			
			
			//retrieve data w/ one statement
			/*
			String query = ""; 
			query = "SELECT Name, Cost, Genre FROM Item";
			query = "SELECT Name, Cost, ESRB FROM item WHERE Genre = \"FPS\""; 
			query = "SELECT SUM(TotalCost) FROM orderhistory WHERE Month = 6";
			//query = "SELECT 
			ps = c.prepareStatement(query);
			rs = ps.executeQuery();
			
			//for sum
			rs.next();
			System.out.println("Monthly sales for June: $" + rs.getInt(1));
			
			//get info about types/properties of columns
			ResultSetMetaData meta = rs.getMetaData();
			//this provides column names
			String col1 = meta.getColumnName(1);
			String col2 = meta.getColumnName(2);
			String col3 = meta.getColumnName(3);
			
			//use Formatter objects to easily format output
			//start with column headers
			Formatter f1 = new Formatter();
			f1.format("%-27s%-21s%s", col1, col2, col3);
			System.out.println(f1);
			//next, follow up with the data
			while (rs.next()) {
				//methods such as getInt and getString 
				//	retrieve values in the designated column 
				Formatter f2 = new Formatter();
				f2.format("%-27s%-21d", rs.getString(1), rs.getInt(2));
				System.out.println(f2 + rs.getString(3));
				f2.close();
			}
			*/

			
			//retrieve data w/ multiple statements
			/*
			String query = "SELECT Id, Name FROM Item WHERE Id = 1;"
					+ "SELECT Id, Name FROM Item WHERE Id = 3";
			
			ps = c.prepareStatement(query);
			//execute returns whether or not the result is a ResultSet
			boolean isResult = ps.execute();
			
			//process results in this do-while loop
			do {
				rs = ps.getResultSet();
				while (rs.next()) {
					System.out.print(rs.getInt(1) + ": ");
					System.out.print(rs.getString(2));
					System.out.println();
				}
				//getMoreResults will call subsequent results
				isResult = ps.getMoreResults();
			} while (isResult);
			*/
			
			
			//add image to database
			/*
			File image = new File("csgo.jpg");
			fin = new FileInputStream(image);
			//make statement to insert image
			ps = c.prepareStatement("INSERT INTO image(PicID, pic, ItemID) VALUES(?, ?, ?)");
			//send binary stream to statement
			//parameters: index to bind, input stream, number of bytes
			ps.setBinaryStream(2,  fin, (int) image.length());
			ps.setInt(1, 6);
			ps.setInt(3, 6);
			//then execute statement
			ps.executeUpdate();
			*/
			
			
			//get image from database
			/*
			//retrieve only one image
			ps = c.prepareStatement("SELECT Pic FROM Image WHERE ItemID = 4 LIMIT 1");
			rs = ps.executeQuery();
			rs.next();
			//the following is used to write a file
			fout = new FileOutputStream("preview.jpg");
			//get image data from column
			Blob blob = rs.getBlob("Pic");
			//get number of bytes
			int length = (int) blob.length();
			//getBytes will get all of the bytes as an array
			byte[] temp = blob.getBytes(1,  length);
			//bytes written to output stream, image created on filesystem
			fout.write(temp, 0, length);
			*/
			
			
		} catch (SQLException ex) { //log message if we get error
				Logger l = Logger.getLogger(gameStore.class.getName());
				l.log(Level.SEVERE, ex.getMessage(), ex);
	/* //uncomment if reading an image			
		} catch (FileNotFoundException ex) {
				Logger l = Logger.getLogger(gameStore.class.getName());
				l.log(Level.SEVERE, ex.getMessage(), ex);
	*/			
	/* //uncomment if writing an image			
		} catch (IOException ex) {
			Logger lgr = Logger.getLogger(gameStore.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);   
           */
		} finally { //close resources to prevent nullpointer exceptions
			
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (c  != null) {
					c.close();
				}
				if (fin != null) {
					fin.close();
				}
				if (fout!= null) {
					fout.close();
				}
					
			} catch (SQLException ex) {
				Logger l = Logger.getLogger(gameStore.class.getName());
				l.log(Level.WARNING, ex.getMessage(), ex);
			} catch (IOException ex) {
				Logger l = Logger.getLogger(gameStore.class.getName());
				l.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
		
		System.out.println("\n\nQuery complete");

	}//end main

}//end class
