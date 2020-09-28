package de.cake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.security.auth.login.LoginException;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import de.cake.commands.manage.LiteSQL;
import de.cake.commands.manage.SQLManager;
import de.cake.listener.CommandListener;
import de.cake.listener.JoinListener;
import de.cake.listener.ReactionListener;
import de.cake.listener.VoiceListener;
import de.cake.musicController.PlayerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class PureCompetence {

	public static PureCompetence INSTANCE;
	
	public JDA shardMan;
	private CommandManager cmdMan;
	private PrivateCommandManager pcmdMan;
	public AudioPlayerManager audioPlayerManager;
	public PlayerManager playerManager;
	
	public String version = "b0.08";

	public String pwrdBy = "Powered by xXConfusedJenni#5117. " + version;
	public int clrBlue = 0x42cbf4;
	public int clrRed = 0xFF0000;
	public int clrGreen = 0x00FF00;
	public int clrViolet = 0xbd025a;
	//If prefix is changed the CommandListener has to be modified - args
	public final String prefix = "$";

	public static void main(String[] args) {
		try {
			new PureCompetence();
		} catch (LoginException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}

	public PureCompetence() throws LoginException, IllegalArgumentException {
		INSTANCE = this;
		
		LiteSQL.connect();
		SQLManager.onCreate();

		JDABuilder builder = JDABuilder.createDefault("Token-goes-here", GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.DIRECT_MESSAGES);
		
		builder.setActivity(Activity.playing("with myself | '$help'"));
		builder.setStatus(OnlineStatus.ONLINE);
		
		this.audioPlayerManager = new DefaultAudioPlayerManager();
		this.playerManager = new PlayerManager();

		this.cmdMan = new CommandManager();
		this.pcmdMan = new PrivateCommandManager();
		
		builder.addEventListeners(new CommandListener());
		builder.addEventListeners(new VoiceListener());
		builder.addEventListeners(new JoinListener());
		builder.addEventListeners(new ReactionListener());
		
		shardMan = builder.build();
		System.out.println("Bot online. [Pure Competence]");

		AudioSourceManagers.registerRemoteSources(audioPlayerManager);
		audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);
		
		shutdown();
		
	}

	public void shutdown() {

		new Thread(() -> {

			String line = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				while ((line = reader.readLine()) != null) {

					if (line.equalsIgnoreCase("end me") || line.equalsIgnoreCase("end") || line.equalsIgnoreCase("FUCK ME") || line.equalsIgnoreCase("SHUT") || line.equalsIgnoreCase("BE GONE UNHOLY")) {
						if (shardMan != null) {
							LiteSQL.disconnect();
							shardMan.shutdown();
							System.out.println("Bot offline.");
						}
						
						reader.close();
						break;
					} else {
						System.out.println("Use 'end' to shutdown.");
					}
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}).start();
	
	}
	
	public CommandManager getCmdMan() {
		return cmdMan;
	}
	
	public PrivateCommandManager getPCmdMan() {
		return pcmdMan;
	}
	
}
