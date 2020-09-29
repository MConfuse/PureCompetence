package de.cake.musicController;

import java.time.OffsetDateTime;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.cake.PureCompetence;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class AudioLoadResult implements AudioLoadResultHandler {

	private final MusicController controller;
	private final String uri;
	private final Long id;
	private final Long idM;
	private final Integer pos;
	private final TextChannel channel;
	private final VoiceChannel vc;
	private final int spos;
	private final Member m;
	EmbedBuilder builder = new EmbedBuilder();

	/**
	 * Adds a AudioTrack to the queue.
	 * 
	 * controller = MusicController from the Guild uri = URL from the Audio Track id
	 * = Text Channel ID idM = Message ID pos = Position in Queue (-1 for normal
	 * add, -998 for YouTubeSearchCommand, -999 for top) channel = Text Channel vc =
	 * Voice Channel spos = Position of the Track in the Search list (-1 is
	 * recommended) m = Member that sent the command
	 * 
	 * @param controller
	 * @param uri
	 * @param id
	 * @param idM
	 * @param pos
	 * @param channel
	 * @param vc
	 * @param spos
	 * @param m
	 */
	public AudioLoadResult(MusicController controller, String uri, Long id, Long idM, Integer pos, TextChannel channel,
			VoiceChannel vc, Integer spos, Member m)
	{
		this.controller = controller;
		this.uri = uri;
		this.id = id;
		this.idM = idM;
		this.pos = pos;
		this.channel = channel;
		this.vc = vc;
		this.spos = spos;
		this.m = m;
	}

	@Override
	public void trackLoaded(AudioTrack track)
	{

		Queue queue = controller.getQueue();
		OpenVoiceConnection ovc = new OpenVoiceConnection(channel.getGuild().getIdLong(), vc);
		AudioManager manager = PureCompetence.INSTANCE.shardMan.getGuildById(channel.getGuild().getIdLong())
				.getAudioManager();

		if (manager.isConnected() == true)
		{
			builder.setTitle("**Track added to queue**");

			builder.setDescription("Track added: [" + track.getInfo().title + "](" + track.getInfo().uri + ")");
			builder.setColor(PureCompetence.INSTANCE.clrGreen);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.PlayMsg);
			builder.setTimestamp(OffsetDateTime.now());
		}
		else
		{
			ovc.OpenVoice();
			builder.setTitle("**Track loaded**");

			builder.setDescription("Now playing: [" + track.getInfo().title + "](" + track.getInfo().uri + ")");
			builder.setColor(PureCompetence.INSTANCE.clrGreen);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.PlayMsg);
			builder.setTimestamp(OffsetDateTime.now());
		}

		if (pos == -999)
		{
			queue.addTrackToTopOfQueue(track, 0);
		}
		else if (pos >= 0 && !(pos < 0))
		{
			queue.addTrackToTopOfQueue(track, pos);
		}
		else
		{
			queue.addTrackToQueue(track);
		}

		controller.getGuild().getTextChannelById(id).editMessageById(idM, builder.build()).queue();
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist)
	{

//		YouTubeSearchCommand.java - Searches
		if (pos == -998)
		{
			returnSearchResult(playlist, spos, channel.getGuild(), channel.getIdLong(), m);

			return;
		}

		Queue queue = controller.getQueue();
		OpenVoiceConnection ovc = new OpenVoiceConnection(channel.getGuild().getIdLong(), vc);
		AudioManager manager = PureCompetence.INSTANCE.shardMan.getGuildById(channel.getGuild().getIdLong())
				.getAudioManager();

		if (manager.isConnected() == true)
		{
			builder.setTitle("**Track added to queue**");

			builder.setDescription("Track added: [" + playlist.getTracks().get(0).getInfo().title + "]("
					+ playlist.getTracks().get(0).getInfo().uri + ")");
			builder.setColor(PureCompetence.INSTANCE.clrGreen);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.PlayMsg);
			builder.setTimestamp(OffsetDateTime.now());
		}
		else
		{
			ovc.OpenVoice();
			builder.setTitle("**Track loaded**");

			builder.setDescription("Now playing: [" + playlist.getTracks().get(0).getInfo().title + "]("
					+ playlist.getTracks().get(0).getInfo().uri + ")");
			builder.setColor(PureCompetence.INSTANCE.clrGreen);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.PlayMsg);
			builder.setTimestamp(OffsetDateTime.now());
		}

		if (uri.startsWith("ytsearch: "))
		{

			if (pos == -999)
			{

				queue.addTrackToTopOfQueue(playlist.getTracks().get(0), 0);

				controller.getGuild().getTextChannelById(id).editMessageById(idM, builder.build()).queue();
				return;
			}
			else if (pos >= 0 && !(pos < 0))
			{

				queue.addTrackToTopOfQueue(playlist.getTracks().get(0), pos);

				controller.getGuild().getTextChannelById(id).editMessageById(idM, builder.build()).queue();
				return;
			}
			else
			{

				queue.addTrackToQueue(playlist.getTracks().get(0));

				controller.getGuild().getTextChannelById(id).editMessageById(idM, builder.build()).queue();
				return;
			}

		}

		controller.getGuild().getTextChannelById(id).editMessageById(idM, builder.build()).queue();

		int added = 0;

		for (AudioTrack track : playlist.getTracks())
		{
			queue.addTrackToQueue(track);
			added++;
		}

		EmbedBuilder builder = new EmbedBuilder().setColor(PureCompetence.INSTANCE.clrGreen)
				.setDescription(added + " tracks were added to queue.");

		MusicUtil.sendEmbed(controller.getGuild().getIdLong(), builder);
	}

	@Override
	public void noMatches()
	{
		EmbedBuilder builder = new EmbedBuilder().setColor(PureCompetence.INSTANCE.clrRed)
				.setTitle("**No Videos found**")
				.setDescription("Couldn't find a Video matching the search queue or link!")
				.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.PlayMsg)
				.setTimestamp(OffsetDateTime.now());

		controller.getGuild().getTextChannelById(id).editMessageById(idM, builder.build()).queue();
	}

	@Override
	public void loadFailed(FriendlyException exception)
	{
		exception.printStackTrace();
		System.out.println(exception.getCause());
		EmbedBuilder builder = new EmbedBuilder().setColor(PureCompetence.INSTANCE.clrRed)
				.setTitle("**Loading failed**")
				.setDescription("Couldn't load the Video due to an error! Please try again later!")
				.setFooter(PureCompetence.INSTANCE.pwrdBy + ", " + PureCompetence.INSTANCE.PlayMsg)
				.setTimestamp(OffsetDateTime.now());

		controller.getGuild().getTextChannelById(id).editMessageById(idM, builder.build()).queue();
	}

	/**
	 * Used to retrieve the first 5 results of the LavaPlayer when searching for the
	 * specified Keywords, if a number was specified it will play the Track at the
	 * position.
	 * 
	 * @param playlist
	 * @param pos
	 * @param guild
	 * @param id
	 * @param m
	 */
	private void returnSearchResult(AudioPlaylist playlist, int pos, Guild guild, long id, Member m)
	{
		StringBuilder builder = new StringBuilder();
		EmbedBuilder ebuilder = new EmbedBuilder();

		if (pos != -1)
		{
			playFromSearch(playlist.getTracks().get(pos), guild.getTextChannelById(id), guild, m);
		}

		for (int i = 0; i < 5; i++)
		{
			AudioTrack track = playlist.getTracks().get(i);

			builder.append(i + 1 + ". [" + track.getInfo().title + "](" + track.getInfo().uri + ") - "
					+ track.getInfo().author + "\r");
		}

		ebuilder.setTitle("**Search Command - YouTube results**");
		ebuilder.addField("Resulting Tracks - Channel", builder.toString(), false);
		ebuilder.addField("Playing listed Track", "To play one of the 5 Listed tracks, use `" + PureCompetence.INSTANCE.prefix + "search [Position in list] Unusual Memes", false);

		ebuilder.setColor(PureCompetence.INSTANCE.clrGreen);
		ebuilder.setFooter(PureCompetence.INSTANCE.pwrdBy + ", Search V0.1");
		ebuilder.setTimestamp(OffsetDateTime.now());

		guild.getTextChannelById(id).sendMessage(ebuilder.build()).queue();
	}

	/**
	 * Used to play directly from the Results of the YouTubeSearchCommand
	 * 
	 * @param track
	 * @param channel
	 * @param guild
	 * @param m
	 */
	private void playFromSearch(AudioTrack track, TextChannel channel, Guild guild, Member m)
	{
		GuildVoiceState state = m.getVoiceState();
		if (state != null)
		{
			VoiceChannel vc = state.getChannel();
			if (vc != null)
			{
				MusicController controller = PureCompetence.INSTANCE.playerManager
						.getController(vc.getGuild().getIdLong());
				AudioPlayerManager apm = PureCompetence.INSTANCE.audioPlayerManager;
				EmbedBuilder builder = new EmbedBuilder();

				builder.setTitle("**Adding Track to queue**");
				builder.setColor(PureCompetence.INSTANCE.clrGreen);
				builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
				builder.setTimestamp(OffsetDateTime.now());

				apm.loadItem(track.getIdentifier(),
						new AudioLoadResult(controller, track.getInfo().uri, channel.getIdLong(),
								channel.sendMessage(builder.build()).complete().getIdLong(), -1, channel,
								state.getChannel(), -1, m));
			}

		}

	}

}
