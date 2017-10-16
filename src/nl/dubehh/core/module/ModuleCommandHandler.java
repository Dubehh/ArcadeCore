package nl.dubehh.core.module;

import nl.dubehh.command.AbstractGroupCommand;
import nl.dubehh.core.module.commands.ModuleSetLocationCommand;
import nl.dubehh.core.module.commands.ModuleToggleCommand;

public class ModuleCommandHandler extends AbstractGroupCommand{
	
	private Module _module;
	public ModuleCommandHandler(Module module){
		super(	"mod_"+module.getAlias(), 
				"arcade.admin."+module.getName().toLowerCase(), 
				"/mod_"+module.getAlias()+" <args>");
		this._module = module;
	}
	
	@Override
	public void registerCommands() {
		register("setlocation", new ModuleSetLocationCommand(_module));
		register("toggle", new ModuleToggleCommand(_module));
	}
}
