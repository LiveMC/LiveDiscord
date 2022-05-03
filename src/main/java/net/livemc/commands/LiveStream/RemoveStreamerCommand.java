package net.livemc.commands.LiveStream;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.livemc.LiveDiscord;
import net.livemc.utils.TwitchProvider;

import java.util.Arrays;

public class RemoveStreamerCommand extends SlashCommand {

    private final TwitchProvider twitchProvider = TwitchProvider.getInstance();

    {
        this.name = "removestreamer";
        this.help = "Remove a streamer to the list!";
        this.options = Arrays.asList(
                new OptionData(OptionType.STRING, "streamer", "Twitch username of the streamer!", true));
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        OptionMapping option = event.getOption("streamer");
        if(option == null) return;
        if(!LiveDiscord.getInstance().getTwitchApp().getStreamers_to_announce().contains(option.getAsString())) {
            event.reply("Streamer isn't in the list!").setEphemeral(true).queue();
            return;
        }
        twitchProvider.unlisten(option.getAsString());
        event.reply("Removed " + option.getAsString() + " from the list!").setEphemeral(true).queue();
    }
}
