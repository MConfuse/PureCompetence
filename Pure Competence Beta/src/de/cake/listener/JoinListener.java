package de.cake.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import de.cake.PureCompetence;
import de.cake.LiteSQL.LiteSQL;
import de.cake.commands.serverCommands.text.SetupResetCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinListener extends ListenerAdapter {
	
	/**
	 * Called whenever a new Member Joins a Guild, if the Setup allows it the Bot will give the User the specified role and 
	 * send a Message into the Join message Channel.
	 * 
	 * @see text#SetupCommand
	 * @see LiteSQL#SQLManager
	 */
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		Member m = event.getMember();
		TextChannel channel;
		
		ResultSet set = LiteSQL.onQuery("SELECT * FROM joinmsgchannels WHERE guildid = " + event.getGuild().getIdLong());
		 
		try {
			if(set.next()) {
				long channelid = set.getLong("channelid");
				int state = set.getInt("state");
				
				if(state == 0) return;
				
				if(channelid <= 0) {
					System.out.println("Error!!!111!!!!");
					return;
				}
				
				Guild guild = PureCompetence.INSTANCE.shardMan.getGuildById(event.getGuild().getIdLong());
				guild.getTextChannelById(channelid).sendMessage("Welcome to the **" + event.getGuild().getName() + "** Discord " + m.getAsMention()).queue();
			}
			
			set.close();
		} catch(SQLException e) {
			
			e.printStackTrace();
			if((channel = event.getGuild().getSystemChannel()) != null) {
				 channel.sendMessage("Welcome to the **" + event.getGuild().getName() + "** Discord " + m.getAsMention()).queue();
			}
			
		}
		
		//Automatically adds the autojoin role to the User
		try{
			ResultSet joinrole = LiteSQL.onQuery("SELECT * FROM joinrole WHERE guildid = " + event.getGuild().getIdLong());
			
			try {
				long role = joinrole.getLong("role");
				int state = joinrole.getInt("state");
				
				if(state == 0) return;
				
				event.getGuild().addRoleToMember(event.getUser().getIdLong(), event.getGuild().getRoleById(role)).queue();
				
				set.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
			
		} catch(NumberFormatException e){
			
		}
		
	}
	
	/**
	 * Called whenever the Bot was added to a Guild, will create a new Database entry for the Guild by using the {@link de.cake.commands.serverCommands.text.SetupResetCommand}.
	 * Also sends a introduction message.
	 * 
	 * @see text#SetupResetCommand
	 * @see LiteSQL#SQLManager
	 */
	public void onGuildJoin(GuildJoinEvent event) {
		EmbedBuilder builder = new EmbedBuilder();
		Guild guild = event.getGuild();

		builder.setTitle(
				"Hi, I'm Pure Competence Beta and buggy because I'm testing features for the real Pure Competence! I do not support every feature of the normal PureCompetence yet.");
		builder.setColor(PureCompetence.INSTANCE.clrBlue);
		builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
		builder.setTimestamp(OffsetDateTime.now());
		guild.getDefaultChannel().sendMessage(builder.build()).queue();
		
		new SetupResetCommand().setupReset(guild, guild.getDefaultChannel(), "joinlistener");
	}

	public void onGuildLeave(GuildLeaveEvent event) {

	}

	/**
	 * Called whenever a Member leaves the Guild, if the Setup allows it the Bot will send a Message into the Leave message Channel.
	 * 
	 * @see text#SetupCommand
	 * @see LiteSQL#SQLManager
	 */
	public void onGuildMemberRemoveEvent(GuildMemberRemoveEvent event) {
		Member m = event.getMember();
		 TextChannel channel;
		 
		 ResultSet set = LiteSQL.onQuery("SELECT * FROM leavemsgchannels WHERE guildid = " + event.getGuild().getIdLong());
			
		try {
			if(set.next()) {
				long channelid = set.getLong("channelid");
				int state = set.getInt("state");
				
				if(state == 0) return;
				
				if(channelid <= 0) {
					System.out.println("Error!!!111!!!!");
					return;
				}
				
				Guild guild = PureCompetence.INSTANCE.shardMan.getGuildById(event.getGuild().getIdLong());
				guild.getTextChannelById(channelid).sendMessage(m.getUser().getAsTag() + " (" + m.getAsMention() + ") just left the Discord Server!").queue();
			}
			
			set.close();
		} catch(SQLException e) {
			
			e.printStackTrace();
			if((channel = event.getGuild().getSystemChannel()) != null) {
				 channel.sendMessage(m.getUser().getAsTag() + " (" + m.getAsMention() + ") just left the Discord Server!").queue();
			}
			
		}
		
	}

}