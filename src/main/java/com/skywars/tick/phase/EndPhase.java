package com.skywars.tick.phase;

import com.skywars.match.Match;
import com.skywars.session.GameSession;
import com.skywars.tick.Timer;
import lombok.NonNull;

public class EndPhase extends Phase {

    private final Timer timer;
    private boolean sendResults = false;

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
        if (!sendResults) {
            getMatch().getBroadcast().publishResults();
            sendResults = true;
        }

        if (timer.isFinished()) {
            getMatch().getBroadcast().publishRemove();

           return;
        }

        getMatch().getBroadcast().publishBossBar("BOSSBAR_GAME_END", new String[]{timer.format()});
    }
}
