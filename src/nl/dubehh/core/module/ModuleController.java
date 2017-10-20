package nl.dubehh.core.module;

import java.io.File;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.UnknownDependencyException;

import nl.dubehh.Main;

public class ModuleController {
	
	private final String MODULE_FOLDER = "modules";
	
	private File _folder;
	private Module _current;
	private HashSet<Module> _modules;
	
	public ModuleController(){
		this._current = null;
		this._modules = new HashSet<>();
		this._folder = new File(Main.getInstance().getDataFolder().getAbsolutePath()+File.separator+MODULE_FOLDER);
	}
	
	public void initialize(){
		if(!this._folder.exists()){
			this._folder.mkdir();
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW+"Module folder empty, creating the folder for you.");
			return;
		}
		File[] modules = _folder.listFiles();
		PluginLoader loader = Main.getInstance().getPluginLoader();
		for(File f : modules){
			if(f.isDirectory()) continue;
			try {
				Module mod = (Module) loader.loadPlugin(f);
				mod.initialize();
				_modules.add(mod);
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"Loaded module: "+ChatColor.DARK_GREEN+mod.getName());
			} catch (UnknownDependencyException | InvalidPluginException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED+"Failed to load "+f.getName()+" as a module.");
				continue;
			}
		}
	}
	
	public Module getCurrent(){
		return this._current;
	}
	
	public HashSet<Module> list(){
		return this._modules;
	}
	
	public void end(Plugin plugin){
		if(this._modules.contains(plugin)){
			Main.getInstance().getPluginLoader().disablePlugin(plugin);
			this._current = null;
		}
	}
	
	public void launch(String name){
		Plugin module = _modules.stream()
				.filter(m-> m.getName().equalsIgnoreCase(name))
				.findFirst()
				.get();
		Main.getInstance().getPluginLoader().enablePlugin(module);
	}
	
}
