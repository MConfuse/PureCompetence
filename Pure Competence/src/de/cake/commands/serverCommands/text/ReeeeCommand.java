package de.cake.commands.serverCommands.text;

import java.time.OffsetDateTime;

import de.cake.PureCompetence;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ReeeeCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		
		
		EmbedBuilder builder = new EmbedBuilder();
		
		message.delete();
		
		builder.setTitle("** R E E E E E E E E E**");
		builder.setImage("https://cdn.discordapp.com/attachments/384293029390843905/479237073627381770/1520694939483.gif");
		builder.setColor(PureCompetence.INSTANCE.clrRed);
		builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
		builder.setTimestamp(OffsetDateTime.now());
		channel.sendMessage(builder.build()).queue();
		
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

}
