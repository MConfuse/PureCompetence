package de.cake.commands.serverCommands.text;

import java.time.ZonedDateTime;

import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class DateCommand implements ServerCommand {

	/**
	 * Fixed Date Command, used to retrieve Date and Time if you, for some reason forgot lol
	 * 
	 * @return Returns Date and Time
	 * 
	 * @see types#ServerCommand
	 * @see manager#ServerCommandManager
	 */
	@Override
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild) {
		EmbedBuilder builder = new EmbedBuilder();
		ZonedDateTime time = ZonedDateTime.now();
		
		builder.setTitle("Current Date and Time");
		builder.addField("Date", time.getDayOfMonth() + "." + time.getMonthValue() + "." + time.getYear(), true);
		builder.addField("System Time", time.getHour() + ":" + time.getMinute() + ":" + time.getSecond(), true);
		
		channel.sendMessage(builder.build()).queue();
		
	}

}
