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
            if (!guild.getAudioManager().isConnected() || event.getMember().getVoiceState().getChannel() == null) {
                AudioChannel audioChannel = guild.getMember(user).getVoiceState().getChannel();
                if (audioChannel == null) {
                    event.getMessage().delete().queue();
                    textChannel.sendMessage("Debes conectarte a un canal de voz").queue();
                    return;
                }
                guild.getAudioManager().openAudioConnection(audioChannel);
            }
            manager.loadTrack(textChannel, command.replaceFirst("!play ", ""));
            textChannel.sendMessage("**->** El usuario **" + user.getName() + "** acaba de colocar una pista en la cola.").queue();
            event.getMessage().delete().queue();
        }
        else if(event.getMessage().getContentRaw().equalsIgnoreCase("!skip")){
            if(event.getMember().getVoiceState().getChannel() == null){
                textChannel.sendMessage("Debes conectarte a un canal de voz").queue();
                return;
            }
            if(!guild.getAudioManager().isConnected()){
                event.getMessage().delete().queue();
                textChannel.sendMessage("No se puede skipear si no hay pista en curso").queue();
                return;
            }
            manager.getPlayer(guild).skipTrack();
            textChannel.sendMessage("**->** El usuario **" + user.getName() + "** acaba de skipear una pista de la cola.").queue();
            event.getMessage().delete().queue();
        }
        else if (event.getMessage().getContentRaw().equalsIgnoreCase("!clear")) {
            MusicPlayer musicPlayer = manager.getPlayer(textChannel.getGuild());
            if(event.getMember().getVoiceState().getChannel() == null){
                textChannel.sendMessage("Debes conectarte a un canal de voz").queue();
                return;
            }
            if(musicPlayer.getListener().getTracks().isEmpty()){
                event.getMessage().delete().queue();
                textChannel.sendMessage("En la lista no hay nada por lo que no se puede procesar").queue();
                return;
            }
            musicPlayer.getListener().getTracks().clear();
            textChannel.sendMessage("**->** El usuario **" + user.getName() + "** acaba de eliminar las pistas de la cola.").queue();

            event.getMessage().delete().queue();
        }
        else if(event.getMessage().getContentRaw().equalsIgnoreCase("!pause") || event.getMessage().getContentRaw().equalsIgnoreCase("!stop")) {
            if(event.getMember().getVoiceState().getChannel() == null){
                textChannel.sendMessage("Debes conectarte a un canal de voz").queue();
                return;
            }
            if (!guild.getAudioManager().isConnected()) {
                event.getMessage().delete().queue();
                textChannel.sendMessage("No se puede pausar si no hay pista en curso").queue();
                return;
            }

            manager.getPlayer(guild).pauseTrack();
            textChannel.sendMessage("**->** El usuario **" + user.getName() + "** acaba de pausar la pista.").queue();
            event.getMessage().delete().queue();
        }
        else if(event.getMessage().getContentRaw().equalsIgnoreCase("!resume")){
            if(event.getMember().getVoiceState().getChannel() == null){
                textChannel.sendMessage("Debes conectarte a un canal de voz").queue();
                return;
            }
            if(!guild.getAudioManager().isConnected()){
                event.getMessage().delete().queue();
                textChannel.sendMessage("No se puede reanudar si no hay pista en curso").queue();
                return;
            }
            manager.getPlayer(guild).resumeTrack();
            textChannel.sendMessage("**->** El usuario **" + user.getName() + "** acaba de reanudar la pista.").queue();
            event.getMessage().delete().queue();
        }
    }
}
