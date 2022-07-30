package com.skywars.utils;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import com.skywars.match.Match;
import lombok.NonNull;

import java.util.UUID;

public final class LevelUtils {

    public static void loadSkyWarsLevel(@NonNull UUID uuid) {
        Server server = Server.getInstance();
        String world = server.getDataPath() + "/worlds/sw_queue/" + uuid + "/";
        if (server.isLevelLoaded(world)) {
            return;
        }
        server.loadLevel(world);
        Level level = server.getLevelByName(world);
        level.setTime(Level.TIME_DAY);
        level.stopTime();
        level.setRaining(false);
        level.setThundering(false);

    }

    public static void unloadSkyWarsLevel(@NonNull UUID uuid) {
        Server server = Server.getInstance();
        String world = server.getDataPath() + "/worlds/sw_queue/" + uuid + "/";
        if (!server.isLevelLoaded(world)) {
            return;
        }
        server.unloadLevel(server.getLevelByName(world));
    }

    public static Level getSkyWarsLevel(@NonNull UUID uuid) {
        Server server = Server.getInstance();
        String world = server.getDataPath() + "/worlds/sw_queue/" + uuid + "/";

        return server.getLevelByName(world);
    }

    public static void prepareSkyWarsLevel(Match match) {
        Server server = Server.getInstance();
        String world = server.getDataPath() + "/worlds/sw_queue/" + match.getUuid().toString() + "/";

        if (!server.isLevelLoaded(world) && match.getPlayers().size() != 0) {
            loadSkyWarsLevel(match.getUuid());

            return;
        }

        unloadSkyWarsLevel(match.getUuid());
    }
}
