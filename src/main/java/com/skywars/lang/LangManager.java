package com.skywars.lang;

import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.utils.TextFormat;
import com.skywars.GameLoader;
import com.skywars.utils.ResourceUtils;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class LangManager {

    public static final List<String> SUPPORTED_LANGUAGES = Arrays.asList("en_US", "zh_CN", "es_MX");
    public static final String DEFAULT_LANG_TAG = "en_US";

    private final Map<String, LangFile> languages;

    public LangManager() {
        languages = new HashMap<>();
    }

    public void init() {
        ResourceUtils.saveDefaultLangFiles();
        for (String language : SUPPORTED_LANGUAGES) {
            registerLangFile(language);
        }
    }

    public void registerLangFile(String id) {
        if (languages.containsKey(id)) {
            return;
        }

        PluginLogger logger = GameLoader.getInstance().getLogger();
        if (!SUPPORTED_LANGUAGES.contains(id)) {
            SUPPORTED_LANGUAGES.add(id);
            logger.warning("Adding (" +  id + ") in supported languages!");
        }

        languages.put(id, new LangFile(id));
        logger.info("Added language support: " + id);
    }

    public LangFile getLangFile(String id) {
        return languages.get(id);
    }

    public String getTranslationValue(String lang, String messageId, String[] args) {
        if (lang == null) {
            return getTranslationValue(DEFAULT_LANG_TAG, messageId, args);
        }

        if (SUPPORTED_LANGUAGES.contains(lang) && languages.containsKey(lang)) {
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
