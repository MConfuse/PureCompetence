package de.cake.commands.serverCommands.text;

import de.cake.PureCompetence;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class LeaveGuildCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		
		String[] args = message.getContentDisplay().split(" ");
		
		long guild = Long.parseLong(args[1].toString());
		
		PureCompetence.INSTANCE.shardMan.getGuildById(guild).leave().queue();
	}

}
