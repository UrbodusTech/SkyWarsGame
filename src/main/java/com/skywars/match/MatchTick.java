package com.skywars.match;

import com.skywars.tick.GameTick;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Getter
public class MatchTick {

    private final Match match;

    private ScheduledExecutorService scheduled = null;
    private GameTick gameTick = null;

    public void check() {
        if (match.getPlayingSize() == 0) {
            stop();

            return;
        }

        if (match.getPlayingSize() > 0 && scheduled == null) {
            start();
        }
    }

    public void start() {
        if (scheduled != null) {
            stop();
        }
        gameTick = new GameTick(match);
        scheduled = Executors.newSingleThreadScheduledExecutor();
        scheduled.scheduleAtFixedRate(gameTick, 0, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        if (scheduled != null) {
            scheduled.shutdownNow();
            scheduled = null;
        }

        if (gameTick != null) {
            gameTick = null;
        }
    }
}
