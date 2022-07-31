package com.skywars.tick.phase;

import com.skywars.match.Match;
import com.skywars.match.MatchStatus;
import com.skywars.tick.Timer;
import lombok.NonNull;

public class InGamePhase extends Phase {

    private final Timer timer;
    private boolean finishedGame = false;

    public InGamePhase(@NonNull Match match) {
        super(match);

        timer = new Timer(match.getData().getCountDownTimerSeconds());
        timer.start();
    }

    @Override
    public boolean preconditions() {
        return getMatch().getStatus() == MatchStatus.CLOSE && !finishedGame;
    }

    @Override
    public void execute() {
        timer.down();
        if (timer.isFinished()) {
            if (timer.getRepeats() < getMatch().getData().getMaxTimerRepetitions()) {
                // TODO refill chests
                timer.restart();
            } else {
                finishedGame = true; // Advance to the next phase because the time is finished
            }
        }

        getMatch().getBroadcast().publishBossBar("BOSSBAR_TIME", new String[]{timer.format()});
        if (getMatch().getWinner() != null) {
            finishedGame = true; // Advance to the next phase because there is a winner
        }
    }
}
