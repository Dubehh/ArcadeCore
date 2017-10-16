package nl.dubehh.core.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.dubehh.Main;

public class DataFile {
	
	private Main _main;
	protected File _file = null;
	private FileConfiguration _configuration = null;
	private File _folder;
	private String _fileName;
	
	/**
	 * Creates a new yml in the given folder with the given name
	 * @param folder File folder 
	 * @param fileName String filename
	 */
	public DataFile(File folder, String fileName){
		this._fileName = fileName;
		this._folder = folder;
		this._main = Main.getInstance();
	}
	
	/**
	 * Creates a new yml in the given folder name with the given name
	 * @param folderName String folder Name
	 * @param fileName String file name
	 */
	public DataFile(String folderName, String fileName){
		this(new File(Main.getInstance().getDataFolder().getAbsolutePath()+File.separator+folderName), fileName);
	}
	
	/**
	 * Creates a new yml in the plugin directory folder with the given name
	 * @param fileName String file name
	 */
	public DataFile(String fileName){
		this(Main.getInstance().getDataFolder(), fileName);
	}
	
	/**
	 * Gets the FileConfiguration object
	 * @return FileConfiguration config
	 */
	public FileConfiguration getConfig(){
		if(_configuration == null){
			reloadConfig();
		}
		return _configuration;
	}
	
	/**
	 * Saves the configuration file
	 */
	public void saveConfig(){
		if(_configuration == null || _file == null) return;
		try{
			getConfig().save(_file);
			reloadConfig();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Reloads the configuration file
	 */
	public void reloadConfig(){
		if(_file == null){
			_file = new File(_folder, _fileName+".yml");
		}
		_configuration = YamlConfiguration.loadConfiguration(_file);
		
		InputStream defConfigStream = _main.getResource(_fileName+".yml");
		if(defConfigStream != null){
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(_file);
	        _configuration.setDefaults(defConfig);
		}
	}
	
	/**
	 * Sets the given value at the given key
	 * @param path The string key
	 * @param val The data
	 */
	public void set(String path, Object val){
		getConfig().set(path, val);
		saveConfig();
	}
	
	/**
	 * Deletes the yml file
	 */
	public void delete(){
		if(_file == null) reloadConfig();
		_file.delete();
	}
	
	/**
	 * Checks if the file exists already
	 * @return True if the file exists
	 */
	public boolean exists(){
		return new File(_folder, _fileName+".yml").exists();
	}
}
