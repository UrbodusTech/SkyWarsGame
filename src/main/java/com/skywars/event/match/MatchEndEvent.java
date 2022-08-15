package com.skywars.event.match;

import cn.nukkit.event.HandlerList;
import com.skywars.event.MatchEvent;
import com.skywars.match.Match;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class MatchEndEvent extends MatchEvent {

    private static final HandlerList handlers = new HandlerList();

    public MatchEndEvent(@NonNull Match match) {
        super(match);
    }

    public static HandlerList getHandlers() {
        return handlers;
    }
}
