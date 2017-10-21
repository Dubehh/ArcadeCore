package nl.dubehh.core.data.database;

import java.util.HashMap;
import java.util.Map;

import nl.dubehh.core.data.DataFile;

public class DatabaseConnectionProperties {
	
	public enum DatabaseProperty {
		USERNAME("root"),
		PASSWORD("usbw"),
		IP("localhost"),
		PORT("3307");
		
		protected String _default;
		private DatabaseProperty(String defaultValue){
			this._default = defaultValue;
		}
	}
	
	private Map<DatabaseProperty, String> _properties; 
	private DataFile _file;
	
	public DatabaseConnectionProperties(String fileName){
		this._properties = new HashMap<>();
		this._file = new DataFile(fileName);
		this.initialize(!this._file.exists());
	}
	
	private void initialize(boolean create){
		for(DatabaseProperty property : DatabaseProperty.values()){
			String key = property.toString().toLowerCase();
			if(create)
				this._file.set(key, property._default);
			this._properties.put(property, this._file.getConfig().getString(key));
		}
	}
	
	public String getProperty(DatabaseProperty property){
		return this._properties.get(property);
	}
}
