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

    public GameSession createGameSession(Player player) {
        if (sessions.containsKey(player.getName())) {
            return sessions.get(player.getName());
        }

        GameSession session = new GameSession(player);
        sessions.put(player.getName(), session);

        return session;
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

    public boolean exists(Player player) {
        return sessions.containsKey(player.getName());
    }
}
