package com.skywars.session;

import cn.nukkit.Player;
import cn.nukkit.utils.BossBarColor;
import cn.nukkit.utils.DummyBossBar;
import com.skywars.match.Match;
import com.skywars.match.island.Island;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class GameSession {

    private final Player player;
    private Match currentMatch = null;
    private Island island = null;

    private DummyBossBar bossBar = null;

    public void createBossBar() {
        bossBar = new DummyBossBar.Builder(player)
                .text("")
                .color(BossBarColor.RED)
                .build();
        bossBar.create();
    }
}
