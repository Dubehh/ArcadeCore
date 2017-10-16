package nl.dubehh.core.module;

import nl.dubehh.core.data.DataFile;

public class ModuleConfiguration {
	
	private final static String DATA_FOLDER = "config";
	private DataFile _file;
	
	public ModuleConfiguration(Module module){
		this._file = new DataFile(DATA_FOLDER, module.getName().toLowerCase());
		if(!this._file.exists())
			for(ModuleSetting setting : ModuleSetting.values())
				_file.set(setting.toString(), setting.getDefault());
	}
	
	/**
	 * Returns the value of the given setting
	 * @param key String (may be anything)
	 * @return Object value
	 */
	public Object getSetting(String key){
		return this._file.getConfig().get(key);
	}
	
	/**
	 * Returns the value of the given setting
	 * @param setting ModuleSetting setting
	 * @return Object value
	 */
	public Object getSetting(ModuleSetting setting){
		return getSetting(setting.toString());
	}
	
	/**
	 * Adds a setting to the configuration
	 * Nothing happens if the setting already exists
	 * @param key String
	 * @param value Object
	 */
	public void addSetting(String key, Object value){
		if(!this._file.getConfig().contains(key))
			setSetting(key, value);
	}
	
	/**
	 * Sets the setting (even if it already exists) to the desired value
	 * @param key String
	 * @param value Object
	 */
	public void setSetting(String key, Object value){
		this._file.set(key, value);
	}
	
	/**
	 * Sets the setting (even if it already exists) to the desired value
	 * @param setting ModuleSetting
	 * @param value Object
	 */
	public void setSetting(ModuleSetting setting, Object value){
		setSetting(setting.toString(), value);
	}
	
}
