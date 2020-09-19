package de.cake.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

import de.cake.PureCompetence;
import de.cake.commands.manage.LiteSQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinListener extends ListenerAdapter {
	
	public long roleid;
	
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
	
	public void onGuildJoin(GuildJoinEvent event) {
		EmbedBuilder builder = new EmbedBuilder();
		Guild guild = event.getGuild();
		
		builder.setTitle("Hi, I'm Pure Competence and I'm competent =)", "https://discord.gg/VtfpcGH");
		builder.addField("Short Description", "I am a universal Bot who can (If I'm online) do pretty "
				+ "much everything the heart desires. From posting (A small amount) of Memes to playing music I do everything!", false);
		builder.addField("Version", "My current build is: " + PureCompetence.INSTANCE.version, false);
		builder.addField("Online times", "Currently I'm hosted on my Devs PC, thats why I'm not online "
				+ "all the time, but that could change in the futur!", false);
		builder.addField("Getting started", "If you want to use me, just type **$help**. \r"
				+ "**$** is my prefix used for every command! \r Use **$setup** for help setting up the bot!", false);
		builder.setThumbnail("https://cdn.discordapp.com/attachments/564208162425798666/695368271465283694/WGPlayers.png");
		builder.setColor(PureCompetence.INSTANCE.clrBlue);
		builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
		builder.setTimestamp(OffsetDateTime.now());
		guild.getDefaultChannel().sendMessage(builder.build()).queue();
		
		//Creating the Role for Administrator permissions
		try{
			ResultSet set = LiteSQL.onQuery("SELECT * FROM adminrights WHERE guildid = " + event.getGuild().getIdLong());
			
			try {
				if(set.next()) {
					//LiteSQL.onUpdate("UPDATE adminrights SET role = " + role + " WHERE guildid = " + event.getGuild().getIdLong());
					this.roleid = set.getLong("role");
				} else {
					this.roleid = event.getGuild().createRole().setName("PureCompetence Admin").setColor(0x12b3db).reason("Pure Competence Admin role, this is role is only needed if Server staff needs to set the bot up! This role is automatically given to Users with Administrator Previliges! ").complete().getIdLong();
					
					LiteSQL.onUpdate("INSERT INTO adminrights(guildid, role, music) VALUES(" + event.getGuild().getIdLong() + ", " + this.roleid + "0)");
				}
				
				List<Member> members = event.getGuild().getMembers();
				
				for(int i = 0; i < members.size(); i++) {
					if(members.get(i).getPermissions().contains(Permission.ADMINISTRATOR)) {
						event.getGuild().addRoleToMember(members.get(i).getIdLong(), event.getGuild().getRoleById(this.roleid)).queue();
					}
					
				}
				
				set.close();
			} catch(SQLException ex) {
				
			}
			
		} catch(NumberFormatException e){
			
		}
		
		//Creating or updating the Join Channel, if new to the server it disables the messages and sets the channel to the system channel, else it will only disable the messages
		try{
			ResultSet set = LiteSQL.onQuery("SELECT * FROM joinmsgchannels WHERE guildid = " + event.getGuild().getIdLong());
			
			try {
				if(set.next()) {
					LiteSQL.onUpdate("UPDATE joinmsgchannels SET state = 0 WHERE guildid = " + event.getGuild().getIdLong());
				} else {
					LiteSQL.onUpdate("INSERT INTO joinmsgchannels(guildid, channelid, state) VALUES(" + event.getGuild().getIdLong() + ", " + event.getGuild().getDefaultChannel().getIdLong() + ", 0)");
				}
				
				set.close();
			} catch(SQLException ex) {
				
			}
			
		} catch(NumberFormatException e){
			
		}
		
		//Creating or updating the Leave Channel, if new to the server it disables the messages and sets the channel to the system channel, else it will only disable the messages
		try{
			ResultSet set = LiteSQL.onQuery("SELECT * FROM leavemsgchannels WHERE guildid = " + event.getGuild().getIdLong());
			
			try {
				if(set.next()) {
					LiteSQL.onUpdate("UPDATE leavemsgchannels SET state = 0 WHERE guildid = " + event.getGuild().getIdLong());
				} else {
					LiteSQL.onUpdate("INSERT INTO leavemsgchannels(guildid, channelid, state) VALUES(" + event.getGuild().getIdLong() + ", " + event.getGuild().getDefaultChannel().getIdLong() + ", 0)");
				}
				
				set.close();
			} catch(SQLException ex) {
				
			}
			
		} catch(NumberFormatException e){
			
		}
		
		//Creating or updating the Update Channel, if new to the server it disables the messages and sets the channel to the system channel, else it will only disable the messages
		try{
			ResultSet set = LiteSQL.onQuery("SELECT * FROM updatechannels WHERE guildid = " + event.getGuild().getIdLong());
			
			try {
				if(set.next()) {
					LiteSQL.onUpdate("UPDATE updatechannels SET state = 0 WHERE guildid = " + event.getGuild().getIdLong());
				} else {
					LiteSQL.onUpdate("INSERT INTO updatechannels(guildid, channelid, state) VALUES(" + event.getGuild().getIdLong() + ", " + event.getGuild().getDefaultChannel().getIdLong() + ", 0)");
				}
				
				set.close();
			} catch(SQLException ex) {
				
			}
			
		} catch(NumberFormatException e){
			
		}
		
		//Creating or updating the Music Volume, it will set the default volume to 10
		try{
			ResultSet set = LiteSQL.onQuery("SELECT * FROM musicvolume WHERE guildid = " + event.getGuild().getIdLong());
			
			try {
				if(set.next()) {
					LiteSQL.onUpdate("UPDATE musicvolume SET volume = 10 WHERE guildid = " + event.getGuild().getIdLong());
				
				} else {
					LiteSQL.onUpdate("INSERT INTO musicvolume(guildid, volume) VALUES(" + event.getGuild().getIdLong() + ", 10)");
				}
				
				set.close();
			} catch(SQLException ex) {
				
			}
			
		} catch(NumberFormatException e){
			
		}
		
		//Creating the Role for Administrator permissions
		try{
			ResultSet set = LiteSQL.onQuery("SELECT * FROM joinrole WHERE guildid = " + event.getGuild().getIdLong());
			
			try {
				if(set.next()) {
					//LiteSQL.onUpdate("UPDATE adminrights SET role = " + role + " WHERE guildid = " + event.getGuild().getIdLong());
				} else {
					LiteSQL.onUpdate("INSERT INTO joinrole(guildid, role) VALUES(" + event.getGuild().getIdLong() + ", " + this.roleid + ")");
				}
				
				set.close();
			} catch(SQLException ex) {
				
			}
			
		} catch(NumberFormatException e){
			
		}
		
		}
	
	public void onGuildLeave(GuildLeaveEvent event) {
		
		System.out.println("Left a guild");
		
		//Deleting the Role for Administrator permissions
//		try{
//			ResultSet set = LiteSQL.onQuery("SELECT * FROM adminrights WHERE guildid = " + event.getGuild().getIdLong());
//			
//			try {
//				
//				if(set.next()) {
//					LiteSQL.onUpdate("INSERT INTO adminrights(guildid, role) VALUES(" + event.getGuild().getIdLong() + ", 0)");
//				} else {
//					LiteSQL.onUpdate("INSERT INTO adminrights(guildid, role) VALUES(" + event.getGuild().getIdLong() + ", 0)");
//				}
//				
//				set.close();
//			} catch(SQLException ex) {
//				ex.printStackTrace();
//			}
//			
//		} catch(NumberFormatException | HierarchyException e){
//			e.printStackTrace();
//		}717416962073821327
		
	}
	
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