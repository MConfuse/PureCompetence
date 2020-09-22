package de.cake.commands.serverCommands.music;

import java.time.OffsetDateTime;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import de.cake.PureCompetence;
import de.cake.commands.types.ServerCommand;
import de.cake.musicController.AudioLoadResult;
import de.cake.musicController.MusicController;
import de.cake.musicController.MusicUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class PlayCommand implements ServerCommand {

	/**
	 * Plays a track via Voice Chat. Only supports the LavaPlayer's Audio Stream with the by LavaPlayer supported File formats.
	 * 
	 * @param URL
	 * @param YouTube
	 * @param SoundCloud
	 * @param MP3
	 * 
	 * @see types#ServerCommand
	 * @see manager#ServerCommandManager
	 * @see musicController#PlayerManager
	 */
	@Override
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild) {
		String[] args = message.getContentDisplay().split(" ");
		EmbedBuilder builder = new EmbedBuilder();
		
		if(args.length > 1) {
			GuildVoiceState state;
			if((state = m.getVoiceState()) != null) {
				VoiceChannel vc;
				
				if(args[1].equalsIgnoreCase("next") && (vc = state.getChannel()) != null) {
					
					MusicController controller = PureCompetence.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
					AudioPlayerManager apm = PureCompetence.INSTANCE.audioPlayerManager;
					
					MusicUtil.updateChannel(channel);
					
					StringBuilder strBuilder = new StringBuilder();
					for(int i = 2; i < args.length; i++) strBuilder.append(args[i] + " ");
					
					String url = strBuilder.toString().trim();
					if(!url.startsWith("http")) {
						url = "ytsearch: " + url;
					}
					
					builder.setTitle("**Searching and adding Track to play next!**");
					builder.setColor(PureCompetence.INSTANCE.clrGreen);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					
					apm.loadItem(url, new AudioLoadResult(controller, url, channel.getIdLong(), channel.sendMessage(builder.build()).complete().getIdLong(), -999, channel, state.getChannel()));
					
				} else if((vc = state.getChannel()) != null) {
					MusicController controller = PureCompetence.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());																		
					AudioPlayerManager apm = PureCompetence.INSTANCE.audioPlayerManager;
					
					MusicUtil.updateChannel(channel);
					
					StringBuilder strBuilder = new StringBuilder();
					for(int i = 1; i < args.length; i++) strBuilder.append(args[i] + " ");
					
					String url = strBuilder.toString().trim();
					if(!url.startsWith("http")) {
						url = "ytsearch: " + url;
					}
					
					builder.setTitle("**Searching and adding Track to queue**");
					builder.setColor(PureCompetence.INSTANCE.clrGreen);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					
					apm.loadItem(url, new AudioLoadResult(controller, url, channel.getIdLong(), channel.sendMessage(builder.build()).complete().getIdLong(), -1, channel, state.getChannel()));
				}
				
			}
			
		} else {
			builder.setTitle("**Unknown Command** ");
			builder.setDescription("Please use **" + PureCompetence.INSTANCE.prefix + "play <url/search query>");
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

}
