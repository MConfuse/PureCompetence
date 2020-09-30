package de.cake.commands.serverCommands.text;

import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class KickCommand implements ServerCommand {

	/**
	 * Not tested yet :)
	 * 
	 */
	@Override
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild)
	{
		String[] args = message.getContentDisplay().split(" ");
		Member target = null;

		if (args.length > 1 && message.getMentionedMembers().size() != 0)
		{
			target = message.getMentionedMembers().get(0);
			StringBuilder builder = new StringBuilder();

			for (int i = 2; i < args.length; i++)
			{
				builder.append(args[i]);
			}

			if (builder.length() == 0)
			{
				guild.kick(target, m.getUser().getAsMention() + " has kicked you!").complete();
			}
			else
			{
				guild.kick(target, builder.toString()).complete();
			}

		}

	}

}
