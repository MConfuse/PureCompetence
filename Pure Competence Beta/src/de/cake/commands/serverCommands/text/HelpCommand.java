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

	private String global = "U+1f310";
	private String Private = "U+1f4e7";
	private String music = "U+1f3b5";
	private String setup = "U+2699U+fe0f";
	private String credits = "U+1f4b3";
	
	@Override
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild) {
		
		EmbedBuilder builder = new EmbedBuilder();
		
		builder.setTitle("Bot help window!");
		
		builder.addField("Welcome to the Pure Competence help section!", "You can use the reactions underneath this message to Navigate!", false);
		builder.addField("**General commands**", "React with :globe_with_meridians: for all available text related Server commands!", false);
		builder.addField("**Private commands**", "React with :e_mail: for all available Private commands!", false);
		builder.addField("**Music Commands**", "React with :musical_note: to find the music related things!", false);
		builder.addField("**Bot Setup**", "React with :gear: to see further information about the Bots setup on your server! Requires Administrator permissions!", false);
		builder.addField("**Credits**", "React with :credit_card: to see who made this bot and other inside related information!", false);
//		builder.addField("****", "React with ", false);
		builder.setColor(PureCompetence.INSTANCE.clrBlue);
		builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
		builder.setTimestamp(OffsetDateTime.now());
		long messageId = channel.sendMessage(builder.build()).complete().getIdLong();
		
		channel.addReactionById(messageId, global).queue();
		channel.addReactionById(messageId, Private).queue();
		channel.addReactionById(messageId, music).queue();
		channel.addReactionById(messageId, setup).queue();				
		channel.addReactionById(messageId, credits).queue();
	}

}
