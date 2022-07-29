package com.skywars.match;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import com.skywars.GameLoader;
import com.skywars.utils.LangUtils;
import com.skywars.match.island.Island;
import com.skywars.match.island.IslandStorage;
import com.skywars.session.GameSession;
import com.skywars.session.SessionManager;
import com.skywars.utils.AttributeUtils;
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

    private final MatchBroadcast broadcast;

    public Match(UUID uuid, MatchData data) {
        super(data);
        this.uuid = uuid;
        this.data = data;

        players = new ArrayList<>();
        broadcast = new MatchBroadcast(this);
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

    public int getPlayingSize() {
        return players.size();
    }

    public int getMaxSlots() {
        return data.getIslandSpawn().size();
    }

    public boolean canJoin() {
        return status == MatchStatus.OPEN && getPlayingSize() < getMaxSlots();
    }

    public void addPlayer(Player player) {
        player.sendMessage(LangUtils.translate(player, "CONNECTING_MATCH", new String[] {data.getName().toUpperCase()}));

        SessionManager sessionManager = GameLoader.getInstance().getSessionManager();
        if (sessionManager.exists(player)) {
            player.sendMessage(LangUtils.translate(player, "ALREADY_PLAYING"));

            return;
        }

        Island island = findIslandAvailable(player);
        if (island == null) {
            player.sendMessage(LangUtils.translate(player, "NOT_ISLAND_AVAILABLE"));

            return;
        }

        island.setOwner(player);
        GameSession session = sessionManager.createGameSession(player);
        session.setCurrentMatch(this);
        session.setIsland(island);
        players.add(session);

        if (players.size() >= data.getIslandSpawn().size()) {
            status = MatchStatus.FULL;
        }

        LevelUtils.prepareSkyWarsLevel(this);

        AttributeUtils.sendScreen(player);
        Vector3 spawn = island.getSpawn();
        player.teleport(new Position(spawn.getX(), spawn.getY(), spawn.getZ(), LevelUtils.getSkyWarsLevel(uuid)));
        AttributeUtils.sendInitialJoin(player);
        broadcast.publishMessage("PLAYER_JOIN", new String[]{player.getName(), String.valueOf(getPlayingSize()), String.valueOf(getMaxSlots())});
    }

    public void removePlayer(Player player) {
        SessionManager sessionManager = GameLoader.getInstance().getSessionManager();
        if (sessionManager.exists(player)) {
            GameSession session = sessionManager.getSessionByPlayer(player);
            players.remove(session);
            sessionManager.removeGameSession(player);

            if (status == MatchStatus.OPEN || status == MatchStatus.FULL) {
                broadcast.publishMessage("PLAYER_LEFT", new String[]{player.getName(), String.valueOf(getPlayingSize()), String.valueOf(getMaxSlots())});
            }

            if (status == MatchStatus.FULL && getPlayingSize() < getMaxSlots()) {
                status = MatchStatus.OPEN;
            }
        }

        removeOwnerFromIsland(player);
        player.teleport(player.getServer().getDefaultLevel().getSpawnLocation());
        AttributeUtils.sendDefault(player);

        LevelUtils.prepareSkyWarsLevel(this);
    }
}
