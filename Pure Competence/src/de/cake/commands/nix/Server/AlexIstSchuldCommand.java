package de.cake.commands.nix.Server;

import java.time.OffsetDateTime;
import java.util.concurrent.ThreadLocalRandom;

import de.cake.PureCompetence;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class AlexIstSchuldCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		String[] args = message.getContentDisplay().split(" ");
		EmbedBuilder builder = new EmbedBuilder();
		String footer = "Alex's fault";
		
		try {
			if("bacc".equalsIgnoreCase(args[1])) {
				if(m.getId().equals("231777358116421633") || m.getId().equals("255313111391666176") || m.getId().equals("391244958323179532")) {
					int min2 = 0;
					int max2 = 5;
					
					int min3 = 0;
					int max3 = 8;
					
					int min4 = 0;
					int max4 = 8;
					
					int min5 = 0;
					int max5 = 8;
					
					int randomNum2 = ThreadLocalRandom.current().nextInt(min2, max2 + 1);
					int randomNum3 = ThreadLocalRandom.current().nextInt(min3, max3 + 1);
					int randomNum4 = ThreadLocalRandom.current().nextInt(min4, max4 + 1);
					int randomNum5 = ThreadLocalRandom.current().nextInt(min5, max5 + 1);
					
					builder.setTitle("Picc");
					builder.setImage("http://media.obutts.ru/butts_preview/0" + randomNum2 + randomNum3 + randomNum4 + randomNum5 + ".jpg");
					builder.setFooter(footer);
					builder.setColor(PureCompetence.INSTANCE.clrRed);
					channel.sendMessage(builder.build()).queue();
				}
				
			} else if("front".equalsIgnoreCase(args[1])) {
				if(m.getId().equals("231777358116421633") || m.getId().equals("255313111391666176") || m.getId().equals("391244958323179532")) {
					//int min = 0;
					//int max = 1;
					
					int min2 = 0;
					int max2 = 8;
					
					int min3 = 0;
					int max3 = 8;
					
					int min4 = 0;
					int max4 = 8;
					
					int min5 = 0;
					int max5 = 8;
					
					int randomNum2 = ThreadLocalRandom.current().nextInt(min2, max2 + 1);
					int randomNum3 = ThreadLocalRandom.current().nextInt(min3, max3 + 1);
					int randomNum4 = ThreadLocalRandom.current().nextInt(min4, max4 + 1);
					int randomNum5 = ThreadLocalRandom.current().nextInt(min5, max5 + 1);
					
					builder.setTitle("Front");
					builder.setImage("http://media.oboobs.ru/boobs_preview/0" + randomNum2 + randomNum3 + randomNum4 + randomNum5 + ".jpg");
					builder.setFooter(footer);
					builder.setColor(PureCompetence.INSTANCE.clrRed);
					channel.sendMessage(builder.build()).queue();
			} else {
				builder.setTitle("Unknown Command");
				builder.setDescription("Please try '" + PureCompetence.INSTANCE.prefix + "help' for more info");
				builder.setColor(PureCompetence.INSTANCE.clrRed);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builder.setTimestamp(OffsetDateTime.now());
				channel.sendMessage(builder.build()).queue();
			}
			
		}
			
		} catch (ArrayIndexOutOfBoundsException e) {
			builder.setTitle("Unknown Command");
			builder.setDescription("Please try '" + PureCompetence.INSTANCE.prefix + "help' for more info");
			builder.setColor(PureCompetence.INSTANCE.clrRed);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
		}
		
	}

}
