package com.skywars.tick.phase;

import com.skywars.event.match.MatchEndEvent;
import com.skywars.match.Match;
import com.skywars.tick.Timer;
import com.skywars.utils.EventUtils;
import lombok.NonNull;

public class EndPhase extends Phase {

    private final Timer timer;

    public EndPhase(@NonNull Match match) {
        super(match);

        timer = new Timer(match.getData().getCountDownEnd());
        timer.start();
    }

    @Override
    public boolean preconditions() {
        return !timer.isFinished();
    }

    @Override
    public void execute() {
        timer.down();

        if (timer.isFinished()) {
            EventUtils.callEvent(new MatchEndEvent(getMatch()));
            getMatch().getBroadcast().publishRemove();

           return;
        }

        getMatch().getBroadcast().publishBossBar("BOSSBAR_GAME_END", new String[]{timer.format()});
    }
}
