package com.skywars.listener;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.event.Listener;
import com.skywars.GameLoader;
import com.skywars.match.Match;
import com.skywars.match.MatchStatus;
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

    public boolean cancelIfIsWaiting(Player player, Event event) {
        Match match = getMatchByPlayer(player);
        if (match == null) {
            return false;
        }

        if (!(event instanceof Cancellable)) {
            return false;
        }

        if (match.getStatus() == MatchStatus.OPEN || match.getStatus() == MatchStatus.FULL || match.getStatus() == MatchStatus.STARTING) {
            event.setCancelled(true);

            return true;
        }

        return false;
    }

    public void cancelFullIfInMatch(Player player, Event event) {
        Match match = getMatchByPlayer(player);
        if (match == null) {
            return;
        }

        if (!(event instanceof Cancellable)) {
            return;
        }

        event.setCancelled(true);
    }
}
