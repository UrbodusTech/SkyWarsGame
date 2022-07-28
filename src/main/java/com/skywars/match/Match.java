package com.skywars.match;

import cn.nukkit.Player;
import com.skywars.GameLoader;
import com.skywars.match.island.IslandStorage;
import com.skywars.session.GameSession;
import com.skywars.session.SessionManager;
import com.skywars.utils.ResourceUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Match extends IslandStorage {

    private final UUID uuid;
    private final MatchData data;

    private final List<GameSession> players = new ArrayList<>();
    private MatchStatus status = MatchStatus.OPEN;

    public Match(UUID uuid, MatchData data) {
        super(data);
        this.uuid = uuid;
        this.data = data;
    }

    public void init() {
        ResourceUtils.releaseMatchMap(this);
    }

    public void close() {
        ResourceUtils.deleteMatchMap(this);
    }

    public void reset() {

    }

    public boolean canJoin() {
        return status == MatchStatus.OPEN && players.size() < data.getIslandSpawn().size();
    }

    public void addPlayer(Player player) {
        SessionManager sessionManager = GameLoader.getInstance().getSessionManager();

        sessionManager.createGameSession(player);
        GameSession session = sessionManager.getSessionByPlayer(player);
        session.setCurrentMatch(this);
        players.add(session);
    }

    public void removePlayer(Player player) {
        SessionManager sessionManager = GameLoader.getInstance().getSessionManager();

        GameSession session = sessionManager.getSessionByPlayer(player);
        if (session != null) {
            // prevent match actions from running while waiting to be removed
            session.setCurrentMatch(null);
        }
        players.remove(session);
        sessionManager.removeGameSession(player);
    }
}
