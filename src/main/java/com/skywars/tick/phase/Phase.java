package com.skywars.tick.phase;

import com.skywars.match.Match;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class Phase {

    @NonNull
    private final Match match;

    public abstract boolean preconditions();

    public abstract void execute();
}
