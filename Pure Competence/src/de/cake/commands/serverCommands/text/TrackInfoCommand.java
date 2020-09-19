package de.cake.commands.serverCommands.text                                                                                                                               ;
                                                                                                                                                                            
import java.io.IOException                                                                                                                                                  ;
import java.io.InputStream                                                                                                                                                  ;
import java.net.URL                                                                                                                                                         ;
import java.sql.ResultSet                                                                                                                                                   ;
import java.sql.SQLException                                                                                                                                                ;
import java.time.OffsetDateTime                                                                                                                                             ;
                                                                                                                                                                            
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer                                                                                                                  ;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack                                                                                                                    ;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo                                                                                                                ;
                                                                                                                                                                            
import de.cake.PureCompetence                                                                                                                                               ;
import de.cake.commands.manage.LiteSQL                                                                                                                                      ;
import de.cake.commands.types.ServerCommand                                                                                                                                 ;
import de.cake.musicController.MusicController                                                                                                                              ;
import de.cake.musicController.MusicUtil                                                                                                                                    ;
import net.dv8tion.jda.api.EmbedBuilder                                                                                                                                     ;
import net.dv8tion.jda.api.entities.Guild                                                                                                                                   ;
import net.dv8tion.jda.api.entities.GuildVoiceState                                                                                                                         ;
import net.dv8tion.jda.api.entities.Member                                                                                                                                  ;
import net.dv8tion.jda.api.entities.Message                                                                                                                                 ;
import net.dv8tion.jda.api.entities.TextChannel                                                                                                                             ;
import net.dv8tion.jda.api.entities.VoiceChannel                                                                                                                            ;
import net.dv8tion.jda.api.managers.AudioManager                                                                                                                            ;
                                                                                                                                                                            
public class TrackInfoCommand implements ServerCommand                                                                                                                      {
                                                                                                                                                                            
   //AudioTrack track                                                                                                                                                       ;
                                                                                                                                                                            
   @Override                                                                                                                                                                
   public void performCommand(Member m, TextChannel channel, Message message)                                                                                               {
      System.out.println("-------------------------- \r" + "[LOG] | Command | " + getClass() + "\r"                                                                         
            + m  + "\r" + m.getPermissions()                                                                                                                                
            + "\r--------------------------")                                                                                                                               ;
                                                                                                                                                                            
      EmbedBuilder builder = new EmbedBuilder()                                                                                                                             ;
                                                                                                                                                                            
      GuildVoiceState state                                                                                                                                                 ;
      if((state = m.getVoiceState()) != null)                                                                                                                               {
         VoiceChannel vc                                                                                                                                                    ;
         if((vc = state.getChannel()) != null)                                                                                                                              {
            MusicController controller = PureCompetence.INSTANCE.playerManager.getController(vc.getGuild().getIdLong())                                                     ;
            AudioPlayer player = controller.getPlayer()                                                                                                                     ;
            AudioTrack track = player.getPlayingTrack()                                                                                                                     ;
            long guildid = PureCompetence.INSTANCE.playerManager.getGuildByPlayerHash(player.hashCode())                                                                    ;
            Guild guild = PureCompetence.INSTANCE.shardMan.getGuildById(guildid)                                                                                            ;
            AudioManager manager = vc.getGuild().getAudioManager()                                                                                                          ;
                                                                                                                                                                            
                                                                                                                                                                            
                                                                                                                                                                            
            if(manager.getConnectedChannel() != null)                                                                                                                       {
               if(player.getPlayingTrack() != null)                                                                                                                         {
                  builder.setColor(PureCompetence.INSTANCE.clrBlue)                                                                                                         ;
                  AudioTrackInfo info = track.getInfo()                                                                                                                     ;
                  builder.setDescription(":cd: Now playing: " + info.title)                                                                                                 ;
                                                                                                                                                                            
                                                                                                                                                                            
                  long pos = player.getPlayingTrack().getPosition()                                                                                                         ;
                  long secondsPos = pos / 1000                                                                                                                              ;
                  long minutesPos = secondsPos / 60                                                                                                                         ;
                  long hoursPos = minutesPos / 60                                                                                                                           ;
                  minutesPos %= 60                                                                                                                                          ;
                  secondsPos %= 60                                                                                                                                          ;
                                                                                                                                                                            
                  long seconds = info.length / 1000                                                                                                                         ;
                  long minutes = seconds / 60                                                                                                                               ;
                  long hours = minutes / 60                                                                                                                                 ;
                  minutes %= 60                                                                                                                                             ;
                  seconds %= 60                                                                                                                                             ;
                                                                                                                                                                            
                  String url = info.uri                                                                                                                                     ;
                  builder.addField(info.author, "[" + info.title + "](" + url + ")", false)                                                                                 ;
                  builder.addField(":clock12: Duration ", info.isStream ? ":red_circle: Stream" : (hours > 0 ? hours + "h " : "") + minutes + "min " + seconds + "s", true) ;
                  if(player.isPaused() == true)                                                                                                                             {
                     builder.addField(":arrow_forward: Paused at", (hoursPos > 0 ? hoursPos + "h " : "") + minutesPos + "min " + secondsPos + "s", true)                    ;}
                    else if(player.isPaused() == false)                                                                                                                     {
                     builder.addField(":arrow_forward: Player at", (hoursPos > 0 ? hoursPos + "h " : "") + minutesPos + "min " + secondsPos + "s", true)                    ;}
                                                                                                                                                                            
                                                                                                                                                                            
                  if(url.startsWith("https://www.youtube.com/watch?v="))                                                                                                    {
                     String videoID = url.replace("https://www.youtube.com/watch?v=", "")                                                                                   ;
                                                                                                                                                                            
                     InputStream file                                                                                                                                       ;
                     try                                                                                                                                                    {
                        file = new URL("https://img.youtube.com/vi/" + videoID + "/hqdefault.jpg").openStream()                                                             ;
                        builder.setImage("attachment://thumbnail.png")                                                                                                      ;
                                                                                                                                                                            
                        ResultSet set = LiteSQL.onQuery("SELECT * FROM musicchannel WHERE guildid = " + guildid)                                                            ;
                                                                                                                                                                            
                        try                                                                                                                                                 {
                           if(set.next())                                                                                                                                   {
                              long channelid = set.getLong("channelid")                                                                                                     ;
                                                                                                                                                                            
                              if((guild = PureCompetence.INSTANCE.shardMan.getGuildById(guildid)) != null)                                                                  {
                                 if((channel = guild.getTextChannelById(channelid)) != null)                                                                                {
                                    channel.sendTyping().queue()                                                                                                            ;
                                    channel.sendFile(file, "thumbnail.png").embed(builder.build()).queue()                                                                  ;}}}}
                                                                                                                                                                            
                                                                                                                                                                            
                                                                                                                                                                            
                                                                                                                                                                            
                                                                                                                                                                            
                                                                                                                                                                            
                          catch(SQLException ex)                                                                                                                            {
                           ex.printStackTrace()                                                                                                                             ;}}
                                                                                                                                                                            
                                                                                                                                                                            
                       catch(IOException e)                                                                                                                                 {
                        e.printStackTrace()                                                                                                                                 ;}}
                                                                                                                                                                            
                                                                                                                                                                            
                    else                                                                                                                                                    {
                     MusicUtil.sendEmbed(guildid, builder)                                                                                                                  ;}}
                                                                                                                                                                            
                                                                                                                                                                            
                 else                                                                                                                                                       {
                  builder.setTitle("**Nothing is currently playing**")                                                                                                      ;
                  builder.setColor(PureCompetence.INSTANCE.clrRed)                                                                                                          ;
                  builder.setFooter(PureCompetence.INSTANCE.pwrdBy)                                                                                                         ;
                  builder.setTimestamp(OffsetDateTime.now())                                                                                                                ;
                  channel.sendMessage(builder.build()).queue()                                                                                                              ;}}
                                                                                                                                                                            
                                                                                                                                                                            
              else                                                                                                                                                          {
               builder.setTitle("**Nothing is currently playing**")                                                                                                         ;
               builder.setColor(PureCompetence.INSTANCE.clrRed)                                                                                                             ;
               builder.setFooter(PureCompetence.INSTANCE.pwrdBy)                                                                                                            ;
               builder.setTimestamp(OffsetDateTime.now())                                                                                                                   ;
               channel.sendMessage(builder.build()).queue()                                                                                                                 ;}}}
                                                                                                                                                                            
                                                                                                                                                                            
                                                                                                                                                                            
                                                                                                                                                                            
                                                                                                                                                                            
                                                                                                                                                                            
      EmbedBuilder log = new EmbedBuilder()                                                                                                                                 ;
      log.setTitle("[LOG] | Server Command | " + this.getClass())                                                                                                           ;
      log.addField("User", m.getEffectiveName() + " (ID Long: " + m.getId() + ")", false)                                                                                   ;
      log.addField("Guild", channel.getGuild().getName() + " (ID Long: " + channel.getGuild().getIdLong() + ")", false)                                                     ;
      log.setImage(channel.getGuild().getIconUrl())                                                                                                                         ;
      log.setColor(PureCompetence.INSTANCE.clrRed)                                                                                                                          ;
      log.setFooter(PureCompetence.INSTANCE.pwrdBy)                                                                                                                         ;
      log.setTimestamp(OffsetDateTime.now())                                                                                                                                ;
      Guild guildid = PureCompetence.INSTANCE.shardMan.getGuildById(374507865521520651l)                                                                                    ;
      guildid.getTextChannelById(694967647380570163l).sendMessage(log.build()).queue()                                                                                      ;}}
                                                                                                                                                                            
                                                                                                                                                                            
                                                                                                                                                                            
                                                                                                                                                                            
