package nl.dubehh.core.module;

import org.bukkit.plugin.java.JavaPlugin;

import nl.dubehh.command.AbstractSubCommand;
import nl.dubehh.core.data.database.table.Table;
import nl.dubehh.core.game.GameManager;
import nl.dubehh.core.module.construction.IModuleCommandHandler;

public abstract class Module extends JavaPlugin{
	
	private Table _table;
	private ModuleCommandHandler _command;
	private String _alias;
	protected ModuleConfiguration _config;
	
	/**
	 * Fired when the module is loaded as a game
	 */
	public abstract void onLoad();
	/**
	 * Fired when the module is ended
	 */
	public abstract void onUnload();
	
	public abstract void onGameStart();
	
	
	public void initialize(){
		this._alias = this.getName();
		this._command = new ModuleCommandHandler(this);
		this._config = new ModuleConfiguration(this);
		if(this instanceof IModuleCommandHandler)
			((IModuleCommandHandler) this).registerCommands();
		this._command.register();
	}
	
	public void onEnable(){
		this.onLoad();
	}
	
	public void setTable(Table table){
		this._table = table;
	}
	
	public void onDisable(){
		this.onUnload();
	}
	
	public Table getDataTable(){
		return this._table;
	}
	
	public ModuleConfiguration getModuleConfig(){
		return this._config;
	}
	
	public void addCommand(String alias, AbstractSubCommand cmd){
		this._command.register(alias, cmd);
	}
	
	public void requestEnd(){
		GameManager.getInstance().prepareEnd();
	}
	
	public void setAlias(String alias){
		this._alias = alias;
	}

	public String getAlias() {
		return this._alias;
	}
}

