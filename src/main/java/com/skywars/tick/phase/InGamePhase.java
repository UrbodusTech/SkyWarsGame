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

        if (getMatch().checkForWinner()) {
            getMatch().getBroadcast().publishResults();
            getMatch().getBroadcast().publishSound("firework.launch");
            finishedGame = true;

            return;
        }


        if (timer.isFinished()) {
            if (timer.getRepeats() < getMatch().getData().getMaxTimerRepetitions()) {
                getMatch().refillChests();
                timer.restart();
            } else {
                finishedGame = true; // Advance to the next phase because the time is finished
            }
        }

        getMatch().getBroadcast().publishBossBar("BOSSBAR_TIME", new String[]{timer.format()});
    }
}
