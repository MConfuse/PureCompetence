package de.cake.commands.serverCommands.music;

import java.time.OffsetDateTime;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import de.cake.PureCompetence;
import de.cake.commands.types.ServerCommand;
import de.cake.musicController.MusicController;
import de.cake.musicController.MusicUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class PauseCommand implements ServerCommand {
	
	/**
	 * Pauses the currently playing track. Only supports the LavaPlayer's Audio Stream.
	 * 
	 * @see types#ServerCommand
	 * @see manager#ServerCommandManager
	 * @see musicController#PlayerManager
	 */
	@Override
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild) {
		EmbedBuilder builder = new EmbedBuilder();
		
		GuildVoiceState state;
		if((state = m.getVoiceState()) != null) {
			VoiceChannel vc;
			if((vc = state.getChannel()) != null) {
				MusicController controller = PureCompetence.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
				AudioPlayer player = controller.getPlayer();
				MusicUtil.updateChannel(channel);
				
				if(player.isPaused() == true) {
					player.setPaused(false);
					builder.setTitle("**Unpaused the Track** ");
					builder.setColor(PureCompetence.INSTANCE.clrGreen);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				} else {
					player.setPaused(true);
					builder.setTitle("**Paused the Track** ");
					builder.setColor(PureCompetence.INSTANCE.clrGreen);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
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
