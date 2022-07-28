package com.skywars.session;

import cn.nukkit.Player;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class SessionManager {

    private final Map<String, GameSession> sessions;

    public SessionManager() {
        sessions = new ConcurrentHashMap<>();
    }

    public void createGameSession(Player player) {
        if (sessions.containsKey(player.getName())) {
            return;
        }

        sessions.put(player.getName(), new GameSession(player));
    }

    public void removeGameSession(Player player) {
        sessions.remove(player.getName());
    }

    public GameSession getSessionByPlayer(Player player) {
        return sessions.get(player.getName());
    }

    public void clear() {
        sessions.clear();
    }
}
