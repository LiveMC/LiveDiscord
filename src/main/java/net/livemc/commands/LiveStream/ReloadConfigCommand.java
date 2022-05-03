package net.livemc.commands.LiveStream;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.livemc.LiveDiscord;

public class ReloadConfigCommand extends SlashCommand {

    {
        this.name = "reloadconfig";
        this.help = "Reload the config!";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        try {
            LiveDiscord.getInstance().reloadConfig();
            event.reply("Reloaded the config!").setEphemeral(true).queue();
        } catch (Exception e) {
            event.reply("Failed to reload the config! (view console for details)").setEphemeral(true).queue();
            e.printStackTrace();
        }
    }
}
