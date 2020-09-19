package de.cake.commands.serverCommands.music;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import de.cake.PureCompetence;
import de.cake.commands.manage.LiteSQL;
import de.cake.commands.types.ServerCommand;
import de.cake.musicController.AudioLoadResult;
import de.cake.musicController.MusicController;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class PlaylistCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		System.out.println("-------------------------- \r" + "[LOG] | Command | " + getClass() + "\r"
				+ m  + "\r" + m.getPermissions()
				+ "\r--------------------------");
		
		String[] args = message.getContentDisplay().split(" ");
		EmbedBuilder builder = new EmbedBuilder();
		EmbedBuilder info = new EmbedBuilder().setTitle("Playlist Info!").addField(PureCompetence.INSTANCE.prefix + "**Shuffle**", "Shuffles the queue", false)
				.addField(PureCompetence.INSTANCE.prefix + "**Playlist play [Playlist]**", "Plays the specified playlist", false)
				.addField(PureCompetence.INSTANCE.prefix + "**Playlist EDM**", "Loads a playlist consisting of several Genres like: \r"
						+ "Trap, DnB, Dubstep, House and more! Perfect for Discord Raves!", false)
				.addField(PureCompetence.INSTANCE.prefix + "**Playlist Chill**", "Loads a playlist consisting of: \r"
						+ "House, Chillstep, DnB and stuff!", false)
				.addField(PureCompetence.INSTANCE.prefix + "**Playlist Hardbass**", "Loads a playlist consisting of: \r"
						+ "Russian Hardbass", false);
		info.setColor(PureCompetence.INSTANCE.clrRed);
		info.setFooter(PureCompetence.INSTANCE.pwrdBy);
		info.setTimestamp(OffsetDateTime.now());
		
		try {
			if(args.length > 1) {
				GuildVoiceState state;
				if((state = m.getVoiceState()) != null) {
					VoiceChannel vc;
					if((vc = state.getChannel()) != null) {
						MusicController controller = PureCompetence.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
						AudioPlayerManager apm = PureCompetence.INSTANCE.audioPlayerManager;
						
						if(args.length > 0) {
							
							if(args[1].equalsIgnoreCase("info")) {
								
								channel.sendMessage(info.build()).queue();
							} else if(args[1].equalsIgnoreCase("EDM")) {
								
								String url = "https://www.youtube.com/playlist?list=PL22otkb4s9l2GrqpwRom4R448tdmyivgg";
								builder.setTitle("**EDM/Bass Playlist!**", url);
								builder.addField(PureCompetence.INSTANCE.prefix + "**Playlist EDM**", "Loads a playlist consisting of several Genres like: \r"
										+ "Trap, DnB, Dubstep, House and more! Perfect for Discord Raves!", false);
								builder.setColor(PureCompetence.INSTANCE.clrGreen);
								builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
								builder.setTimestamp(OffsetDateTime.now());
								channel.sendMessage(builder.build()).queue();
							} else if(args[1].equalsIgnoreCase("Chill")) {
								
								String url = "https://www.youtube.com/playlist?list=PL22otkb4s9l2h6oVr9VTyNOBf8MI0mboD";
								builder.setTitle("**Chill Playlist**", url);
								builder.addField(PureCompetence.INSTANCE.prefix + "**Playlist Chill**", "Loads a playlist consisting of: \r"
										+ "House, Chillstep, DnB and stuff!", false);
								builder.setColor(PureCompetence.INSTANCE.clrGreen);
								builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
								builder.setTimestamp(OffsetDateTime.now());
								channel.sendMessage(builder.build()).queue();
							} else if(args[1].equalsIgnoreCase("hardbass")) {
								
								String url = "https://www.youtube.com/playlist?list=PL22otkb4s9l2h6oVr9VTyNOBf8MI0mboD";
								builder.setTitle("**Hardbass Playlist**", url);
								builder.addField(PureCompetence.INSTANCE.prefix + "**Playlist Hardbass**", "Loads a playlist consisting of: \r"
										+ "Russian Hardbass", false);
								builder.setColor(PureCompetence.INSTANCE.clrGreen);
								builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
								builder.setTimestamp(OffsetDateTime.now());
								channel.sendMessage(builder.build()).queue();
							} else if(args[1].equalsIgnoreCase("shuffle")) {
								
									controller.getQueue().shuffel();
									
									builder.setTitle("**Playlist Shuffled** ");
									builder.setColor(PureCompetence.INSTANCE.clrGreen);
									builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
									builder.setTimestamp(OffsetDateTime.now());
									channel.sendMessage(builder.build()).queue();
							} else if(args[1].equalsIgnoreCase("play")) { 
								
								EmbedBuilder permissions = new EmbedBuilder().setTitle("Insufficient Permission to perform this command!").setDescription("Please add `Pure Music` "
										+ "to your Server to be able to use the music feature or ask a Server Admin to deactivate the Permission check.")
										.setColor(PureCompetence.INSTANCE.clrRed).setFooter(PureCompetence.INSTANCE.pwrdBy).setTimestamp(OffsetDateTime.now());
								
								ResultSet check = LiteSQL.onQuery("SELECT * FROM adminrights WHERE guildid = " + channel.getGuild().getIdLong());
								
								try {
									if(check.next()) {
										long roleid = check.getLong("role");
										int music = check.getInt("music");
										
										//Returns if user does not have enough permissions
										if(!(m.getRoles().contains(channel.getGuild().getRoleById(roleid))) && music == 0) {
											if(m.getIdLong() != 255313111391666176l) {
												channel.sendMessage(permissions.build()).queue();
												
												return;
											}
											
										}
										
									}
									
									check.close();
								} catch(SQLException ex) {
									ex.printStackTrace();
								}
								
								//Play Command
								if(args[2].equalsIgnoreCase("EDM")) {
									
									String url = "https://www.youtube.com/playlist?list=PL22otkb4s9l2GrqpwRom4R448tdmyivgg";
									builder.setTitle("**EDM/Bass Playlist loaded!**", url);
									builder.setDescription("Have some of that BPM stuff");
									builder.setColor(PureCompetence.INSTANCE.clrGreen);
									builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
									builder.setTimestamp(OffsetDateTime.now());
									
									apm.loadItem(url, new AudioLoadResult(controller, url, channel.getIdLong(), channel.sendMessage(builder.build()).complete().getIdLong(), -1, channel, state.getChannel()));
								} else if(args[2].equalsIgnoreCase("Chill")) {
									
									String url = "https://www.youtube.com/playlist?list=PL22otkb4s9l2h6oVr9VTyNOBf8MI0mboD";
									builder.setTitle("**Chill Playlist loaded!**", url);
									builder.setDescription("Have some of these relaxing beats!");
									builder.setColor(PureCompetence.INSTANCE.clrGreen);
									builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
									builder.setTimestamp(OffsetDateTime.now());
									
									apm.loadItem(url, new AudioLoadResult(controller, url, channel.getIdLong(), channel.sendMessage(builder.build()).complete().getIdLong(), -1, channel, state.getChannel()));
								} else if(args[2].equalsIgnoreCase("hardbass")) {
									
									String url = "https://www.youtube.com/playlist?list=PLtQbLNyftupQj93vfVkTj9355rFo0XQ7Y";
									builder.setTitle("**Hardbass Playlist loaded!**", url);
									builder.setDescription("Have some of these Slav beats!");
									builder.setColor(PureCompetence.INSTANCE.clrGreen);
									builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
									builder.setTimestamp(OffsetDateTime.now());
									
									apm.loadItem(url, new AudioLoadResult(controller, url, channel.getIdLong(), channel.sendMessage(builder.build()).complete().getIdLong(), -1, channel, state.getChannel()));
								}
								
							} else {
								
								channel.sendMessage(info.build()).queue();
							}
							
						}
			
					}
				
				}
			
			} else {
				
				channel.sendMessage(info.build()).queue();
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

}
