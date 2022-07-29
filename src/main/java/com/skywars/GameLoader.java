package com.skywars;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import com.google.gson.Gson;
import com.skywars.command.NativeSkyWarsCommand;
import com.skywars.extension.ExtensionManager;
import com.skywars.lang.LangManager;
import com.skywars.listener.GameBlockListener;
import com.skywars.listener.GameEntityListener;
import com.skywars.listener.GamePlayerListener;
import com.skywars.match.MatchManager;
import com.skywars.session.SessionManager;
import com.skywars.utils.ResourceUtils;
import lombok.Getter;

@Getter
public class GameLoader extends PluginBase {

    public static final Gson JSON = new Gson();
    public static final Double API_VERSION = 1.0;

    private static GameLoader instance;
    private ExtensionManager extensionManager;
    private LangManager langManager;
    private MatchManager matchManager;
    private SessionManager sessionManager;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        getLogger().info("Checking resources...");
        saveDefaultConfig();
        ResourceUtils.createDefaultDirectories();

        langManager = new LangManager();
        langManager.init();

        matchManager = new MatchManager();
        matchManager.init();

        sessionManager = new SessionManager();

        if (getConfig().getBoolean("join-command-method")) {
            NativeSkyWarsCommand command = new NativeSkyWarsCommand();
            getServer().getCommandMap().register(command.getName(), command);
        }

        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new GameBlockListener(), this);
        manager.registerEvents(new GamePlayerListener(), this);
        manager.registerEvents(new GameEntityListener(), this);

        if (getConfig().getBoolean("extension-actions")) {
            extensionManager = new ExtensionManager();
            extensionManager.init();
        }
    }

    @Override
    public void onDisable() {
        if (getConfig().getBoolean("extension-actions")) {
            extensionManager.close();
        }
        matchManager.close();
        sessionManager.clear();
    }

    public static GameLoader getInstance() {
        return instance;
    }
}
