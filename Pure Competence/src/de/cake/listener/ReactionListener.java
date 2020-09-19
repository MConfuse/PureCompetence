package de.cake.listener;

import net.dv8tion.jda.api.events.guild.update.GuildUpdateBoostCountEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateBoostTierEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {
	
	@Override
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
		System.out.println("Success");
	}
	
	@Override
	public void onGuildUpdateBoostCount(GuildUpdateBoostCountEvent event) {
		
	}
	
	@Override
	public void onGuildUpdateBoostTier(GuildUpdateBoostTierEvent event) {
		
	}
	
}
