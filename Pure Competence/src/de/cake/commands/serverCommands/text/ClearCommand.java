package de.cake.commands.serverCommands.text;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.cake.PureCompetence;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

public class ClearCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		System.out.println("-------------------------- \r" + "[LOG] | Command | " + getClass() + "\r"
				+ m  + "\r" + m.getPermissions()
				+ "\r--------------------------");
		
		EmbedBuilder builder = new EmbedBuilder();
		
		if(m.hasPermission(channel, Permission.MESSAGE_MANAGE)) {
			String[] args = message.getContentDisplay().split(" ");
			
			//arg 0	 1
			//$clear 3
			if(args.length == 1) {
				builder.setTitle("**Command usage:** \r");
				builder.setDescription(PureCompetence.INSTANCE.prefix + "**Clear [amount]** \r"
						+ m.getAsMention() + ", you wrote: " + message.getContentDisplay());
				builder.setColor(PureCompetence.INSTANCE.clrRed);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builder.setTimestamp(OffsetDateTime.now());
				channel.sendMessage(builder.build()).queue();
			}
			
			message.delete().queue();
			
			if(args.length == 2) {
				
				try {
					int amount = Integer.parseInt(args[1]);
					channel.purgeMessages(get(channel, amount));
					channel.sendMessage("Successfully deleted " + amount + " messages").complete().delete().queueAfter(3, TimeUnit.SECONDS);
					return;
					
				} catch (NumberFormatException e) {
					builder.setDescription("**Only Numbers are allowed as arg[2]** \r"
							+ "**Example:** \r"
							+ PureCompetence.INSTANCE.prefix + "clear 4");
					builder.setColor(PureCompetence.INSTANCE.clrRed);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
				
			}
			
		} else if(!m.hasPermission(channel, Permission.MESSAGE_MANAGE)) {
			builder.setDescription("**Insufficient permissions to clear the chat**");
			builder.setColor(PureCompetence.INSTANCE.clrRed);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
		}
		
		EmbedBuilder log = new EmbedBuilder();
		log.setTitle("[LOG] | Server Command | " + this.getClass());
		log.addField("User", m.getEffectiveName() + " (ID Long: " + m.getId() + ")", false);
		log.addField("Guild", channel.getGuild().getName() + " (ID Long: " + channel.getGuild().getIdLong() + ")", false);
		log.setImage(channel.getGuild().getIconUrl());
		log.setColor(PureCompetence.INSTANCE.clrRed);
		log.setFooter(PureCompetence.INSTANCE.pwrdBy);
		log.setTimestamp(OffsetDateTime.now());
		Guild guildid = PureCompetence.INSTANCE.shardMan.getGuildById(374507865521520651l);
		guildid.getTextChannelById(694967647380570163l).sendMessage(log.build()).queue();
		
	}

	public List<Message> get(MessageChannel channel, int amount) {
		List<Message> messages = new ArrayList<>();
		
		int i = amount + 1;
		
		for(Message message : channel.getIterableHistory().cache(false)) {
			if(!message.isPinned()) {
				messages.add(message);
			}
			
			if(--i <= 0) break;
		}
		
		return messages;
	}

}
