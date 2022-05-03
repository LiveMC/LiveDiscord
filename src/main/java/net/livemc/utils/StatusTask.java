package net.livemc.utils;

import lombok.var;
import net.dv8tion.jda.api.entities.Activity;
import net.livemc.LiveDiscord;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class StatusTask implements Runnable {
    private List<String> list;
    public StatusTask(List<String> list){this.list = list;}

    @Override
    public void run() {
        while(true){
            var word = getRandom();
            LiveDiscord.getInstance().getJda().getPresence().setActivity(Activity.playing(word));
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(LiveDiscord.getInstance().getConfig().getStatus_bot_task()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public String getRandom(){
        return list.get(LiveDiscord.getInstance().getRandom().nextInt(list.size()));
    }
}
