package com.skywars.tick;

import com.skywars.match.Match;
import com.skywars.tick.phase.Phase;
import com.skywars.tick.phase.WaitingPhase;

public class GameTick implements Runnable {

    private final Match match;
    private final Phase waitingPhase;

    public GameTick(Match match) {
        this.match = match;

        waitingPhase = new WaitingPhase(match);
    }

    @Override
    public void run() {
        if (waitingPhase.preconditions()) {
            waitingPhase.execute();

            return;
        }


    }
}
