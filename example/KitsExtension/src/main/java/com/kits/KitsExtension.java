package com.kits;

import com.skywars.extension.Extension;

public class KitsExtension extends Extension {

    @Override
    public void install() {
        getLogger().info("Kits extension enabled!");
    }

    @Override
    public void uninstall() {
        getLogger().info("Kits extension disabled!");
    }
}
