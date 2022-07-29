package com.skywars.listener;

import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import com.skywars.GameLoader;
import com.skywars.match.Match;
import com.skywars.session.SessionManager;
import lombok.NonNull;

public class BaseListener implements Listener {

    protected final SessionManager sessionManager;

    public BaseListener() {
        sessionManager = GameLoader.getInstance().getSessionManager();
    }

    protected Match getMatchByPlayer(@NonNull Player player) {
        if (!sessionManager.exists(player)) {
            return null;
        }

        return sessionManager.getSessionByPlayer(player).getCurrentMatch();
    }


}
