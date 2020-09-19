package de.cake.LiteSQL;

public class SQLManager {

	
	public static void onCreate() {

		//		---Some tables need to be added to the JoinListener, else the bot wont be able to be used properly sometimes---
		
		//id   guildid   channelid   messageid   emote   rollenid
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS reactroles(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, guildid INTEGER, channelid INTEGER, messageid INTEGER, emote VARCHAR, rollenid INTEGER)");
		
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS statchannels(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, guildid INTEGER, categoryid INTEGER)");
		
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS joinmsgchannels(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, guildid LONG, channelid LONG, state BOOLEAN)");
		
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS leavemsgchannels(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, guildid LONG, channelid LONG, state BOOLEAN)");
		
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS updatechannels(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, guildid LONG, channelid LONG, state BOOLEAN)");
		
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS musicvolume(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, guildid LONG, volume INTEGER)");
		
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS adminrights(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, guildid LONG, role LONG, state BOOLEAN)");
		
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS joinrole(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, guildid LONG, role LONG, state BOOLEAN)");
		
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS musicchannel(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, guildid INTEGER, channelid INTEGER)");
	}
	
}

