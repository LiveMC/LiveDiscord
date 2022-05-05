package net.livemc.utils;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.common.events.channel.ChannelGoLiveEvent;
import com.github.twitch4j.helix.domain.UserList;
import lombok.Getter;
import net.dv8tion.jda.api.entities.TextChannel;
import net.livemc.LiveDiscord;

import java.util.Arrays;

public class TwitchProvider {
    @Getter
    private static final TwitchProvider instance = new TwitchProvider();

    private TwitchClient client;

    public void connect() {
        client = TwitchClientBuilder.builder()
                .withEnableHelix(true)
                .withClientId(LiveDiscord.getInstance().getTwitchApp().getTwitch_client_id())
                .withTimeout(10000)
                .withClientSecret(LiveDiscord.getInstance().getTwitchApp().getTwitch_client_secret())
                .build();

        System.out.println("[Twitch] Connected!");
        client.getEventManager().onEvent(ChannelGoLiveEvent.class, event -> {
            TextChannel channel = LiveDiscord.getInstance().getJda().getTextChannelById(LiveDiscord.getInstance().getTwitchApp().getChannel_to_announce());
            String message = LiveDiscord.getInstance().getTwitchApp().getMessage_to_announce()
                    .replace("{$title}", event.getTitle())
                    .replace("{$link}", "https://twitch.tv/" + event.getChannel().getName())
                    .replace("{$user}", event.getChannel().getName())
                    ;
            channel.sendMessage(message).queue();
        });
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("[Twitch] Fetching streamers...");
            LiveDiscord.getInstance().getTwitchApp().getStreamers_to_announce().forEach(streamer -> {
                listen(streamer);
            });
        }).start();
    }
    public void listen(String streamer) {
        System.out.println("LISTENING TO " + streamer);
        client.getClientHelper().enableStreamEventListener(streamer);
        if (LiveDiscord.getInstance().getTwitchApp().getStreamers_to_announce().add(streamer)) {
            JsonConfig.save(LiveDiscord.getInstance().getTwitchApp());
        }
    }

    public void unlisten(String streamer) {
        System.out.println("UNLISTENING TO " + streamer);
        client.getClientHelper().disableStreamEventListener(streamer);
        if (LiveDiscord.getInstance().getTwitchApp().getStreamers_to_announce().remove(streamer)) {
            JsonConfig.save(LiveDiscord.getInstance().getTwitchApp());
        }
    }

    public boolean checkIfStreamerExists(String streamer) {
        UserList list = client.getHelix().getUsers(null, null, Arrays.asList(streamer)).execute();
        return !list.getUsers().isEmpty();

    }
}

