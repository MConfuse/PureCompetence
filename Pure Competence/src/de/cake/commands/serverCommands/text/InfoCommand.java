package de.cake.commands.serverCommands.text;

import java.time.OffsetDateTime;
import de.cake.PureCompetence;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class InfoCommand implements ServerCommand {

	private Member mention;
	
	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		EmbedBuilder builder = new EmbedBuilder();
		EmbedBuilder pointException = new EmbedBuilder();
		String[] args = message.getContentDisplay().split(" ");
		//SimpleDateFormat sdf = new SimpleDateFormat("DD.MM.YYYY");
		//DateTimeFormatterBuilder sdf = new DateTimeFormatterBuilder();
		//$info <667322795025498132>
		
		try {
			if(args.length == 1) {
				System.out.println("1");
				
				Integer roleL = m.getRoles().get(0).toString().length();
				String[] activities = m.getActivities().toString().split("Activity");
				
				//Abfrage für die Anzahl des Status/Vorhanden sein des Status
				try {
					try {
						try {
							
							if(activities.length == 1) {		//Kein Status
								builder.setTitle("**Info about " + m.getEffectiveName() + "** *(" + m.getOnlineStatus() + ")*");
								builder.addField("Name", m.getAsMention(), true);
								builder.addField("Discord ID", "" + m.getIdLong(), true);
								builder.addField("Joined Discord on", m.getTimeCreated().getDayOfMonth() + "." + m.getTimeCreated().getMonthValue()+ "." + m.getTimeCreated().getYear(), true);
								builder.addField("Joined the Server", m.getTimeJoined().getDayOfMonth() + "." + m.getTimeJoined().getMonthValue() + "." + m.getTimeJoined().getYear(), true);
								builder.addField(m.getEffectiveName() + " display Color", "R:" + m.getColor().getRed() + " G:" + m.getColor().getGreen() + " B:" + m.getColor().getBlue() + "\r [Converter](https://www.rgbtohex.net)", true);
								builder.addField("Highest Role", "" + m.getRoles().get(0).toString().substring(2, roleL - 20), true);
								builder.addField(m.getEffectiveName() + "'s current Activitie/s", "No Activities!", false);
								builder.setThumbnail(m.getUser().getAvatarUrl());
								builder.setColor(PureCompetence.INSTANCE.clrViolet);
								builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
								builder.setTimestamp(OffsetDateTime.now());
								channel.sendMessage(builder.build()).queue();
								
								
							} else if(activities.length == 2) {	//Spielt nur Spiel/Hat Status
								Integer lI1 = activities[1].length() - 3;
								
								String activitie1 = activities[1].toString().substring(1, lI1);
								
								builder.setTitle("**Info about " + m.getEffectiveName() + "** *(" + m.getOnlineStatus() + ")*");
								builder.addField("Name", m.getAsMention(), true);
								builder.addField("Discord ID", "" + m.getIdLong(), true);
								builder.addField("Joined Discord on", m.getTimeCreated().getDayOfMonth() + "." + m.getTimeCreated().getMonthValue()+ "." + m.getTimeCreated().getYear(), true);
								builder.addField("Joined the Server", m.getTimeJoined().getDayOfMonth() + "." + m.getTimeJoined().getMonthValue() + "." + m.getTimeJoined().getYear(), true);
								builder.addField(m.getEffectiveName() + " display Color", "R:" + m.getColor().getRed() + " G:" + m.getColor().getGreen() + " B:" + m.getColor().getBlue() + "\r [Converter](https://www.rgbtohex.net)", true);
								builder.addField("Highest Role", "" + m.getRoles().get(0).toString().substring(2, roleL - 20), true);
								builder.addField(m.getEffectiveName() + "'s current Activitie/s", "" + m.getActivities() == "[]" ? "No Activities!" : "" + activitie1, false);
								builder.setThumbnail(m.getUser().getAvatarUrl());
								builder.setColor(PureCompetence.INSTANCE.clrViolet);
								builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
								builder.setTimestamp(OffsetDateTime.now());
								channel.sendMessage(builder.build()).queue();
								
								
							} else if(activities.length == 3) {	//Hat Status und Spielt
								Integer lI1 = activities[1].length() - 3;
								Integer lI2 = activities[2].length() - 2;
								
								String activitie1 = activities[1].toString().substring(1, lI1);
								String activitie2 = activities[2].toString().substring(1, lI2);
							
								builder.setTitle("**Info about " + m.getEffectiveName() + "** *(" + m.getOnlineStatus() + ")*");
								builder.addField("Name", m.getAsMention(), true);
								builder.addField("Discord ID", "" + m.getIdLong(), true);
								builder.addField("Joined Discord on", m.getTimeCreated().getDayOfMonth() + "." + m.getTimeCreated().getMonthValue()+ "." + m.getTimeCreated().getYear(), true);
								builder.addField("Joined the Server", m.getTimeJoined().getDayOfMonth() + "." + m.getTimeJoined().getMonthValue() + "." + m.getTimeJoined().getYear(), true);
								builder.addField(m.getEffectiveName() + " display Color", "R:" + m.getColor().getRed() + " G:" + m.getColor().getGreen() + " B:" + m.getColor().getBlue() + "\r [Converter](https://www.rgbtohex.net)", true);
								builder.addField("Highest Role", "" + m.getRoles().get(0).toString().substring(2, roleL - 20), true);
								builder.addField(m.getEffectiveName() + "'s current Activitie/s", "" + m.getActivities() == "[]" ? "No Activities!" : "" + activitie1 + ", " + activitie2, false);
								builder.setThumbnail(m.getUser().getAvatarUrl());
								builder.setColor(PureCompetence.INSTANCE.clrViolet);
								builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
								builder.setTimestamp(OffsetDateTime.now());
								channel.sendMessage(builder.build()).queue();
							}
							
						} catch(NullPointerException e) {
							
							if(activities.length == 1) {		//Kein Status
								pointException.setTitle("**Info about " + m.getEffectiveName() + "** *(" + m.getOnlineStatus() + ")*");
								pointException.addField("Name", m.getAsMention(), true);
								pointException.addField("Discord ID", "" + m.getIdLong(), true);
								pointException.addField("Joined Discord on", m.getTimeCreated().getDayOfMonth() + "." + m.getTimeCreated().getMonthValue()+ "." + m.getTimeCreated().getYear(), true);
								pointException.addField("Joined the Server", m.getTimeJoined().getDayOfMonth() + "." + m.getTimeJoined().getMonthValue() + "." + m.getTimeJoined().getYear(), true);
								pointException.addField("Highest Role", "" + m.getRoles().get(0).toString().substring(2, roleL - 20), true);
								pointException.addField(m.getEffectiveName() + "'s current Activitie/s", "No Activities!", false);
								pointException.setThumbnail(m.getUser().getAvatarUrl());
								pointException.setColor(PureCompetence.INSTANCE.clrViolet);
								pointException.setFooter(PureCompetence.INSTANCE.pwrdBy);
								pointException.setTimestamp(OffsetDateTime.now());
								channel.sendMessage(pointException.build()).queue();
								
								
							} else if(activities.length == 2) {	//Spielt nur Spiel/Hat Status
								Integer lI1 = activities[1].length() - 3;
								
								String activitie1 = activities[1].toString().substring(1, lI1);
								
								pointException.setTitle("**Info about " + m.getEffectiveName() + "** *(" + m.getOnlineStatus() + ")*");
								pointException.addField("Name", m.getAsMention(), true);
								pointException.addField("Discord ID", "" + m.getIdLong(), true);
								pointException.addField("Joined Discord on", m.getTimeCreated().getDayOfMonth() + "." + m.getTimeCreated().getMonthValue()+ "." + m.getTimeCreated().getYear(), true);
								pointException.addField("Joined the Server", m.getTimeJoined().getDayOfMonth() + "." + m.getTimeJoined().getMonthValue() + "." + m.getTimeJoined().getYear(), true);
								pointException.addField("Highest Role", "" + m.getRoles().get(0).toString().substring(2, roleL - 20), true);
								pointException.addField(m.getEffectiveName() + "'s current Activitie/s", "" + m.getActivities() == "[]" ? "No Activities!" : "" + activitie1, false);
								pointException.setThumbnail(m.getUser().getAvatarUrl());
								pointException.setColor(PureCompetence.INSTANCE.clrViolet);
								pointException.setFooter(PureCompetence.INSTANCE.pwrdBy);
								pointException.setTimestamp(OffsetDateTime.now());
								channel.sendMessage(builder.build()).queue();
								
								
							} else if(activities.length == 3) {	//Hat Status und Spielt
								Integer lI1 = activities[1].length() - 3;
								Integer lI2 = activities[2].length() - 2;
								
								String activitie1 = activities[1].toString().substring(1, lI1);
								String activitie2 = activities[2].toString().substring(1, lI2);
							
								pointException.setTitle("**Info about " + m.getEffectiveName() + "** *(" + m.getOnlineStatus() + ")*");
								pointException.addField("Name", m.getAsMention(), true);
								pointException.addField("Discord ID", "" + m.getIdLong(), true);
								pointException.addField("Joined Discord on", m.getTimeCreated().getDayOfMonth() + "." + m.getTimeCreated().getMonthValue()+ "." + m.getTimeCreated().getYear(), true);
								pointException.addField("Joined the Server", m.getTimeJoined().getDayOfMonth() + "." + m.getTimeJoined().getMonthValue() + "." + m.getTimeJoined().getYear(), true);
								pointException.addField("Highest Role", "" + m.getRoles().get(0).toString().substring(2, roleL - 20), true);
								pointException.addField(m.getEffectiveName() + "'s current Activitie/s", "" + m.getActivities() == "[]" ? "No Activities!" : "" + activitie1 + ", " + activitie2, false);
								pointException.setThumbnail(m.getUser().getAvatarUrl());
								pointException.setColor(PureCompetence.INSTANCE.clrViolet);
								pointException.setFooter(PureCompetence.INSTANCE.pwrdBy);
								pointException.setTimestamp(OffsetDateTime.now());
								channel.sendMessage(pointException.build()).queue();
							}
							
						}
						
					} catch (IndexOutOfBoundsException e) {
						
					}
					
				} catch(ArrayIndexOutOfBoundsException e) {
					
				}
			
			} else if(args.length == 2) {
				System.out.println("2");
				
				if(message.getContentRaw().length() < 28) {
					builder.setTitle("Please mention a user with @[user]");
					builder.setColor(PureCompetence.INSTANCE.clrRed);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
					return;
				}
				
				if(message.getMentionedMembers().size() != 0) {
					this.mention = message.getMentionedMembers().get(0);
				} else {
					this.mention = null;
				}
				
				try {
					String mention = message.getContentRaw().substring(9, 27);
					User mem = channel.getGuild().getJDA().getUserById(mention);
					
					if(mem.getMutualGuilds().contains(message.getGuild())) {
						Guild guildID = PureCompetence.INSTANCE.shardMan.getGuildById(message.getGuild().getIdLong());
						Member mentionedMem = guildID.getMemberById(mention);
						Integer roleL = mentionedMem.getRoles().get(0).toString().length();
						String[] activities = mentionedMem.getActivities().toString().split("Activity");
						
						//Abfrage für die Anzahl des Status/Vorhanden sein des Status
						try {
							try {
								try {
									
									if(activities.length == 1) {		//Kein Status
										builder.setTitle("**Info about " + mentionedMem.getEffectiveName() + "** *(" + mentionedMem.getOnlineStatus() + ")*");
										builder.addField("Name", mentionedMem.getAsMention(), true);
										builder.addField("Discord ID", "" + mentionedMem.getIdLong(), true);
										builder.addField("Joined Discord on", mentionedMem.getTimeCreated().getDayOfMonth() + "." + mentionedMem.getTimeCreated().getMonthValue()+ "." + mentionedMem.getTimeCreated().getYear(), true);
										builder.addField("Joined the Server", mentionedMem.getTimeJoined().getDayOfMonth() + "." + mentionedMem.getTimeJoined().getMonthValue() + "." + mentionedMem.getTimeJoined().getYear(), true);
										builder.addField(m.getEffectiveName() + " display Color", "R:" + mentionedMem.getColor().getRed() + " G:" + mentionedMem.getColor().getGreen() + " B:" + mentionedMem.getColor().getBlue() + "\r [Convert to Hex](https://www.rgbtohex.net)", true);
										builder.addField("Highest Role", "" + mentionedMem.getRoles().get(0).toString().substring(2, roleL - 20), true);
										builder.addField(mentionedMem.getEffectiveName() + "'s current Activitie/s", "No Activities!", false);
										builder.setThumbnail(mentionedMem.getUser().getAvatarUrl());
										builder.setColor(PureCompetence.INSTANCE.clrViolet);
										builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
										builder.setTimestamp(OffsetDateTime.now());
										channel.sendMessage(builder.build()).queue();
										
										
									} else if(activities.length == 2) {	//Spielt nur Spiel/Hat Status
										Integer lI1 = activities[1].length() - 3;
										
										String activitie1 = activities[1].toString().substring(1, lI1);
										
										builder.setTitle("**Info about " + mentionedMem.getEffectiveName() + "** *(" + mentionedMem.getOnlineStatus() + ")*");
										builder.addField("Name", mentionedMem.getAsMention(), true);
										builder.addField("Discord ID", "" + mentionedMem.getIdLong(), true);
										builder.addField("Joined Discord on", mentionedMem.getTimeCreated().getDayOfMonth() + "." + mentionedMem.getTimeCreated().getMonthValue()+ "." + mentionedMem.getTimeCreated().getYear(), true);
										builder.addField("Joined the Server", mentionedMem.getTimeJoined().getDayOfMonth() + "." + mentionedMem.getTimeJoined().getMonthValue() + "." + mentionedMem.getTimeJoined().getYear(), true);
										builder.addField(m.getEffectiveName() + " display Color", "R:" + mentionedMem.getColor().getRed() + " G:" + mentionedMem.getColor().getGreen() + " B:" + mentionedMem.getColor().getBlue() + "\r [Convert to Hex](https://www.rgbtohex.net)", true);
										builder.addField("Highest Role", "" + mentionedMem.getRoles().get(0).toString().substring(2, roleL - 20), true);
										builder.addField(mentionedMem.getEffectiveName() + "'s current Activitie/s", "" + mentionedMem.getActivities() == "[]" ? "No Activities!" : "" + activitie1, false);
										builder.setThumbnail(mentionedMem.getUser().getAvatarUrl());
										builder.setColor(PureCompetence.INSTANCE.clrViolet);
										builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
										builder.setTimestamp(OffsetDateTime.now());
										channel.sendMessage(builder.build()).queue();
										
										
									} else if(activities.length == 3) {	//Hat Status und Spielt
										Integer lI1 = activities[1].length() - 3;
										Integer lI2 = activities[2].length() - 2;
										
										String activitie1 = activities[1].toString().substring(1, lI1);
										String activitie2 = activities[2].toString().substring(1, lI2);
									
										builder.setTitle("**Info about " + mentionedMem.getEffectiveName() + "** *(" + mentionedMem.getOnlineStatus() + ")*");
										builder.addField("Name", mentionedMem.getAsMention(), true);
										builder.addField("Discord ID", "" + mentionedMem.getIdLong(), true);
										builder.addField("Joined Discord on", mentionedMem.getTimeCreated().getDayOfMonth() + "." + mentionedMem.getTimeCreated().getMonthValue()+ "." + mentionedMem.getTimeCreated().getYear(), true);
										builder.addField("Joined the Server", mentionedMem.getTimeJoined().getDayOfMonth() + "." + mentionedMem.getTimeJoined().getMonthValue() + "." + mentionedMem.getTimeJoined().getYear(), true);
										builder.addField(m.getEffectiveName() + " display Color", "R:" + mentionedMem.getColor().getRed() + " G:" + mentionedMem.getColor().getGreen() + " B:" + mentionedMem.getColor().getBlue() + "\r [Convert to Hex](https://www.rgbtohex.net)", true);
										builder.addField("Highest Role", "" + mentionedMem.getRoles().get(0).toString().substring(2, roleL - 20), true);
										builder.addField(mentionedMem.getEffectiveName() + "'s current Activitie/s", "" + mentionedMem.getActivities() == "[]" ? "No Activities!" : "" + activitie1 + ", " + activitie2, false);
										builder.setThumbnail(mentionedMem.getUser().getAvatarUrl());
										builder.setColor(PureCompetence.INSTANCE.clrViolet);
										builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
										builder.setTimestamp(OffsetDateTime.now());
										channel.sendMessage(builder.build()).queue();
									}
									
								} catch(NullPointerException e) {
									
									if(activities.length == 1) {		//Kein Status
										pointException.setTitle("**Info about " + mentionedMem.getEffectiveName() + "** *(" + mentionedMem.getOnlineStatus() + ")*");
										pointException.addField("Name", mentionedMem.getAsMention(), true);
										pointException.addField("Discord ID", "" + mentionedMem.getIdLong(), true);
										pointException.addField("Joined Discord on", mentionedMem.getTimeCreated().getDayOfMonth() + "." + mentionedMem.getTimeCreated().getMonthValue()+ "." + mentionedMem.getTimeCreated().getYear(), true);
										pointException.addField("Joined the Server", mentionedMem.getTimeJoined().getDayOfMonth() + "." + mentionedMem.getTimeJoined().getMonthValue() + "." + mentionedMem.getTimeJoined().getYear(), true);
										pointException.addField("Highest Role", "" + mentionedMem.getRoles().get(0).toString().substring(2, roleL - 20), true);
										pointException.addField(mentionedMem.getEffectiveName() + "'s current Activitie/s", "No Activities!", false);
										pointException.setThumbnail(mentionedMem.getUser().getAvatarUrl());
										pointException.setColor(PureCompetence.INSTANCE.clrViolet);
										pointException.setFooter(PureCompetence.INSTANCE.pwrdBy);
										pointException.setTimestamp(OffsetDateTime.now());
										channel.sendMessage(pointException.build()).queue();
										
										
									} else if(activities.length == 2) {	//Spielt nur Spiel/Hat Status
										Integer lI1 = activities[1].length() - 3;
										
										String activitie1 = activities[1].toString().substring(1, lI1);
										
										pointException.setTitle("**Info about " + mentionedMem.getEffectiveName() + "** *(" + mentionedMem.getOnlineStatus() + ")*");
										pointException.addField("Name", mentionedMem.getAsMention(), true);
										pointException.addField("Discord ID", "" + mentionedMem.getIdLong(), true);
										pointException.addField("Joined Discord on", mentionedMem.getTimeCreated().getDayOfMonth() + "." + mentionedMem.getTimeCreated().getMonthValue()+ "." + mentionedMem.getTimeCreated().getYear(), true);
										pointException.addField("Joined the Server", mentionedMem.getTimeJoined().getDayOfMonth() + "." + mentionedMem.getTimeJoined().getMonthValue() + "." + mentionedMem.getTimeJoined().getYear(), true);
										pointException.addField("Highest Role", "" + mentionedMem.getRoles().get(0).toString().substring(2, roleL - 20), true);
										pointException.addField(mentionedMem.getEffectiveName() + "'s current Activitie/s", "" + mentionedMem.getActivities() == "[]" ? "No Activities!" : "" + activitie1, false);
										pointException.setThumbnail(mentionedMem.getUser().getAvatarUrl());
										pointException.setColor(PureCompetence.INSTANCE.clrViolet);
										pointException.setFooter(PureCompetence.INSTANCE.pwrdBy);
										pointException.setTimestamp(OffsetDateTime.now());
										channel.sendMessage(pointException.build()).queue();
										
										
									} else if(activities.length == 3) {	//Hat Status und Spielt
										Integer lI1 = activities[1].length() - 3;
										Integer lI2 = activities[2].length() - 2;
										
										String activitie1 = activities[1].toString().substring(1, lI1);
										String activitie2 = activities[2].toString().substring(1, lI2);
									
										pointException.setTitle("**Info about " + mentionedMem.getEffectiveName() + "** *(" + mentionedMem.getOnlineStatus() + ")*");
										pointException.addField("Name", mentionedMem.getAsMention(), true);
										pointException.addField("Discord ID", "" + mentionedMem.getIdLong(), true);
										pointException.addField("Joined Discord on", mentionedMem.getTimeCreated().getDayOfMonth() + "." + mentionedMem.getTimeCreated().getMonthValue()+ "." + mentionedMem.getTimeCreated().getYear(), true);
										pointException.addField("Joined the Server", mentionedMem.getTimeJoined().getDayOfMonth() + "." + mentionedMem.getTimeJoined().getMonthValue() + "." + mentionedMem.getTimeJoined().getYear(), true);
										pointException.addField("Highest Role", "" + mentionedMem.getRoles().get(0).toString().substring(2, roleL - 20), true);
										pointException.addField(mentionedMem.getEffectiveName() + "'s current Activitie/s", "" + mentionedMem.getActivities() == "[]" ? "No Activities!" : "" + activitie1 + ", " + activitie2, false);
										pointException.setThumbnail(mentionedMem.getUser().getAvatarUrl());
										pointException.setColor(PureCompetence.INSTANCE.clrViolet);
										pointException.setFooter(PureCompetence.INSTANCE.pwrdBy);
										pointException.setTimestamp(OffsetDateTime.now());
										channel.sendMessage(pointException.build()).queue();
									}
									
								}
								
							} catch (IndexOutOfBoundsException e){
								
							}
							
						} catch(ArrayIndexOutOfBoundsException e) {
							
						}
						
					} else {
						builder.setTitle("You've tagged someone not on the Server? How lol");
						builder.setColor(PureCompetence.INSTANCE.clrRed);
						builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
						builder.setTimestamp(OffsetDateTime.now());
						channel.sendMessage(builder.build()).queue();
					}
					
				} catch(NumberFormatException e) {
					builder.setTitle("Instead of smashing your head into and through the keyboard consider mentioning a user with @[user]!");
					builder.setColor(PureCompetence.INSTANCE.clrRed);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
			
			} else {
				builder.setTitle("Work in progress due to weird code lol");
				builder.setColor(PureCompetence.INSTANCE.clrViolet);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builder.setTimestamp(OffsetDateTime.now());
				channel.sendMessage(builder.build()).queue();
			}
			
			String ment = message.getContentRaw().substring(9, 27);
			System.out.println(ment);
			System.out.println(message.getContentRaw());
			
		} catch (StringIndexOutOfBoundsException e) {
			
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
