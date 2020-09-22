package de.cake.commands.serverCommands.music;

import java.time.OffsetDateTime;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

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

public class QueueListCommand implements ServerCommand {

	/**
	 * Used to retrieve the current Queue list of the Bot on that specific Guild.
	 * 
	 * @return Queue list in String format
	 * 
	 * @see types#ServerCommand
	 * @see manager#ServerCommandManager
	 * @see musicController#PlayerManager
	 */
	@Override
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild) {
		EmbedBuilder builder = new EmbedBuilder();
		
		GuildVoiceState state;
		if((state = m.getVoiceState()) != null)
		{
			VoiceChannel vc;
			if((vc = state.getChannel()) != null)
			{
				MusicController controller = PureCompetence.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
				AudioPlayer player = controller.getPlayer();
				AudioTrack track = player.getPlayingTrack();
				AudioTrackInfo info = track.getInfo();
				Queue queue = controller.getQueue();
				//String listString = "";
				StringBuilder listString = new StringBuilder();
				
				long seconds = player.getPlayingTrack().getPosition() / 1000;
				long minutes = seconds / 60;
				long hours = minutes / 60;
				minutes %= 60;
				seconds %= 60;
				
				Integer listSize = queue.getQueuelist().size();
				Integer listSizeE = queue.getQueuelist().size() - 10;
				
				builder.addField(":arrow_forward: Currently playing", info.title + "\r " + "Player at: " + (hours > 0 ? hours + "h " : "") + minutes + "min " + seconds + "s", false);
				
				if(listSize <= 10)
				{
					for(int i = 0; i < listSize; i++)
					{
						//listString += i + 1 + ". " + queue.getQueuelist().get(i).getInfo().title + "\r";
						listString.append(i + 1 + ". " + queue.getQueuelist().get(i).getInfo().title + " \r");
					}
				}
				else
				{
					for(int i = 0; i < 10; i++)
					{
						//listString += i + 1 + ". " + queue.getQueuelist().get(i).getInfo().title + "\r";
						listString.append(i + 1 + ". " + queue.getQueuelist().get(i).getInfo().title + " \r");
						if(i == 10)
						{
							//listString += "**There are " + listSizeE.toString() + " more tracks in queue.**";
							listString.append("**There are " + listSizeE.toString() + " more tracks in queue.**");
						}
						
					}
					
				}
				
				builder.addField("Queue list (" + listSize + "/600 Tracks in the queue)", "" + listString, false);
				builder.setTitle("**Queue list**");
				builder.setColor(PureCompetence.INSTANCE.clrGreen);
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
