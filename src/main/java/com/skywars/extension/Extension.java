package com.skywars.extension;

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

    public synchronized void init(@NonNull File file, @NonNull ExtensionInfo info, ExtensionManager extensionManager) {
        if (initialized) {
            throw new IllegalStateException("Extension is already initialized!");
        }

        initialized = true;
        file.getParentFile().mkdirs();

        this.file = file;
        this.info = info;
        this.extensionManager = extensionManager;
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
}
