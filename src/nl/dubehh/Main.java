package nl.dubehh;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import nl.dubehh.command.core.ArcadeCommand;
import nl.dubehh.core.game.GameManager;
import nl.dubehh.event.ConnectEvent;

public class Main extends JavaPlugin{

	private static Main _instance;
	
	public void onEnable(){
		_instance = this;
		GameManager.getInstance().getModuleController().initialize();
		new ArcadeCommand().register();
		initEvents();
	}
	
	public static Main getInstance() {
		return _instance;
	}
	
	private void initEvents(){
		for(Listener l : new Listener[]{
			new ConnectEvent()
		}){ Bukkit.getPluginManager().registerEvents(l, this);}
	}
	
}
