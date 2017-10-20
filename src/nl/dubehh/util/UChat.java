package nl.dubehh.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum UChat {

	ERROR(ChatColor.DARK_GRAY+"["+ChatColor.RED+ChatColor.BOLD+"!"+ChatColor.DARK_GRAY+"] "+ChatColor.GRAY.toString()),
	NOTIFY(ChatColor.DARK_GRAY+"["+ChatColor.AQUA+ChatColor.BOLD+"-"+ChatColor.DARK_GRAY+"] "+ChatColor.GRAY.toString()),
	CLEAN(ChatColor.GRAY.toString()),
	INFO_UNIMPORTANT(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+ChatColor.BOLD+"-"+ChatColor.DARK_GRAY+"] "+ChatColor.GRAY.toString());
	
	private final String _prefix;
	private UChat(String prefix){
		this._prefix = prefix;
	}
	
	@Override
	public String toString(){
		return _prefix;
	}
	
	public void send(Player p, String msg){ 
		p.sendMessage(_prefix+msg);
	}
}
