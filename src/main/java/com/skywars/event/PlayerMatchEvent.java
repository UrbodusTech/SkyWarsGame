package com.skywars.event;

import cn.nukkit.Player;
import cn.nukkit.event.Event;
import com.skywars.match.Match;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class PlayerMatchEvent extends Event {

    @NonNull
    private final Player player;
    @NonNull
    private final Match match;
}
