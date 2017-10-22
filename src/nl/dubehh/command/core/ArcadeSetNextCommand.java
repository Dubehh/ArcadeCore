package nl.dubehh.command.core;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import nl.dubehh.command.AbstractSubCommand;
import nl.dubehh.core.game.GameManager;
import nl.dubehh.core.module.Module;
import nl.dubehh.util.UChat;

public class ArcadeSetNextCommand extends AbstractSubCommand{

	public ArcadeSetNextCommand() {
		super(1, "arcade.admin.setnext", "/arcade setnext <module>", "Change the current queue position");
	}

	@Override
	public void onCommand(Player p, String[] args) {
		GameManager game = GameManager.getInstance();
		Module module = game.getModuleController().fetch(args[0].toLowerCase());
		if(module != null){
			UChat.NOTIFY.send(p, "Updated the queue, position set at module: "+ChatColor.WHITE+module.getAlias()+ChatColor.GRAY+"!");
			UChat.CLEAN.send(p, " Be aware that the module may be skipped if it has too little or too many players.");
			game.getQueue().setNext(module);
		}else{
			UChat.ERROR.send(p, "A module called '"+ChatColor.WHITE+args[0]+ChatColor.GRAY+" couldn't be found.");
			UChat.CLEAN.send(p, " Use the command "+ChatColor.YELLOW+"/arcade modules"+ChatColor.GRAY+" to view all modules.");
		}
	}

}
