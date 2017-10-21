package nl.dubehh.command.core;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import nl.dubehh.command.AbstractSubCommand;
import nl.dubehh.core.game.GameManager;
import nl.dubehh.core.module.ModuleSetting;
import nl.dubehh.util.UChat;

public class ArcadeModulesCommand extends AbstractSubCommand{

	public ArcadeModulesCommand() {
		super(1, "arcade.admin.list", "/arcade modules", "Lists all modules");
	}

	@Override
	public void onCommand(Player p, String[] args) {
		UChat.INFO_UNIMPORTANT.send(p, "Showing all modules:");
		GameManager.getInstance().getModuleController().list().forEach((module) -> {
			boolean enabled = (boolean) module.getModuleConfig().getSetting(ModuleSetting.ENABLED);
			UChat.CLEAN.send(p, " - "+ChatColor.WHITE+module.getAlias()+ChatColor.GRAY+" > "+(enabled ? ChatColor.GREEN +"Enabled" : ChatColor.RED+"Disabled"));
		});
	}
	
	
}
