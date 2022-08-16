package com.skywars.lang;

import cn.nukkit.utils.TextFormat;
import com.skywars.generic.Manager;
import com.skywars.utils.ResourceUtils;

public class LangManager extends Manager<String, LangFile> {

    public static final String DEFAULT_LANG_TAG = "en_US";

    @Override
    public void init() {
        ResourceUtils.saveDefaultLangFile();
        register(new LangFile(DEFAULT_LANG_TAG));
    }

    public String getTranslationValue(String lang, String messageId, String[] args) {
        if (lang == null) {
            return getTranslationValue(DEFAULT_LANG_TAG, messageId, args);
        }

        LangFile langFile = get(lang);

        if (langFile != null) {
            String content = langFile.getMessage(messageId);
            for (int i = 0; i < args.length; i++) {
                content = content.replace("{" + i + "}", args[i]);
            }

            return TextFormat.colorize(content);
        }

        return getTranslationValue(DEFAULT_LANG_TAG, messageId, args);
    }

    public String getTranslationValue(String lang, String messageId) {
        return getTranslationValue(lang, messageId, new String[0]);
    }

}
