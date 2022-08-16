package com.skywars.match;

import cn.nukkit.Player;
import com.skywars.GameLoader;
import com.skywars.generic.Manager;
import com.skywars.utils.LangUtils;
import com.skywars.utils.ResourceUtils;
import lombok.Getter;
import lombok.NonNull;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Getter
public class MatchManager extends Manager<UUID, Match> {

    private final ExecutorService mapPool;

    public MatchManager() {
        mapPool = Executors.newFixedThreadPool(GameLoader.getInstance().getConfig().getInt("thread-pool-size"));
    }

    @Override
    public void init() {
        for (MatchData matchData : ResourceUtils.readValidMatchData()) {
            Match arena = new Match(UUID.randomUUID(), matchData);
            arena.init();
            register(arena);
        }

        GameLoader.getInstance().getLogger().info("Loaded " + getEntries().size() + " matches!");
    }

    public void close() {
        mapPool.shutdown();

        try {
            mapPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (Match match : getEntries()) {
            match.close();
        }

        unregisterAll();
    }

    public boolean queue(Player player) {
        String emptyMsg = LangUtils.translate(player, "EMPTY_MATCH_LIST");

        if (getEntries().size() == 0) {
            player.sendMessage(emptyMsg);

            return false;
        }

        Match foundGame = null;

        for (Match match : getEntries()) {
            if (match.canJoin()) {
                if (foundGame == null || match.getPlayingSize() > foundGame.getPlayingSize()) {
                    foundGame = match;
                }
            }
        }

        if (foundGame == null) {
            player.sendMessage(emptyMsg);
            return false;
        }

        foundGame.addPlayer(player);
        return true;
    }

}
