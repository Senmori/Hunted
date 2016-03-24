package net.senmori.hunted.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.LogHandler;

/**
 * This class sets up the correct database type. </br> It also handles connecting to the database. </br> - Closing
 * connections should only be done by the applicable handler. </br>
 *
 * @author Senmori
 */
public class Database {
	private Hunted plugin;
	/** Name of database */
	private String databaseName;
	/** The file name of the sqlite database */
	private String fileName;
	private Connection connection;

	public Database(String fileName, String databaseName, Hunted plugin) {
		this.fileName = fileName;
		this.databaseName = databaseName;
		this.plugin = plugin;
		connection = null;
	}

	/** Used to see if the appropriate database exists */
	public void checkDatabase() {
		Statement statement = null;
		ResultSet catalogs = null;
		try {
			boolean dbExists = false;
			// try sqlite db
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			connection = DriverManager.getConnection("jdbc:sqlite:" + fileName);
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
				//create tables/columns here
				createTables(connection);
			}
			return;
		} catch (SQLException e) {
			LogHandler.dbWarning("Error state: " + e.getSQLState());
			LogHandler.dbWarning("Error code: " + e.getErrorCode());
			LogHandler.dbWarning("Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (catalogs != null) {
					catalogs.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				LogHandler.dbWarning("SQL State: " + e.getSQLState());
				LogHandler.dbWarning("Error code: " + e.getErrorCode());
				LogHandler.dbWarning("Error message: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	private void createTables(Connection con) {
		String playerKillsTable = "CREATE TABLE IF NOT EXISTS `player_kills` (" + 
								  "`killer_uuid` TEXT NOT NULL," +
								  "`killed_uuid` TEXT NOT NULL," + 
								  "`kill_method` TEXT NOT NULL," +
								  "`timestamp` NUMERIC NOT NULL," +
								  "`distance` INTEGER NOT NULL," + 
								  "`killer_type` TEXT NOT NULL," + 
								  "`killed_type` TEXT NOT NULL" + 
								  ");";
		
		String playerKitsTable = "CREATE TABLE IF NOT EXISTS `player_kits` (" +
								 "`kit_armor` INTEGER," +
								 "`kit_weapon` INTEGER," +
								 "`kit_item` INTEGER," + 
								 "`kit_potion_tier1` INTEGER," +
								 "`kit_potion_tier2` INTEGER," + 
								 "`player_uuid` TEXT" + 
								 ");";
		
		
		String playersTable = "CREATE TABLE IF NOT EXISTS `players` (" + 
							  "`uuid` TEXT NOT NULL UNIQUE," +
							  "`display_name` TEXT NOT NULL," + 
							  "`last_login` INTEGER NOT NULL," + 
							  "`time_played` INTEGER NOT NULL" + 
							  "`PK` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + 
							  ");";
		try {
	        PreparedStatement statement = con.prepareStatement(playersTable);
	        statement.execute();
	        statement = con.prepareStatement(playerKillsTable);
	        statement.execute();
	        statement = con.prepareStatement(playerKitsTable);
	        statement.execute();
        } catch (SQLException e) {
	        e.printStackTrace();
        }
	}

	/** Gets the applicable connection */
	public Connection getConnection() {
		try {
			if(connection != null) return connection;
			connection = DriverManager.getConnection("jdbc:sqlite:D:\\" + fileName);
			return connection;
		} catch (SQLException e) {
			LogHandler.dbWarning("Cannot connect to database: " + fileName);
			LogHandler.dbWarning("Please check your config to make sure everything is correct!");
			e.printStackTrace();
		}
		return null;
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the name of the database. </br> This doesn't always match {@link Database#databaseName} so be careful </br>
	 *
	 * @return id of database
	 */
	public String getName() {
		return this.fileName;
	}
}
