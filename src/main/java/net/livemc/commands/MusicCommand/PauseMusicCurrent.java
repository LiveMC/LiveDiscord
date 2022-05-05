package net.livemc.commands.MusicCommand;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.livemc.hadlers.MusicBot.MusicManager;

public class PauseMusicCurrent extends SlashCommand {

    private final MusicManager manager = new MusicManager();

    public PauseMusicCurrent(){
        this.name = "pause";
        this.help = "pause the current song.";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        Guild guild = event.getGuild();
        User user = event.getUser();
        TextChannel textChannel = event.getTextChannel();
        if(event.getMember().getVoiceState().getChannel() == null){
            event.reply("Debes conectarte a un canal de voz").setEphemeral(true).queue();
            return;
        }
        if (!guild.getAudioManager().isConnected()) {
            event.reply("No se puede pausar si no hay pista en curso").setEphemeral(true).queue();
            return;
        }
        manager.getPlayer(guild).pauseTrack();
        textChannel.sendMessage("**->** El usuario **" + user.getName() + "** acaba de pausar la pista.").queue();
        event.reply("Acabas de pausar la pista.").setEphemeral(true).queue();
    }
}