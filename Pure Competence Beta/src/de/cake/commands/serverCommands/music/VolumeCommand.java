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

public class VolumeCommand implements ServerCommand {
	
	/**
	 * Changes the volume of the Audio Player. Only effects the LavaPlayer's Audio Stream.
	 * 
	 * @param int volume
	 * 
	 * @see types#ServerCommand
	 * @see manager#ServerCommandManager
	 * @see musicController#PlayerManager
	 */
	@Override
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild) {
		
		EmbedBuilder builder = new EmbedBuilder();
		String[] args = message.getContentDisplay().split(" ");
		
		GuildVoiceState state;
		
		try {
			if(args.length > 0) {
				if((state = m.getVoiceState()) != null) {
					VoiceChannel vc;
					if((vc = state.getChannel()) != null) {
						MusicController controller = PureCompetence.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
						AudioPlayer player = controller.getPlayer();
						MusicUtil.updateChannel(channel);
						int vol = player.getVolume();
						
						if(args.length == 2) {
							int amount = Integer.parseInt(args[1]);
							
							if(amount > 100) {
								amount = 100;
							}
							
							player.setVolume(amount);
							builder.setTitle("**Volume set to " + amount + "**");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
							
						} else {
							builder.setTitle("**Volume is at " + vol + "%**");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						}
						
					}
					
				}
				
			}
		
		} catch(ArrayIndexOutOfBoundsException e) {
		
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
