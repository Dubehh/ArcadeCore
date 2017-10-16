package nl.dubehh;

import org.bukkit.plugin.java.JavaPlugin;

import nl.dubehh.core.module.ModuleController;

public class Main extends JavaPlugin{

	private static Main _instance;
	
	public void onEnable(){
		_instance = this;
		if(!getDataFolder().exists()) 
			getDataFolder().mkdir();
		ModuleController.getInstance().initialize();
		ModuleController.getInstance().launch("TestModule");
	}
	
	public static Main getInstance() {
		return _instance;
	}
	
}
