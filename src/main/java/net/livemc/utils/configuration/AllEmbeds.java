package net.livemc.utils.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AllEmbeds {

    private String joinEmbed_title = "%member_tag%";
    private String joinEmbed_thumbnail = "%member_avatarURL%";
    private String joinEmbed_color = "#00FF42";
    private String joinEmbed_description = "";
    private String joinEmbed_image = "https://i.imgur.com/fUrGKY9.gif";
    private String joinEmbed_footer = "Te da la bienvenida LiveMC Network";

    private String leaveEmbed_title = "%member_tag%";
    private String leaveEmbed_thumbnail = "%member_avatarURL%";
    private String leaveEmbed_color = "#FF0000";
    private String leaveEmbed_description = "";
    private String leaveEmbed_image = "https://i.imgur.com/Xn1SDkP.gif";
    private String leaveEmbed_footer = "Nos vemos pronto en LiveMC Network";

}
