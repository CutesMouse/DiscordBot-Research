package com.cutesmouse.dbot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA jda = new JDABuilder("NDc3ODI1Mzg1OTUwNzQwNTA0.W27fkQ.QWDwiYwnW5IKC6j7QoFvYanUxhQ").build().awaitReady();
        jda.addEventListener(new Main());
    }

    public boolean hasPrivateChannel(Member mem){
        return (mem.getGuild().getTextChannelsByName("private-"+mem.getUser().getId(),false).size() > 0);
    }

    private Category CATE;
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        if (CATE == null) {
            CATE = e.getGuild().getCategoryById(757435601610866689L);
        }
        if (e.getAuthor().isBot()) return;
        if (e.getMessage().getContentRaw().startsWith("!create")) {
            if (hasPrivateChannel(e.getMember())) {
                e.getChannel().sendMessage("你的個人頻道已存在!").queue();
                return;
            }
            TextChannel c = CATE.createTextChannel("private-" + e.getAuthor().getId()).complete();
            c.createPermissionOverride(e.getGuild().getRoleById(757424606796709988L)).complete().getManager().setDeny(Permission.VIEW_CHANNEL).queue();
            c.createPermissionOverride(e.getMember()).complete().getManager().setAllow(Permission.VIEW_CHANNEL).queue();
            c.sendMessage(new EmbedBuilder()
                    .setTitle("Any problems?")
                    .setColor(new Color(0, 126, 154))
                    .setFooter(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                    .setDescription("系統已自動替您建立屬於您自己的私人聊天頻道\n有任何問題可以直接在這裡輸入").build()).append(e.getAuthor().getAsMention()).queue();
        }
        if(e.getMessage().getContentRaw().startsWith("!close")) {
            if (!hasPrivateChannel(e.getMember())) {
                e.getChannel().sendMessage("你沒有個人頻道!").queue();
                return;
            }
            TextChannel c = e.getMember().getGuild().getTextChannelsByName("private-" + e.getAuthor().getId()+"/"
                    +new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), false).get(0);
            c.getManager().setName("closed-"+e.getAuthor().getId()).queue();
            c.sendMessage(new EmbedBuilder()
                    .setTitle("頻道已封存")
                    .setDescription(":lock: 已結案")
                    .setColor(new Color(255, 39, 39))
                    .setFooter(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).build()).queue();

        }
    }
}
