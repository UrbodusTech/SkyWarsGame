package com.skywars.extension;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginLogger;
import com.skywars.GameLoader;
import com.skywars.lang.LangManager;
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
    }

    public void setEnabled(boolean value) {
        if (initialized) {
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
        return GameLoader.getInstance().getMatchManager();
    }

    public LangManager getLangManager() {
        return GameLoader.getInstance().getLangManager();
    }

    public SessionManager getSessionManager() {
        return GameLoader.getInstance().getSessionManager();
    }

    public Server getServer() {
        return Server.getInstance();
    }

    public PluginLogger getLogger() {
        return GameLoader.getInstance().getLogger();
    }
}
