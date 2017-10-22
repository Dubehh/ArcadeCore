package nl.dubehh.command.core;

import org.bukkit.entity.Player;

import nl.dubehh.command.AbstractSubCommand;
import nl.dubehh.core.game.GameManager;
import nl.dubehh.util.UChat;

public class ArcadeTriggerStartEvent extends AbstractSubCommand{

	public ArcadeTriggerStartEvent() {
		super(1, "arcade.admin.triggerstart", "/arcade triggerstart", "Trigger the start condition");
	}

	@Override
	public void onCommand(Player p, String[] args) {
		GameManager.getInstance().callPlayerJoin();
		UChat.NOTIFY.send(p, "Triggered the start condition. If nothing happens the condition isn't met yet.");
	}

}
