package nl.dubehh.core.data.database;

import com.zaxxer.hikari.HikariDataSource;

import nl.dubehh.core.data.database.DatabaseConnectionProperties.DatabaseProperty;

public class DatabaseConnectionHandler {
	
	private final String PROPERTIES_FILE = "database";
	private final HikariDataSource _source;
	private final DatabaseConnectionProperties _properties;
	
	private static DatabaseConnectionHandler _instance;
	
	private DatabaseConnectionHandler(){
		this._source = new HikariDataSource();
		this._properties = new DatabaseConnectionProperties(PROPERTIES_FILE);
		this._source.setJdbcUrl("jdbc:mysql://"
				+_properties.getProperty(DatabaseProperty.IP)+":"
				+_properties.getProperty(DatabaseProperty.PORT)+"/"
				+_properties.getProperty(DatabaseProperty.DATABASE));
		this._source.setUsername(_properties.getProperty(DatabaseProperty.USERNAME));
		this._source.setPassword(_properties.getProperty(DatabaseProperty.PASSWORD));
		this._source.setMaximumPoolSize(50);
	}
	
	public static DatabaseConnectionHandler getInstance(){
		if(_instance==null)
			_instance = new DatabaseConnectionHandler();
		return _instance;
	}
	
	public HikariDataSource getSource(){
		return this._source;
	}
	
}
