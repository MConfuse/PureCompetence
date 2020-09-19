package de.cake.commands.serverCommands.text;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import de.cake.PureCompetence;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

public class ChangelogCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {

		String[] args = message.getContentDisplay().split(" ");
		EmbedBuilder builder = new EmbedBuilder();
		EmbedBuilder error = new EmbedBuilder();
		Guild guildID = PureCompetence.INSTANCE.shardMan.getGuildById(374507865521520651l);
		TextChannel channelID = guildID.getTextChannelById(668244509167190068l);
		
		if(args.length == 1) {
			Integer logL = get(channelID, 0).get(0).toString().length() + 1;
			
			builder.setTitle("This is the latest changelog I found");
			builder.setDescription(get(channelID, 0).toString().substring(1, logL));
			
			builder.setColor(PureCompetence.INSTANCE.clrBlue);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
		}
		
		try {
			
			if(args.length == 2) {
				Integer amount = Integer.parseInt(args[1]);
				
				if(amount > get(channelID, amount).size()) {
					Integer size = get(channelID, amount).size() - 1;
					
					error.setTitle("The oldest Changelog I found is " + size);
					error.setColor(PureCompetence.INSTANCE.clrBlue);
					error.setFooter(PureCompetence.INSTANCE.pwrdBy);
					error.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(error.build()).queue();
				} else {
					builder.setTitle("This is the changelog I found " + amount + " builds ago");
					builder.setDescription(get(channelID, amount).get(amount).toString());
					builder.setColor(PureCompetence.INSTANCE.clrBlue);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
				
			}
			
		} catch (ArrayIndexOutOfBoundsException e) {
			
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
	
	public List<String> get(MessageChannel channel, Integer amount) {
		List<String> messages = new ArrayList<>();
		
		int i = amount + 1;
		
		for(Message message : channel.getIterableHistory().cache(false)) {
			messages.add(message.getContentDisplay());
			
			if(--i <= 0) break;
		}
		
		return messages;
	}
	
}
