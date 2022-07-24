package com.skywars;

import cn.nukkit.plugin.PluginBase;
import com.google.gson.Gson;
import com.skywars.extension.ExtensionManager;
import com.skywars.lang.LangManager;
import com.skywars.utils.ResourceUtils;
import lombok.Getter;

@Getter
public class GameLoader extends PluginBase {

    public static final Gson JSON = new Gson();
    public static final Double API_VERSION = 1.0;

    private static GameLoader instance;
    private ExtensionManager extensionManager;
    private LangManager langManager;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        getLogger().info("Checking resources...");
        ResourceUtils.createDefaultDirectories();

        langManager = new LangManager();
        langManager.init();

        extensionManager = new ExtensionManager();
        extensionManager.init();
    }

    @Override
    public void onDisable() {
        extensionManager.close();
    }

    public static GameLoader getInstance() {
        return instance;
    }
}
