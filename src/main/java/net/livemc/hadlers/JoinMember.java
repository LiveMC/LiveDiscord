package net.livemc.hadlers;

import lombok.NonNull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.livemc.LiveDiscord;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.OffsetDateTime;

public class JoinMember extends ListenerAdapter {
    private final LiveDiscord instance = LiveDiscord.getInstance();


    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        TextChannel channel = event.getGuild().getTextChannelById(instance.getConfig().getWelcome_channel());
        Role role = event.getGuild().getRoleById(instance.getConfig().getJoin_role_member());
        @NonNull Member member = event.getMember();
        if (channel == null || role == null) return;
        event.getGuild().addRoleToMember(member, role).queue();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(instance.getAllEmbeds().getJoinEmbed_title()
                .replace("%member_tag%", member.getUser().getAsTag()));

        embed.setThumbnail(instance.getAllEmbeds().getJoinEmbed_thumbnail()
                .replace("%member_avatarURL%", member.getEffectiveAvatarUrl()));

        embed.setColor(Color.decode(instance.getAllEmbeds().getJoinEmbed_color()));
        embed.setDescription(instance.getAllEmbeds().getJoinEmbed_description());

        embed.addField("", "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬", false);
        embed.addField("", "\uD83C\uDFB2 Espero disfrutes tu **estadía** en el servidor al igual aquí en nuestro discord.", false);
        embed.addField("", "🎯 Te invito a pasar por <#931027995706138645> para que sepas que es lo que está permitido.", false);
        embed.addField("", "\uD83D\uDCEA Visita <#931027974877241404> para estar enterado de todo.", false);
        embed.addField("", "\uD83D\uDE8E Ingresa a <#933204139721252974> para dialogar con todos.", false);
        embed.addField("", "\uD83D\uDCBB ¿Necesitas ayuda? Ve a <#931028781974585354> y abre un ticket si tienes **problemas/dudas**.", false);
        embed.addField("", "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬", false);
        embed.setImage(instance.getAllEmbeds().getJoinEmbed_image());
        embed.setFooter(instance.getAllEmbeds().getJoinEmbed_footer());
        embed.setTimestamp(OffsetDateTime.now());

        channel.sendMessageEmbeds(embed.build()).queue();

    }
}
