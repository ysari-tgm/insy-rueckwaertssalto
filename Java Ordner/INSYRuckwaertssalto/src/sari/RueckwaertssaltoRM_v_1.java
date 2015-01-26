package sari;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Prints all databases and all their tables to console.
 * This prototype is used to try managing the metadata from 
 * a given database-server. 
 * 
 * In dieser Version werden alle Tabellen und deren Attribute
 * in einem File gespeichert
 * @author Yunus SARI
 * @version 26.01.2015 - v.01
 */
public class RueckwaertssaltoRM_v_1 {
    
    public static void main(String[] args) throws SQLException {
        // Create Filewriter
    	YunusFileWriter output = new YunusFileWriter("rm.txt", true);
    	
    	
    	// Create DataSource for MySQL 
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName("premiere");
        dataSource.setServerName("localhost");
        dataSource.setUser("root");
        dataSource.setPassword("1234");
        // Connects using root to get all available databases

        Connection connection = dataSource.getConnection();
        
        /*
        Stores all meta data for later use.
        For instance, you can see what tables are defined in the database, 
        and what columns each table has, whether given features are supported etc. 
         */
        DatabaseMetaData metaData = connection.getMetaData();

        /*
        Receives all information for every database.
        Warning: since the database-server we're connected to is a MySQL-Server,
        the getSchemas()-Method does not work.
          */
        
        /*
        ResultSet rsDatabases = metaData.getCatalogs();
        
        // Look through ResultSet with the database information
       
        while (rsDatabases.next()) {
 
       	// Print each database catalog name 
      	System.out.println("Datenbank = " + rsDatabases.getString("premiere") );   
           
  	  	// Get all tables for the database with the catalog name currently used
   	    ResultSet rsTables = metaData.getTables(rsDatabases.getString("TABLE_CAT"), null, null, null);
            
        */
        
        ResultSet rsTables = metaData.getTables("premiere", null, null, null);
        while (rsTables.next()) {
        	ResultSet rsColumns = metaData.getColumns("premiere", null, rsTables.getString("TABLE_NAME"), null);
        	output.extendFile("TABLE_NAME = " + rsTables.getString("TABLE_NAME"),true);
        	while (rsColumns.next()) {
        		output.extendFile("COLUMN_NAME = " + rsColumns.getString("COLUMN_NAME"),true);
            }
        	output.extendFile("",true);
        }
        
        connection.close();
    }
    
}
