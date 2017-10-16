package nl.dubehh.command;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import nl.dubehh.util.UChat;

public abstract class AbstractSingularCommand implements CommandExecutor, TabExecutor {

	private final List<String> _alias;
	protected final String _command, _usage, _perm;

	protected static CommandMap cmap;

	public AbstractSingularCommand(String command, String perm) {
		this(command, perm, null);
	}
	
	protected void addAlias(String... alias){
		for(String s : alias)
			_alias.add(s);
	}
	
	public AbstractSingularCommand(String command, String perm, String usage) {
		this._command = command.toLowerCase();
		this._perm = perm;
		this._alias = new ArrayList<>();
		this._usage = usage;
	}

	public void register() {
		ReflectCommand cmd = new ReflectCommand(this._command);
		if (this._usage != null)
			cmd.setUsage(this._usage);
		if(!this._alias.isEmpty())
			cmd.setAliases(this._alias);
		getCommandMap().register("", cmd);
		cmd.setExecutor(this);
	}

	final CommandMap getCommandMap() {
		if (cmap == null) {
			try {
				final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
				f.setAccessible(true);
				cmap = (CommandMap) f.get(Bukkit.getServer());
				return getCommandMap();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (cmap != null) {
			return cmap;
		}
		return getCommandMap();
	}

	private final class ReflectCommand extends Command {
		private AbstractSingularCommand exe = null;

		protected ReflectCommand(String command) {
			super(command);
		}

		public void setExecutor(AbstractSingularCommand exe) {
			this.exe = exe;
		}

		@Override
		public boolean execute(CommandSender sender, String commandLabel, String[] args) {
			if (exe != null) {
				return exe.onCommand(sender, this, commandLabel, args);
			}
			return false;
		}

		@Override
		public List<String> tabComplete(CommandSender sender, String alais, String[] args) {
			if (exe != null) {
				return exe.onTabComplete(sender, this, alais, args);
			}
			return null;
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if(_perm != null && !p.hasPermission(_perm)){
				UChat.ERROR.send(p, "Insufficient permissions.");
				return true;
			}
			onCommand(p, args);
		}
		return true;
	}

	public abstract void onCommand(Player player, String[] args);

	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return null;
	}
}
