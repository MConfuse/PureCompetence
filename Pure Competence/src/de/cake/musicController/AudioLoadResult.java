package de.cake.musicController;

import java.time.OffsetDateTime;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.cake.PureCompetence;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class AudioLoadResult implements AudioLoadResultHandler {

	private final MusicController controller;
	private final String uri;
	private final Long id;
	private final Long idM;
	private final Integer pos;
	private final TextChannel channel;
	private final VoiceChannel vc;
	EmbedBuilder builder = new EmbedBuilder();
	
	public AudioLoadResult(MusicController controller, String uri, Long id, Long idM, Integer pos, TextChannel channel, VoiceChannel vc) {
		this.controller = controller;
		this.uri = uri;
		this.id = id;
		this.idM = idM;
		this.pos = pos;
		this.channel = channel;
		this.vc = vc;
	}
	
	@Override
	public void trackLoaded(AudioTrack track) {
//		System.out.println("trackLoaded");
		
		Queue queue = controller.getQueue();
		OpenVoiceConnection ovc = new OpenVoiceConnection(channel.getGuild().getIdLong(), vc);
		AudioManager manager = PureCompetence.INSTANCE.shardMan.getGuildById(channel.getGuild().getIdLong()).getAudioManager();
		
//		System.out.println("Vars");
		
		if(manager.isConnected() == false) {
			ovc.OpenVoice();
		}
		
//		System.out.println("Connected");
		
		if(pos == -999) {
//			System.out.println("1");
			queue.addTrackToTopOfQueue(track, 0);
		} else if(pos >= 0 && !(pos < 0)) {
//			System.out.println("2");
			queue.addTrackToTopOfQueue(track, pos);
		} else {
//			System.out.println("3");
			queue.addTrackToQueue(track);
		}
//		System.out.println("end");
		
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist) {
		Queue queue = controller.getQueue();
		OpenVoiceConnection ovc = new OpenVoiceConnection(channel.getGuild().getIdLong(), vc);
		AudioManager manager = PureCompetence.INSTANCE.shardMan.getGuildById(channel.getGuild().getIdLong()).getAudioManager();
		
		if(manager.isConnected() == false) {
			ovc.OpenVoice();
		}
		
		if(uri.startsWith("ytsearch: ")) {
			
			if(pos == -999) {
				
				queue.addTrackToTopOfQueue(playlist.getTracks().get(0), 0);
				return;
			} else if(pos >= 0 && !(pos < 0)) {
				
				queue.addTrackToTopOfQueue(playlist.getTracks().get(0), pos);
				return;
			} else {
			
				queue.addTrackToQueue(playlist.getTracks().get(0));
				return;
			}
			
		}
		
		int added = 0;
		
		for(AudioTrack track : playlist.getTracks()) {
			queue.addTrackToQueue(track);
			added++;
		}
		
		EmbedBuilder builder = new EmbedBuilder().setColor(PureCompetence.INSTANCE.clrGreen).setDescription(added + " tracks were added to queue.");
		
		MusicUtil.sendEmbed(controller.getGuild().getIdLong(), builder);
	}

	@Override
	public void noMatches() {
		EmbedBuilder builder = new EmbedBuilder().setColor(PureCompetence.INSTANCE.clrRed).setTitle("**No Videos found**")
				.setDescription("Couldn't find a Video matching the search queue").setFooter(PureCompetence.INSTANCE.pwrdBy)
				.setTimestamp(OffsetDateTime.now());
		
		controller.getGuild().getTextChannelById(id).deleteMessageById(idM).complete();
		
		MusicUtil.sendEmbed(controller.getGuild().getIdLong(), builder);
	}

	@Override
	public void loadFailed(FriendlyException exception) {
		EmbedBuilder builder = new EmbedBuilder().setColor(PureCompetence.INSTANCE.clrRed).setTitle("**Loading failed**")
				.setDescription("Couldn't load the Video due to an error!").setFooter(PureCompetence.INSTANCE.pwrdBy)
				.setTimestamp(OffsetDateTime.now());
		
		controller.getGuild().getTextChannelById(id).deleteMessageById(idM).complete();
		
		MusicUtil.sendEmbed(controller.getGuild().getIdLong(), builder);
	}

}
