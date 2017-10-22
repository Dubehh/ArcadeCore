package nl.dubehh.command.core;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import nl.dubehh.command.AbstractSubCommand;
import nl.dubehh.core.game.GameManager;
import nl.dubehh.util.UChat;

public class ArcadeDebugCommand extends AbstractSubCommand{

	public ArcadeDebugCommand() {
		super(1, "arcade.admin.debug", "/arcade debug", "Toggle debug mode "+ChatColor.RED+"(Unsafe)");
	}

	@Override
	public void onCommand(Player p, String[] args) {
		GameManager game = GameManager.getInstance();
		boolean mode = !game.isDebugging();
		UChat.NOTIFY.send(p, "Debugging mode has been set to: "+ChatColor.WHITE+mode);
		game.setDebugging(mode);
	}

}
