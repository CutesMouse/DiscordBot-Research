package com.cutesmouse.dbot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.api.events.emote.update.GenericEmoteUpdateEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class Main extends ListenerAdapter {
    private static final ArrayList<Long> WORKS = new ArrayList<Long>(Arrays.asList(604278586962083841L,757424606796709988L));
    private HashMap<String,FlipPage> pageData = new HashMap<String, FlipPage>();
    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA jda = JDABuilder.createDefault(getToken()).build().awaitReady();
        jda.addEventListener(new Main());
    }
    public static String getToken() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/TOKEN")));
        try {
            String token = reader.readLine();
            reader.close();
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean hasPrivateChannel(Member mem){
        return (mem.getGuild().getTextChannelsByName("private-"+mem.getUser().getId(),false).size() > 0);
    }

    private Category CATE;
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        if (!WORKS.contains(e.getGuild().getIdLong())) return;
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
        if(e.getMessage().getContentRaw().startsWith("!page")) {
            FlipPage page = new FlipPage("　　古之學者必有師。師者，所以傳道、受業、解惑也。人非生而知之者，孰能無惑？惑而不從師，其為惑也終不解矣！生乎吾前，其聞道也，固先乎吾，吾從而師之；生乎吾後，其聞道也，亦先乎吾，吾從而師之。吾師道也，夫庸知其年之先後生於吾乎？是故無貴、無賤、無長、無少，道之所存，師之所存也。　\n" +
                    "\n" +
                    "　　嗟乎！師道之不傳也久矣！欲人之無惑也難矣！古 之聖人，其出人也遠矣，猶且從師而問焉；今之眾人，其下聖人也亦遠矣，而恥學於師。是故聖益聖，愚益愚。聖人之所以為聖，愚人之所以為愚，其皆出於此乎？\n" +
                    "\n" +
                    "　　愛其子，擇師而教之，於其身也則恥師焉，惑矣！彼童子之師，授之書而習其句讀者也，非吾所謂傳其道、解其惑者也。句讀之不知，惑之不解，或師焉，或不焉，小學而大遺，吾未見其明也。\n" +
                    "\n" +
                    "　　巫醫、樂師、百工之人，不恥相師；士大夫之族，曰師、曰弟子云者，則群聚而笑之，問之，則曰：「彼與彼年相若也，道相似也。」位卑則足羞，官盛則近諛。嗚乎！師道之不復可知矣！巫醫、樂師、百工之人，君子不齒，今其智乃反不能及，其可怪也歟！\n" +
                    "\n" +
                    " \n" +
                    "\n" +
                    "　　聖人無常師：孔子師郯子、萇弘、師襄、老聃。郯子之徒，其賢不及孔子。孔子曰：「三人行，則必有我師」。是故弟子不必不如師，師不必賢於弟子。聞道有先後，術業有專攻，如是而已。　\n" +
                    "\n" +
                    "　　李氏子蟠，年十七，好古文，六藝經傳，皆通習之。不拘於時，請學於余，余嘉其能行古道，作師說以貽之。");
            Message msg = e.getChannel().sendMessage(page.getMessage(1)).append("1/"+page.getMaxPage()).complete();
            msg.addReaction("◀️").queue();
            msg.addReaction("▶️").queue();
            pageData.put(msg.getId(),page);
        }
    }

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent e) {
        if (e.getUser().isBot()) return;
        if (pageData.containsKey(e.getMessageId())) {
            FlipPage flip = pageData.get(e.getMessageId());
            int page = Integer.parseInt(e.getChannel().retrieveMessageById(e.getMessageId()).complete().getContentRaw().split("/")[0]);
            e.getReaction().removeReaction(e.getUser()).queue();
            switch (e.getReaction().getReactionEmote().getAsCodepoints()) {
                case "U+25c0U+fe0f": // LeftArrow
                    page--;
                    break;
                case "U+25b6U+fe0f": // RightArrow
                    page++;
                    break;
                default:
                    return;
            }
            if (page > flip.getMaxPage()) page = 1;
            if (page < 1) page = flip.getMaxPage();
            e.getChannel().retrieveMessageById(e.getMessageId()).complete()
                    .editMessage(flip.getMessage(page)).append(page+"/"+flip.getMaxPage()).queue();
        }
    }
}
