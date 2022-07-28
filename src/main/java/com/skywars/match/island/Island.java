package com.skywars.match.island;

import cn.nukkit.math.Vector3;
import com.skywars.session.GameSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Island {

    private final Vector3 spawn;
    private GameSession owner = null;
    private boolean destroyed = false;
}
