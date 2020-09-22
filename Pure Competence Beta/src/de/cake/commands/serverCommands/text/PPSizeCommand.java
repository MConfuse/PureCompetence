package de.cake.commands.serverCommands.text;

import java.util.concurrent.ThreadLocalRandom;

import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class PPSizeCommand implements ServerCommand {

	/**
	 * Useless command which was requested by x_gaming.
	 * 
	 * @return Returns the users PP Size.
	 * 
	 * @see types#ServerCommand
	 * @see manager#ServerCommandManager
	 */
	@Override
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild) {
		
		int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
		
		StringBuilder sbf = new StringBuilder("8");
		
		for(int i = 0; i < randomNum; i++) {
			sbf.append("=");
		}
		
		channel.sendMessage(sbf + "D").queue();
		
	}

}
