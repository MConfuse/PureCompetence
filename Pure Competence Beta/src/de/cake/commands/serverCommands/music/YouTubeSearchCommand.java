package de.cake.commands.serverCommands.music;

import java.time.OffsetDateTime;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import de.cake.PureCompetence;
import de.cake.commands.types.ServerCommand;
import de.cake.musicController.AudioLoadResult;
import de.cake.musicController.MusicController;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class YouTubeSearchCommand implements ServerCommand {

	@Override
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild)
	{
		MusicController controller = PureCompetence.INSTANCE.playerManager.getController(guild.getIdLong());
		AudioPlayerManager apm = PureCompetence.INSTANCE.audioPlayerManager;
		String[] args = message.getContentDisplay().split(" ");
		int amount = -1;

		if (!(args.length > 1))
		{
			commandUsage(channel);
			
			return;
		}

//		If no position was specified it will just skip this and not play a Track, if it was specified
		try
		{
			amount = Integer.parseInt(args[1]);

			if (amount > 0)
				amount--;
			else if (amount < 0)
				amount = 0;
		}
		catch (NumberFormatException e)
		{

		}

		StringBuilder strBuilder = new StringBuilder();
		for (int i = 2; i < args.length; i++)
			strBuilder.append(args[i] + " ");

		String url = strBuilder.toString().trim();
		if (url.startsWith("http"))
		{
			commandUsage(channel);

			return;
		}
		else
		{
			url = "ytsearch: " + url;
		}

		apm.loadItem(url,
				new AudioLoadResult(controller, url, channel.getIdLong(), 0L, -998, channel, null, amount, m));
	}

	private void commandUsage(TextChannel channel)
	{
		EmbedBuilder builder = new EmbedBuilder();

		builder.addField("Playing listed Track", "To play one of the 5 Listed tracks, use `" + PureCompetence.INSTANCE.prefix + "search [Position in list] Unusual Memes`", false);
		builder.setDescription("Does not support URL's yet, if nothing Matching the search therm was found it currently displays nothing lmao");
		
		builder.setColor(PureCompetence.INSTANCE.clrGreen);
		builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", Search V0.1");
		builder.setTimestamp(OffsetDateTime.now());
		
		channel.sendMessage(builder.build()).queue();
	}

}
