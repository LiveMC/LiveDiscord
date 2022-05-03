package net.livemc.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class AutoRole extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;

        if(event.getMessage().getContentRaw().equalsIgnoreCase("!autorole")){
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Sexualidad | AutoRole");
            embed.setColor(Color.green);
            embed.setDescription("Puedes elegir la sexualidad en el que te identificas.");
            embed.setThumbnail("https://www.polemos.pe/wp-content/uploads/2021/07/sexualidad-04.png");
            embed.addField("Sexualidad: Hombre", "\uD83C\uDF88 Bisexual", false);
            embed.addField("Sexualidad: Mujer", "\uD83C\uDFF3️\u200D\uD83C\uDF08 Homosexual", false);

            event.getMessage().replyEmbeds(embed.build()).setActionRow(new ItemComponent[] {
                    Button.primary("reaction_bi", "\uD83C\uDF88 Bisexual"),
                    Button.primary("reaction_homo", "\uD83C\uDFF3️\u200D\uD83C\uDF08 Homosexual"),
            }).queueAfter(1L, TimeUnit.SECONDS);
        }
        event.getMessage().delete().queue();
    }
}

