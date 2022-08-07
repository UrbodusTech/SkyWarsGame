package com.skywars.event.session;

import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import com.skywars.event.SessionMatchEvent;
import com.skywars.match.Match;
import com.skywars.session.GameSession;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class SessionExecuteCommandEvent extends SessionMatchEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final String prefix;
    private final String[] args;

    public SessionExecuteCommandEvent(@NonNull GameSession session, @NonNull Match match, String prefix, String[] args) {
        super(session, match);

        this.prefix = prefix;
        this.args = args;
    }
}
