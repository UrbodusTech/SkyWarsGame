package com.skywars.extension;

import cn.nukkit.Server;
import com.skywars.GameLoader;
import com.skywars.command.GameCommandManager;
import com.skywars.lang.LangManager;
import com.skywars.loot.LootManager;
import com.skywars.match.MatchManager;
import com.skywars.session.SessionManager;
import lombok.Getter;
import lombok.NonNull;

import java.io.File;

@Getter
public abstract class Extension {

    private File file;
    private ExtensionInfo info;
    private ExtensionManager extensionManager;

    private boolean initialized;
    private boolean enabled;

    private File dataPath;

    private ExtensionLogger logger;


    public synchronized void init(@NonNull File file, @NonNull ExtensionInfo info, ExtensionManager extensionManager) {
        if (initialized) {
            throw new IllegalStateException("Extension is already initialized!");
        }

        initialized = true;
        file.getParentFile().mkdirs();

        this.file = file;
        this.info = info;
        this.extensionManager = extensionManager;

        dataPath = new File(GameLoader.getInstance().getDataFolder() + "/ext-dat/" + info.getName());
        dataPath.mkdirs();

        logger = new ExtensionLogger(this.info.getName());
    }

    public void setEnabled(boolean value) {
        if (!initialized) {
            throw new IllegalStateException("Extension is not initialized.");
        }

        if (enabled != value) {
            enabled = value;

            if (value) {
                install();
            } else {
                uninstall();
            }
        }
    }

    public abstract void install();

    public abstract void uninstall();

    public MatchManager getMatchManager() {
        return getMain().getMatchManager();
    }

    public LangManager getLangManager() {
        return getMain().getLangManager();
    }

    public SessionManager getSessionManager() {
        return getMain().getSessionManager();
    }

    public Server getServer() {
        return Server.getInstance();
    }

    public GameCommandManager getCommandManager() {
        return getMain().getCommandManager();
    }

    public LootManager getLootManager() {
        return getMain().getLootManager();
    }

    public GameLoader getMain() {
        return GameLoader.getInstance();
    }
}
