package de.cake.commands.serverCommands.music;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import de.cake.PureCompetence;
import de.cake.commands.manage.LiteSQL;
import de.cake.commands.types.ServerCommand;
import de.cake.musicController.AudioLoadResult;
import de.cake.musicController.MusicController;
import de.cake.musicController.MusicUtil;
import de.cake.musicController.OpenVoiceConnection;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand implements ServerCommand {

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
		
		if(args.length > 1) {
			GuildVoiceState state;
			if((state = m.getVoiceState()) != null) {
				VoiceChannel vc;
				
				//cmd	Guild					VC
				//$play <374507865521520651> <374507865521520651> https
				if(args[1].startsWith("<") && args[2].startsWith("<")) {
					message.delete().complete();
					String arg1 = args[1].substring(1, 19);
					long cGuild = Long.parseLong(arg1);
					String cVC = args[2].substring(1, 19);
					System.out.println(arg1);
					System.out.println(cVC);
					
					VoiceChannel cv = PureCompetence.INSTANCE.shardMan.getGuildById(cGuild).getVoiceChannelById(cVC);
					MusicController controller = PureCompetence.INSTANCE.playerManager.getController(cGuild);
					AudioPlayerManager apm = PureCompetence.INSTANCE.audioPlayerManager;
					AudioManager manager = PureCompetence.INSTANCE.shardMan.getGuildById(cGuild).getAudioManager();
					
					manager.openAudioConnection(cv);
					
					MusicUtil.updateChannel(channel);
					
					StringBuilder strBuilder = new StringBuilder();
					
					for(int i = 3; i < args.length; i++) strBuilder.append(args[i] + " ");
					
					String url = strBuilder.toString().trim();
					
					if(!url.startsWith("http")) {
						url = "ytsearch: " + url;
					}
					
					apm.loadItem(url, new AudioLoadResult(controller, url, channel.getIdLong(), channel.sendMessage(builder.build()).complete().getIdLong(), -1, channel, state.getChannel()));
					
				} else if(args[1].equalsIgnoreCase("next") && (vc = state.getChannel()) != null) {
					
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
					OpenVoiceConnection ovc = new OpenVoiceConnection(channel.getGuild().getIdLong(), vc);
					ovc.OpenVoice();
					
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
