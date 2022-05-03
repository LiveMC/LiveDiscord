package net.livemc.utils.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class StatusBot {

    private String[] list_status = {
            "\uD83D\uDCB8 | paypal.me/LiveMCNetwork",
            "\uD83C\uDFAE | twitch.tv/livemcnetwork",
            "\uD83D\uDC26 | @LiveMCNetwork",
    };
}