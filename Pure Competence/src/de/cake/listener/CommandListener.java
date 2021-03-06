package de.cake.listener;

import java.time.OffsetDateTime;

import de.cake.PureCompetence;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		EmbedBuilder builder = new EmbedBuilder();
		String message = event.getMessage().getContentDisplay();
		
		if (event.getChannelType() == ChannelType.TEXT) {
			TextChannel channel = event.getTextChannel();

			// !pc arg1 arg2 arg3...
			if (message.startsWith(PureCompetence.INSTANCE.prefix)) {
				String[] args = message.substring(1).split(" ");

				if (args.length > 0) {
					if (!PureCompetence.INSTANCE.getCmdMan().perform(args[0], event.getMember(), channel, event.getMessage())) {
						builder.setTitle("Unknown Command");
						builder.setDescription("Please try '" + PureCompetence.INSTANCE.prefix + "help' for more info");
						builder.setColor(PureCompetence.INSTANCE.clrRed);
						builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
						builder.setTimestamp(OffsetDateTime.now());
						channel.sendMessage(builder.build()).queue();
					}
					
				}
				
			}
			
		}
		
	}
	
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		
		String message = event.getMessage().getContentDisplay();
		EmbedBuilder builder = new EmbedBuilder();
		
		if(message.startsWith(PureCompetence.INSTANCE.prefix)) {
			PrivateChannel channel = event.getChannel();
			String[] args = message.substring(1).split(" ");
			
			if(args.length > 0) {
				if(!PureCompetence.INSTANCE.getPCmdMan().performPrivate(args[0], event.getAuthor(), channel, event.getMessage())) {
					builder.setTitle("Unknown Command");
					builder.setDescription("Please try '" + PureCompetence.INSTANCE.prefix + "help' for more info");
					builder.setColor(PureCompetence.INSTANCE.clrRed);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
				
			}
			
		}
		
	}
	
}
