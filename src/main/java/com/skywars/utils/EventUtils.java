package com.skywars.utils;

import cn.nukkit.event.Event;
import com.skywars.GameLoader;

public final class EventUtils {

    public static void callEvent(Event event) {
        GameLoader.getInstance().getServer().getPluginManager().callEvent(event);
    }
}
