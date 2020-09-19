package de.cake;

import java.util.concurrent.ConcurrentHashMap;

import de.cake.commands.nix.Server.AlexIstSchuldCommand;
import de.cake.commands.nix.Server.GeneratorV;
import de.cake.commands.serverCommands.music.PauseCommand;
import de.cake.commands.serverCommands.music.PlayCommand;
import de.cake.commands.serverCommands.music.PlaylistCommand;
import de.cake.commands.serverCommands.music.QueueListCommand;
import de.cake.commands.serverCommands.music.SkipCommand;
import de.cake.commands.serverCommands.music.SkipFwCommand;
import de.cake.commands.serverCommands.music.StopCommand;
import de.cake.commands.serverCommands.music.TrackInfoCommand;
import de.cake.commands.serverCommands.music.VolumeCommand;
import de.cake.commands.serverCommands.text.BotUpdateCommand;
import de.cake.commands.serverCommands.text.ChangelogCommand;
import de.cake.commands.serverCommands.text.ClearCommand;
import de.cake.commands.serverCommands.text.DateCommand;
import de.cake.commands.serverCommands.text.HelpCommand;
import de.cake.commands.serverCommands.text.HiCommand;
import de.cake.commands.serverCommands.text.HugCommand;
import de.cake.commands.serverCommands.text.InfoCommand;
import de.cake.commands.serverCommands.text.KickCommand;
import de.cake.commands.serverCommands.text.LeaveGuildCommand;
import de.cake.commands.serverCommands.text.MemeCommand;
import de.cake.commands.serverCommands.text.PreviewCommand;
import de.cake.commands.serverCommands.text.ReeeeCommand;
import de.cake.commands.serverCommands.text.SetupCommand;
import de.cake.commands.serverCommands.text.SpamCommand;
import de.cake.commands.serverCommands.text.TestHelp;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandManager {

	public ConcurrentHashMap<String, ServerCommand> commands;
	
	public CommandManager() {
		this.commands = new ConcurrentHashMap<String, ServerCommand>();
		
		this.commands.put("clear", new ClearCommand());
		this.commands.put("clr", new ClearCommand());
		
		this.commands.put("date", new DateCommand());
		this.commands.put("hi", new HiCommand());
		this.commands.put("meme", new MemeCommand());
		this.commands.put("hug", new HugCommand());
		this.commands.put("help", new HelpCommand());
		this.commands.put("kick", new KickCommand());
		this.commands.put("getpicc", new AlexIstSchuldCommand());
		this.commands.put("getvid", new GeneratorV());
		this.commands.put("changelog", new ChangelogCommand());
		//this.commands.put("submit", new SubmitCommand());
		
		this.commands.put("leave", new LeaveGuildCommand());
		
		this.commands.put("spam", new SpamCommand());
		
		this.commands.put("setup", new SetupCommand());
		this.commands.put("update", new BotUpdateCommand());
		
		this.commands.put("re", new ReeeeCommand());
		this.commands.put("ree", new ReeeeCommand());
		this.commands.put("reee", new ReeeeCommand());
		this.commands.put("reeee", new ReeeeCommand());
		
		this.commands.put("preview", new PreviewCommand());
		this.commands.put("pvw", new PreviewCommand());
		
		this.commands.put("info", new InfoCommand());
		
		//Music
		this.commands.put("play", new PlayCommand());
		this.commands.put("stop", new StopCommand());
		
		this.commands.put("pause", new PauseCommand());
		this.commands.put("p", new PauseCommand());
		
		this.commands.put("tinfo", new TrackInfoCommand());
		this.commands.put("cp", new TrackInfoCommand());
		this.commands.put("np", new TrackInfoCommand());
		
		this.commands.put("volume", new VolumeCommand());
		this.commands.put("set", new VolumeCommand());
		this.commands.put("vol", new VolumeCommand());
		
		this.commands.put("skipfw", new SkipFwCommand());
		this.commands.put("skip", new SkipCommand());
		
		this.commands.put("qlist", new QueueListCommand());
		this.commands.put("queue", new QueueListCommand());
		this.commands.put("playlist", new PlaylistCommand());
		
		//Test Commands
		this.commands.put("beta", new TestHelp());
	}
	
	public boolean perform(String command, Member m, TextChannel channel, Message message) {
		
		ServerCommand cmd;
		if((cmd = this.commands.get(command.toLowerCase())) != null) {
			cmd.performCommand(m, channel, message);
			return true;
		}
		
		return false;
	}

}
