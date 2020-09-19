package de.cake.commands.privateCommands.text;

import java.time.OffsetDateTime;

import de.cake.PureCompetence;
import de.cake.commands.types.PrivateCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;

public class InfoCommand implements PrivateCommand {

	@Override
	public void performCommand(User u, PrivateChannel channel, Message message) {
		EmbedBuilder builder = new EmbedBuilder();

		builder.setTitle("**Info about " + u.getName() + "**");
		builder.addField("Name", u.getAsMention(), true);
		builder.addField("Discord ID", "" + u.getIdLong(), true);
		builder.addField("Joined Discord on", u.getTimeCreated().getDayOfMonth() + "." + u.getTimeCreated().getMonthValue() + "." + u.getTimeCreated().getYear(), true);
		builder.setThumbnail(u.getAvatarUrl());
		builder.setColor(PureCompetence.INSTANCE.clrViolet);
		builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
		builder.setTimestamp(OffsetDateTime.now());
		channel.sendMessage(builder.build()).queue();

		EmbedBuilder log = new EmbedBuilder();
		log.setTitle("[LOG] | Private Command | " + this.getClass());
		log.addField("User", u.getName() + " (ID Long: " + u.getId() + ")", false);
		log.addField("Private Channel", "" + u.getName(), false);
		log.setThumbnail(u.getAvatarUrl());
		log.setColor(PureCompetence.INSTANCE.clrRed);
		log.setFooter(PureCompetence.INSTANCE.pwrdBy);
		log.setTimestamp(OffsetDateTime.now());
		Guild guildid = PureCompetence.INSTANCE.shardMan.getGuildById(374507865521520651l);
		guildid.getTextChannelById(694967647380570163l).sendMessage(log.build()).queue();
	}

}
