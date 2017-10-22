package nl.dubehh.command.core;

import nl.dubehh.command.AbstractGroupCommand;

public class ArcadeCommand extends AbstractGroupCommand {

	public ArcadeCommand() {
		super("arcade", "arcade.admin", "/arcade <args>");
		addAlias("ar", "core");
	}

	@Override
	public void registerCommands() {
		register("setspawn", new ArcadeSpawnCommand());
		register("modules", new ArcadeModulesCommand());
		register("setnext", new ArcadeSetNextCommand());
		register("debug", new ArcadeDebugCommand());
		register("triggerstart", new ArcadeTriggerStartEvent());
	}

}
