package com.cutesmouse.dbot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.ArrayList;

public class FlipPage {
    private final int MAX_WORDS = 100;
    private String s;
    public FlipPage(String s) {
        this.s= s;
    }
    public MessageEmbed getMessage(int page) {
        if (page > getMaxPage()) return new EmbedBuilder().setDescription("ERROR").build();
        int index = page-1;
        String display;
        int from = MAX_WORDS * index;
        display = s.substring(from,Math.min(s.length(),from+MAX_WORDS));
        EmbedBuilder b = new EmbedBuilder()
                .setDescription(display)
                .setTitle("換頁測試");
        return b.build();
    }
    public int getMaxPage() {
        if (s.length() % MAX_WORDS == 0) {
            return s.length() / MAX_WORDS;
        }
        return (s.length() / MAX_WORDS) +1;
    }
}
