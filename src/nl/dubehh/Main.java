package nl.dubehh;

import org.bukkit.plugin.java.JavaPlugin;

import nl.dubehh.core.game.GameManager;

public class Main extends JavaPlugin{

	private static Main _instance;
	
	public void onEnable(){
		_instance = this;
		GameManager.getInstance().getModuleController().initialize();
	}
	
	public static Main getInstance() {
		return _instance;
	}
	
}
