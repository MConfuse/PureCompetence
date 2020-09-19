package de.cake.commands.serverCommands.text;

import java.time.OffsetDateTime;
import java.util.concurrent.ThreadLocalRandom;

import de.cake.PureCompetence;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class MemeCommand implements ServerCommand{

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		System.out.println("-------------------------- \r" + "[LOG] | Command | " + getClass() + "\r"
				+ m  + "\r" + m.getPermissions()
				+ "\r--------------------------");
		
		EmbedBuilder builder = new EmbedBuilder();
		String[] args = message.getContentDisplay().split(" ");
		
	//arg	0 	1	2
		//§meme no u
		
		try {
			//If no meme was specified send Command Help
			if(args.length == 1) {
				builder.setTitle("**ArrayIndexOutOfBoundsException || Command usage** \r");
				builder.setDescription(PureCompetence.INSTANCE.prefix + "**Meme No U** - Sends a random 'No U' Meme \r"
						+ PureCompetence.INSTANCE.prefix + "**Meme Oof** - Sends a Meme with Oof effect \r"
						+ PureCompetence.INSTANCE.prefix + "**Meme Sleepy** - Sends a random picture/gif with a sleepy theme \r"
						+ PureCompetence.INSTANCE.prefix + "**Meme Hmmm** - Sends a Meme with hmmm effect \r"
						+ PureCompetence.INSTANCE.prefix + "**Meme Nsfw** - Sends a NSFW Meme \r"
						+ PureCompetence.INSTANCE.prefix + "**Meme Smile** - Makes you (maybe) smile \r"
						+ PureCompetence.INSTANCE.prefix + "**Meme Noice** - Sends a Noice Gif \r"
						+ "\r"
						+ m.getAsMention() + ", you wrote: " + message.getContentDisplay());
				builder.setColor(PureCompetence.INSTANCE.clrRed);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builder.setTimestamp(OffsetDateTime.now());
				channel.sendMessage(builder.build()).queue();
			}
			
			//If 1-2 extra words were added it'll go through this
			if(args.length >= 2 && args.length <= 3) {
				//memes with no u
				if("no".equalsIgnoreCase(args[1])) {
					//if arg[2] is not 'u', send command help for [$meme no]
					try {
						if("u".equalsIgnoreCase(args[2])) {
							int min = 1;	//min int for random gen
							int max = 3;	//max int for rancom gen
							int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
							if(randomNum == 1) {
								builder.setDescription("**No U!!**");
								builder.setImage("https://cdn.discordapp.com/attachments/298089354478354433/646752435599310859/No_u_Shield_break_2.0.jpg");
								builder.setColor(PureCompetence.INSTANCE.clrBlue);
								builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
								builder.setTimestamp(OffsetDateTime.now());
								channel.sendMessage(builder.build()).queue();
							} else if(randomNum == 2) {
								builder.setDescription("**No U!!**");
								builder.setImage("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fimages7.memedroid.com%2Fimages%2FUPLOADED530%2F5aad42b373dfe.jpeg&f=1&nofb=1");
								builder.setColor(PureCompetence.INSTANCE.clrBlue);
								builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
								builder.setTimestamp(OffsetDateTime.now());
								channel.sendMessage(builder.build()).queue();
							} else if(randomNum == 3) {
								builder.setDescription("**No U!!**");
								builder.setImage("https://cdn.discordapp.com/attachments/637695358373199879/668450529243955201/no_u.png");
								builder.setColor(PureCompetence.INSTANCE.clrBlue);
								builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
								builder.setTimestamp(OffsetDateTime.now());
								channel.sendMessage(builder.build()).queue();
							}
						}
						//if no third arg[] is specified, catch the error resulting and send the help
					} catch (ArrayIndexOutOfBoundsException e) {
						builder.setTitle("**Command usage** \r");
						builder.setDescription(PureCompetence.INSTANCE.prefix + "**Meme No [U]** - Sends a random 'No U' Meme \r"
								+ "\r"
								+ m.getAsMention() + ", you wrote: " + message.getContentDisplay());
						builder.setColor(PureCompetence.INSTANCE.clrRed);
						builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
						builder.setTimestamp(OffsetDateTime.now());
						channel.sendMessage(builder.build()).queue();
					}
				
				}
				//Memes with Oof
				if("oof".equalsIgnoreCase(args[1])) {
					builder.setDescription("**Oof!!**");
					builder.setImage("https://media1.tenor.com/images/a503ba22d2f94951ae96a388e84aefe4/tenor.gif?itemid=3394840");
					builder.setColor(PureCompetence.INSTANCE.clrBlue);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
				//Sleepy Memes
				if("sleepy".equalsIgnoreCase(args[1])) {
					builder.setTitle("**Z** Z z");
					builder.setImage("https://media.tenor.com/images/97e8cffcbe7b883affc84c090e19756d/tenor.gif");
					builder.setColor(PureCompetence.INSTANCE.clrBlue);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
				//Memes with Hmmm
				if("hmmm".equalsIgnoreCase(args[1])) {
					int min = 1;	//min int for random gen
					int max = 2;	//max int for rancom gen
					int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
					if(randomNum == 1) {
						builder.setTitle("**HmMmMm**");
						builder.setImage("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi1.ytimg.com%2Fvi%2F8T03RRxrfSU%2Fmqdefault.jpg&f=1");
						builder.setColor(PureCompetence.INSTANCE.clrBlue);
						builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
						builder.setTimestamp(OffsetDateTime.now());
						channel.sendMessage(builder.build()).queue();
					} else if(randomNum == 2) {
						builder.setTitle("**HmMmMm**");
						builder.setImage("https://cdn.discordapp.com/attachments/255694184042856448/668898120297349121/IMG_6783.jpg");
						builder.setColor(PureCompetence.INSTANCE.clrBlue);
						builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
						builder.setTimestamp(OffsetDateTime.now());
						channel.sendMessage(builder.build()).queue();
					}
					
				}
				//Dumb NSFW Memes - Nothing Sexual
				if("nsfw".equalsIgnoreCase(args[1])) {
					builder.setTitle("**You wanted it**");
					builder.setImage("https://cdn.discordapp.com/attachments/255694184042856448/668898148457906181/IMG_20200119_212754.jpg");
					builder.setColor(PureCompetence.INSTANCE.clrBlue);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
				//smily memes
				if("smile".equalsIgnoreCase(args[1])) {
					builder.setTitle("**Look at that doggo**");
					builder.setImage("https://i.imgur.com/bDi9AwM.gifv");
					builder.setColor(PureCompetence.INSTANCE.clrBlue);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
				
				if("noice".equalsIgnoreCase(args[1])) {
					builder.setTitle("**Noice**");
					builder.setImage("https://cdn.discordapp.com/attachments/670375315410780160/692489704309784586/Noice.jpg");
					builder.setColor(PureCompetence.INSTANCE.clrBlue);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
				
				if("owl".equalsIgnoreCase(args[1])) {
					builder.setTitle("**O w l**");
					builder.setImage("https://cdn.discordapp.com/attachments/670375315410780160/692488094284513450/images.jpeg");
					builder.setColor(PureCompetence.INSTANCE.clrBlue);
					builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
				
			}
		
		} catch (ArrayIndexOutOfBoundsException e) {
			builder.setTitle("**ArrayIndexOutOfBoundsException || Command usage** \r");
			builder.setDescription(PureCompetence.INSTANCE.prefix + "**Meme No U** - Sends a random 'No U' Meme \r"
					+ PureCompetence.INSTANCE.prefix + "**Meme Oof** - Sends a Meme with Oof effect \r"
					+ PureCompetence.INSTANCE.prefix + "**Meme Sleepy** - Sends a random picture/gif with a sleepy theme \r"
					+ PureCompetence.INSTANCE.prefix + "**Meme Hmmm** - Sends a Meme with hmmm effect \r"
					+ PureCompetence.INSTANCE.prefix + "**Meme Nsfw** - Sends a NSFW Meme \r"
					+ PureCompetence.INSTANCE.prefix + "**Meme Smile** - Makes you (maybe) smile \r"
					+ PureCompetence.INSTANCE.prefix + "**Meme Noice** - Sends a Noice Gif \r"
					+ "\r"
					+ m.getAsMention() + ", you wrote: " + message.getContentDisplay());
			builder.setColor(PureCompetence.INSTANCE.clrRed);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
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
