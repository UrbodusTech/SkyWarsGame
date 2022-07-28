package com.skywars.session;

import cn.nukkit.Player;
import com.skywars.match.Match;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class GameSession {

    private final Player player;
    private Match currentMatch = null;
}
