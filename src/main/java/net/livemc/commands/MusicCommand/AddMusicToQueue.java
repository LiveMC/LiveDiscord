package net.livemc.commands.MusicCommand;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.livemc.hadlers.MusicBot.MusicManager;

import java.util.Arrays;

public class AddMusicToQueue extends SlashCommand {
    private final MusicManager manager = new MusicManager();

    public AddMusicToQueue(){
        this.name = "play";
        this.help = "Add music to queue";
        this.options = Arrays.asList(new OptionData(OptionType.STRING, "name", "You must enter link or name of the song", true));
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        Guild guild = event.getGuild();
        User user = event.getUser();
        TextChannel textChannel = event.getTextChannel();
        OptionMapping option = event.getOption("name");
        if(option == null || guild == null) return;
        if(option.getAsString().startsWith("www.") || option.getAsString().startsWith("https:")){
            if (!guild.getAudioManager().isConnected() || event.getMember().getVoiceState().getChannel() == null) {
                AudioChannel audioChannel = guild.getMember(user).getVoiceState().getChannel();
                if (audioChannel == null) {
                    textChannel.sendMessage("Debes conectarte a un canal de voz").queue();
                    return;
                }
                guild.getAudioManager().openAudioConnection(audioChannel);
            }
            manager.loadTrack(textChannel, option.getAsString());
            textChannel.sendMessage("**->** El usuario **" + user.getName() + "** acaba de colocar una pista en la cola.").queue();
        }else{
            if (!guild.getAudioManager().isConnected() || event.getMember().getVoiceState().getChannel() == null) {
                AudioChannel audioChannel = guild.getMember(user).getVoiceState().getChannel();
                if (audioChannel == null) {
                    textChannel.sendMessage("Debes conectarte a un canal de voz").queue();
                    return;
                }
                guild.getAudioManager().openAudioConnection(audioChannel);
            }
            manager.loadTrack(textChannel, "ytsearch:" + option.getAsString());
            textChannel.sendMessage("**->** El usuario **" + user.getName() + "** acaba de colocar una pista en la cola.").queue();
        }
        event.reply("Acabas de colocar una pista a la cola.").setEphemeral(true).queue();
    }
}
