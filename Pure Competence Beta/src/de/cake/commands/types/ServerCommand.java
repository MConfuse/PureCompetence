package de.cake.commands.types;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public interface ServerCommand {

	/**
	 * This is called whenever the {@link de.cake.manager.ServerCommandManager} has the specified command the User asked for.
	 * 
	 * @see manager#ServerCommandManager
	 */
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild);
}
