package com.skywars.tick.phase;

import com.skywars.match.Match;
import com.skywars.session.GameSession;
import com.skywars.tick.Timer;
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
           for (GameSession session : getMatch().getPlayers()) {
               if (session == null) {
                   continue;
               }

               if (session.getPlayer() == null) {
                   continue;
               }

               getMatch().removePlayer(session.getPlayer());
           }

           return;
        }

        getMatch().getBroadcast().publishBossBar("BOSSBAR_GAME_END", new String[]{timer.format()});
    }
}
