package de.cake.commands.serverCommands.text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

import de.cake.PureCompetence;
import de.cake.LiteSQL.LiteSQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class SetupResetCommand {
	
	private long roleid;
	
	public void setupReset(Guild guild, TextChannel channel, String call) {
		
		StringBuilder sbf = new StringBuilder();
		EmbedBuilder builder = new EmbedBuilder();
		
		//Resetting the Administrator role and creating a new one, disables the requirement
		try{
			ResultSet set = LiteSQL.onQuery("SELECT * FROM adminrights WHERE guildid = " + guild.getIdLong());
			
			try {
				if(set.next()) {
					System.out.println("next admin");
					
					this.roleid = set.getLong("role");
					
					LiteSQL.onUpdate("UPDATE adminrights SET role = " + this.roleid + ", state = 0 WHERE guildid = " + guild.getIdLong());
				} else {
					System.out.println("else admin");
					this.roleid = guild.createRole().setName("PureCompetence Admin").setColor(0x32CD32).reason("Pure Competence Admin role, this is role is only needed if Server staff needs to set the bot up! This role is automatically given to Users with Administrator Previliges!").complete().getIdLong();
					
					LiteSQL.onUpdate("INSERT INTO adminrights(guildid, role, state) VALUES(" + guild.getIdLong() + ", " + this.roleid + ", 0)");
				}
				
				List<Member> members = guild.getMembers();
				
				for(int i = 0; i < members.size(); i++) {
					if(members.get(i).getPermissions().contains(Permission.ADMINISTRATOR)) {
						guild.addRoleToMember(members.get(i).getIdLong(), guild.getRoleById(this.roleid)).queue();
					}
					
				}
				
				set.close();
			} catch(SQLException ex) {
				sbf.append("Admin role could not be reset! \r");
			}
			
		} catch(NumberFormatException e){
			
		}
		
		//Reseting the join messages to the default channel, disables them
		try{
			ResultSet set = LiteSQL.onQuery("SELECT * FROM joinmsgchannels WHERE guildid = " + guild.getIdLong());
			
			try {
				if(set.next()) {
					LiteSQL.onUpdate("UPDATE joinmsgchannels SET channelid = " + guild.getDefaultChannel().getIdLong() + ", state = 0 WHERE guildid = " + guild.getIdLong());
				} else {
					LiteSQL.onUpdate("INSERT INTO joinmsgchannels(guildid, channelid, state) VALUES(" + guild.getIdLong() + ", " + guild.getDefaultChannel().getIdLong() + ", 0)");
				}
				
				set.close();
			} catch(SQLException ex) {
				sbf.append("Join message channel could not be reset! \r");
			}
			
		} catch(NumberFormatException e){
			
		}

		//Reseting the leave messages to the default channel, disables them
		try{
			ResultSet set = LiteSQL.onQuery("SELECT * FROM leavemsgchannels WHERE guildid = " + guild.getIdLong());
			
			try {
				if(set.next()) {
					LiteSQL.onUpdate("UPDATE leavemsgchannels SET channelid = " + guild.getDefaultChannel().getIdLong() + ", state = 0 WHERE guildid = " + guild.getIdLong());
				} else {
					LiteSQL.onUpdate("INSERT INTO leavemsgchannels(guildid, channelid, state) VALUES(" + guild.getIdLong() + ", " + guild.getDefaultChannel().getIdLong() + ", 0)");
				}
				
				set.close();
			} catch(SQLException ex) {
				sbf.append("Leave message channel could not be reset! \r");
			}
			
		} catch(NumberFormatException e){
			
		}
		
		//Reseting the update messages to the default channel, disables them
		try{
			ResultSet set = LiteSQL.onQuery("SELECT * FROM updatechannels WHERE guildid = " + guild.getIdLong());
			
			try {
				if(set.next()) {
					LiteSQL.onUpdate("UPDATE updatechannels SET channelid = " + guild.getDefaultChannel().getIdLong() + ", state = 0 WHERE guildid = " + guild.getIdLong());
				} else {
					LiteSQL.onUpdate("INSERT INTO updatechannels(guildid, channelid, state) VALUES(" + guild.getIdLong() + ", " + guild.getDefaultChannel().getIdLong() + ", 0)");
				}
				
				set.close();
			} catch(SQLException ex) {
				sbf.append("Update message channel could not be reset! \r");
			}
			
		} catch(NumberFormatException e){
			
		}
		
		//Reseting the default volume
		try{
			ResultSet set = LiteSQL.onQuery("SELECT * FROM musicvolume WHERE guildid = " + guild.getIdLong());
			
			try {
				if(set.next()) {
					LiteSQL.onUpdate("UPDATE musicvolume SET volume = 10 WHERE guildid = " + guild.getIdLong());
				
				} else {
					LiteSQL.onUpdate("INSERT INTO musicvolume(guildid, volume) VALUES(" + guild.getIdLong() + ", 10)");
				}
				
				set.close();
			} catch(SQLException ex) {
				sbf.append("Default volume could not be reset! \r");
			}
			
		} catch(NumberFormatException e){
			
		}
		
		//Reseting the Role you get when joining
		try{
			ResultSet set = LiteSQL.onQuery("SELECT * FROM joinrole WHERE guildid = " + guild.getIdLong());
			
			try {
				if(set.next()) {
					LiteSQL.onUpdate("UPDATE joinrole SET role = " + this.roleid + ", state = 0 WHERE guildid = " + guild.getIdLong());
				} else {
					LiteSQL.onUpdate("INSERT INTO joinrole(guildid, role, state) VALUES(" + guild.getIdLong() + ", " + this.roleid + ", 0)");
				}
				
				set.close();
			} catch(SQLException ex) {
				sbf.append("Joinrole role could not be reset! \r");
			}
			
		} catch(NumberFormatException e){
			
		}
		
		if(call.equals("joinlistener")) {
			return;
		}
		
		builder.setTitle("**Successfully reset the Setup**");
		
		builder.setDescription("You have successfully reset the Setup!");
		builder.addField("**Erros while reseting the Setup**", sbf.length() == 0 ? "0 Errors while reseting" : sbf + "", false);
		
		//If there was no error Embed will be green, else it's red
		if(sbf.length() == 0) builder.setColor(PureCompetence.INSTANCE.clrGreen);
		
		if(sbf.length() > 0) builder.setColor(PureCompetence.INSTANCE.clrRed);
		
		builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.HelpUI);
		builder.setTimestamp(OffsetDateTime.now());
		channel.sendMessage(builder.build()).queue();
	}
	
}
