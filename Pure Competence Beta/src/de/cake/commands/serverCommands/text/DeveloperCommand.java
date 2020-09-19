package de.cake.commands.serverCommands.text;

import java.util.concurrent.TimeUnit;

import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.RestAction;

public class DeveloperCommand implements ServerCommand {

	@Override
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild) {
		
		System.out.println("1");
		selfDestruct(channel, message).queue();
		
		channel.sendMessage("6").queue();
		System.out.println("2");
	}
	
	public RestAction<Void> selfDestruct(TextChannel channel, Message message){
		return channel.sendMessage("Yeet").delay(10, TimeUnit.SECONDS)
				.flatMap((it) -> it.editMessage("Editeded")).delay(10, TimeUnit.SECONDS).flatMap((it) -> it.delete());
	}

}
