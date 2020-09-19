package de.cake.commands.serverCommands.music;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import de.cake.PureCompetence;
import de.cake.commands.manage.LiteSQL;
import de.cake.commands.types.ServerCommand;
import de.cake.musicController.MusicController;
import de.cake.musicController.MusicUtil;
import de.cake.musicController.Queue;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class StopCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		System.out.println("-------------------------- \r" + "[LOG] | Command | " + getClass() + "\r"
				+ m  + "\r" + m.getPermissions()
				+ "\r--------------------------");
		
		String[] args = message.getContentDisplay().split(" ");
		EmbedBuilder builder = new EmbedBuilder();
		EmbedBuilder permissions = new EmbedBuilder().setTitle("Insufficient Permission to perform this command!").setDescription("Please add `Pure Music` "
				+ "to your Server to be able to use the music feature or ask a Server Admin to deactivate the Permission check.")
				.setColor(PureCompetence.INSTANCE.clrRed).setFooter(PureCompetence.INSTANCE.pwrdBy).setTimestamp(OffsetDateTime.now());
		
		ResultSet check = LiteSQL.onQuery("SELECT * FROM adminrights WHERE guildid = " + channel.getGuild().getIdLong());
		
		try {
			if(check.next()) {
				long roleid = check.getLong("role");
				int music = check.getInt("music");
				
				//Returns if user does not have enough permissions
				if(!(m.getRoles().contains(channel.getGuild().getRoleById(roleid))) && music == 0) {
					if(m.getIdLong() != 255313111391666176l) {
						channel.sendMessage(permissions.build()).queue();
						
						return;
					}
					
				}
				
			}
			
			check.close();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		GuildVoiceState state;
		if((state = m.getVoiceState()) != null) {
			VoiceChannel vc;
			
			try {
				if(args[1].startsWith("<")) {
					message.delete().complete();
					String arg1 = args[1].substring(1, 19);
					long cGuild = Long.parseLong(arg1);
					
					MusicController controller = PureCompetence.INSTANCE.playerManager.getController(cGuild);
					AudioManager manager = PureCompetence.INSTANCE.shardMan.getGuildById(cGuild).getAudioManager();
					AudioPlayer player = controller.getPlayer();
					Queue queue = controller.getQueue();
					MusicUtil.updateChannel(channel);
					
					player.stopTrack();
					queue.clear();
					manager.closeAudioConnection();
					
				}
				
			} catch(ArrayIndexOutOfBoundsException e){
				
			}
			
			if((vc = state.getChannel()) != null) {
				MusicController controller = PureCompetence.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
				AudioManager manager = vc.getGuild().getAudioManager();
				AudioPlayer player = controller.getPlayer();
				Queue queue = controller.getQueue();
				MusicUtil.updateChannel(channel);
				
				player.stopTrack();
				queue.clear();
				
				manager.closeAudioConnection();
				builder.setTitle("**Stopped the Track and disconnected**");
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
