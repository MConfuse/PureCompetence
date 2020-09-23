package de.cake.manager;

import java.util.concurrent.ConcurrentHashMap;

import de.cake.commands.serverCommands.music.PauseCommand;
import de.cake.commands.serverCommands.music.PlayCommand;
import de.cake.commands.serverCommands.music.QueueListCommand;
import de.cake.commands.serverCommands.music.SkipCommand;
import de.cake.commands.serverCommands.music.StopCommand;
import de.cake.commands.serverCommands.music.TrackInfoCommand;
import de.cake.commands.serverCommands.music.VolumeCommand;
import de.cake.commands.serverCommands.text.DateCommand;
import de.cake.commands.serverCommands.text.DeveloperCommand;
import de.cake.commands.serverCommands.text.GetPicCommand;
import de.cake.commands.serverCommands.text.HelpCommand;
import de.cake.commands.serverCommands.text.LeaveGuildCommand;
import de.cake.commands.serverCommands.text.PPSizeCommand;
import de.cake.commands.serverCommands.text.SetupCommand;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ServerCommandManager {

	public ConcurrentHashMap<String, ServerCommand> commands;
	
	public ServerCommandManager() {
		this.commands = new ConcurrentHashMap<String, ServerCommand>();
		
		//--Text Commands--
		
		this.commands.put("dev", new DeveloperCommand());
		this.commands.put("help", new HelpCommand());
		this.commands.put("getpicc", new GetPicCommand());
		
		this.commands.put("pp", new PPSizeCommand());
		
		this.commands.put("setup", new SetupCommand());
		
		this.commands.put("leave", new LeaveGuildCommand());
		
		this.commands.put("date", new DateCommand());
		
		//--Music Commands--
		
		this.commands.put("play", new PlayCommand());
		this.commands.put("pause", new PauseCommand());
		this.commands.put("stop", new StopCommand());
		
		this.commands.put("queue", new QueueListCommand());
		this.commands.put("queuelist", new QueueListCommand());
		this.commands.put("qlist", new QueueListCommand());
		
		this.commands.put("skip", new SkipCommand());
		
		this.commands.put("np", new TrackInfoCommand());
		this.commands.put("cp", new TrackInfoCommand());
		this.commands.put("tinfo", new TrackInfoCommand());
		
		this.commands.put("vol", new VolumeCommand());
		this.commands.put("volume", new VolumeCommand());
		
		this.commands.put("playlist", new PlayCommand());
	}
	
	/**
	 * Used to check for Commands by the {@link de.cake.listener.CommandListener} and perform the called Command.
	 * 
	 * @return Returns true if the specified command is in the Hash Map, if it is it will perform the Command.
	 * 
	 * @param command
	 * @param m
	 * @param channel
	 * @param message
	 * @param guild
	 * @return
	 */
	public boolean perform(String command, Member m, TextChannel channel, Message message, Guild guild) {
		
		ServerCommand cmd;
		if((cmd = this.commands.get(command.toLowerCase())) != null) {
			cmd.performCommand(command, m, channel, message, guild);
			return true;
		}
		
		return false;
	}
	
}
