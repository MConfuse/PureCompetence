package de.cake.commands;

import de.cake.PureCompetence;

public class PrivateIDRetriever {

	public String getMessageID(Long mentionedID) {
		return PureCompetence.INSTANCE.shardMan.getUserById(mentionedID).openPrivateChannel().complete().getLatestMessageId();
	}
	
}
