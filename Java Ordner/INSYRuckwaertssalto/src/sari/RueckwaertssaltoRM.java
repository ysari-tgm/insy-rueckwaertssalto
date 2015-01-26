package sari;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Prints all databases and all their tables to console.
 * This prototype is used to try managing the metadata from 
 * a given database-server.
 * 
 * In dieser Version werden PK FK usw. angegeben.
 * @author Yunus SARI
 * @version 26.01.2015 - v.02
 */
public class RueckwaertssaltoRM {
    
    public static void main(String[] args) throws SQLException {
        // Create Filewriter
    	YunusFileWriter output = new YunusFileWriter("rm.txt", true);
    	
    	
    	//DataSource für MySQL wird erstellt
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

        
        //Daten in ArrayList speichern
        ResultSet rsTables = metaData.getTables("premiere", null, null, null);

    	ArrayList<String> tablesal = new ArrayList<String>();
    	ArrayList<String> columnsal = new ArrayList<String>();
        
    	while (rsTables.next()) {
        	ResultSet rsColumns = metaData.getColumns("premiere", null, rsTables.getString("TABLE_NAME"), null);
        	tablesal.add(rsTables.getString("TABLE_NAME"));
        	while (rsColumns.next()) {
        		columnsal.add(rsColumns.getString("COLUMN_NAME"));
        	}
    	}
    	
        //Von ArrayList zu txt File umwandeln
        for(int i = 0; i<tablesal.size(); i++){
    		output.extendFile(tablesal.get(i),true);
        	for(int j = 0; i<columnsal.size(); j++){
        		//Fehler Size bei columnsal
        		output.extendFile(columnsal.get(j),true);
            }
        	output.extendFile("",true);
        }
        connection.close();
    }
}