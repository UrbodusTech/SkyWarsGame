package com.skywars.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import com.skywars.event.PlayerMatchEvent;
import com.skywars.match.Match;
import com.skywars.match.island.Island;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class PlayerJoinMatchEvent extends PlayerMatchEvent {

    private static final HandlerList handlers = new HandlerList();

    private final Island island;

    public PlayerJoinMatchEvent(@NonNull Player player, @NonNull Match match, Island island) {
        super(player, match);

        this.island = island;
    }
}
