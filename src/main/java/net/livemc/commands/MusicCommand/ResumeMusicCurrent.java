package net.livemc.commands.MusicCommand;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.livemc.hadlers.MusicBot.MusicManager;

public class ResumeMusicCurrent extends SlashCommand {

    private final MusicManager manager = new MusicManager();

    public ResumeMusicCurrent(){
        this.name = "resume";
        this.help = "resume the current song.";
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
            event.reply("No se puede reanudar si no hay pista en curso").setEphemeral(true).queue();
            return;
        }
        manager.getPlayer(guild).resumeTrack();
        textChannel.sendMessage("**->** El usuario **" + user.getName() + "** acaba de reanudar la pista.").queue();
        event.reply("Acabas de reanudar la pista que estaba en pause").setEphemeral(true).queue();
    }
}
