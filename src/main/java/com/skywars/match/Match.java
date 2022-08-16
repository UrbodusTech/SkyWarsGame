package com.skywars.match;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityChest;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import com.skywars.GameLoader;
import com.skywars.event.match.MatchCloseEvent;
import com.skywars.event.match.MatchOpenEvent;
import com.skywars.event.match.MatchRefillChestsEvent;
import com.skywars.event.match.MatchStartResetEvent;
import com.skywars.event.player.PlayerConvertSpectatorEvent;
import com.skywars.event.player.PlayerJoinMatchEvent;
import com.skywars.event.player.PlayerQuitMatchEvent;
import com.skywars.generic.Identifiable;
import com.skywars.loot.Loot;
import com.skywars.loot.LootManager;
import com.skywars.loot.LootType;
import com.skywars.tick.ResetMatchTick;
import com.skywars.utils.*;
import com.skywars.match.island.Island;
import com.skywars.match.island.IslandStorage;
import com.skywars.session.GameSession;
import com.skywars.session.SessionManager;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Match extends IslandStorage implements Identifiable<UUID> {

    private final UUID id;
    private final MatchData data;

    private final List<GameSession> players;
    private MatchStatus status = MatchStatus.OPEN;

    private final MatchBroadcast broadcast;
    private final MatchTick tick;

    private String winnerName = null;
    private final List<String> aliveNames;

    public Match(UUID uuid, MatchData data) {
        super(data);
        this.id = uuid;
        this.data = data;

        players = new ArrayList<>();
        aliveNames = new ArrayList<>();
        broadcast = new MatchBroadcast(this);
        tick = new MatchTick(this);
    }

    public void init() {
        ResourceUtils.releaseMatchMap(this);
        if (data.getCountDownMinPlayers() == 0) {
            data.setCountDownMinPlayers(2);
        }
        if (data.getCountDownTimerSeconds() < 60) {
            data.setCountDownTimerSeconds(6 * 60);
        }
        if (data.getMaxTimerRepetitions() == 0) {
            data.setMaxTimerRepetitions(2);
        }
        if (data.getMinLayer() <= 0) {
            data.setMinLayer(2);
        }

        LevelUtils.loadSkyWarsLevel(id);
        EventUtils.callEvent(new MatchOpenEvent(this));
    }

    public void close() {
        LevelUtils.unloadSkyWarsLevel(id);
        ResourceUtils.deleteMatchMap(this);
        EventUtils.callEvent(new MatchCloseEvent(this));
    }

    public void reset() {
        EventUtils.callEvent(new MatchStartResetEvent(this));
        status = MatchStatus.RESETTING;
        winnerName = null;
        players.clear();
        aliveNames.clear();
        GameLoader.getInstance().getMatchManager()
                .getMapPool()
                .execute(new ResetMatchTick(this));
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
        if (sessionManager.contains(player.getName())) {
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
        aliveNames.add(player.getName());

        if (players.size() >= data.getIslandSpawn().size()) {
            status = MatchStatus.FULL;
        }

        AttributeUtils.sendScreen(player);
        Vector3 spawn = island.getSpawn();
        player.teleport(new Position(spawn.getX(), spawn.getY(), spawn.getZ(), LevelUtils.getSkyWarsLevel(id)));
        AttributeUtils.sendInitialJoin(player);
        broadcast.publishMessage("PLAYER_JOIN", new String[]{player.getName(), String.valueOf(getPlayingSize()), String.valueOf(getMaxSlots())});
        player.sendMessage(LangUtils.translate(player, "MATCH_USE_EXIT"));
        EventUtils.callEvent(new PlayerJoinMatchEvent(player, this, island));
        tick.check();
    }

    public void removePlayer(Player player) {
        removeOwnerFromIsland(player);
        player.teleport(Server.getInstance().getDefaultLevel().getSpawnLocation());
        AttributeUtils.sendDefault(player);

        SessionManager sessionManager = GameLoader.getInstance().getSessionManager();
        GameSession session = sessionManager.getSessionByPlayer(player);

        if (session != null) {
            players.remove(session);
            aliveNames.remove(player.getName());
            if (session.getBossBar() != null) {
                session.getBossBar().destroy();
                session.setBossBar(null);
            }
            sessionManager.unregister(session.getId());

            if (status == MatchStatus.OPEN || status == MatchStatus.FULL) {
                broadcast.publishMessage("PLAYER_LEFT", new String[]{player.getName(), String.valueOf(getPlayingSize()), String.valueOf(getMaxSlots())});
            }

            if (status == MatchStatus.FULL && getPlayingSize() < getMaxSlots()) {
                status = MatchStatus.OPEN;
            }
        }

        EventUtils.callEvent(new PlayerQuitMatchEvent(player, this));
        tick.check();
    }

    public void addSpectator(Player player) {
        if (!aliveNames.contains(player.getName())) {
            return;
        }

        AttributeUtils.sendSpectator(player);
        aliveNames.remove(player.getName());
        player.teleport(player.getPosition().add(0, 12, 0));
        player.sendMessage(LangUtils.translate(player, "MATCH_USE_EXIT"));
        broadcast.publishPopup("PLAYER_ELIMINATED", new String[]{player.getName(), String.valueOf(aliveNames.size())});
        broadcast.publishSound("block.turtle_egg.break");
        EventUtils.callEvent(new PlayerConvertSpectatorEvent(player, this));
    }

    private String tryObtainWinner() {
        for (String candidate : aliveNames) {
            Player player = Server.getInstance().getPlayerExact(candidate);
            if (player == null) {
                continue;
            }

            if (player.getGamemode() == Player.SURVIVAL) {
                return candidate;
            }
        }

        return null;
    }

    /*
     * Return true if in-game tick can continue to the next phase
     */
    public boolean checkForWinner() {
        if (aliveNames.isEmpty()) {
            return true;
        }

        if (aliveNames.size() == 1) {
            winnerName = tryObtainWinner();

            return true;
        }

        return false;
    }

    public void refillChests() {
        MatchRefillChestsEvent event = new MatchRefillChestsEvent(this);
        EventUtils.callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        refillIslandChests();
        refillOtherChests();
    }

    private void refillIslandChests() {
        for (Integer[] coords : data.getIslandChests()) {
            if (coords.length < 3) {
                continue;
            }

            Vector3 vector = new Vector3(coords[0], coords[1], coords[2]);
            getAndFillChest(vector, LootType.ISLAND);
        }
    }

    private void refillOtherChests() {
        for (Integer[] coords : data.getOtherChests()) {
            if (coords.length < 3) {
                continue;
            }

            Vector3 vector = new Vector3(coords[0], coords[1], coords[2]);
            getAndFillChest(vector, LootType.OTHER);
        }
    }

    private void getAndFillChest(Vector3 vector3, LootType type) {
        Level level = LevelUtils.getSkyWarsLevel(id);
        if (level == null) {
            return;
        }

        BlockEntity blockEntity = level.getBlockEntity(vector3);
        if (!(blockEntity instanceof BlockEntityChest)) {
            return;
        }

        LootManager manager = GameLoader.getInstance().getLootManager();
        Loot loot;
        if (type == LootType.ISLAND) {
            loot = manager.getRandomLootIsland();
        } else {
            loot = manager.getRandomLootOther();
        }

        if (loot == null) {
            return;
        }

        loot.fillChest(((BlockEntityChest) blockEntity).getRealInventory());
    }
}
