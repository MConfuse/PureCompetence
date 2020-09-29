package de.cake.commands.serverCommands.text;

import java.util.concurrent.TimeUnit;

import de.cake.commands.types.ServerCommand;
import de.cake.util.TimeHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.RestAction;

public class DeveloperCommand implements ServerCommand {

	/**
	 * Changes to what ever I'm currently testing :) No need to be documented ;)
	 * 
	 * @see types#ServerCommand
	 * @see manager#ServerCommandManager
	 */
	@Override
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild)
	{
//		selfDestruct in less weird but still not able to retrieve the updated version of the message history
		channel.sendMessage("Yikes").complete();

		while (!TimeHelper.hasTimeElapsed(5000, true))
		{
		}

		channel.sendMessage("Yeet").complete();

//		selfDestruct(channel, message).queue();
//		channel.sendMessage("6").queue();
	}

	public RestAction<Void> selfDestruct(TextChannel channel, Message message)
	{
		return channel.sendMessage("Yeet").delay(10, TimeUnit.SECONDS).flatMap((it) -> it.editMessage("Editeded"))
				.delay(10, TimeUnit.SECONDS).flatMap((it) -> it.delete());
	}

}
