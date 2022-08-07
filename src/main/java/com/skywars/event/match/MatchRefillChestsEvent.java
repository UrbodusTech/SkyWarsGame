package com.skywars.event.match;

import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import com.skywars.event.MatchEvent;
import com.skywars.match.Match;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class MatchRefillChestsEvent extends MatchEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public MatchRefillChestsEvent(@NonNull Match match) {
        super(match);
    }
}
