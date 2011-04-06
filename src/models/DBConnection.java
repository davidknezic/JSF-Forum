package models;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Manages the database connection
 * 
 * @author David Knezic <davidknezic@gmail.com>
 */
public class DBConnection {

	private static DBConnection instance;
	
	/**
	 * MySQL database object
	 */
	private MysqlDataSource database;
	
	/**
	 * MySQL connection
	 */
	private Connection connection;

	public static DBConnection getInstance() throws Throwable {
		if (DBConnection.instance == null) {
			DBConnection.instance = new DBConnection();
		}
		return DBConnection.instance;
	}
	
	/**
	 * Constructor
	 */
	public DBConnection() throws Throwable {
		// New data source
		this.database = new MysqlDataSource();

		// Specify connection parameters
		this.database.setDatabaseName("forum");
		this.database.setServerName("localhost");
		this.database.setPort(3306);
		this.database.setUser("root");
		this.database.setPassword("");

		try {
			// Connect and save connection
			this.connection = this.database.getConnection();
		} catch (SQLException e) {
			throw new Exception("Keine DB-Verbindung aufgebaut!");
		}
	}

	/**
	 * Retrieve the connection object
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		return this.connection;
	}

	/**
	 * Clean up routine
	 * 
	 * @return void
	 */
	public void finalize() {
		try {
			// Close connection
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
