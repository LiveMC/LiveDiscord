package net.livemc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.impl.CommandClientImpl;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.livemc.commands.LiveStream.AddStreamerCommand;
import net.livemc.commands.LiveStream.ReloadConfigCommand;
import net.livemc.commands.LiveStream.RemoveStreamerCommand;
import net.livemc.commands.LiveStream.SetAnnounceChannelCommand;
import net.livemc.hadlers.AutoRoleReaction;
import net.livemc.hadlers.JoinMember;
import net.livemc.hadlers.LeaveMember;
import net.livemc.hadlers.MusicBot.MusicCommand;
import net.livemc.hadlers.ReadyStatus;
import net.livemc.utils.JsonConfig;
import net.livemc.utils.configuration.AllEmbeds;
import net.livemc.utils.configuration.Config;
import net.livemc.utils.configuration.StatusBot;
import net.livemc.utils.configuration.TwitchApp;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

@Getter
public class LiveDiscord {
    private static LiveDiscord instance;
    private JDA jda;
    private Config config;
    private AllEmbeds allEmbeds;
    private StatusBot statusBot;
    private TwitchApp twitchApp;

    private final Random random = new Random();
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    @SneakyThrows
    public void startBot(){
        try {
            config = JsonConfig.loadConfig(Config.class);
            allEmbeds = JsonConfig.loadConfig(AllEmbeds.class);
            statusBot = JsonConfig.loadConfig(StatusBot.class);
            twitchApp = JsonConfig.loadConfig(TwitchApp.class);
        } catch (InstantiationException | IllegalAccessException | IOException e1) {
            System.out.println("Failed to load config!");
            e1.printStackTrace();
            return;
        }
        JDABuilder jdaBuilder = JDABuilder.create(config.getBot_token(), Arrays.asList(
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.GUILD_EMOJIS,
                GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_VOICE_STATES));
        jdaBuilder.setStatus(OnlineStatus.ONLINE);

        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setOwnerId("299732456037154817");
        builder.useHelpBuilder(false);
        builder.addSlashCommands(
                new AddStreamerCommand(),
                new RemoveStreamerCommand(),
                new ReloadConfigCommand(),
                new SetAnnounceChannelCommand()
        );
        CommandClientImpl client = (CommandClientImpl) builder.build();
        jdaBuilder.addEventListeners(client, new ReadyStatus())
                .addEventListeners(new AutoRoleReaction())
                .addEventListeners(new MusicCommand())
                .addEventListeners(new JoinMember())
                .addEventListeners(new LeaveMember());

        try {
            jda = jdaBuilder.build();
        } catch (LoginException e1) {
            e1.printStackTrace();
        }
    }

    public static LiveDiscord getInstance() {
        synchronized (LiveDiscord.class) {
            if (instance == null) {
                instance = new LiveDiscord();
            }
        }
        return instance;
    }
    public void reloadConfig() throws Exception {
        this.twitchApp = JsonConfig.loadConfig(TwitchApp.class);
    }
}
