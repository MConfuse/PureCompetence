package de.cake.commands.privateCommands.text;

import java.time.OffsetDateTime;
import de.cake.PureCompetence;
import de.cake.commands.PrivateIDRetriever;
import de.cake.commands.types.PrivateCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;

public class SendMessageCommand implements PrivateCommand {

	@Override
	public void performCommand(User u, PrivateChannel channel, Message message) {

		PrivateIDRetriever retriever = new PrivateIDRetriever();
		String[] args = message.getContentDisplay().split(" ");
		String[] title = message.getContentDisplay().split("<Title>");
		String[] titleurl = message.getContentDisplay().split("<TitleUrl>");
		String[] description = message.getContentDisplay().split("<Description>");
		String[] thumbnail = message.getContentDisplay().split("<Thumbnail>");
		String[] image = message.getContentDisplay().split("<Image>");
		EmbedBuilder builder = new EmbedBuilder();
		EmbedBuilder funny = new EmbedBuilder();
		EmbedBuilder error = new EmbedBuilder().setTitle("Command Usage").setDescription("$sendmsg <" + u.getIdLong() + "> <-- Defines the User to send the message to (Example is your own)\r\r\n" + 
				"<Title> This sets the Title, the TitleUrl is optional <Title>\r\r\n" + 
				"<TitleUrl> https://www.youtube.com/watch?v=dQw4w9WgXcQ <TitleUrl> <-- Brings you to the Internet Address if you click on the Title\r\r\n" + 
				"<Description> Write anything here, just make sure that you put `<Description>` at the end to mark where the description ends! <Description>\r\r\n" + 
				"<Image> https://www.berkeleyside.com/wp-content/uploads/2019/04/Tree-by-cirrus-wood.jpeg <Image> <-- Adds the Image at the bottom\r\r\n" + 
				"<Thumbnail> https://www.berkeleyside.com/wp-content/uploads/2019/04/Tree-by-cirrus-wood.jpeg <Thumbnail> <-- Adds the Image as a Thumbnail\r\r\n" +
				"**Please note: Everything in between the Markers (Markers are the Keywords like <Title>, <Description> etc.) will be ignored! The Markers need to be spaced!**"
				+ "You can get the ID Long for the Users through the **Info Command** on a Server or by using the Developer mode in Discord -> Right Clicking on the User you want to send the message to -> Copy ID")
				.setColor(PureCompetence.INSTANCE.clrViolet).setFooter(PureCompetence.INSTANCE.pwrdBy).setTimestamp(OffsetDateTime.now());

		try {
			if(args.length == 1) {
				
				channel.sendMessage(error.build()).queue();
			} else if(args[1].startsWith("<")) {
				
				Long mentionedID = Long.parseLong(args[1].substring(1, 19));
				User mentioned = PureCompetence.INSTANCE.shardMan.getUserById(mentionedID);
				
				//Checks if there is a marker for the Title
				if(!message.getContentDisplay().contains("<TitleUrl>") && message.getContentDisplay().contains("<Title>")) {
					
					builder.setTitle(title[1].toString());
				} else if(message.getContentDisplay().contains("<TitleUrl>") && message.getContentDisplay().contains("<Title>")) {
					
					builder.setTitle(title[1].toString(), titleurl[1].toString());
				}
				
				//Checks if there is a marker for the Description
				if(message.getContentDisplay().contains("<Description>")) {
					
					builder.setDescription(description[1].toString());
				}
				
				//Checks for the Thumbnail marker and uses the Provided URL to set the Thumbnail
				if(message.getContentDisplay().contains("<Thumbnail>")) {
					
					builder.setThumbnail(thumbnail[1].toString());
				}
				
				//Checks for the Image marker and uses the Provided URL to set the Image
				if(message.getContentDisplay().contains("<Image>")) {
					
					builder.setImage(image[1].toString());
				}
				
				builder.setAuthor("This message was sent by: " + u.getAsTag(), u.getAvatarUrl(), u.getAvatarUrl());
				builder.setColor(PureCompetence.INSTANCE.clrViolet);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builder.setTimestamp(OffsetDateTime.now());
				PureCompetence.INSTANCE.shardMan.getUserById(mentionedID).openPrivateChannel().queue((ch) -> {
					ch.sendMessage(builder.build()).queue();
				});
				
				EmbedBuilder builderFeedback = new EmbedBuilder(builder);
				EmbedBuilder builderID = new EmbedBuilder();
				builderID.setTitle("Information about the Message");
				builderID.addField("The Embed above is how the sent Message looks!", "The ID of the Message is `" + retriever.getMessageID(mentionedID) + "`", false);
				builderID.addField("Adressed to", mentioned.getAsTag(), false);
				builderID.setColor(PureCompetence.INSTANCE.clrBlue);
				builderID.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builderID.setTimestamp(OffsetDateTime.now());
				channel.sendMessage(builderFeedback.build()).queue();
				channel.sendMessage(builderID.build()).queue();
				
			} else if(args[2].startsWith("{")) {
				
			} else {
				
				channel.sendMessage(error.build()).queue();
			}
			
		} catch (UnsupportedOperationException e) {
			funny.setTitle("You achieved funny", "https://www.youtube.com/watch?v=dQw4w9WgXcQ");
			funny.addField("**__Don't put the Bots ID here you funny person!__**", "Just don't", false);
			funny.setColor(PureCompetence.INSTANCE.clrViolet);
			funny.setFooter(PureCompetence.INSTANCE.pwrdBy);
			funny.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(funny.build()).queue();
		}
		
		EmbedBuilder log = new EmbedBuilder();
		log.setTitle("[LOG] | Private Command | " + this.getClass());
		log.addField("User", u.getName() + " (ID Long: " + u.getId() + ")", false);
		log.addField("Private Channel", "" + u.getName(), false);
		log.setThumbnail(u.getAvatarUrl());
		log.setColor(PureCompetence.INSTANCE.clrRed);
		log.setFooter(PureCompetence.INSTANCE.pwrdBy);
		log.setTimestamp(OffsetDateTime.now());
		Guild guildid = PureCompetence.INSTANCE.shardMan.getGuildById(374507865521520651l);
		guildid.getTextChannelById(694967647380570163l).sendMessage(log.build()).queue();
	}

}
