package com.skywars.session;

import cn.nukkit.Player;
import com.skywars.generic.Manager;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class SessionManager extends Manager<String, GameSession> {

    @Override
    protected Map<String, GameSession> generateValue() {
        return new ConcurrentHashMap<>();
    }

    @Override
    public void init() {
        // ...
    }

    public GameSession getSessionByPlayer(Player player) {
        return get(player.getName());
    }

    public GameSession createGameSession(Player player) {
        GameSession session = get(player.getName());

        if (session == null) {
            session = new GameSession(player);
            register(session);
        }

        return session;
    }

}
