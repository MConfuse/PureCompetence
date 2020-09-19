package de.cake.commands.serverCommands.text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import de.cake.PureCompetence;
import de.cake.commands.manage.LiteSQL;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class SetupCommand implements ServerCommand {
	
	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		
		EmbedBuilder builder = new EmbedBuilder();
		EmbedBuilder permissions = new EmbedBuilder().setTitle("Insufficient Permission to perform this command!").setColor(PureCompetence.INSTANCE.clrRed).setFooter(PureCompetence.INSTANCE.pwrdBy).setTimestamp(OffsetDateTime.now());
		String[] args = message.getContentDisplay().split(" ");	//Retrieves the contents of the message that are visible
		
		//System.out.println("Channel " + message.getMentionedChannels().size());
		//System.out.println("Role " + message.getMentionedRoles().size());
		//System.out.println(args.length);
		
		ResultSet check = LiteSQL.onQuery("SELECT * FROM adminrights WHERE guildid = " + channel.getGuild().getIdLong());
		
		try {
			if(check.next()) {
				long roleid = check.getLong("role");
				
				//Returns if user does not have enough permissions
				if(!(m.getRoles().contains(channel.getGuild().getRoleById(roleid)))) {
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
		
		try {
			if(args.length == 1) {
				
				builder.setTitle("**Setup Command**");
				builder.addField("Command usage", "", false);
				builder.addField("**$setup**", "Displays the details of the Setup Command and their usage. Administrator permissions are required to use this command.", false);
				builder.addField("**$setup join #channel**", "Sets the channel where the join messages are displayed, if no channel has ben specified the bot will send the message into the Default Channel until it has been disabled.", false);
				builder.addField("**$setup join [enable/disable]**", "Enables/disables the join messages on the Server, the channel they were sent in will be saved.", false);
				builder.addField("**$setup leave #channel**", "Sets the channel where the leave messages are displayed, if no channel has ben specified the bot will send the message into the Default Channel until it has been disabled.", false);
				builder.addField("**$setup leave [enable/disable]**", "Enables/disables the leave messages on the Server, the channel they were sent in will be saved.", false);
				channel.sendMessage(builder.build()).queue();
				
				return;
			//		---Join Commands---
				
			//channel format <#716071240607203368>
			} else if(args[1].equalsIgnoreCase("join") && message.getMentionedChannels().size() == 1) {
				
				try{
					long channelID = message.getMentionedChannels().get(0).getIdLong();
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM joinmsgchannels WHERE guildid = " + channel.getGuild().getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE joinmsgchannels SET channelid = " + channelID + " WHERE guildid = " + channel.getGuild().getIdLong());
							LiteSQL.onUpdate("UPDATE joinmsgchannels SET state = 1 WHERE guildid = " + channel.getGuild().getIdLong());
							
							builder.setTitle("Join message channel changed!");
							builder.setDescription("The new join message channel is " + args[2]);
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("INSERT INTO joinmsgchannels(guildid, channelid, state) VALUES(" + channel.getGuild().getIdLong() + "," + channelID + ", 1)");
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						
					}
					
				} catch(NumberFormatException e){
					
				}
				
				//checks if args is long enough
			} else if(args.length > 2) {
				
				//enable/true notifications
				if(args[1].equalsIgnoreCase("join") && args[2].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("join") && args[2].equalsIgnoreCase("true")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM joinmsgchannels WHERE guildid = " + channel.getGuild().getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE joinmsgchannels SET state = 1 WHERE guildid = " + channel.getGuild().getIdLong());
							
							builder.setTitle("Join message notifications enabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("UPDATE joinmsgchannels SET state = 1 WHERE guildid = " + channel.getGuild().getIdLong());
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						
					}
					
					//disable/false notifications
				} else if(args[1].equalsIgnoreCase("join") && args[2].equalsIgnoreCase("disable") || args[1].equalsIgnoreCase("join") && args[2].equalsIgnoreCase("false")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM joinmsgchannels WHERE guildid = " + channel.getGuild().getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE joinmsgchannels SET state = 0 WHERE guildid = " + channel.getGuild().getIdLong());
							
							builder.setTitle("Join message notifications disabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("UPDATE joinmsgchannels SET state = 0 WHERE guildid = " + channel.getGuild().getIdLong());
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						
					}
					
				}
				
			}
			
			if(args[1].equalsIgnoreCase("join")) {
				
				builder.setTitle("Please use ***$setup join #channel***!");
				builder.setColor(PureCompetence.INSTANCE.clrRed);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builder.setTimestamp(OffsetDateTime.now());
				channel.sendMessage(builder.build()).queue();
				
				return;
			}
			
			//		---Leave Commands---	
				//channel format <#716071240607203368>
			if(args[1].equalsIgnoreCase("leave") && message.getMentionedChannels().size() == 1) {
				
				try{
					long channelID = message.getMentionedChannels().get(0).getIdLong();
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM leavemsgchannels WHERE guildid = " + channel.getGuild().getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE leavemsgchannels SET channelid = " + channelID + " WHERE guildid = " + channel.getGuild().getIdLong());
							LiteSQL.onUpdate("UPDATE leavemsgchannels SET state = 1 WHERE guildid = " + channel.getGuild().getIdLong());
						
							builder.setTitle("Leave message channel changed!");
							builder.setDescription("The new leave message channel is " + args[2]);
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
							
						} else {
							LiteSQL.onUpdate("INSERT INTO leavemsgchannels(guildid, channelid, state) VALUES(" + channel.getGuild().getIdLong()+ "," + channelID + ", 1)");
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						
					}
					
				} catch(NumberFormatException e){
					
				}
				
				//Checks if args is long enough
			} else if(args.length > 2) {
				
				//enable/true notifications
				if(args[1].equalsIgnoreCase("leave") && args[2].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("leave") && args[2].equalsIgnoreCase("true")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM leavemsgchannels WHERE guildid = " + channel.getGuild().getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE leavemsgchannels SET state = 1 WHERE guildid = " + channel.getGuild().getIdLong());
							
							builder.setTitle("Leave message notifications enabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("UPDATE leavemsgchannels SET state = 1 WHERE guildid = " + channel.getGuild().getIdLong());
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						
					}
					
					//disable/false notifications
				} else if(args[1].equalsIgnoreCase("leave") && args[2].equalsIgnoreCase("disable") || args[1].equalsIgnoreCase("leave") && args[2].equalsIgnoreCase("false")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM leavemsgchannels WHERE guildid = " + channel.getGuild().getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE leavemsgchannels SET state = 0 WHERE guildid = " + channel.getGuild().getIdLong());
							
							builder.setTitle("Leave message notifications disabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("UPDATE leavemsgchannels SET state = 0 WHERE guildid = " + channel.getGuild().getIdLong());
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						
					}
					
				}
				
			}
			
			if(args[1].equalsIgnoreCase("leave")) {
				
				builder.setTitle("Please use ***$setup leave #channel***!");
				builder.setColor(PureCompetence.INSTANCE.clrRed);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builder.setTimestamp(OffsetDateTime.now());
				channel.sendMessage(builder.build()).queue();
				
				return;
			}
			
			//		---Update Channels---
			
			//channel format <#716071240607203368>
			if(args[1].equalsIgnoreCase("update") && message.getMentionedChannels().size() == 1) {
				
				try{
					long channelID = message.getMentionedChannels().get(0).getIdLong();
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM updatechannels WHERE guildid = " + channel.getGuild().getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE updatechannels SET channelid = " + channelID + " WHERE guildid = " + channel.getGuild().getIdLong());
							LiteSQL.onUpdate("UPDATE updatechannels SET state = 1 WHERE guildid = " + channel.getGuild().getIdLong());
							
							builder.setTitle("Update channel changed!");
							builder.setDescription("The new update channel is " + args[2]);
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("INSERT INTO updatechannels(guildid, channelid, state) VALUES(" + channel.getGuild().getIdLong() + "," + channelID + ", 1)");
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						
					}
					
				} catch(NumberFormatException e){
					
				}
				
				//Checks if args are long enough
			} else if(args.length > 2) {
				
				//enable/true notifications
				if(args[1].equalsIgnoreCase("update") && args[2].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("update") && args[2].equalsIgnoreCase("true")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM updatechannels WHERE guildid = " + channel.getGuild().getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE updatechannels SET state = 1 WHERE guildid = " + channel.getGuild().getIdLong());
							
							builder.setTitle("Update messages enabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("UPDATE updatechannels SET state = 1 WHERE guildid = " + channel.getGuild().getIdLong());
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						
					}
					
					//disable/false notifications
				} else if(args[1].equalsIgnoreCase("update") && args[2].equalsIgnoreCase("disable") || args[1].equalsIgnoreCase("update") && args[2].equalsIgnoreCase("false")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM updatechannels WHERE guildid = " + channel.getGuild().getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE updatechannels SET state = 0 WHERE guildid = " + channel.getGuild().getIdLong());
							
							builder.setTitle("Update messages disabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("UPDATE updatechannels SET state = 0 WHERE guildid = " + channel.getGuild().getIdLong());
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						
					}
					
					//If update was true, but no channels were mentioned
				}
				
			} else if(args[1].equalsIgnoreCase("update")) {
				
				builder.setTitle("Please use ***$setup update #channel***!");
				builder.setColor(PureCompetence.INSTANCE.clrRed);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builder.setTimestamp(OffsetDateTime.now());
				channel.sendMessage(builder.build()).queue();
				
				return;
			}
			
			//		---Administrator rights command---
			if(args[1].equalsIgnoreCase("admin") && message.getMentionedRoles().size() == 1) {
				
				try{
					long role = message.getMentionedRoles().get(0).getIdLong();
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM adminrights WHERE guildid = " + channel.getGuild().getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE adminrights SET role = " + role + " WHERE guildid = " + channel.getGuild().getIdLong());
							
							builder.setTitle("Admin role changed!");
							builder.setDescription("The new Admin role is <@&" + role + ">");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
							
						} else {
							LiteSQL.onUpdate("INSERT INTO adminrights(guildid, role, music) VALUES(" + channel.getGuild().getIdLong() + ", " + role + "0)");
							
							builder.setTitle("Admin role changed!");
							builder.setDescription("The new Admin role is <@&" + role + ">");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						
					}
					
				} catch(NumberFormatException e) {
					
				}
				
			} else if(args.length > 2) {
				
				//enable/true notifications
				if(args[1].equalsIgnoreCase("admin") && args[2].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("admin") && args[2].equalsIgnoreCase("true")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM adminrights WHERE guildid = " + channel.getGuild().getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE adminrights SET music = 0 WHERE guildid = " + channel.getGuild().getIdLong());
							
							builder.setTitle("Permission checks for music commands are now: Enabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("UPDATE adminrights SET music = 0 WHERE guildid = " + channel.getGuild().getIdLong());
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						
					}
					
					//disable/false notifications
				} else if(args[1].equalsIgnoreCase("admin") && args[2].equalsIgnoreCase("disable") || args[1].equalsIgnoreCase("admin") && args[2].equalsIgnoreCase("false")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM adminrights WHERE guildid = " + channel.getGuild().getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE adminrights SET music = 1 WHERE guildid = " + channel.getGuild().getIdLong());
							
							builder.setTitle("Permission checks for music commands are now: Disabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("UPDATE adminrights SET music = 1 WHERE guildid = " + channel.getGuild().getIdLong());
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						
					}
					
				}
					
			}
			
			if(args[1].equalsIgnoreCase("admin")) {
				
				builder.setTitle("Please use ***$setup admin @role***!");
				builder.setColor(PureCompetence.INSTANCE.clrRed);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builder.setTimestamp(OffsetDateTime.now());
				channel.sendMessage(builder.build()).queue();
				
				return;
			}
			
//			---Joinrole command---
				if(args[1].equalsIgnoreCase("joinrole") && message.getMentionedRoles().size() == 1) {
					
					long role = message.getMentionedRoles().get(0).getIdLong();
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM joinrole WHERE guildid = " + channel.getGuild().getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE joinrole SET role = " + role + " WHERE guildid = " + channel.getGuild().getIdLong());
							
							builder.setTitle("Join role changed!");
							builder.setDescription("The new Join role is <@&" + role + ">");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
							
						} else {
							LiteSQL.onUpdate("INSERT INTO joinrole(guildid, role, state) VALUES(" + channel.getGuild().getIdLong() + ", " + role + ", 1)");
							
							builder.setTitle("Join role changed!");
							builder.setDescription("The new Join role is <@&" + role + ">");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						}
						
						set.close();
					
						return;
					} catch(SQLException ex) {
						ex.printStackTrace();
					}
					
				} else if(args.length > 2) {
					
					//enable/true notifications
					if(args[1].equalsIgnoreCase("joinrole") && args[2].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("joinrole") && args[2].equalsIgnoreCase("true")) {
						
						ResultSet set = LiteSQL.onQuery("SELECT * FROM joinrole WHERE guildid = " + channel.getGuild().getIdLong());
						
						try {
							if(set.next()) {
								LiteSQL.onUpdate("UPDATE joinrole SET state = 1 WHERE guildid = " + channel.getGuild().getIdLong());
								
								builder.setTitle("Roles on join enabled!");
								builder.setColor(PureCompetence.INSTANCE.clrGreen);
								builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
								builder.setTimestamp(OffsetDateTime.now());
								channel.sendMessage(builder.build()).queue();
							
							} else {
								LiteSQL.onUpdate("UPDATE joinrole SET state = 1 WHERE guildid = " + channel.getGuild().getIdLong());
							}
							
							set.close();
							
							return;
							
						} catch(SQLException ex) {
							
						}
						
						//disable/false notifications
					} else if(args[1].equalsIgnoreCase("joinrole") && args[2].equalsIgnoreCase("disable") || args[1].equalsIgnoreCase("joinrole") && args[2].equalsIgnoreCase("false")) {
						
						ResultSet set = LiteSQL.onQuery("SELECT * FROM joinrole WHERE guildid = " + channel.getGuild().getIdLong());
						
						try {
							if(set.next()) {
								LiteSQL.onUpdate("UPDATE joinrole SET state = 0 WHERE guildid = " + channel.getGuild().getIdLong());
								
								builder.setTitle("Roles on join disabled!");
								builder.setColor(PureCompetence.INSTANCE.clrGreen);
								builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
								builder.setTimestamp(OffsetDateTime.now());
								channel.sendMessage(builder.build()).queue();
							
							} else {
								LiteSQL.onUpdate("UPDATE joinrole SET state = 0 WHERE guildid = " + channel.getGuild().getIdLong());
							}
							
							set.close();
							
							return;
							
						} catch(SQLException ex) {
							
						}
						
					}
						
				}
				//If update was true, but no role was mentioned
				if(args[1].equalsIgnoreCase("joinrole")) {
					
					builder.setTitle("Please use ***$setup joinrole @role***!");
					builder.setColor(PureCompetence.INSTANCE.clrRed);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
					
					return;
				}
			
			
			//Info Command
			if(args[1].equalsIgnoreCase("info")) {
				
				try {
					//		---Opens the table "joinmsgchannels"---
					ResultSet setjoin = LiteSQL.onQuery("SELECT * FROM joinmsgchannels WHERE guildid = " + channel.getGuild().getIdLong());
					
					int jstate = setjoin.getInt("state");
					long joinChannel = setjoin.getLong("channelid");
					
					builder.setTitle("Information about PureCompetence's setup on the server");
					
					//Returns the Channel and state
					builder.addField("Setup \"Join messages\"", "The currently selected channel for the join messages is: " + channel.getGuild().getTextChannelById(joinChannel).getAsMention() + "\r"
							+ (jstate == 0 ? "Currently the join messages are: Disabled :x:" : "Currently the join messages are: Enabled :white_check_mark:"), false);
					//Closes the join result so it can open another result
					setjoin.close();
					
					//		---Opens the table "leavemsgchannels"---
					ResultSet setleave = LiteSQL.onQuery("SELECT * FROM leavemsgchannels WHERE guildid = " + channel.getGuild().getIdLong());
					
					int lstate = setleave.getInt("state");
					long leaveChannel = setleave.getLong("channelid");
					
					//Returns the Channel and state
					builder.addField("Setup \"Leave messages\"", "The currently selected channel for the leave messages is: " + channel.getGuild().getTextChannelById(leaveChannel).getAsMention() + "\r"
							+ (lstate == 0 ? "Currently the leave messages are: Disabled :x:" : "Currently the leave messages are: Enabled :white_check_mark:"), false);
					//Closes the join result so it can open another result
					setleave.close();
					
					//		---Opens the table "leavemsgchannels"---
					ResultSet setupdate = LiteSQL.onQuery("SELECT * FROM updatechannels WHERE guildid = " + channel.getGuild().getIdLong());
					
					int ustate = setupdate.getInt("state");
					long updateChannel = setupdate.getLong("channelid");
					
					//Returns the Channel and state
					builder.addField("Setup \"Update messages\"", "The currently selected channel for the leave messages is: " + channel.getGuild().getTextChannelById(updateChannel).getAsMention() + "\r"
							+ (ustate == 0 ? "Currently the leave messages are: Disabled :x:" : "Currently the leave messages are: Enabled :white_check_mark:"), false);
					//Closes the join result so it can open another result
					setupdate.close();
					
					//		---Opens the table "leavemsgchannels"---
					ResultSet setvol = LiteSQL.onQuery("SELECT * FROM musicvolume WHERE guildid = " + channel.getGuild().getIdLong());
					
					int volume = setvol.getInt("volume");
					
					//Returns the Channel and state
					builder.addField("Setup \"Default volume\"", "The currently selected default volume is: **" + volume + "**", false);
					//Closes the join result so it can open another result
					setvol.close();
					
					//		---Opens the table "adminrights"---
					ResultSet setadmin = LiteSQL.onQuery("SELECT * FROM adminrights WHERE guildid = " + channel.getGuild().getIdLong());
					
					long admin = setadmin.getLong("role");
					int music = setadmin.getInt("music");
					
					//Returns the Role
					builder.addField("Setup \"Admin role\"", "The currently selected role is: <@&" + admin + "> \r"
							+ (music == 0 ? "Music commands require Admin role: Enabled :white_check_mark:" : "Music commands require Admin role: Disabled :x:"), false);
					//Closes the Administrator result so it can open another result
					setadmin.close();
					
					//		---Opens the table "joinrole"---
					ResultSet setrole = LiteSQL.onQuery("SELECT * FROM joinrole WHERE guildid = " + channel.getGuild().getIdLong());
					
					long role = setrole.getLong("role");
					int rstate = setrole.getInt("state");
					
					//Returns the Role
					builder.addField("Setup \"Joinrole role\"", "The currently selected role is: <@&" + role + "> \r"
							+ (rstate == 0 ? "Currently the roles on join are: Disabled :x:" : "Currently the roles on join are: Enabled :white_check_mark:"), false);
					//Closes the joinrole result so it can open another result
					setrole.close();
					
					builder.setColor(PureCompetence.INSTANCE.clrViolet);
					//builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setFooter("Currently Experimental and will not work 100%, Database resets may occur due to development. No guarantee that any of the Commands function (yet).");
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
					
					return;
					
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
				
			}
//			---Base volume command---
			
			if(args[1].equalsIgnoreCase("volume") && args.length > 2) {
				
				try{
					int vol = Integer.parseInt(args[2]);
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM musicvolume WHERE guildid = " + channel.getGuild().getIdLong());
						
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE musicvolume SET volume = " + vol + " WHERE guildid = " + channel.getGuild().getIdLong());
							
							builder.setTitle("Default volume changed!");
							builder.setDescription("The new default volume is " + args[2]);
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
							
						} else {
							LiteSQL.onUpdate("INSERT INTO musicvolume(guildid, volume) VALUES(" + channel.getGuild().getIdLong() + ", " + vol + ")");
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						
					}
					
				} catch(NumberFormatException e){
					
					builder.setTitle("Please use ***$setup volume <0-100>***!");
					builder.setColor(PureCompetence.INSTANCE.clrRed);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
				
			}
			
			if(args[1].equalsIgnoreCase("volume")) {
				
				builder.setTitle("Please use ***$setup volume <0-100>***!");
				builder.setColor(PureCompetence.INSTANCE.clrRed);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builder.setTimestamp(OffsetDateTime.now());
				channel.sendMessage(builder.build()).queue();
				
				return;
			}
			
			builder.setTitle("**Setup Command**");
			builder.addField("Command usage", "", false);
			builder.addField("**$setup**", "Displays the details of the Setup Command and their usage. Administrator permissions are required to use this command.", false);
			builder.addField("**$setup join #channel**", "Sets the channel where the join messages are displayed, if no channel has ben specified the bot will send the message into the Default Channel until it has been disabled.", false);
			builder.addField("**$setup join [enable/disable]**", "Enables/disables the join messages on the Server, the channel they were sent in will be saved.", false);
			builder.addField("**$setup leave #channel**", "Sets the channel where the leave messages are displayed, if no channel has ben specified the bot will send the message into the Default Channel until it has been disabled.", false);
			builder.addField("**$setup leave [enable/disable]**", "Enables/disables the leave messages on the Server, the channel they were sent in will be saved.", false);
			channel.sendMessage(builder.build()).queue();
				
		} catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}

	}
	
	public void setupInfo(String args) {
		
	}

}
