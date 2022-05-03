package net.livemc.commands.LiveStream;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.livemc.LiveDiscord;
import net.livemc.utils.TwitchProvider;

import java.util.Arrays;

public class AddStreamerCommand extends SlashCommand {

    private final TwitchProvider twitchProvider = TwitchProvider.getInstance();

    {
        this.name = "addstreamer";
        this.help = "Add a streamer to the list!";
        this.options = Arrays.asList(
                new OptionData(OptionType.STRING, "streamer", "Twitch username of the streamer!", true));
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        OptionMapping option = event.getOption("streamer");
        if(!twitchProvider.checkIfStreamerExists(option.getAsString())) {
            event.reply("Streamer does not exist!").setEphemeral(true).queue();
            return;
        }
        if(LiveDiscord.getInstance().getTwitchApp().getStreamers_to_announce().contains(option.getAsString())) {
            event.reply("Streamer is already in the list!").setEphemeral(true).queue();
            return;
        }
        twitchProvider.listen(option.getAsString());
        event.reply("Added " + option.getAsString() + " to the list!").setEphemeral(true).queue();
    }
}
