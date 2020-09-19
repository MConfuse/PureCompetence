package de.cake.musicController;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.cake.PureCompetence;
import de.cake.commands.manage.LiteSQL;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class OpenVoiceConnection {
	
	Long guildID;
	VoiceChannel vc;
	
	public OpenVoiceConnection(Long guildID, VoiceChannel vc) {
		this.guildID = guildID;
		this.vc = vc;
	}
	
	public void OpenVoice() {
		ResultSet set = LiteSQL.onQuery("SELECT * FROM musicvolume WHERE guildid = " + this.guildID);
		
		try {
			if(set.next()) {
				int vol = set.getInt("volume");
				
				PureCompetence.INSTANCE.shardMan.getGuildById(guildID).getAudioManager().openAudioConnection(vc);
				PureCompetence.INSTANCE.playerManager.getController(guildID).getPlayer().setVolume(vol);
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
	}

}
