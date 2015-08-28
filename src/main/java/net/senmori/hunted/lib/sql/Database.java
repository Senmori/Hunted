package net.senmori.hunted.lib.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.LogHandler;

/**
 * This class sets up the correct database type. </br>
 * It also handles connecting to the database. </br>
 *  - Closing connections should only be done by the applicable handler. </br>
 * 
 * @author Senmori
 *
 */
public class Database
{
    private Hunted plugin;
	/** User that has access to mysql server */
	private String user;
	/** Password to mysql server */
	private String password;
	/** Name of database */
	private String databaseName;
	/** Host */
	private String host;
	/** Port */
	private String port;
	
	public Database(String user, String password, String databaseName, String host, String port, Hunted plugin)
	{
		this.user = user;
		this.password = password;
		this.databaseName = databaseName;
		this.host = host;
		this.port = port;
		this.plugin = plugin;
	}
	
	/** Used to see if the appropriate database exists */
	public void checkDatabase() {
	    Connection connection = null;
	    Statement statement = null;
	    ResultSet catalogs = null;
		try {
	            boolean dbExists = false;
	            //try mysql database
	            try {
		            Class.forName("com.mysql.jdbc.Driver");
	            } catch (ClassNotFoundException e) {
		            e.printStackTrace();
	            }
	            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/?user=" + user + "&password=" + password);
	            DatabaseMetaData dbmd = connection.getMetaData();
	            catalogs = dbmd.getCatalogs();
	            while (catalogs.next()) {
		            if (databaseName.equals(catalogs.getString(1))) {
			            dbExists = true;
			            break;
		            }
	            }
	            if (!dbExists) {
		            // create database here
		            statement = connection.createStatement();
		            LogHandler.dbWarning("Database does not exist! Creating now...");
		            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + databaseName);
	            }
	            return;	
		}
		catch(SQLException e) {
			LogHandler.dbWarning("Error state: " + e.getSQLState());
			LogHandler.dbWarning("Error code: " + e.getErrorCode());
			LogHandler.dbWarning("Error: " + e.getMessage());
			e.printStackTrace();
		}
		finally {
		        try {
		            if(catalogs != null) {
		                catalogs.close();
		            }
		            if(statement != null) {
                        statement.close();
                    }
		            if(connection != null) {
		                connection.close();
		            }
		        }
		        catch(SQLException e) {
		            LogHandler.dbWarning("SQL State: " + e.getSQLState());
		            LogHandler.dbWarning("Error code: " + e.getErrorCode());
		            LogHandler.dbWarning("Error message: " + e.getMessage());
		            e.printStackTrace();
		        }
		}
	}
	
	/** Gets the applicable connection */
	public Connection getConnection() {
		try {
				return plugin.getHikari().getConnection();
		} catch(SQLException e) {
			LogHandler.dbWarning("Cannot connect to database: " + databaseName);
			LogHandler.dbWarning("Please check your config to make sure everything is correct!");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get the name of the database. </br>
	 * This doesn't always match {@link Database#databaseName} so be careful </br>
	 * @return id of database
	 */
	public String getName() {
		return this.databaseName;
	}
}
