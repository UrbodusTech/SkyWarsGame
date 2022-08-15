package com.skywars.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import com.skywars.event.PlayerMatchEvent;
import com.skywars.match.Match;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class PlayerBlockCommandEvent extends PlayerMatchEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final String command;

    public PlayerBlockCommandEvent(@NonNull Player player, @NonNull Match match, String command) {
        super(player, match);

        this.command = command;
    }
}
