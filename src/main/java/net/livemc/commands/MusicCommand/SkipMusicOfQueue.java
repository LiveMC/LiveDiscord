package net.livemc.commands.MusicCommand;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.livemc.hadlers.MusicBot.MusicManager;

public class SkipMusicOfQueue extends SlashCommand {

    private final MusicManager manager = new MusicManager();

    public SkipMusicOfQueue(){
        this.name = "skip";
        this.help = "skip the current song.";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        Guild guild = event.getGuild();
        User user = event.getUser();
        TextChannel textChannel = event.getTextChannel();
        if(event.getMember().getVoiceState().getChannel() == null){
            textChannel.sendMessage("Debes conectarte a un canal de voz").queue();
            return;
        }
        if(!guild.getAudioManager().isConnected()){
            textChannel.sendMessage("No se puede skipear si no hay pista en curso").queue();
            return;
        }
        manager.getPlayer(guild).skipTrack();
        textChannel.sendMessage("**->** El usuario **" + user.getName() + "** acaba de skipear una pista de la cola.").queue();
        event.reply("Acabas de skipear la pista.").setEphemeral(true).queue();
    }
}
