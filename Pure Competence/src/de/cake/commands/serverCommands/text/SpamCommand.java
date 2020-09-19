package de.cake.commands.serverCommands.text;

import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class SpamCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		
		String[] args = message.getContentDisplay().split(" ");
		EmbedBuilder builder = new EmbedBuilder();
		Integer amount = Integer.parseInt(args[2]);
		builder.setTitle("Hi");
		
		for(int i = 0; i < amount; i++) {
			m.getUser().openPrivateChannel().queue((ch) -> {
				ch.sendMessage("Spam things").queue();
			});
		}
		
	}

}
