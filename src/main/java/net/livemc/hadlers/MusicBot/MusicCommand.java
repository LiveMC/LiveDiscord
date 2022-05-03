package net.livemc.hadlers.MusicBot;

import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MusicCommand extends ListenerAdapter {

    private final MusicManager manager = new MusicManager();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String command = event.getMessage().getContentRaw();
        Guild guild = event.getGuild();
        User user = event.getAuthor();
        TextChannel textChannel = event.getTextChannel();
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getContentRaw().startsWith("!play")) {
            if (!guild.getAudioManager().isConnected()) {
                AudioChannel audioChannel = guild.getMember(user).getVoiceState().getChannel();
                if (audioChannel == null) {
                    event.getMessage().delete().queue();
                    textChannel.sendMessage("Debes conectarte a un canal de voz").queue();
                    return;
                }
                guild.getAudioManager().openAudioConnection(audioChannel);
            }
            manager.loadTrack(textChannel, command.replaceFirst("!play ", ""));
            event.getMessage().delete().queue();
        }
        else if(event.getMessage().getContentRaw().startsWith("!skip")){
            if(!guild.getAudioManager().isConnected()){
                event.getMessage().delete().queue();
                textChannel.sendMessage("No se puede skipear si no hay pista en curso").queue();
                return;
            }

            manager.getPlayer(guild).skipTrack();
            textChannel.sendMessage("La pista fue skipeada correctamente").queue();
            event.getMessage().delete().queue();
        }
        else if (event.getMessage().getContentRaw().startsWith("!clear")) {
            MusicPlayer musicPlayer = manager.getPlayer(textChannel.getGuild());
            if(musicPlayer.getListener().getTracks().isEmpty()){
                event.getMessage().delete().queue();
                textChannel.sendMessage("En la lista no hay nada por lo que no se puede procesar").queue();
                return;
            }
            musicPlayer.getListener().getTracks().clear();
            textChannel.sendMessage("Todas las pistas fueron eliminadas.").queue();

            event.getMessage().delete().queue();
        }
    }
}
