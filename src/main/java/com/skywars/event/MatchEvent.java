package com.skywars.event;

import cn.nukkit.event.Event;
import com.skywars.match.Match;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class MatchEvent extends Event {

    @NonNull
    private final Match match;

}
