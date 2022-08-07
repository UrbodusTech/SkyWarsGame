package com.skywars.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import com.skywars.event.PlayerMatchEvent;
import com.skywars.match.Match;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class PlayerLostMatchEvent extends PlayerMatchEvent {

    private static final HandlerList handlers = new HandlerList();

    public PlayerLostMatchEvent(@NonNull Player player, @NonNull Match match) {
        super(player, match);
    }
}
