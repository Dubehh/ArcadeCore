package nl.dubehh.core.module;

import org.bukkit.plugin.java.JavaPlugin;

import nl.dubehh.command.AbstractSubCommand;
import nl.dubehh.core.game.GameManager;

public abstract class Module extends JavaPlugin{
	
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
	
	public void onDisable(){
		this.onEnd();
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

