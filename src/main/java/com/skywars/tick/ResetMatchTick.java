package com.skywars.tick;

import com.skywars.match.Match;
import com.skywars.match.MatchStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResetMatchTick implements Runnable {

    private final Match match;

    @Override
    public void run() {
        match.close();
        match.init();
        match.setStatus(MatchStatus.OPEN);
    }
}
