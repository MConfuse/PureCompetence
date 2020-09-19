package de.cake;

import java.util.concurrent.ConcurrentHashMap;

import de.cake.commands.nix.Private.GetPicCommand;
import de.cake.commands.nix.Private.GetVidCommand;
import de.cake.commands.privateCommands.text.DeleteMessageCommand;
import de.cake.commands.privateCommands.text.HelpCommand;
import de.cake.commands.privateCommands.text.HiCommand;
import de.cake.commands.privateCommands.text.InfoCommand;
import de.cake.commands.privateCommands.text.SendMessageCommand;
import de.cake.commands.types.PrivateCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;

public class PrivateCommandManager {

	public ConcurrentHashMap<String, PrivateCommand> commands;
	
	public PrivateCommandManager() {
		this.commands = new ConcurrentHashMap<String, PrivateCommand>();
		
		this.commands.put("hi", new HiCommand());
		
		this.commands.put("getpicc", new GetPicCommand());
		this.commands.put("getvid", new GetVidCommand());
		this.commands.put("info", new InfoCommand());
		this.commands.put("sendmsg", new SendMessageCommand());
		this.commands.put("deletemsg", new DeleteMessageCommand());
		this.commands.put("help", new HelpCommand());
	}
	
	public boolean performPrivate(String command, User u, PrivateChannel channel, Message message) {
		
		PrivateCommand cmd;
		if((cmd = this.commands.get(command.toLowerCase())) != null) {
			cmd.performCommand(u, channel, message);
			return true;
		}
		return false;
	}
	
}
