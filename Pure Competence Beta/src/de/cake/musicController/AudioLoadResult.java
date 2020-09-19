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
		
		Queue queue = controller.getQueue();
		OpenVoiceConnection ovc = new OpenVoiceConnection(channel.getGuild().getIdLong(), vc);
		AudioManager manager = PureCompetence.INSTANCE.shardMan.getGuildById(channel.getGuild().getIdLong()).getAudioManager();
		
		if(manager.isConnected() == true) {
			builder.setTitle("**Track added to queue**");
			
			builder.setDescription("Track added: [" + track.getInfo().title + "](" + track.getInfo().uri + ")");
			builder.setColor(PureCompetence.INSTANCE.clrGreen);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.PlayMsg);
			builder.setTimestamp(OffsetDateTime.now());
		} else {
			ovc.OpenVoice();
			builder.setTitle("**Track loaded**");
			
			builder.setDescription("Now playing: [" + track.getInfo().title + "](" + track.getInfo().uri + ")");
			builder.setColor(PureCompetence.INSTANCE.clrGreen);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.PlayMsg);
			builder.setTimestamp(OffsetDateTime.now());
		}
		
		if(pos == -999) {
			queue.addTrackToTopOfQueue(track, 0);
		} else if(pos >= 0 && !(pos < 0)) {
			queue.addTrackToTopOfQueue(track, pos);
		} else {
			queue.addTrackToQueue(track);
		}
		
		controller.getGuild().getTextChannelById(id).editMessageById(idM, builder.build()).queue();
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist) {
		
		Queue queue = controller.getQueue();
		OpenVoiceConnection ovc = new OpenVoiceConnection(channel.getGuild().getIdLong(), vc);
		AudioManager manager = PureCompetence.INSTANCE.shardMan.getGuildById(channel.getGuild().getIdLong()).getAudioManager();
		
		if(manager.isConnected() == true) {
			builder.setTitle("**Track added to queue**");
			
			builder.setDescription("Track added: [" + playlist.getTracks().get(0).getInfo().title + "](" + playlist.getTracks().get(0).getInfo().uri + ")");
			builder.setColor(PureCompetence.INSTANCE.clrGreen);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.PlayMsg);
			builder.setTimestamp(OffsetDateTime.now());
		} else {
			ovc.OpenVoice();
			builder.setTitle("**Track loaded**");
			
			builder.setDescription("Now playing: [" + playlist.getTracks().get(0).getInfo().title + "](" + playlist.getTracks().get(0).getInfo().uri + ")");
			builder.setColor(PureCompetence.INSTANCE.clrGreen);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.PlayMsg);
			builder.setTimestamp(OffsetDateTime.now());
		}
		
		if(uri.startsWith("ytsearch: ")) {
			
			if(pos == -999) {
				
				queue.addTrackToTopOfQueue(playlist.getTracks().get(0), 0);
				
				controller.getGuild().getTextChannelById(id).editMessageById(idM, builder.build()).queue();
				return;
			} else if(pos >= 0 && !(pos < 0)) {
				
				queue.addTrackToTopOfQueue(playlist.getTracks().get(0), pos);
				
				controller.getGuild().getTextChannelById(id).editMessageById(idM, builder.build()).queue();
				return;
			} else {
			
				queue.addTrackToQueue(playlist.getTracks().get(0));
				
				controller.getGuild().getTextChannelById(id).editMessageById(idM, builder.build()).queue();
				return;
			}
			
		}
		
		controller.getGuild().getTextChannelById(id).editMessageById(idM, builder.build()).queue();
		
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
				.setDescription("Couldn't find a Video matching the search queue or link!").setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.PlayMsg)
				.setTimestamp(OffsetDateTime.now());
		
		controller.getGuild().getTextChannelById(id).editMessageById(idM, builder.build()).queue();
	}

	@Override
	public void loadFailed(FriendlyException exception) {
		EmbedBuilder builder = new EmbedBuilder().setColor(PureCompetence.INSTANCE.clrRed).setTitle("**Loading failed**")
				.setDescription("Couldn't load the Video due to an error! Please try again later!").setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.PlayMsg)
				.setTimestamp(OffsetDateTime.now());
		
		controller.getGuild().getTextChannelById(id).editMessageById(idM, builder.build()).queue();
	}

}
