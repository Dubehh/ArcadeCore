package nl.dubehh.command.core;

import nl.dubehh.command.AbstractGroupCommand;

public class ArcadeCmd extends AbstractGroupCommand{

	public ArcadeCmd(String command, String perm, String usage) {
		super("arcade", "arcade.admin", "/arcade <args>");
		addAlias("ar", "core");
	}

	@Override
	public void registerCommands() {
	}

}
