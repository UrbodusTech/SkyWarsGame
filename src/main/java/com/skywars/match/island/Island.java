package com.skywars.match.island;

import cn.nukkit.Player;
import cn.nukkit.math.Vector3;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Island {

    private final Vector3 spawn;
    private Player owner = null;
}
