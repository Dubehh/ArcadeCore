package nl.dubehh.core.module.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import nl.dubehh.command.AbstractSubCommand;
import nl.dubehh.core.module.Module;
import nl.dubehh.core.module.ModuleSetting;
import nl.dubehh.util.UChat;

public class ModuleToggleCommand extends AbstractSubCommand{

	private Module _module;
	public ModuleToggleCommand(Module module) {
		super(1, "arcade.admin", "/<module> toggle", "Toggles the module");
		this._module = module;
	}

	@Override
	public void onCommand(Player p, String[] args) {
		boolean enabled = !((boolean )_module.getModuleConfig().getSetting(ModuleSetting.ENABLED));
		_module.getModuleConfig().setSetting(ModuleSetting.ENABLED, enabled);
		UChat.NOTIFY.send(p, "Module "+ChatColor.WHITE+_module.getAlias()+ChatColor.GRAY+" is now: "+ChatColor.WHITE+(enabled ? "enabled" : "disabled")+ChatColor.GRAY+"!");
	}

}
