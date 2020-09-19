package de.cake.commands.serverCommands.text;

import java.time.OffsetDateTime;

import de.cake.PureCompetence;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class HelpCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		System.out.println("-------------------------- \r" + "[LOG] | Command | " + getClass() + "\r"
				+ m  + "\r" + m.getPermissionsExplicit()
				+ "\r--------------------------");
		
		String[] args = message.getContentDisplay().split(" ");
		message.delete().queue();
		
		EmbedBuilder cmdH = new EmbedBuilder();
		EmbedBuilder builder = new EmbedBuilder();
		builder.setThumbnail("https://cdn.discordapp.com/attachments/564208162425798666/695368271465283694/WGPlayers.png");
		builder.setColor(PureCompetence.INSTANCE.clrBlue);
		builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
		builder.setTimestamp(OffsetDateTime.now());
		int i = 0;
		
		try {
			
			if(args.length == 1) {
				builder.setTitle("**BotHelp** \r");
				
				builder.addField("**--Further Command Information--**", "This is only a basic overview of all commands, for further Information on a specific command use **help [command]**. For example: **$help play**", false);
				
				builder.addField("***___Server Commands___***", "", false);
				
				builder.addField("**--General Commands--**", "", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Meme [meme]**", "Post a stupid Meme", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Hi [@user]**", "Greets you or the specified user!", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Date**", "Tells you the current date", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Clear [amount messages]**", "[Admin Command] Clears specified amount of Messages above", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Hug [@user]**", "Hug yourself or specified user", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Preview / pvw**", "Type anything after this! =)", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Re/Ree/Reee/Reeee**", "Does what the command suggests", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Info [@user]**", "Retrieves Information about the Specified User/Yourself **!!Does not work correctly when Status includes 'Activity'!!**", false);
				
				builder.addBlankField(false);
				builder.addField("**--Music Commands--**", "", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Play [next] [youtube search/url]**", "Plays the Sound of the video. The `[next]` is optional and determines if the track should be played directly after the currently playing track or be queued in the regular queue. Currently Supports YouTube, Soundcloud, Vimeo, Twitch Livestreams, Bandcamps and URLs", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Pause / p**", "Pauses/Resumes the current track (Toggle)", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Stop**", "Disconnects the bot and clears the queue", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Skipfw [amount]**", "Skipfw [amount] of seconds forward/backward", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Playlist Info**", "Shows a list of further Commands for premade playlists/playlist commands", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Volume / Vol [0-100]**", "Changes the volume", false);
				
				builder.addBlankField(false);
				builder.addField("***___Private Commands___***", "", false);
				
				builder.addField(PureCompetence.INSTANCE.prefix + "**Help**", "Shows this Command list.", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Hi**", "Greets you!", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Info [@user]**", "Shows your details or tells you about the bot.", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**SendMsg**", "This Command is basically a messenger within the Bot, the messages are not logged! If you get spammed by someone report the issue to @xXConfusedJenni#5117 For further help on this command please use **$help sendmsg**.", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**DeleteMsg**", "Used to delete the latest message sent with the **SendMsg** command.", false);
				
				builder.addBlankField(false);
				builder.addField("**--Passive Skills on Servers--**", "", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Temp Channels**", "If a User joins a channel (For example) called **'➡ |Temp Channels'** the Bot creates a temporary "
						+ "channel in the Category that the '➡ |Temp Channels' is in, the temporary channel called **[⏳ | {UserName}]**. It will be deleted when 0 users are in the channel. \r"
						+ "**!!Note - For this to work the Channel name needs to contain the word 'temp'.**", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Music**", "Use Pure Competence as your DJ! For further details see the 'Music Command' section.", false);
				//builder.addField(PureCompetence.INSTANCE.prefix + "", "", false);
				
				builder.addBlankField(false);
				builder.addField("**--Dev Commands--**", "", false);
				builder.addField("**--Information about Dev Commands--**", "Dev Commands are reserved for the Developer and helpers only! You (most likely) won't be able to use these Commands.", false);
				builder.addField(PureCompetence.INSTANCE.prefix + "**Restart**", "Restarts the Bot, closes all Shards and Restarts he Bot, this usually takes about .5 seconds for each Shard.", false);
				
				builder.addField("**--Further information--**", "", false);
				builder.addField("Many thanks to the people that helped me create this bot and fix all the bugs that I would've missed <3", "Cookie time!", false);
				
				m.getUser().openPrivateChannel().queue((ch) -> {
					ch.sendMessage(builder.build()).queue();
				});
			} else if(args.length == 2) {
				
				//Command specific help (help me)
				
				//Meme
				if(args[1].equalsIgnoreCase("meme")) {
					cmdH.setTitle("**Meme [meme] - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "meme noice", false);
					cmdH.addField("Description", "Will send a random picture with the topic \"noice\".", false);
					
					i++;
				}
				
				else if(args[1].equalsIgnoreCase("hi")) {
					cmdH.setTitle("**Hi [@user] - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "hi @PureCompetence", false);
					cmdH.addField("Description", "Will greet the mentioned person.", false);
					
					i++;
				}
				
				else if(args[1].equalsIgnoreCase("date")) {
					cmdH.setTitle("**Date - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "Date", false);
					cmdH.addField("Description", "Sends the current date and time.", false);
					
					i++;
				}
				
				else if(args[1].equalsIgnoreCase("clear")) {
					cmdH.setTitle("**Clear [amount messages]**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "clear 2", false);
					cmdH.addField("Description", "Deletes the newest 2 messages as well as the command itself.", false);
				
					i++;
				}
				
				else if(args[1].equalsIgnoreCase("hug")) {
					cmdH.setTitle("**Hug [@user] - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "hug @PureCompetence", false);
					cmdH.addField("Description", "Sends a virtual hug to the mentioned person.", false);
				
					i++;
				}
				
				else if(args[1].equalsIgnoreCase("preview")) {
					cmdH.setTitle("**Preview / pvw - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "preview This stonks like the stonk that stoinks.", false);
					cmdH.addField("Description", "Sends the typed message after the command. You can short link URLs or do what ever you want!", false);
				
					i++;
				} else if(args[1].equalsIgnoreCase("pvw")) {
					cmdH.setTitle("**Preview / pvw**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "preview This stonks like the stonk that stoinks.", false);
					cmdH.addField("Description", "Sends the typed message after the command. You can short link URLs or do what ever you want!", false);
				
					i++;
				}
				
				else if(args[1].startsWith("R") && args[1].startsWith("e", 1)) {
					cmdH.setTitle("**Re/Ree/Reee/Reeee - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "ree", false);
					cmdH.addField("Description", "Does what the command suggests.", false);
				
					i++;
				}
				
				else if(args[1].equalsIgnoreCase("info")) {
					cmdH.setTitle("**Info [@user] - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "info @PureCompetence \r"
							+ PureCompetence.INSTANCE.prefix + "info", false);
					cmdH.addField("Description", "Is used to retrieve a lot of information about the specified user!", false);
				
					i++;
				}
				
				else if(args[1].equalsIgnoreCase("sendmsg")) {
					cmdH.setTitle("**SendMsg - Help**");
					cmdH.addField("Example of usage", "$sendmsg <" + m.getIdLong() + "> <Title> This Title should be linked to YouTube <Title>\r\n" + 
							"<TitleUrl> https://www.youtube.com/watch?v=dQw4w9WgXcQ <TitleUrl>\r\n" + 
							"<Description> This is the part where you can write your stuff. <Description>\r\n" + 
							"<Image> https://www.berkeleyside.com/wp-content/uploads/2019/04/Tree-by-cirrus-wood.jpeg <Image>\r\n" + 
							"<Thumbnail> https://www.berkeleyside.com/wp-content/uploads/2019/04/Tree-by-cirrus-wood.jpeg <Thumbnail>", false);
					cmdH.addField("Description", "$sendmsg <" + m.getIdLong() + "> <-- Defines the User to send the message to (Example is your own)\r\r\n" + 
							"<Title> This sets the Title, the TitleUrl is optional <Title>\r\r\n" + 
							"<TitleUrl> https://www.youtube.com/watch?v=dQw4w9WgXcQ <TitleUrl> <-- Brings you to the Internet Address if you click on the Title\r\r\n" + 
							"<Description> Write anything here, just make sure that you put `<Description>` at the end to mark where the description ends! <Description>\r\r\n" + 
							"<Image> https://www.berkeleyside.com/wp-content/uploads/2019/04/Tree-by-cirrus-wood.jpeg <Image> <-- Adds the Image at the bottom\r\r\n" + 
							"<Thumbnail> https://www.berkeleyside.com/wp-content/uploads/2019/04/Tree-by-cirrus-wood.jpeg <Thumbnail> <-- Adds the Image as a Thumbnail\r\r\n" +
							"**Please note: Everything in between the Markers (Markers are the Keywords like <Title>, <Description> etc.) will be ignored! The Markers need to be spaced!** \r"
							+ "You can get the ID Long for the Users through the **Info Command** on a Server or by using the Developer mode in Discord -> Right Clicking on the User you want to send the message to -> Copy ID", false);
				
					i++;
				}
				
				else if(args[1].equalsIgnoreCase("deletemsg")) {
					cmdH.setTitle("**Info [@user] - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "deletemsg", false);
					cmdH.addField("Description", "Is used to delte the latest message sent by the bot to that specific user.", false);
				
					i++;
				}
				
				
						//				\\
						//Music Commands\\
						//				\\
				else if(args[1].equalsIgnoreCase("play")) {
					cmdH.setTitle("**Play [next] [youtube search/url] - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "play despacito \r"
							+ PureCompetence.INSTANCE.prefix + "play https://www.youtube.com/watch?v=dQw4w9WgXcQ \r"
								+ PureCompetence.INSTANCE.prefix + "play next https://soundcloud.com/thepopposse/never-gonna-give-you-up", false);
					cmdH.addField("Description", "The bot will search for the tracks and connect to the voice channel you are in, it then will play the track you've searched for. You can use the play command while the bot is playing a track, it will add the searched track into the queue. \r"
							+ "The **play next** command puts the searched track into the first position of the queue, so it will be played when the current track is done or skipped.", false);
				
					i++;
				}
				
				else if(args[1].equalsIgnoreCase("pause")) {
					cmdH.setTitle("**Pause / p - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "pause \r"
							+ PureCompetence.INSTANCE.prefix + "p", false);
					cmdH.addField("Description", "Will pause the currently playing track. The command is used as a switch, meaning that after pausing you have to use the command agian to unpause the track.", false);
				
					i++;
				} else if(args[1].equalsIgnoreCase("p")) {
					cmdH.setTitle("**Pause / p - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "pause \r"
							+ PureCompetence.INSTANCE.prefix + "p", false);
					cmdH.addField("Description", "Will pause the currently playing track. The command is used as a switch, meaning that after pausing you have to use the command agian to unpause the track.", false);
				
					i++;
				}
				
				else if(args[1].equalsIgnoreCase("stop")) {
					cmdH.setTitle("**Stop - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "stop", false);
					cmdH.addField("Description", "Disconnects the bot and clears the queue.", false);
				
					i++;
				}
				
				else if(args[1].equalsIgnoreCase("skipfw")) {
					cmdH.setTitle("**Skipfw [amount] - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "skipfw 20 \r"
							+ PureCompetence.INSTANCE.prefix + "skipfw -20", false);
					cmdH.addField("Description", "Fast forwards/rewinds 20 seconds of the currently playing track.", false);
				
					i++;
				}
				
				else if(args[1].equalsIgnoreCase("playlist")) {
					cmdH.setTitle("**Playlist Info - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "playlist play edm \r"
							+ PureCompetence.INSTANCE.prefix + "playlist play info", false);
					cmdH.addField("Description", "Plays the specified playlist or gives further playlist information.", false);
				
					i++;
				}
				
				else if(args[1].equalsIgnoreCase("volume")) {
					cmdH.setTitle("**Volume [0 - 100] - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "volume 69 \r"
							+ PureCompetence.INSTANCE.prefix + "vol 88", false);
					cmdH.addField("Description",  "Sets the volume of the audio to specified amount, the bot joins with a default volume of 10.", false);
				
					i++;
				} else if(args[1].equalsIgnoreCase("vol")) {
					cmdH.setTitle("**Volume [0 - 100] - Help**");
					cmdH.addField("Example of usage", PureCompetence.INSTANCE.prefix + "volume 69 \r"
							+ PureCompetence.INSTANCE.prefix + "vol 88", false);
					cmdH.addField("Description", "Sets the volume of the audio to specified amount, the bot joins with a default volume of 10.", false);
				
					i++;
				}
				
				if(i != 0) {
					cmdH.setThumbnail("https://cdn.discordapp.com/attachments/564208162425798666/695368271465283694/WGPlayers.png");
					cmdH.setColor(PureCompetence.INSTANCE.clrBlue);
					cmdH.setFooter(PureCompetence.INSTANCE.pwrdBy);
					cmdH.setTimestamp(OffsetDateTime.now());
					
					m.getUser().openPrivateChannel().queue((ch) -> {
						ch.sendMessage(cmdH.build()).queue();
					});
				} else {
					builder.setTitle("**BotHelp** \r");
					
					builder.addField("**--Further Command Information--**", "For further Information on a specific command use **help [command]**. For example: **$help play**", false);
					builder.addBlankField(false);
					
					builder.addField("**--General Commands--**", "", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Meme [meme]**", "Post a stupid Meme", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Hi [@user]**", "Greets you or the specified user!", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Date**", "Tells you the current date", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Clear [amount messages]**", "[Admin Command] Clears specified amount of Messages above", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Hug [@user]**", "Hug yourself or specified user", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Preview / pvw**", "Type anything after this! =)", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Re/Ree/Reee/Reeee**", "Does what the command suggests", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Info [@user]**", "Retrieves Information about the Specified User/Yourself **!!Does not work when Status includes 'Activity'!!**", false);
					
					builder.addBlankField(false);
					builder.addField("**--Music Commands--**", "", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Play [next] [youtube search/url]**", "Plays the Sound of the video. The `[next]` is optional and determines if the track should be played directly after the currently playing track or be queued in the regular queue. Currently Supports YouTube, Soundcloud, Vimeo, Twitch Livestreams, Bandcamps and URLs", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Pause / p**", "Pauses/Resumes the current track (Toggle)", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Stop**", "Disconnects the bot and clears the queue", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Skipfw [amount]**", "Skipfw [amount] of seconds forward/backward", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Playlist Info**", "Shows a list of further Commands for premade playlists/playlist commands", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Volume / Vol [0-100]**", "Changes the volume", false);
					
					builder.addBlankField(false);
					builder.addField("**--Passive Skills--**", "", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Temp Channels**", "If a User joins a channel (For example) called **'➡ |Temp Channels'** the Bot creates a temporary "
							+ "channel in the Category that the '➡ |Temp Channels' is in, the temporary channel called **[⏳ | {UserName}]**. It will be deleted when 0 users are in the channel. \r"
							+ "**!!Note - For this to work the Channel name needs to contain the word 'temp'.**", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Music**", "Use Pure Competence as your DJ! For further details see the 'Music Command' section.", false);
					//builder.addField(PureCompetence.INSTANCE.prefix + "", "", false);
					
					builder.addBlankField(false);
					builder.addField("**--Dev Commands--**", "", false);
					builder.addField("**--Information about Dev Commands--**", "Dev Commands are reserved for the Developer and helpers only! You (most likely) won't be able to use these Commands.", false);
					builder.addField(PureCompetence.INSTANCE.prefix + "**Restart**", "Restarts the Bot, closes all Shards and Restarts he Bot, this usually takes about .5 seconds for each Shard.", false);

					builder.addField("**--Further information--**", "", false);
					builder.addField("Many thanks to the people that helped me create this bot and fix all the bugs that I would've missed <3", "Cookie time!", false);
					
					m.getUser().openPrivateChannel().queue((ch) -> {
						ch.sendMessage(builder.build()).queue();
					});
				}
			
			}
			
		} catch(ArrayIndexOutOfBoundsException e) {
			
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
