package com.skywars.event;

import cn.nukkit.event.Event;
import com.skywars.match.Match;
import com.skywars.session.GameSession;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class SessionMatchEvent extends Event {

    @NonNull
    private final GameSession session;
    @NonNull
    private final Match match;
}
