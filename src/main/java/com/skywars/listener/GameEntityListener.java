package com.skywars.listener;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityShootBowEvent;

public class GameEntityListener extends BaseListener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }

        if (cancelIfIsWaiting(((Player) entity), event)) {
            return;
        }
    }

    @EventHandler
    public void onShootBow(EntityShootBowEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }

        if (cancelIfIsWaiting(((Player) entity), event)) {
            return;
        }
    }
}
