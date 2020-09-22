package de.cake.commands.serverCommands.text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import de.cake.PureCompetence;
import de.cake.LiteSQL.LiteSQL;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class SetupCommand implements ServerCommand {

	/*TODO 	Add this stuff in the JoinListener
	 * 		maybe some more stuff later=
	 * 		Add Lines here so I can find shit easier!
	 */
	
	/**
	 * Used to retrieve and set the Bot's setup.
	 * 
	 * @param I wrote a super detailed explanation before I accidentally deleted it, now you can fuck off 
	 * 		  and look in the {@link de.cake.listener.ReactionListener} for the Setup help menu, might find something useful here too.
	 * 
	 * @return Setup of the Bot on the Guild this message was sent in. Only works if the User has the Administrator role or Administrator permissions on the Server.
	 * 
	 * @see types#ServerCommand
	 * @see manager#ServerCommandManager
	 */
	@Override
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild) {
		
		String[] args = message.getContentDisplay().split(" ");
		EmbedBuilder builder = new EmbedBuilder();
		EmbedBuilder permissions = new EmbedBuilder().setTitle("Insufficient Permission to perform this command!").setColor(PureCompetence.INSTANCE.clrRed).setFooter(PureCompetence.INSTANCE.pwrdBy).setTimestamp(OffsetDateTime.now());
		
		ResultSet check = LiteSQL.onQuery("SELECT * FROM adminrights WHERE guildid = " + channel.getGuild().getIdLong());
		
		try {
			if(check.next()) {
				long roleid = check.getLong("role");
				int state = check.getInt("state");
				
				//Returns if user does not have enough permissions
				if(!(m.getRoles().contains(channel.getGuild().getRoleById(roleid))) && state == 0) {
					if(m.getIdLong() != 255313111391666176l) {
						channel.sendMessage(permissions.build()).queue();
						
						check.close();
						return;
					}
					
				}
				
			}
			
			check.close();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		if(args.length == 1) {
			helpMenu("help", channel, guild, 0);
			return;
		}
		
		//-------------------------- Join message --------------------------
		//				channel format <#716071240607203368>
		if(args[1].equalsIgnoreCase("join") && args.length > 2 && message.getMentionedChannels().size() == 1) {
			long channelID = message.getMentionedChannels().get(0).getIdLong();
			ResultSet set = LiteSQL.onQuery("SELECT * FROM joinmsgchannels WHERE guildid = " + guild.getIdLong());
			
			try {
				if(set.next()) {
					LiteSQL.onUpdate("UPDATE joinmsgchannels SET channelid = " + channelID + " WHERE guildid = " + guild.getIdLong());
					LiteSQL.onUpdate("UPDATE joinmsgchannels SET state = 1 WHERE guildid = " + guild.getIdLong());
					
					builder.setTitle("Default Join notification channel changed!");
					builder.setDescription("The new channel is " + guild.getTextChannelById(channelID).getAsMention());
					builder.setColor(PureCompetence.INSTANCE.clrGreen);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				} else {
					LiteSQL.onUpdate("INSERT INTO joinmsgchannels(guildid, channelid, state) VALUES(" + guild.getIdLong() + "," + channelID + ", 1)");
					
					builder.setTitle("Default Join notification channel set!");
					builder.setDescription("The new channel is " + guild.getTextChannelById(channelID).getAsMention());
					builder.setColor(PureCompetence.INSTANCE.clrGreen);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
				
				set.close();
				
				return;
			} catch(SQLException | NumberFormatException ex) {
				ex.printStackTrace();
				
				builder.setTitle("Please use **$setup join #channel**!");
				builder.setDescription("If you used the command correctly and still get this error again, the database is broken :D");
				builder.setColor(PureCompetence.INSTANCE.clrRed);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builder.setTimestamp(OffsetDateTime.now());
				channel.sendMessage(builder.build()).queue();
				
				return;
			}
			
		}
		
		//-------------------------- Join message toggle --------------------------
		if(args[1].equalsIgnoreCase("join") && args[2].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("join") && args[2].equalsIgnoreCase("true")) {
			
			ResultSet set = LiteSQL.onQuery("SELECT * FROM joinmsgchannels WHERE guildid = " + guild.getIdLong());
			
			try {
				if(set.next()) {
					LiteSQL.onUpdate("UPDATE joinmsgchannels SET state = 1 WHERE guildid = " + guild.getIdLong());
					
					builder.setTitle("Join message notifications enabled!");
					builder.setColor(PureCompetence.INSTANCE.clrGreen);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				
				} else {
					LiteSQL.onUpdate("INSERT INTO joinmsgchannels SET state = 1 WHERE guildid = " + guild.getIdLong());
					
					builder.setTitle("Join message notifications enabled!");
					builder.setColor(PureCompetence.INSTANCE.clrGreen);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
				
				set.close();
				
				return;
				
			} catch(SQLException ex) {
				ex.printStackTrace();
				return;
			}
			
		}
		
		if(args[1].equalsIgnoreCase("join") && args[2].equalsIgnoreCase("disable") || args[1].equalsIgnoreCase("join") && args[2].equalsIgnoreCase("false")) {
			
			ResultSet set = LiteSQL.onQuery("SELECT * FROM joinmsgchannels WHERE guildid = " + guild.getIdLong());
			
			try {
				if(set.next()) {
					LiteSQL.onUpdate("UPDATE joinmsgchannels SET state = 0 WHERE guildid = " + guild.getIdLong());
					
					builder.setTitle("Join message notifications disabled!");
					builder.setColor(PureCompetence.INSTANCE.clrGreen);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				
				} else {
					LiteSQL.onUpdate("INSERT INTO joinmsgchannels SET state = 0 WHERE guildid = " + guild.getIdLong());
					
					builder.setTitle("Join message notifications disabled!");
					builder.setColor(PureCompetence.INSTANCE.clrGreen);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
				
				set.close();
				
				return;
			} catch(SQLException ex) {
				ex.printStackTrace();
				return;
			}
			
		}
		
		if(args[1].equalsIgnoreCase("join")) {
			builder.setTitle("Please use **$setup join #channel**!");
			builder.setDescription("Example: `$setup join #welcome`");
			builder.setColor(PureCompetence.INSTANCE.clrRed);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
			
			return;
		}
		
		//-------------------------- Leave message --------------------------
				//				channel format <#716071240607203368>
				if(args[1].equalsIgnoreCase("leave") && args.length > 2 && message.getMentionedChannels().size() == 1) {
					long channelID = message.getMentionedChannels().get(0).getIdLong();
					ResultSet set = LiteSQL.onQuery("SELECT * FROM leavemsgchannels WHERE guildid = " + guild.getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE leavemsgchannels SET channelid = " + channelID + " WHERE guildid = " + guild.getIdLong());
							LiteSQL.onUpdate("UPDATE leavemsgchannels SET state = 1 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Default leave notification channel changed!");
							builder.setDescription("The new channel is " + guild.getTextChannelById(channelID).getAsMention());
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						} else {
							LiteSQL.onUpdate("INSERT INTO leavemsgchannels(guildid, channelid, state) VALUES(" + guild.getIdLong() + "," + channelID + ", 1)");
							
							builder.setTitle("Default leave notification channel set!");
							builder.setDescription("The new channel is " + guild.getTextChannelById(channelID).getAsMention());
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						}
						
						set.close();
						
						return;
					} catch(SQLException | NumberFormatException ex) {
						ex.printStackTrace();
						
						builder.setTitle("Please use **$setup leave #channel**!");
						builder.setDescription("If you used the command correctly and still get this error again, the database is broken :D");
						builder.setColor(PureCompetence.INSTANCE.clrRed);
						builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
						builder.setTimestamp(OffsetDateTime.now());
						channel.sendMessage(builder.build()).queue();
						
						return;
					}
					
				}
				
				//-------------------------- leave message toggle --------------------------
				if(args[1].equalsIgnoreCase("leave") && args[2].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("leave") && args[2].equalsIgnoreCase("true")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM leavemsgchannels WHERE guildid = " + guild.getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE leavemsgchannels SET state = 1 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("leave message notifications enabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("INSERT INTO leavemsgchannels SET state = 1 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("leave message notifications enabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						ex.printStackTrace();
						return;
					}
					
				}
				
				if(args[1].equalsIgnoreCase("leave") && args[2].equalsIgnoreCase("disable") || args[1].equalsIgnoreCase("leave") && args[2].equalsIgnoreCase("false")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM leavemsgchannels WHERE guildid = " + guild.getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE leavemsgchannels SET state = 0 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("leave message notifications disabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("INSERT INTO leavemsgchannels SET state = 0 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("leave message notifications disabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						}
						
						set.close();
						
						return;
					} catch(SQLException ex) {
						ex.printStackTrace();
						return;
					}
					
				}
				
				if(args[1].equalsIgnoreCase("leave")) {
					builder.setTitle("Please use **$setup leave #channel**!");
					builder.setDescription("Example: `$setup leave #welcome`");
					builder.setColor(PureCompetence.INSTANCE.clrRed);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
					
					return;
				}
				
				//-------------------------- Update message --------------------------
				//				channel format <#716071240607203368>
				if(args[1].equalsIgnoreCase("update") && args.length > 2 && message.getMentionedChannels().size() == 1) {
					long channelID = message.getMentionedChannels().get(0).getIdLong();
					ResultSet set = LiteSQL.onQuery("SELECT * FROM updatechannels WHERE guildid = " + guild.getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE updatechannels SET channelid = " + channelID + " WHERE guildid = " + guild.getIdLong());
							LiteSQL.onUpdate("UPDATE updatechannels SET state = 1 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Default update notification channel changed!");
							builder.setDescription("The new channel is " + guild.getTextChannelById(channelID).getAsMention());
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						} else {
							LiteSQL.onUpdate("INSERT INTO updatechannels(guildid, channelid, state) VALUES(" + guild.getIdLong() + "," + channelID + ", 1)");
							
							builder.setTitle("Default update notification channel set!");
							builder.setDescription("The new channel is " + guild.getTextChannelById(channelID).getAsMention());
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						}
						
						set.close();
						
						return;
					} catch(SQLException | NumberFormatException ex) {
						ex.printStackTrace();
						
						builder.setTitle("Please use **$setup update #channel**!");
						builder.setDescription("If you used the command correctly and still get this error again, the database is broken :D");
						builder.setColor(PureCompetence.INSTANCE.clrRed);
						builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
						builder.setTimestamp(OffsetDateTime.now());
						channel.sendMessage(builder.build()).queue();
						
						return;
					}
					
				}
				
				//-------------------------- Update message toggle --------------------------
				if(args[1].equalsIgnoreCase("update") && args[2].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("update") && args[2].equalsIgnoreCase("true")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM updatechannels WHERE guildid = " + guild.getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE updatechannels SET state = 1 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Update message notifications enabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("INSERT INTO updatechannels SET state = 1 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Update message notifications enabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						ex.printStackTrace();
						return;
					}
					
				}
				
				if(args[1].equalsIgnoreCase("update") && args[2].equalsIgnoreCase("disable") || args[1].equalsIgnoreCase("update") && args[2].equalsIgnoreCase("false")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM updatechannels WHERE guildid = " + guild.getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE updatechannels SET state = 0 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Update message notifications disabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("INSERT INTO updatechannels SET state = 0 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Update message notifications disabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						}
						
						set.close();
						
						return;
					} catch(SQLException ex) {
						ex.printStackTrace();
						return;
					}
					
				}
				
				if(args[1].equalsIgnoreCase("update")) {
					builder.setTitle("Please use **$setup update #channel**!");
					builder.setDescription("Example: `$setup update #welcome`");
					builder.setColor(PureCompetence.INSTANCE.clrRed);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
					
					return;
				}
				
				//-------------------------- Administrator rights --------------------------
				if(args[1].equalsIgnoreCase("admin") && args.length > 2 && message.getMentionedRoles().size() == 1) {
					long roleID = message.getMentionedRoles().get(0).getIdLong();
					ResultSet set = LiteSQL.onQuery("SELECT * FROM adminrights WHERE guildid = " + guild.getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE adminrights SET role = " + roleID + " WHERE guildid = " + guild.getIdLong());
							LiteSQL.onUpdate("UPDATE adminrights SET state = 1 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Admin permissions changed!");
							builder.setDescription("The new role is " + guild.getRoleById(roleID).getAsMention());
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						} else {
							LiteSQL.onUpdate("INSERT INTO adminrights(guildid, role, state) VALUES(" + guild.getIdLong() + ", " + roleID + ", 1)");
							
							builder.setTitle("Admin permissions set!");
							builder.setDescription("The new role is " + guild.getRoleById(roleID).getAsMention());
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						}
						
						set.close();
						
						return;
					} catch(SQLException | NumberFormatException ex) {
						ex.printStackTrace();
						
						builder.setTitle("Please use **$setup admin @role**!");
						builder.setDescription("If you used the command correctly and still get this error again, the database is broken :D");
						builder.setColor(PureCompetence.INSTANCE.clrRed);
						builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
						builder.setTimestamp(OffsetDateTime.now());
						channel.sendMessage(builder.build()).queue();
						
						return;
					}
					
				}
				
				//-------------------------- Administrator rights toggle --------------------------
				if(args[1].equalsIgnoreCase("admin") && args[2].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("admin") && args[2].equalsIgnoreCase("true")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM adminrights WHERE guildid = " + guild.getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE adminrights SET state = 1 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Admin message notifications enabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("INSERT INTO adminrights SET state = 1 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Admin permissions enabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						ex.printStackTrace();
						return;
					}
					
				}
				
				if(args[1].equalsIgnoreCase("admin") && args[2].equalsIgnoreCase("disable") || args[1].equalsIgnoreCase("admin") && args[2].equalsIgnoreCase("false")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM adminrights WHERE guildid = " + guild.getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE adminrights SET state = 0 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Admin message notifications disabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("INSERT INTO adminrights SET state = 0 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Admin permissions disabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						}
						
						set.close();
						
						return;
					} catch(SQLException ex) {
						ex.printStackTrace();
						return;
					}
					
				}
				
				if(args[1].equalsIgnoreCase("admin")) {
					builder.setTitle("Please use **$setup admin @role**!");
					builder.setDescription("Example: `$setup admin @admin`");
					builder.setColor(PureCompetence.INSTANCE.clrRed);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
					
					return;
				}
		
				//-------------------------- Join role --------------------------
				if(args[1].equalsIgnoreCase("joinrole") && args.length > 2 && message.getMentionedRoles().size() == 1) {
					long roleID = message.getMentionedRoles().get(0).getIdLong();
					ResultSet set = LiteSQL.onQuery("SELECT * FROM joinrole WHERE guildid = " + guild.getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE joinrole SET role = " + roleID + " WHERE guildid = " + guild.getIdLong());
							LiteSQL.onUpdate("UPDATE joinrole SET state = 1 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Joinrole changed!");
							builder.setDescription("The new role is " + guild.getRoleById(roleID).getAsMention());
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						} else {
							LiteSQL.onUpdate("INSERT INTO joinrole(guildid, role, state) VALUES(" + guild.getIdLong() + ", " + roleID + ", 1)");
							
							builder.setTitle("Joinrole set!");
							builder.setDescription("The new role is " + guild.getRoleById(roleID).getAsMention());
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						}
						
						set.close();
						
						return;
					} catch(SQLException | NumberFormatException ex) {
						ex.printStackTrace();
						
						builder.setTitle("Please use **$setup joinrole #channel**!");
						builder.setDescription("If you used the command correctly and still get this error again, the database is broken :D");
						builder.setColor(PureCompetence.INSTANCE.clrRed);
						builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
						builder.setTimestamp(OffsetDateTime.now());
						channel.sendMessage(builder.build()).queue();
						
						return;
					}
					
				}
				
				//-------------------------- Join role toggle --------------------------
				if(args[1].equalsIgnoreCase("joinrole") && args[2].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("joinrole") && args[2].equalsIgnoreCase("true")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM joinrole WHERE guildid = " + guild.getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE joinrole SET state = 1 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Joinrole enabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("INSERT INTO joinrole SET state = 1 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Joinrole enabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						}
						
						set.close();
						
						return;
						
					} catch(SQLException ex) {
						ex.printStackTrace();
						return;
					}
					
				}
				
				if(args[1].equalsIgnoreCase("joinrole") && args[2].equalsIgnoreCase("disable") || args[1].equalsIgnoreCase("joinrole") && args[2].equalsIgnoreCase("false")) {
					
					ResultSet set = LiteSQL.onQuery("SELECT * FROM joinrole WHERE guildid = " + guild.getIdLong());
					
					try {
						if(set.next()) {
							LiteSQL.onUpdate("UPDATE joinrole SET state = 0 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Joinrole disabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						
						} else {
							LiteSQL.onUpdate("INSERT INTO joinrole SET state = 0 WHERE guildid = " + guild.getIdLong());
							
							builder.setTitle("Joinrole disabled!");
							builder.setColor(PureCompetence.INSTANCE.clrGreen);
							builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
							builder.setTimestamp(OffsetDateTime.now());
							channel.sendMessage(builder.build()).queue();
						}
						
						set.close();
						
						return;
					} catch(SQLException ex) {
						ex.printStackTrace();
						return;
					}
					
				}
				
				if(args[1].equalsIgnoreCase("joinrole")) {
					builder.setTitle("Please use **$setup joinrole @role**!");
					builder.setDescription("Example: `$setup joinrole @admin`");
					builder.setColor(PureCompetence.INSTANCE.clrRed);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
					
					return;
				}
				
		//-------------------------- Volume --------------------------
		if(args[1].equalsIgnoreCase("volume") && args.length > 2) {
			int vol = Integer.parseInt(args[2]);
			ResultSet set = LiteSQL.onQuery("SELECT * FROM musicvolume WHERE guildid = " + guild.getIdLong());
			
			try {
				if(set.next()) {
					LiteSQL.onUpdate("UPDATE musicvolume SET volume = " + vol + " WHERE guildid = " + guild.getIdLong());
					
					builder.setTitle("Default volume changed!");
					builder.setDescription("The new default volume is " + args[2]);
					builder.setColor(PureCompetence.INSTANCE.clrGreen);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				} else {
					LiteSQL.onUpdate("INSERT INTO musicvolume(guildid, volume) VALUES(" + guild.getIdLong() + ", " + vol + ")");
					
					builder.setTitle("Default volume set!");
					builder.setDescription("The new default volume is " + args[2]);
					builder.setColor(PureCompetence.INSTANCE.clrGreen);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
				
				set.close();
				
				return;
			} catch(SQLException | NumberFormatException e) {
				e.printStackTrace();
				
				builder.setTitle("Please use **$setup volume <0-100>**!");
				builder.setDescription("If you used the command correctly and still get this error again, the database is broken :D");
				builder.setColor(PureCompetence.INSTANCE.clrRed);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builder.setTimestamp(OffsetDateTime.now());
				channel.sendMessage(builder.build()).queue();
				
				return;
			}
		
		} else if(args[1].equalsIgnoreCase("volume") && args.length > 2) {
			builder.setTitle("Please use **$setup volume <0-100>**!");
			builder.setDescription("Example: `$setup volume 20`");
			builder.setColor(PureCompetence.INSTANCE.clrRed);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
			
			return;
		}
		
		//Info Command
		if(args[1].equalsIgnoreCase("info")) {
			
			setupInfo("cmd", channel, 0);
			
			return;
		}
		
		if(args[1].equalsIgnoreCase("reset") && args.length == 3) {
			
			if(args[2].equalsIgnoreCase("confirm")) {
				new SetupResetCommand().setupReset(guild, channel, "cmd");
				
				return;
			} else {
				builder.setTitle("**__Reset your Setup__**");
				
				builder.setDescription("This command will reset every change you have made to the Setup on this Server. Only use this command if you get an error trying to load your Setup with the `$Setup info` command. All your assigned channels, roles and enabled/disabled Settings will be reset to the default channel, a new Pure Competence Admin role will be created so make sure you have deleted the old role (Only if you are using the Automatically generated role). **__This command cannot be undone!__** \r Use `$setup reset confirm` to reset the Setup. \r This command may take a few seconds to complete!");
				
				builder.setColor(PureCompetence.INSTANCE.clrRed);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.HelpUI);
				builder.setTimestamp(OffsetDateTime.now());
				channel.sendMessage(builder.build()).queue();
			}
			
		}
		
		if(args[1].equalsIgnoreCase("reset")) {
			
			builder.setTitle("**__Reset your Setup__**");
			
			builder.setDescription("This command will reset every change you have made to the Setup on this Server. Only use this command if you get an error trying to load your Setup with the `$Setup info` command. All your assigned channels, roles and enabled/disabled Settings will be reset to the default channel, a new Pure Competence Admin role will be created so make sure you have deleted the old role (Only if you are using the Automatically generated role). **__This command cannot be undone!__** \r Use `$setup reset confirm` to reset the Setup. \r This command may take a few seconds to complete!");
			
			builder.setColor(PureCompetence.INSTANCE.clrRed);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.HelpUI);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
			
			return;
		}
		
		helpMenu(args[1], channel, guild, 0l);
	}
	
	public void helpMenu(String args, TextChannel channel, Guild guild, long messageId) {
		
		EmbedBuilder builder = new EmbedBuilder();
		
		builder.setTitle("Setup command help!");
		
		builder.addField("Informations about each Setup command!", "", false);
		builder.addField("$Setup Join [#channel / true / false]", "With this command you can set the channel / toggle the messages sent when someone joins the Server!", false);
		builder.addField("$Setup Leave [#channel / true / false]", "With this command you can set the channel / toggle the messages sent when someone leaves the Server!", false);
		builder.addField("$Setup Update [#channel / true / false]", "With this command you can set the channel / toggle the changelogs sent when the Bot is updated!", false);
		builder.addField("$Setup Volume [0 - 100]", "This command is used to set the default volume for the Music Commands! Max volume is 100 (Not recommended).", false);
		builder.addField("**$Setup Admin [@role / true / false]**", "This command is used to set the role / toggle the Administrator permissions. Usually a role is created Automatically when joining the guild, you can however delete that role and create your own. Administrator permissions are used to limit access to Setup and Music commands.", false);
		builder.addField("**$Setup Join [@role / true / false]**", "This command is used to set the role / toggle the roles the Bot assignes when a new User joins the Server! Currently only one role is allowed.", false);
//		builder.addField("$", "", false);
		
		builder.setColor(PureCompetence.INSTANCE.clrViolet);
		builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.HelpUI);
		builder.setTimestamp(OffsetDateTime.now());
		
		//Sends new message if requested by the direct command
		if(messageId == 0) channel.sendMessage(builder.build()).queue();
		
		//Edits message if called by ReactionListener
		if(messageId != 0) channel.editMessageById(messageId, builder.build()).queue();
		
		return;
	}
	
	public void setupInfo(String args, TextChannel channel, long messageId) {
		
		EmbedBuilder builder = new EmbedBuilder();
		
		try {
			builder.setTitle("Information about PureCompetence's setup on the server");
			
			builder.addField("Help using the Setup commands", "React with :newspaper: to get to the Setup help", false);
			
			//		---Opens the table "joinmsgchannels"---
			ResultSet setjoin = LiteSQL.onQuery("SELECT * FROM joinmsgchannels WHERE guildid = " + channel.getGuild().getIdLong());
			
			int jstate = setjoin.getInt("state");
			long joinChannel = setjoin.getLong("channelid");
			
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
			int astate = setadmin.getInt("state");
			
			//Returns the Role
			builder.addField("Setup \"Admin role\"", "The currently selected role is: <@&" + admin + "> \r"
					+ (astate == 0 ? "Admin permissions: Enabled :white_check_mark:" : "Admin permissions: Disabled :x:"), false);
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
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.HelpUI);
			builder.setTimestamp(OffsetDateTime.now());
			
			//Sends new message if requested by the direct command
			if(messageId == 0) channel.sendMessage(builder.build()).queue();
			
			//Edits message if called by ReactionListener
			if(messageId != 0) channel.editMessageById(messageId, builder.build()).queue();
			
			return;
			
		} catch(SQLException ex) {
			ex.printStackTrace();
			
			builder.setTitle("An error occured while loading your Setup!");
			builder.setDescription("If this error persists try reassigning the roles and channels using the Setup commands, visit the Setup help screen for the commands!");
			
			builder.setColor(PureCompetence.INSTANCE.clrViolet);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.HelpUI);
			builder.setTimestamp(OffsetDateTime.now());
			
			//Sends new message if requested by the direct command
			if(messageId == 0) channel.sendMessage(builder.build()).queue();
			
			//Edits message if called by ReactionListener
			if(messageId != 0) channel.editMessageById(messageId, builder.build()).queue();
		}
		
		return;
	}

}
