package com.skywars.match.island;

import com.skywars.match.MatchData;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class IslandStorage {

    private final List<Island> islands;

    public IslandStorage(@NonNull MatchData data) {
        islands = new ArrayList<>();
        setup(data);
    }

    private void setup(@NonNull MatchData data) {

    }
}
