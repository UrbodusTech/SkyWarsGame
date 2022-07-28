package com.skywars.lang;

import cn.nukkit.Player;
import com.skywars.GameLoader;

public final class LangUtils {

    public static String translate(String lang, String messageId, String[] args) {
        return GameLoader.getInstance().getLangManager().getTranslationValue(lang, messageId, args);
    }

    public static String translate(String lang, String messageId) {
        return translate(lang, messageId, new String[0]);
    }

    public static String translate(Player player, String messageId, String[] args) {
        return GameLoader.getInstance().getLangManager().getTranslationValue(player.getLocale(), messageId, args);
    }

    public static String translate(Player player, String messageId) {
        return translate(player, messageId, new String[0]);
    }
}
