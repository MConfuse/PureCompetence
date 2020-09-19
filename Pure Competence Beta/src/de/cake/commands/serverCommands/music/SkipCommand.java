package de.cake.commands.serverCommands.music;

import java.time.OffsetDateTime;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import de.cake.PureCompetence;
import de.cake.commands.types.ServerCommand;
import de.cake.musicController.MusicController;
import de.cake.musicController.Queue;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class SkipCommand  implements ServerCommand {
	
	@Override
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild) {
		
		EmbedBuilder builder = new EmbedBuilder();
		String[] args = message.getContentDisplay().split(" ");
		
		GuildVoiceState state;
		if((state = m.getVoiceState()) != null) {
			VoiceChannel vc;
			if((vc = state.getChannel()) != null) {
				try {
					System.out.println(args.length);
					
					 if(args.length == 1) {
						MusicController controller = PureCompetence.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
						AudioPlayer player = controller.getPlayer();
						long skip = player.getPlayingTrack().getDuration();
						
						player.getPlayingTrack().setPosition(skip - 1);
						builder.setTitle("**I have skipped the track for you**");
						builder.setColor(PureCompetence.INSTANCE.clrGreen);
						builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
						builder.setTimestamp(OffsetDateTime.now());
						channel.sendMessage(builder.build()).queue(); 
					} else if(args.length == 2) {
						MusicController controller = PureCompetence.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
						AudioPlayer player = controller.getPlayer();
						Integer skipAmmount = Integer.parseInt(args[1]);
						System.out.println(skipAmmount);
						Queue q = PureCompetence.INSTANCE.playerManager.getController(vc.getGuild().getIdLong()).getQueue();
						
						long skip = player.getPlayingTrack().getDuration();
						
						player.getPlayingTrack().setPosition(skip - 1);
						q.removeTrack(skipAmmount - 1);
						
						builder.setTitle("**I have skipped " + skipAmmount + " tracks for you**");
						builder.setColor(PureCompetence.INSTANCE.clrGreen);
						builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
						builder.setTimestamp(OffsetDateTime.now());
						channel.sendMessage(builder.build()).queue();
					}
					
				} catch(ArrayIndexOutOfBoundsException e) {
					
				}
				
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