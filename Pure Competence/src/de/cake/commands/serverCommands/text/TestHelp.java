package de.cake.commands.serverCommands.text;

import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class TestHelp implements ServerCommand{

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		
		
		System.out.println(message.getAuthor());
	}

}
