package com.skywars.match;

import cn.nukkit.Player;
import com.skywars.GameLoader;
import com.skywars.utils.LangUtils;
import com.skywars.utils.ResourceUtils;
import lombok.Getter;
import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class MatchManager {

    private final Map<UUID, Match> matches;

    public MatchManager() {
        matches = new HashMap<>();
    }

    public void init() {
        for (MatchData matchData : ResourceUtils.readValidMatchData()) {
            Match arena = new Match(UUID.randomUUID(), matchData);
            arena.init();
            matches.put(arena.getUuid(), arena);
        }

        GameLoader.getInstance().getLogger().info("Loaded " + matches.size() + " matches!");
    }

    public void close() {
        for (Match match : matches.values()) {
            match.close();
        }
        matches.clear();
    }

    public void deleteMatch(UUID uuid) {
        matches.remove(uuid);
    }

    public Match getMatch(@NonNull UUID uuid) {
        return matches.get(uuid);
    }

    public boolean queue(Player player) {
        String emptyMsg = LangUtils.translate(player, "EMPTY_MATCH_LIST");

        if (matches.size() == 0) {
            player.sendMessage(emptyMsg);

            return false;
        }

        // Step 1: found available games
        Map<UUID, Match> available = matches.entrySet().stream()
                .filter(found -> found.getValue().canJoin())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (available.size() == 0) {
            player.sendMessage(emptyMsg);

            return false;
        }

        // Step 2: find the match with more players or choose a random one
        Map<Match, Integer> stack = new HashMap<>();
        available.forEach((key, value) -> stack.put(value, value.getPlayers().size()));
        available.clear();

        Match maxEntry = Collections.max(stack.entrySet(), Map.Entry.comparingByValue()).getKey();
        stack.clear();
        maxEntry.addPlayer(player);

        return true;
    }
}
