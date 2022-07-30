package com.skywars.tick.phase;

import com.skywars.match.Match;
import com.skywars.match.MatchStatus;
import com.skywars.tick.Timer;
import lombok.NonNull;

public class InGamePhase extends Phase {

    private final Timer timer;

    public InGamePhase(@NonNull Match match) {
        super(match);

        timer = new Timer(match.getData().getCountDownTimerSeconds());
    }

    @Override
    public boolean preconditions() {
        return getMatch().getStatus() == MatchStatus.CLOSE;
    }

    @Override
    public void execute() {
        timer.down();
        if (timer.isFinished()) {
            if (timer.getRepeats() < getMatch().getData().getMaxTimerRepetitions()) {
                // TODO refill chests
                timer.restart();
            } else {
                // TODO stop match
            }
        }




    }
}
