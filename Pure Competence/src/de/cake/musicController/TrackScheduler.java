package de.cake.musicController;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import de.cake.PureCompetence;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.managers.AudioManager;

public class TrackScheduler extends AudioEventAdapter {
	
	EmbedBuilder builder = new EmbedBuilder();
	
	@Override
	public void onPlayerPause(AudioPlayer player) {
		
	}
	
	@Override
	public void onPlayerResume(AudioPlayer player) {
		
	}
	
	@Override
	public void onTrackStart(AudioPlayer player, AudioTrack track) {
		
	}
	
	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		long guildid = PureCompetence.INSTANCE.playerManager.getGuildByPlayerHash(player.hashCode());
		Guild guild = PureCompetence.INSTANCE.shardMan.getGuildById(guildid);
		MusicController controller = PureCompetence.INSTANCE.playerManager.getController(guildid);
		Queue queue = controller.getQueue();
		
		if(endReason.mayStartNext) {
			if(queue.next()) {
				return;
			}
			
		}
		
		//System.out.println("Wenn ich hier angekommen bin disconnected dieser dreck hier REEEEEEEEEEEEEEEEEEEEEEEE");
		AudioManager manager = guild.getAudioManager();
		player.stopTrack();
		manager.closeAudioConnection();
	}

}
