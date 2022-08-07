package com.skywars.tick;

import com.skywars.event.match.MatchResetFinishedEvent;
import com.skywars.match.Match;
import com.skywars.match.MatchStatus;
import com.skywars.utils.EventUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResetMatchTick implements Runnable {

    private final Match match;

    @Override
    public void run() {
        match.close();
        match.init();
        match.setStatus(MatchStatus.OPEN);
        EventUtils.callEvent(new MatchResetFinishedEvent(match));
    }
}
