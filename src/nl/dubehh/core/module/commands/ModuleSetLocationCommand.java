package nl.dubehh.core.module.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import nl.dubehh.command.AbstractSubCommand;
import nl.dubehh.core.module.Module;
import nl.dubehh.core.module.ModuleSetting;
import nl.dubehh.util.UChat;
import nl.dubehh.util.UConfig;

public class ModuleSetLocationCommand extends AbstractSubCommand{

	private Module _module;
	public ModuleSetLocationCommand(Module module) {
		super(1, "arcade.admin", "/<module> setlocation", "Sets the default location");
		this._module = module;
	}

	@Override
	public void onCommand(Player p, String[] args) {
		UChat.NOTIFY.send(p, "Set the location for module "+ChatColor.WHITE+this._module.getAlias()+ChatColor.GRAY+" at your current location");
		_module.getModuleConfig().setSetting(ModuleSetting.TELEPORT_LOCATION, UConfig.serialize(p.getLocation()));
	}

}
