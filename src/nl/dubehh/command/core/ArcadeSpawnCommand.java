package nl.dubehh.command.core;

import org.bukkit.entity.Player;

import nl.dubehh.command.AbstractSubCommand;
import nl.dubehh.core.data.DataFile;
import nl.dubehh.util.UChat;
import nl.dubehh.util.UConfig;

public class ArcadeSpawnCommand extends AbstractSubCommand {

	public ArcadeSpawnCommand() {
		super(1, "arcade.admin.spawn", "/arcade setSpawn", "Sets the server spawn");
	}

	@Override
	public void onCommand(Player p, String[] args) {
		UChat.NOTIFY.send(p, "Main spawn set at your current location!");
		new DataFile("settings").set("spawn", UConfig.serialize(p.getLocation()));
	}

}
