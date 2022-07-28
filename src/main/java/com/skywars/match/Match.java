package com.skywars.match;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import com.skywars.GameLoader;
import com.skywars.lang.LangUtils;
import com.skywars.match.island.Island;
import com.skywars.match.island.IslandStorage;
import com.skywars.session.GameSession;
import com.skywars.session.SessionManager;
import com.skywars.utils.LevelUtils;
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

    private final List<GameSession> players;
    private MatchStatus status = MatchStatus.OPEN;

    public Match(UUID uuid, MatchData data) {
        super(data);
        this.uuid = uuid;
        this.data = data;

        players = new ArrayList<>();
    }

    public void init() {
        ResourceUtils.releaseMatchMap(this);
    }

    public void close() {
        LevelUtils.unloadSkyWarsLevel(uuid);
        ResourceUtils.deleteMatchMap(this);
    }

    public void reset() {
    }

    public boolean canJoin() {
        return status == MatchStatus.OPEN && players.size() < data.getIslandSpawn().size();
    }

    public void addPlayer(Player player) {
        player.sendMessage(LangUtils.translate(player, "CONNECTING_MATCH", new String[] {data.getName()}));

        SessionManager sessionManager = GameLoader.getInstance().getSessionManager();
        if (sessionManager.exists(player)) {
            player.sendMessage(LangUtils.translate(player, "ALREADY_PLAYING"));

            return;
        }

        Island island = findIslandAvailable(player);
        if (island == null) {

            return;
        }

        island.setOwner(player);
        GameSession session = sessionManager.createGameSession(player);
        session.setCurrentMatch(this);
        session.setIsland(island);
        players.add(session);

        LevelUtils.prepareSkyWarsLevel(this);

        Vector3 spawn = island.getSpawn();
        player.teleport(new Position(spawn.getX(), spawn.getY(), spawn.getZ(), LevelUtils.getSkyWarsLevel(uuid)));
    }

    public void removePlayer(Player player) {
        SessionManager sessionManager = GameLoader.getInstance().getSessionManager();
        if (sessionManager.exists(player)) {
            GameSession session = sessionManager.getSessionByPlayer(player);
            players.remove(session);
            sessionManager.removeGameSession(player);
        }

        removeOwnerFromIsland(player);
        player.teleport(player.getServer().getDefaultLevel().getSpawnLocation());

        LevelUtils.prepareSkyWarsLevel(this);
    }
}
