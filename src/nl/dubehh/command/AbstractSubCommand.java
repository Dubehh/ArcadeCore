package nl.dubehh.command;

import org.bukkit.entity.Player;

public abstract class AbstractSubCommand {
	
	private int _minArgs;
	private String _permission, _usage, _desc;
	public AbstractSubCommand(int minArgs, String permission, String usage, String desc){
		this._minArgs = minArgs;
		this._permission = permission;
		this._usage = usage;
		this._desc = desc;
	}
	
	public int getRequiredArguments(){
		return _minArgs;
	}
	
	public String getDescription(){
		return _desc;
	}
	
	public String getUsage(){
		return _usage;
	}
	
	public String getRequiredPermission(){
		return _permission;
	}
	
	public abstract void onCommand(Player p, String[] args);
}
