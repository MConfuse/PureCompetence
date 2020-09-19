package de.cake.commands.privateCommands.text;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import de.cake.PureCompetence;
import de.cake.commands.types.PrivateCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;

public class DeleteMessageCommand implements PrivateCommand {

	@Override
	public void performCommand(User u, PrivateChannel channel, Message message) {

		String[] args = message.getContentDisplay().split(" ");
		EmbedBuilder builder = new EmbedBuilder().setColor(PureCompetence.INSTANCE.clrViolet)
				.setFooter(PureCompetence.INSTANCE.pwrdBy).setTimestamp(OffsetDateTime.now());
		EmbedBuilder error = new EmbedBuilder().setTitle("Command Usage").addField("Example of Usage", PureCompetence.INSTANCE.prefix + "deletemsg [index] \r"
				+ "Deletes the newest message from the newest Message Upwards. For example: \r"
				+ "Message 69 \r"
				+ "Message 420 \r"
				+ "$deletemsg 1 <-- This will delete **Message 420** because it is the newest and is at position 1.", false).setColor(PureCompetence.INSTANCE.clrViolet)
		.setFooter(PureCompetence.INSTANCE.pwrdBy).setTimestamp(OffsetDateTime.now());
		EmbedBuilder usernotification = new EmbedBuilder().setTitle("A message was deleted by " + u.getAsTag());
		
		//With Long ID
		if(args[1].startsWith("<")) {
			
			Long mentionedID = Long.parseLong(args[1].substring(1, 19));
			User mentioned = PureCompetence.INSTANCE.shardMan.getUserById(mentionedID);
			
			if(args.length == 3) {
				
				try {
					int amount = Integer.parseInt(args[2]);
					PrivateChannel channeel = mentioned.openPrivateChannel().complete();
					List<Message> messageID =  get(amount, mentioned, mentionedID);
					
					channeel.purgeMessages(messageID.get(amount - 1));
					builder.setTitle("Deleted message number " + amount);
					channel.sendMessage(builder.build()).queue();
					channeel.sendMessage(usernotification.build()).queue();
				} catch(ArrayIndexOutOfBoundsException e) {
					
					try {
						int amount = 1;
						PrivateChannel channeel = mentioned.openPrivateChannel().complete();
						List<Message> messageID =  get(amount, mentioned, mentionedID);
						
						channeel.purgeMessages(messageID.get(amount - 1));
						builder.setTitle("Deleted message number " + amount);
						channel.sendMessage(builder.build()).queue();
						channeel.sendMessage(usernotification.build()).queue();
					} catch(IndexOutOfBoundsException f) {
						
						PrivateChannel channeel = mentioned.openPrivateChannel().complete();
						
						builder.setTitle("No more Messages left in the Channel with the User: " + channeel.getUser().getAsTag());
						channel.sendMessage(builder.build()).queue();
						channeel.sendMessage(usernotification.build()).queue();
					}
					
				}
				
			}
			
			//With Name#Discriminator
		} else if(args[1].startsWith("{")) {
			
		} else {
			
			channel.sendMessage(error.build()).queue();
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

	public List<Message> get(int amount, User mentioned, Long mentionedID) {
		List<Message> messages = new ArrayList<>();
		
		int i = amount;
		
		for(Message message : mentioned.openPrivateChannel().complete().getIterableHistory().cache(false)) {
			if(message.getAuthor() != mentioned) {
				messages.add(message);
				if(--i <= 0) break;
			}
			
		}
		
		return messages;
	}
	
}
