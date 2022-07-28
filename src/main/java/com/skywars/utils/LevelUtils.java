package com.skywars.utils;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import com.skywars.match.Match;
import lombok.NonNull;

import java.util.UUID;

public final class LevelUtils {

    public static void loadSkyWarsLevel(@NonNull UUID uuid) {
        Server server = Server.getInstance();
        String world = server.getDataPath() + "/worlds/sw_queue/" + uuid.toString() + "/";
        if (server.isLevelLoaded(world)) {
            return;
        }
        server.loadLevel(world);
    }

    public static void unloadSkyWarsLevel(@NonNull UUID uuid) {
        Server server = Server.getInstance();
        String world = server.getDataPath() + "/worlds/sw_queue/" + uuid.toString() + "/";
        if (!server.isLevelLoaded(world)) {
            return;
        }
        server.unloadLevel(server.getLevelByName(world), true);
    }

    public static Level getSkyWarsLevel(@NonNull UUID uuid) {
        Server server = Server.getInstance();
        String world = server.getDataPath() + "/worlds/sw_queue/" + uuid.toString() + "/";

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
