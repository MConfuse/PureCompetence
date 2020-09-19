package de.cake.listener;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import de.cake.PureCompetence;
import de.cake.commands.serverCommands.text.SetupCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateBoostCountEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateBoostTierEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {
	
	private String home = "U+1f3e0";
	private String global = "U+1f310";
	private String Private = "U+1f4e7";
	private String music = "U+1f3b5";
	private String setup = "U+2699U+fe0f";
	private String credits = "U+1f4b3";
	private String setupHelp = "U+1f4f0";
	
	/*TODO: When adding new pages further strings are required, don't forget to add symbols and such!
	 *		Order is underneath the onGuildMessageReactionAdd event.
	 * 		New Pages for the Help UI *have* to be updated in the HelpCommand class.
	 *		
	 *		Features to be added:
	 *		UI for play list command?
	*/
	
	@Override
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
		
		//----------------------------Help menu V1----------------------------
		EmbedBuilder builder = new EmbedBuilder();
		
		if(event.getMember().getUser().isBot()) return;
		
		long id = event.getMessageIdLong();
		Message message = event.getChannel().retrieveMessageById(id).complete();
		
//		System.out.println(event.getReaction());
		
		if(message.getMember().getIdLong() == 741370197163769876l || message.getMember().getIdLong() == 667322795025498132l) {
			try {
				
				//---------------------------------------- Help UI ----------------------------------------
				
//				Home UI
				if(event.getReaction().toString().contains("RE:" + home)) {
					
					builder.setTitle("**__Bot help window!__**");
					
					builder.addField("Welcome to the Pure Competence help section!", "You can use the reactions underneath this message to Navigate!", false);
					builder.addField("**General commands**", "React with :globe_with_meridians: for all available text related Server commands!", false);
					builder.addField("**Private commands**", "React with :e_mail: for all available Private commands!", false);
					builder.addField("**Music Commands**", "React with :musical_note: to find the music related things!", false);
					builder.addField("**Bot Setup**", "React with :gear: to see further information about the Bots setup on your server! Requires Administrator permissions!", false);
					builder.addField("**Credits**", "React with :credit_card: to see who made this bot and other inside related information!", false);
//					builder.addField("****", "React with ", false);
					builder.setColor(PureCompetence.INSTANCE.clrBlue);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.HelpUI);
					builder.setTimestamp(OffsetDateTime.now());
					
					long messageId = message.getIdLong();
					
					event.getChannel().editMessageById(messageId, builder.build()).queue();
					
					message.clearReactions().complete();
					message.getChannel().addReactionById(messageId, global).queue();
					message.getChannel().addReactionById(messageId, Private).queue();
					message.getChannel().addReactionById(messageId, music).queue();
					message.getChannel().addReactionById(messageId, setup).queue();				
					message.getChannel().addReactionById(messageId, credits).queue();
					
					return;
				}
				
//				Global UI
				if(event.getReaction().toString().contains("RE:" + global)) {
					
					builder.setTitle("**__Global commands__**");
					
					builder.addField("**$**", "", false);
					builder.addField("**$**", "", false);
					builder.addField("**$**", "", false);
					builder.addField("**$**", "", false);
					builder.addField("**$**", "", false);
					builder.addField("**$**", "", false);
//					builder.addField("**$**", "", false);
					builder.setColor(PureCompetence.INSTANCE.clrBlue);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.HelpUI);
					builder.setTimestamp(OffsetDateTime.now());
					
					long messageId = message.getIdLong();
					
					event.getChannel().editMessageById(messageId, builder.build()).queue();
					
					message.clearReactions().complete();
					message.getChannel().addReactionById(messageId, home).queue();
					
					return;
				}
				
				//Private UI
				if(event.getReaction().toString().contains("RE:" + Private)) {
					
					builder.setTitle("**__Private commands__**");
					
					builder.addField("**$**", "", false);
					builder.addField("**$**", "", false);
					builder.addField("**$**", "", false);
					builder.addField("**$**", "", false);
					builder.addField("**$**", "", false);
					builder.addField("**$**", "", false);
//					builder.addField("**$**", "", false);
					builder.setColor(PureCompetence.INSTANCE.clrBlue);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.HelpUI);
					builder.setTimestamp(OffsetDateTime.now());
					
					long messageId = message.getIdLong();
					
					event.getChannel().editMessageById(messageId, builder.build()).queue();
					
					message.clearReactions().complete();
					message.getChannel().addReactionById(messageId, home).queue();
					
					return;
				}
				
				//Music UI
				if(event.getReaction().toString().contains("RE:" + music)) {
					
					builder.setTitle("**__Music commands__**");
					
					builder.addField("**$Play [next] [YouTube/url]**", "Plays the Sound of the video. The `[next]` is optional and determines wether or not the track should be played directly after the current one or be queued in the regular queue. Currently Supports YouTube, Soundcloud, Vimeo, Twitch Livestreams, Bandcamps by using the URL.", false);
					builder.addField("**$Pause / p**", "Disconnects the Bot and clears the queue", false);
					builder.addField("**$Queuelist / qlist / queue**", "Shows the currently playing track and the next 10 tracks in queue.", false);
					builder.addField("**$Volume / vol**", "Sets the Audio volume of the current player. The default value is 10%, the default value can be configured with the **Setup commands** which can be found in the **Bot Setup** category.", false);
					builder.addField("**$Skip [amount]**", "Skips the currently playing track/s.", false);
					builder.addField("**$Playlist [playlist/play playlist]**", "Plays a premade Playlist full of music! Use `$playlist` or react with : INSERT_EMOJI : for further Commands for premade playlists/playlist commands", false);
//					builder.addField("****", "", false);
					builder.setColor(PureCompetence.INSTANCE.clrBlue);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.HelpUI);
					builder.setTimestamp(OffsetDateTime.now());
					
					long messageId = message.getIdLong();
					
					event.getChannel().editMessageById(messageId, builder.build()).queue();
					
					message.clearReactions().complete();
					message.getChannel().addReactionById(messageId, home).queue();
					
					return;
				}
				
				//Setup UI
				if(event.getReaction().toString().contains("RE:" + setup) && event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getReaction().toString().contains("RE:" + setup) && event.getMember().getIdLong() == 255313111391666176l) {
					
					new SetupCommand().setupInfo("info", event.getChannel(), message.getIdLong());
					
					message.clearReactions().complete();
					message.getChannel().addReactionById(message.getIdLong(), home).queue();
					message.getChannel().addReactionById(message.getIdLong(), setupHelp).queue();
					
					return;
				} else if(event.getReaction().toString().contains("RE:" + setup) && !event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					message.removeReaction(event.getReactionEmote().getAsReactionCode(), event.getMember().getUser()).queue();
					
					builder.setTitle("**__Insuffecient permissions!__**");
					builder.setDescription("Only Server Admins are allowed to change and view the Bots setup!");
					builder.setColor(PureCompetence.INSTANCE.clrRed);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.HelpUI);
					builder.setTimestamp(OffsetDateTime.now());
					message.getChannel().sendMessage(builder.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
					
					return;
				}
				
				//Setup UI Help
				if(event.getReaction().toString().contains("RE:" + setupHelp) && event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getReaction().toString().contains("RE:" + setupHelp) && event.getMember().getIdLong() == 255313111391666176l) {
					
					new SetupCommand().helpMenu("help", event.getChannel(), event.getGuild(), message.getIdLong());
					
					message.clearReactions().complete();
					message.getChannel().addReactionById(message.getIdLong(), home).queue();
					message.getChannel().addReactionById(message.getIdLong(), setup).queue();
					
					return;
				} else if(event.getReaction().toString().contains("RE:" + setup) && !event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					message.removeReaction(event.getReactionEmote().getAsReactionCode(), event.getMember().getUser()).queue();
					
					builder.setTitle("**__Insuffecient permissions!__**");
					builder.setDescription("Only Server Admins are allowed to change and view the Bots setup!");
					builder.setColor(PureCompetence.INSTANCE.clrRed);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.HelpUI);
					builder.setTimestamp(OffsetDateTime.now());
					message.getChannel().sendMessage(builder.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
					
					return;
				}
				
				//Credits UI
				if(event.getReaction().toString().contains("RE:" + credits)) {
					
					builder.setTitle("**__Credits__**");
					
					builder.addField("**$**", "", false);
					builder.addField("**$**", "", false);
					builder.addField("**$**", "", false);
					builder.addField("**$**", "", false);
					builder.addField("**$**", "", false);
					builder.addField("**$**", "", false);
//					builder.addField("**$**", "", false);
					builder.setColor(PureCompetence.INSTANCE.clrBlue);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.HelpUI);
					builder.setTimestamp(OffsetDateTime.now());
					
					long messageId = message.getIdLong();
					
					event.getChannel().editMessageById(messageId, builder.build()).queue();
					
					message.clearReactions().complete();
					message.getChannel().addReactionById(messageId, home).queue();
					
					return;
				}
				
				// ---------------------------------------- Help UI end ----------------------------------------
				
				//Removing the reaction that does not fit, NOTE: This will cause reactions on the Bot to be removed. I might change that later lol
				message.removeReaction(event.getReactionEmote().getAsReactionCode(), event.getMember().getUser()).queue();
				
				builder.setTitle("**Unknown reaction!**");
				builder.setDescription("Please only react with the given choice, thank you =)");
				builder.setColor(PureCompetence.INSTANCE.clrRed);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.HelpUI);
				builder.setTimestamp(OffsetDateTime.now());
				message.getChannel().sendMessage(builder.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
				
				return;
				
			} catch(PermissionException e) {
				e.printStackTrace();
				
				builder.setTitle("Insufficient permissions");
				
				builder.setDescription("The `Help` command might not work as intended without the permission MESSAGE_ADD_REACTION");
				
				event.getChannel().sendMessage(builder.build()).queue();
			}
		
		}
		
		//----------------------------Help menu V1 end----------------------------
	}
	
	//Order for Emotes to be added
	//Main			Global UI	Private UI	Music UI	Setup UI		Credits UI	SetupHelp UI
	//Home/House 	Globe		E Mail		Music note 	Gear			Credit card	Newspaper
	//U+1f3e0 		U+1f310		U+1f4e7		U+1f3b5 	U+2699U+fe0f	U+1f4b3		U+1f4f0
	
	@Override
	public void onGuildUpdateBoostCount(GuildUpdateBoostCountEvent event) {
		
	}
	
	@Override
	public void onGuildUpdateBoostTier(GuildUpdateBoostTierEvent event) {
		
	}
	
}
