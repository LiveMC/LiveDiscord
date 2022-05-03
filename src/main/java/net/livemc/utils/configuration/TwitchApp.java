package net.livemc.utils.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Setter
@Getter
@ToString
public class TwitchApp {

    private String twitch_client_id = "HERE_IS_YOUR_TWITCH_CLIENT_ID";
    private String twitch_client_secret = "HERE_IS_YOUR_TWITCH_CLIENT_SECRET";

    private String channel_to_announce = "HERE_IS_YOUR_CHANNEL_TO_ANNOUNCE";
    private String message_to_announce = "@everyone {$user} is now live on Twitch! {$link}";

    private List<String> streamers_to_announce = Arrays.asList("imjoycepg");
}
