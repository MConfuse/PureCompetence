package de.cake.commands.serverCommands.text;

import de.cake.PureCompetence;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class LeaveGuildCommand implements ServerCommand {

	/**
	 * Leaves the specified Guild.
	 * Does not need to be documented ;).
	 * 
	 * @see types#ServerCommand
	 * @see manager#ServerCommandManager
	 */
	@Override
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild) {
		
		String[] args = message.getContentDisplay().split(" ");
		
		long guild1 = Long.parseLong(args[1].toString());
		
		PureCompetence.INSTANCE.shardMan.getGuildById(guild1).leave().queue();
		
	}
	
}
