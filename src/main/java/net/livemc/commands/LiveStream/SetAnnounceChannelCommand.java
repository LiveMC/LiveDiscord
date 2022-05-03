package net.livemc.commands.LiveStream;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.livemc.LiveDiscord;
import net.livemc.utils.JsonConfig;

import java.util.Arrays;

public class SetAnnounceChannelCommand extends SlashCommand {

    {
        this.name = "setannouncechannel";
        this.help = "Set the channel to announce streams!";
        this.options = Arrays.asList(
                new OptionData(OptionType.CHANNEL, "channel", "Streams will be announced in this channel!", true)
        );

    }

    @Override
    protected void execute(SlashCommandEvent event) {
        OptionMapping option = event.getOption("channel");
        if(option == null) return;
        LiveDiscord.getInstance().getTwitchApp().setChannel_to_announce(option.getAsGuildChannel().getId());
        event.reply("Now announcing streams in " + option.getAsGuildChannel().getAsMention()).setEphemeral(true).queue();
        JsonConfig.save(LiveDiscord.getInstance().getTwitchApp());
    }
}
