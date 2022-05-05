package net.livemc.hadlers.MusicBot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AudioListener extends AudioEventAdapter {
    private final BlockingQueue<AudioTrack> tracks = new LinkedBlockingQueue<>();
    private final MusicPlayer musicPlayer;

    public AudioListener(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }

    public BlockingQueue<AudioTrack> getTracks(){
        return tracks;
    }

    public int getTrackSize(){
        return tracks.size();
    }

    public void nextTrack(){
        if(tracks.isEmpty()){
            if(musicPlayer.getGuild().getAudioManager().getConnectedChannel() != null) musicPlayer.getGuild().getAudioManager().closeAudioConnection(); return;
        }
        musicPlayer.getAudioPlayer().startTrack(tracks.poll(), false);
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        System.out.println("DEBUG PAUSE TRACK");
        musicPlayer.getAudioPlayer().setPaused(true);
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        musicPlayer.getAudioPlayer().setPaused(false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, com.sedmelluq.discord.lavaplayer.track.AudioTrack track, AudioTrackEndReason endReason) {
        if(endReason.mayStartNext) nextTrack();
    }

    public void queue(AudioTrack track) {
        if(!musicPlayer.getAudioPlayer().startTrack(track, true)) tracks.offer(track);
    }


}
