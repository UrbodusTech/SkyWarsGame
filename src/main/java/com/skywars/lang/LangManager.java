package com.skywars.lang;

import cn.nukkit.utils.TextFormat;
import com.skywars.utils.ResourceUtils;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LangManager {

    public static final String DEFAULT_LANG_TAG = "en_US";

    private final Map<String, LangFile> languages;

    public LangManager() {
        languages = new HashMap<>();
    }

    public void init() {
        ResourceUtils.saveDefaultLangFile();
        registerLangFile(DEFAULT_LANG_TAG);
    }

    public void registerLangFile(String id) {
        if (languages.containsKey(id)) {
            return;
        }

        languages.put(id, new LangFile(id));
    }

    public LangFile getLangFile(String id) {
        return languages.get(id);
    }

    public String getTranslationValue(String lang, String messageId, String[] args) {
        if (lang == null) {
            return getTranslationValue(DEFAULT_LANG_TAG, messageId, args);
        }

        if (languages.containsKey(lang)) {
            String content = languages.get(lang).getMessage(messageId);
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
