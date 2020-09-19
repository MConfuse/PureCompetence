package de.cake.musicController;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import de.cake.PureCompetence;
import net.dv8tion.jda.api.entities.Guild;

public class MusicController {
	
	private Guild guild;
	private AudioPlayer player;
	private Queue queue;
	
	public MusicController(Guild guild) {
		this.guild = guild;
		this.player = PureCompetence.INSTANCE.audioPlayerManager.createPlayer();
		this.queue = new Queue(this);
		
		this.guild.getAudioManager().setSendingHandler(new AudioPlayerSentHandler(player));
		this.player.addListener(new TrackScheduler());
	}
	
	public Guild getGuild() {
		return guild;
	}
	
	public AudioPlayer getPlayer() {
		return player;
	}
	
	public Queue getQueue() {
		return queue;
	}
	
}
