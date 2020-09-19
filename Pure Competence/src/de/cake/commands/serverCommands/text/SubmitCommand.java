package de.cake.commands.serverCommands.text;

import java.time.Duration;

import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.RestAction;

public class SubmitCommand implements ServerCommand {
	
	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		
		EmbedBuilder builder = new EmbedBuilder();
		
		String[] args = message.getContentDisplay().split(" ");
		
		//args 0  1
		//$submit Submission
		if(args.length >= 1) {
			
			builder.setDescription("Do you want to submit this?");
			
			String idM = channel.sendMessage(builder.build()).complete().getId();
			
			RestAction<MessageHistory> msg = channel.getHistoryAfter(idM, 1).delay(Duration.ofSeconds(10));
			
			while (msg.toString().contains("net.dv8tion")) {
				System.out.println("ree");
			}
			
			String msg2 = msg.toString();
			System.out.println(idM);
			System.out.println(msg2);
			
			
			if(idM.equals(msg2)) {
				System.out.println("fuck yea");
			} else {
				System.out.println("fuck no");
			}
			
		}

	}
	
}
