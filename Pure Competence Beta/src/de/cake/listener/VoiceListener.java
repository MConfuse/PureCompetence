package de.cake.listener;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceListener extends ListenerAdapter {
	
	/**
	 * This stupid code is the thing that creates the Temp channels lmao, who knows how this stuff works anyways
	 */
	
	public List<Long> tempchannels;
	
	public VoiceListener()
	{
		this.tempchannels = new ArrayList<>();
	}
	
	@Override
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event)
	{
		onJoin(event.getChannelJoined(), event.getEntity());
	}
	
	@Override
	public void onGuildVoiceLeave(GuildVoiceLeaveEvent event)
	{
		onLeave(event.getChannelLeft());
	}
	
	@Override
	public void onGuildVoiceMove(GuildVoiceMoveEvent event)
	{
		onLeave(event.getChannelLeft());
		onJoin(event.getChannelJoined(), event.getEntity());
	}
	
	public void onJoin(VoiceChannel joined, Member memb)
	{
		try
		{
			
			if(joined.getName().toLowerCase().contains("➡") || joined.getName().toLowerCase().contains("temp") || joined.getName().toLowerCase().contains("temporary"))
			{	//Thanks to @Amejonah 1200#5697 and @Johnny#3826
				Category cat = joined.getParent();
				VoiceChannel vc = cat.createVoiceChannel("⏳ | " + memb.getEffectiveName()).complete();
				vc.getManager().setUserLimit(joined.getUserLimit()).queue();
				Guild controller = vc.getGuild();
				controller.moveVoiceMember(memb, vc).queue();
				this.tempchannels.add(vc.getIdLong());
			}
			
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			
		}
	
	}
	
	public void onLeave(VoiceChannel channel) {
		
		if(channel.getMembers().size() <= 0) {
			if(this.tempchannels.contains(channel.getIdLong()))
			{
				this.tempchannels.remove(channel.getIdLong());
				channel.delete().complete();
			}
			
		}
		
	}
	
}
