package nl.dubehh.command;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import nl.dubehh.util.UChat;

public abstract class AbstractGroupCommand implements CommandExecutor, TabExecutor {

	private final List<String> _alias;
	protected final HashMap<String, AbstractSubCommand> _commands;
	protected final String _command, _usage, _perm;

	protected static CommandMap cmap;

	public AbstractGroupCommand(String command, String perm) {
		this(command, perm, null);
	}
	
	protected void addAlias(String... alias){
		for(String s : alias)
			_alias.add(s);
	}
	
	public void register(String cmd, AbstractSubCommand instance){
		this._commands.put(cmd, instance);
	}

	public AbstractGroupCommand(String command, String perm, String usage) {
		this._commands = new HashMap<>();
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
		registerCommands();
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
		private AbstractGroupCommand exe = null;

		protected ReflectCommand(String command) {
			super(command);
		}

		public void setExecutor(AbstractGroupCommand exe) {
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

	@SuppressWarnings("rawtypes")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				if(_perm != null && !p.hasPermission(_perm)){
					UChat.ERROR.send(p, "Insufficient permissions.");
					return true;
				}
				else{
					UChat.INFO_UNIMPORTANT.send(p, "Command List:");
					Iterator<Entry<String, AbstractSubCommand>> it = _commands.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pair = (Map.Entry) it.next();
						p.sendMessage(ChatColor.DARK_GRAY + "  /" + cmd.getName() + " " + pair.getKey().toString() + ": "
								+ ChatColor.GRAY + ((AbstractSubCommand) pair.getValue()).getDescription());
					}
					return true;
				}
			}else{
				String alias = args[0].toLowerCase();
				if (_commands.containsKey(alias)) {
					AbstractSubCommand command = _commands.get(alias);
					if (p.hasPermission(command.getRequiredPermission())) {
						if (args.length >= command.getRequiredArguments()) {
							command.onCommand(p, args);
						} else
							UChat.ERROR.send(p, command.getUsage());
					} else
						UChat.ERROR.send(p, "Insufficient permissions.");
				} else
					UChat.ERROR.send(p, "Unknown command: See the documentation for available commands.");
			}
		}
		return true;
	}

	public abstract void registerCommands();

	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return null;
	}
}