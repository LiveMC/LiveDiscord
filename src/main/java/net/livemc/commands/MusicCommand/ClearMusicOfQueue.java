package net.livemc.commands.MusicCommand;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.livemc.hadlers.MusicBot.MusicManager;
import net.livemc.hadlers.MusicBot.MusicPlayer;

public class ClearMusicOfQueue extends SlashCommand {

    private final MusicManager manager = new MusicManager();

    public ClearMusicOfQueue(){
        this.name = "clear";
        this.help = "clear the list song.";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        User user = event.getUser();
        TextChannel textChannel = event.getTextChannel();
        MusicPlayer musicPlayer = manager.getPlayer(textChannel.getGuild());
        if(event.getMember().getVoiceState().getChannel() == null){
            textChannel.sendMessage("Debes conectarte a un canal de voz").queue();
            return;
        }
        if(musicPlayer.getListener().getTracks().isEmpty()){
            textChannel.sendMessage("En la lista no hay nada por lo que no se puede procesar").queue();
            return;
        }
        musicPlayer.getListener().getTracks().clear();
        textChannel.sendMessage("**->** El usuario **" + user.getName() + "** acaba de eliminar las pistas de la cola.").queue();
        event.reply("Acabas de eliminar todas las pistas.").setEphemeral(true).queue();
    }
}

