package nl.dubehh.core.module;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.UnknownDependencyException;

import nl.dubehh.Main;
import nl.dubehh.core.data.database.request.DatabaseTransactionRequest;
import nl.dubehh.core.data.database.table.Table;
import nl.dubehh.core.game.GameManager;
import nl.dubehh.core.module.construction.IModuleDataSource;

public class ModuleController {
	
	private final String MODULE_FOLDER = "modules";
	
	private File _folder;
	private Module _current;
	private HashSet<Module> _modules;
	private GameManager _game;
	
	public ModuleController(GameManager manager){
		this._current = null;
		this._game = manager;
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
		constructDataSources();
		_game.loadQueue();
		
	}
	
	private void constructDataSources(){
		List<String> transaction = new ArrayList<>();
		this._modules.forEach((module)->{
			if(module instanceof IModuleDataSource){
				Table table  = ((IModuleDataSource) module).intializeDatabaseTable();
				module.setTable(table);
				transaction.add(table.creation());
			}	
		});
		new DatabaseTransactionRequest(() -> {
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Database tables initialized");
		} , transaction);
	}
	
	
	public Module getCurrent(){
		return this._current;
	}
	
	public HashSet<Module> getModules(){
		return this._modules;
	}
	
	public void unload(Module module){
		if(this._modules.contains(module)){
			Main.getInstance().getPluginLoader().disablePlugin(module);
			this._current = null;
		}
	}

	public boolean load(Module module){
		if(module==null) return false;
		this._current = module;
		Main.getInstance().getPluginLoader().enablePlugin(module);
		return true;
	}
	
	public Module fetch(String name){
		return _modules.stream()
				.filter(m-> m.getName().equalsIgnoreCase(name))
				.findFirst().orElse(null);
	}
}
