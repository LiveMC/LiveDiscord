package net.livemc.hadlers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.livemc.LiveDiscord;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.OffsetDateTime;

public class LeaveMember extends ListenerAdapter {
    private final LiveDiscord instance = LiveDiscord.getInstance();

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        User user = event.getUser();
        TextChannel channel = event.getGuild().getTextChannelById(instance.getConfig().getGoodbye_channel());
        if(channel == null) return;

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(instance.getAllEmbeds().getLeaveEmbed_title()
                .replace("%member_tag%", user.getAsTag()));

        embed.setThumbnail(instance.getAllEmbeds().getLeaveEmbed_thumbnail()
                .replace("%member_avatarURL%", user.getEffectiveAvatarUrl()));

        embed.setColor(Color.decode(instance.getAllEmbeds().getLeaveEmbed_color()));
        embed.setImage(instance.getAllEmbeds().getLeaveEmbed_image());
        embed.setFooter(instance.getAllEmbeds().getLeaveEmbed_footer());
        embed.setTimestamp(OffsetDateTime.now());

        embed.addField("", "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬", false);
        embed.addField("", "\uD83D\uDCCC Gracias por la visita %member_tag% por nuestro servidor, esperamos volver a verlo pronto."
                .replace("%member_tag%", user.getAsTag()), false);
        embed.addField("", "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬", false);

        channel.sendMessageEmbeds(embed.build()).queue();
    }
}
