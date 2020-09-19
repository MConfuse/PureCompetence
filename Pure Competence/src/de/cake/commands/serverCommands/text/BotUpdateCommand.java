package de.cake.commands.serverCommands.text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import de.cake.PureCompetence;
import de.cake.commands.manage.LiteSQL;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class BotUpdateCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		
		EmbedBuilder builder = new EmbedBuilder();
		EmbedBuilder permissions = new EmbedBuilder().setTitle("Insufficient Permission to perform this command!").setColor(PureCompetence.INSTANCE.clrRed).setFooter(PureCompetence.INSTANCE.pwrdBy).setTimestamp(OffsetDateTime.now());
		
		//$update
		String args = message.getContentRaw().substring(8);
		String[] title = message.getContentDisplay().split("<Title>");
		String[] titleurl = message.getContentDisplay().split("<TitleUrl>");
		
		if(m.getIdLong() != 255313111391666176l) channel.sendMessage(permissions.build());
		if(m.getIdLong() != 255313111391666176l) return;
		
		ResultSet set = LiteSQL.onQuery("SELECT * FROM updatechannels");
		
		builder.setDescription(args);
		
		try {
			while(set.next()) {
				long guild = set.getLong("guildid");
				long ch = set.getLong("channelid");
				int state = set.getInt("state");
				
				if(state == 1) {
					//Checks if there is a marker for the Title
					if(!message.getContentDisplay().contains("<TitleUrl>") && message.getContentDisplay().contains("<Title>")) {
						
						builder.setTitle(title[1].toString());
					} else if(message.getContentDisplay().contains("<TitleUrl>") && message.getContentDisplay().contains("<Title>")) {
						
						builder.setTitle(title[1].toString(), titleurl[1].toString());
					}
					
					System.out.println(guild);
					System.out.println(ch);
					
					PureCompetence.INSTANCE.shardMan.getGuildById(guild).getTextChannelById(ch).sendMessage(builder.build()).queue();
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
