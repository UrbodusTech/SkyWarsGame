package com.skywars.match.island;

import cn.nukkit.Player;
import cn.nukkit.math.Vector3;
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
        for (Integer[] coords : data.getIslandSpawn()) {
            islands.add(new Island(new Vector3(coords[0], coords[1], coords[2])));
        }
    }

    public Island findIslandAvailable(Player player) {
        for (Island island : islands) {
            if (island.getOwner() == null) {
                island.setOwner(player);

                return island;
            }
        }

        return null;
    }

    public void removeOwnerFromIsland(Player player) {
        for (Island island : islands) {
            if (island.getOwner() == player) {
                island.setOwner(null);

                break;
            }
        }
    }
}
