package de.cake.commands.serverCommands.text;

import java.time.OffsetDateTime;

import de.cake.PureCompetence;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.HierarchyException;

public class KickCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		EmbedBuilder builder = new EmbedBuilder();
		
		if(m.hasPermission(Permission.ADMINISTRATOR)) {
			String mention = message.getContentRaw().substring(9, 27);
			Guild guild = channel.getGuild();
			
			try {
				//System.out.println(mention);
				//System.out.println(mention + args);
				guild.kick(mention).complete();
				builder.setDescription("**HmMmMm**");
				builder.setImage("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi1.ytimg.com%2Fvi%2F8T03RRxrfSU%2Fmqdefault.jpg&f=1");
				builder.setColor(PureCompetence.INSTANCE.clrBlue);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builder.setTimestamp(OffsetDateTime.now());
				channel.sendMessage(builder.build()).queue();
			
			} catch (HierarchyException e) {
				builder.setTitle("Hierarchy Exception");
				builder.setDescription("Pure Competence needs more Permissions to ban players");
				builder.setColor(PureCompetence.INSTANCE.clrRed);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builder.setTimestamp(OffsetDateTime.now());
				channel.sendMessage(builder.build()).queue();
			}
			
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

}