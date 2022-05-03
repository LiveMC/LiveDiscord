package net.livemc.hadlers;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.livemc.LiveDiscord;
import net.livemc.utils.StatusTask;
import net.livemc.utils.TwitchProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ReadyStatus extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        TwitchProvider.getInstance().connect();
        Thread thread = new Thread(new StatusTask(Arrays.asList(LiveDiscord.getInstance().getStatusBot().getList_status())));
        thread.setName("LiveMC Network");
        thread.start();
    }
}
