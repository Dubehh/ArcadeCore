package nl.dubehh.core.module;

import org.bukkit.plugin.java.JavaPlugin;

import nl.dubehh.command.AbstractSubCommand;
import nl.dubehh.core.data.database.table.Table;
import nl.dubehh.core.game.GameManager;
import nl.dubehh.core.module.construction.IModuleCommandHandler;
import nl.dubehh.core.user.User;

public abstract class Module extends JavaPlugin{
	
	private Table _table;
	private ModuleCommandHandler _command;
	private String _alias;
	protected ModuleConfiguration _config;
	
	/**
	 * Fired when the module is loaded as a game
	 */
	public abstract void onStart();
	/**
	 * Fired when the module is ended
	 */
	public abstract void onEnd();
	
	/**
	 * Fired when a new user joins the module
	 * @param user User
	 */
	public abstract void onUserJoin(User user);
	
	public void initialize(){
		this._alias = this.getName();
		this._command = new ModuleCommandHandler(this);
		if(this instanceof IModuleCommandHandler)
			((IModuleCommandHandler) this).registerCommands();
		this._command.register();
	}
	
	public void onEnable(){
		this._config = new ModuleConfiguration(this);
		this.onStart();
	}
	
	public void setTable(Table table){
		this._table = table;
	}
	
	public void onDisable(){
		this.onEnd();
	}
	
	public Table getDataTable(){
		return this._table;
	}
	
	public ModuleConfiguration getModuleConfig(){
		return this._config;
	}
	
	protected void addCommand(String alias, AbstractSubCommand cmd){
		this._command.register(alias, cmd);
	}
	
	public void requestEnd(){
		GameManager.getInstance()
			.getModuleController()
			.end(this);
	}
	
	protected void setAlias(String alias){
		this._alias = alias;
	}

	public String getAlias() {
		return this._alias;
	}
}

