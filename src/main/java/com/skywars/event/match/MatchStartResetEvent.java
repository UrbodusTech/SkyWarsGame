package com.skywars.event.match;

import cn.nukkit.event.HandlerList;
import com.skywars.event.MatchEvent;
import com.skywars.match.Match;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class MatchStartResetEvent extends MatchEvent {

    private static final HandlerList handlers = new HandlerList();

    public MatchStartResetEvent(@NonNull Match match) {
        super(match);
    }
}
