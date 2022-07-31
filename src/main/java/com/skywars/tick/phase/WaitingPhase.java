package com.skywars.tick.phase;

import com.skywars.match.Match;
import com.skywars.match.MatchStatus;
import com.skywars.tick.Timer;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class WaitingPhase extends Phase {

    private final Timer timer;

    public WaitingPhase(@NonNull Match match) {
        super(match);

        timer = new Timer(match.getData().getCountDownSeconds());
        timer.start();
    }

    @Override
    public boolean preconditions() {
        boolean status = getMatch().getStatus() == MatchStatus.OPEN || getMatch().getStatus() == MatchStatus.FULL || getMatch().getStatus() == MatchStatus.STARTING;
        return status && !timer.isFinished();
    }

    @Override
    public void execute() {
        int playing = getMatch().getPlayingSize();
        if (playing < getMatch().getData().getCountDownMinPlayers()) {
            if (timer.getTime() < timer.getSeconds()) {
                timer.restart();
            }

            getMatch().getBroadcast().publishPopup("WAITING_STATS_LEFT", new String[]{
                    String.valueOf(getMatch().getPlayingSize()),
                    String.valueOf(getMatch().getMaxSlots())
            });

            return;
        }

        timer.down();
        getMatch().getBroadcast().publishPopup("WAITING_STATS_STARTING", new String[]{
                String.valueOf(getMatch().getPlayingSize()),
                String.valueOf(getMatch().getMaxSlots()),
                timer.format()
        });

        if (timer.getTime() <= 5) {
            if (getMatch().getStatus() != MatchStatus.STARTING) {
                getMatch().setStatus(MatchStatus.STARTING);
            }

            getMatch().getBroadcast().publishSound("random.pop");
        }

        if (timer.getTime() == 0) {
            getMatch().setStatus(MatchStatus.CLOSE);
            getMatch().refillChests();
            getMatch().getBroadcast().publishSound("bubble.up");
            getMatch().getBroadcast().publishStartAttributes();
            getMatch().getBroadcast().publishTitle(
                    "AUTHOR_TITLE_MAP",
                    new String[]{getMatch().getData().getName()},
                    "AUTHOR_TITLE_MAP_NAME",
                    new String[]{getMatch().getData().getAuthor()}
            );
        }
    }
}
