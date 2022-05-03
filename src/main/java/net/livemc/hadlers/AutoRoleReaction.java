package net.livemc.hadlers;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class AutoRoleReaction extends ListenerAdapter {

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (event.getTextChannel().getId().equalsIgnoreCase("931028148114567208")) {
            Member member = event.getMember();
            Guild guild = event.getGuild();
            if(guild == null || member == null) return;
            switch (event.getComponentId()) {
                case "reaction_men":
                    guild.addRoleToMember(member, guild.getRoleById("936122189122531348")).queue();
                    break;
                case "reaction_women":
                    guild.addRoleToMember(member, guild.getRoleById("936245875099963494")).queue();
                    break;
                case "reaction_bi":
                    guild.addRoleToMember(member, guild.getRoleById("936246343637299270")).queue();
                    break;
                case "reaction_homo":
                    guild.addRoleToMember(member, guild.getRoleById("936246371944652830")).queue();
                    break;
                case "notify_twitch":
                    guild.addRoleToMember(member, guild.getRoleById("936250616836149288")).queue();
                    break;
                case "notify_update":
                    guild.addRoleToMember(member, guild.getRoleById("936249783297929307")).queue();
                    break;
            }
            event.reply("Se te ha entregado un rol correctamente.").setEphemeral(true).queue();
        }
    }
}
