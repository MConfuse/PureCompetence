package de.cake.commands.serverCommands.text;

import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.ArrayList;

import de.cake.PureCompetence;
import de.cake.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class InfoCommand implements ServerCommand {

	/**
	 * This could take a rework due to confusing Code... but it works now (mostly)
	 * :D.
	 * 
	 * @return Returns info about specified User
	 * 
	 * @see types#ServerCommand
	 * @see manager#ServerCommandManager
	 */
	@Override
	public void performCommand(String command, Member m, TextChannel channel, Message message, Guild guild)
	{
		String[] args = message.getContentDisplay().split(" ");

		if (args.length == 1)
		{
			try
			{
				selfInfoWithActivity(m, channel, guild);
			}
			catch (IndexOutOfBoundsException e)
			{
				selfInfoNoActivity(m, channel, guild);
			}

		}
		else if (args.length >= 2 && message.getMentionedMembers().size() != 0)
		{
			Member mem = message.getMentionedMembers().get(0);

			try
			{
				otherInfoWithActivity(mem, channel, guild);
			}
			catch (IndexOutOfBoundsException e)
			{
				otherInfoNoActivity(mem, channel, guild);
			}

		}

	}

	private void selfInfoWithActivity(Member m, TextChannel channel, Guild guild)
	{
		EmbedBuilder builder = new EmbedBuilder();
		ArrayList<Activity> Activity = new ArrayList<>();
		Activity.addAll(m.getActivities());
		Color color = m.getColor();

//		NullPointerException if USers Color is Default
		try
		{
			builder.setTitle("Info about **" + m.getUser().getName() + "**");
			builder.addField("Name", m.getUser().getAsMention(), true);
			builder.addField("Discord ID", "" + m.getIdLong(), true);
			builder.addField("Joined Discord on", m.getTimeCreated().getDayOfMonth() + "/"
					+ m.getTimeCreated().getMonthValue() + "/" + m.getTimeCreated().getYear(), true);
			builder.addField("Joined Server", m.getTimeJoined().getDayOfMonth() + "/"
					+ m.getTimeJoined().getMonthValue() + "/" + m.getTimeJoined().getYear(), true);
			builder.addField("Display Color",
					"R: " + color.getRed() + " G: " + color.getGreen() + " B: " + color.getBlue(), true);
			builder.addField("Highest role", m.getRoles().get(0).getAsMention(), true);
			builder.addField("Activities",
					Activity.size() == 2 ? Activity.get(0).toString() + ", " + Activity.get(1).toString()
							: Activity.get(0).toString(),
					false);

			builder.setThumbnail(m.getUser().getAvatarUrl());
			builder.setColor(PureCompetence.INSTANCE.clrViolet);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
		}
		catch (NullPointerException e)
		{
			builder.clear();

			builder.setTitle("Info about **" + m.getUser().getName() + "**");
			builder.addField("Name", m.getUser().getAsMention(), true);
			builder.addField("Discord ID", "" + m.getIdLong(), true);
			builder.addField("Joined Discord on", m.getTimeCreated().getDayOfMonth() + "/"
					+ m.getTimeCreated().getMonthValue() + "/" + m.getTimeCreated().getYear(), true);
			builder.addField("Joined Server", m.getTimeJoined().getDayOfMonth() + "/"
					+ m.getTimeJoined().getMonthValue() + "/" + m.getTimeJoined().getYear(), true);
			builder.addField("Display Color", "R: 255 G: 255 B: 255", true);
			builder.addField("Highest role", m.getRoles().get(0).getAsMention(), true);
			builder.addField("Activities",
					Activity.size() == 2 ? Activity.get(0).toString() + ", " + Activity.get(1).toString()
							: Activity.get(0).toString(),
					false);

			builder.setThumbnail(m.getUser().getAvatarUrl());
			builder.setColor(PureCompetence.INSTANCE.clrViolet);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
		}

	}

	private void selfInfoNoActivity(Member m, TextChannel channel, Guild guild)
	{
		EmbedBuilder builder = new EmbedBuilder();
		Color color = m.getColor();

//		NullPointerException if USers Color is Default
		try
		{
			builder.setTitle("Info about **" + m.getUser().getName() + "**");
			builder.addField("Name", m.getUser().getAsMention(), true);
			builder.addField("Discord ID", "" + m.getIdLong(), true);
			builder.addField("Joined Discord on", m.getTimeCreated().getDayOfMonth() + "/"
					+ m.getTimeCreated().getMonthValue() + "/" + m.getTimeCreated().getYear(), true);
			builder.addField("Joined Server", m.getTimeJoined().getDayOfMonth() + "/"
					+ m.getTimeJoined().getMonthValue() + "/" + m.getTimeJoined().getYear(), true);
			builder.addField("Display Color",
					"R: " + color.getRed() + " G: " + color.getGreen() + " B: " + color.getBlue(), true);
			builder.addField("Highest role", m.getRoles().get(0).getAsMention(), true);
			builder.addField("Activities", "No Activities!", false);

			builder.setThumbnail(m.getUser().getAvatarUrl());
			builder.setColor(PureCompetence.INSTANCE.clrViolet);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
		}
		catch (NullPointerException e)
		{
			builder.clear();

			builder.setTitle("Info about **" + m.getUser().getName() + "**");
			builder.addField("Name", m.getUser().getAsMention(), true);
			builder.addField("Discord ID", "" + m.getIdLong(), true);
			builder.addField("Joined Discord on", m.getTimeCreated().getDayOfMonth() + "/"
					+ m.getTimeCreated().getMonthValue() + "/" + m.getTimeCreated().getYear(), true);
			builder.addField("Joined Server", m.getTimeJoined().getDayOfMonth() + "/"
					+ m.getTimeJoined().getMonthValue() + "/" + m.getTimeJoined().getYear(), true);
			builder.addField("Display Color", "R: 255 G: 255 B: 255", true);
			builder.addField("Highest role", m.getRoles().get(0).getAsMention(), true);
			builder.addField("Activities", "No Activities!", false);

			builder.setThumbnail(m.getUser().getAvatarUrl());
			builder.setColor(PureCompetence.INSTANCE.clrViolet);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
		}

	}

	private void otherInfoWithActivity(Member m, TextChannel channel, Guild guild)
	{
		EmbedBuilder builder = new EmbedBuilder();
		ArrayList<Activity> Activity = new ArrayList<>();
		Activity.addAll(m.getActivities());
		Color color = m.getColor();

//		NullPointerException if USers Color is Default
		try
		{
			builder.setTitle("Info about **" + m.getUser().getName() + "**");
			builder.addField("Name", m.getUser().getAsMention(), true);
			builder.addField("Discord ID", "" + m.getIdLong(), true);
			builder.addField("Joined Discord on", m.getTimeCreated().getDayOfMonth() + "/"
					+ m.getTimeCreated().getMonthValue() + "/" + m.getTimeCreated().getYear(), true);
			builder.addField("Joined Server", m.getTimeJoined().getDayOfMonth() + "/"
					+ m.getTimeJoined().getMonthValue() + "/" + m.getTimeJoined().getYear(), true);
			builder.addField("Display Color",
					"R: " + color.getRed() + " G: " + color.getGreen() + " B: " + color.getBlue(), true);
			builder.addField("Highest role", m.getRoles().get(0).getAsMention(), true);
			builder.addField("Activities",
					Activity.size() == 2 ? Activity.get(0).toString() + ", " + Activity.get(1).toString()
							: Activity.get(0).toString(),
					false);

			builder.setThumbnail(m.getUser().getAvatarUrl());
			builder.setColor(PureCompetence.INSTANCE.clrViolet);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
		}
		catch (NullPointerException e)
		{
			builder.clear();

			builder.setTitle("Info about **" + m.getUser().getName() + "**");
			builder.addField("Name", m.getUser().getAsMention(), true);
			builder.addField("Discord ID", "" + m.getIdLong(), true);
			builder.addField("Joined Discord on", m.getTimeCreated().getDayOfMonth() + "/"
					+ m.getTimeCreated().getMonthValue() + "/" + m.getTimeCreated().getYear(), true);
			builder.addField("Joined Server", m.getTimeJoined().getDayOfMonth() + "/"
					+ m.getTimeJoined().getMonthValue() + "/" + m.getTimeJoined().getYear(), true);
			builder.addField("Display Color", "R: 255 G: 255 B: 255", true);
			builder.addField("Highest role", m.getRoles().get(0).getAsMention(), true);
			builder.addField("Activities",
					Activity.size() == 2 ? Activity.get(0).toString() + ", " + Activity.get(1).toString()
							: Activity.get(0).toString(),
					false);

			builder.setThumbnail(m.getUser().getAvatarUrl());
			builder.setColor(PureCompetence.INSTANCE.clrViolet);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
		}

	}

	private void otherInfoNoActivity(Member m, TextChannel channel, Guild guild)
	{
		EmbedBuilder builder = new EmbedBuilder();
		Color color = m.getColor();

//		NullPointerException if USers Color is Default
		try
		{
			builder.setTitle("Info about **" + m.getUser().getName() + "**");
			builder.addField("Name", m.getUser().getAsMention(), true);
			builder.addField("Discord ID", "" + m.getIdLong(), true);
			builder.addField("Joined Discord on", m.getTimeCreated().getDayOfMonth() + "/"
					+ m.getTimeCreated().getMonthValue() + "/" + m.getTimeCreated().getYear(), true);
			builder.addField("Joined Server", m.getTimeJoined().getDayOfMonth() + "/"
					+ m.getTimeJoined().getMonthValue() + "/" + m.getTimeJoined().getYear(), true);
			builder.addField("Display Color",
					"R: " + color.getRed() + " G: " + color.getGreen() + " B: " + color.getBlue(), true);
			builder.addField("Highest role", m.getRoles().get(0).getAsMention(), true);
			builder.addField("Activities", "No Activities!", false);

			builder.setThumbnail(m.getUser().getAvatarUrl());
			builder.setColor(PureCompetence.INSTANCE.clrViolet);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
		}
		catch (NullPointerException e)
		{
			builder.clear();

			builder.setTitle("Info about **" + m.getUser().getName() + "**");
			builder.addField("Name", m.getUser().getAsMention(), true);
			builder.addField("Discord ID", "" + m.getIdLong(), true);
			builder.addField("Joined Discord on", m.getTimeCreated().getDayOfMonth() + "/"
					+ m.getTimeCreated().getMonthValue() + "/" + m.getTimeCreated().getYear(), true);
			builder.addField("Joined Server", m.getTimeJoined().getDayOfMonth() + "/"
					+ m.getTimeJoined().getMonthValue() + "/" + m.getTimeJoined().getYear(), true);
			builder.addField("Display Color", "R: 255 G: 255 B: 255", true);
			builder.addField("Highest role", m.getRoles().get(0).getAsMention(), true);
			builder.addField("Activities", "No Activities!", false);

			builder.setThumbnail(m.getUser().getAvatarUrl());
			builder.setColor(PureCompetence.INSTANCE.clrViolet);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
		}

	}

}
