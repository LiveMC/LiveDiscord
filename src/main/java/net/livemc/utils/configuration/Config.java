package net.livemc.utils.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Config {

    private String bot_token = "HERE_IS_BOT_TOKEN";
    private String welcome_channel = "HERE_IS_ID_CHANNEL";
    private String goodbye_channel = "HERE_IS_ID_CHANNEL";
    private String join_role_member = "HERE_IS_ID_ROLE";
    private int status_bot_task = 5;
}
