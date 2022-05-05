package net.livemc.hadlers.MusicBot;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MusicManager {
    private final AudioPlayerManager manager = new DefaultAudioPlayerManager();
    private final Map<String, MusicPlayer> players = new HashMap<>();

    public MusicManager(){
        AudioSourceManagers.registerRemoteSources(manager);
        AudioSourceManagers.registerLocalSource(manager);
    }

    public synchronized MusicPlayer getPlayer(@NotNull Guild guild){
        if(!players.containsKey(guild.getId())) players.put(guild.getId(), new MusicPlayer(manager.createPlayer(),guild));
        return players.get(guild.getId());
    }

    public void loadTrack(final @NotNull TextChannel channel, final String source){
        MusicPlayer player = getPlayer(channel.getGuild());
        channel.getGuild().getAudioManager().setSendingHandler(player.getAudioPlayerSendHandler());

        manager.loadItemOrdered(player, source, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                System.out.println("DEBUG CON ENLACE");
                channel.sendMessage("Nombre de la pista **" + track.getInfo().title + "**.").queue();
                player.playTrack(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {

                System.out.println("DEBUG SIN ENLACE");
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0); ;
                }

                channel.sendMessage("Nombre de la pista **" + firstTrack.getInfo().title + "**.").queue();
                System.out.println(firstTrack.getInfo().title + " - " + firstTrack.getPosition());

                player.playTrack(firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("La pista **" + source + "** no fue encontrada.").queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                channel.sendMessage("Imposible reproducir esto. (Razón: " + e.getMessage() + ")").queue();
            }
        });
    }

}
