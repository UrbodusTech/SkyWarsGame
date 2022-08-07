package com.skywars.listener;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityShootBowEvent;
import com.skywars.match.Match;

public class GameEntityListener extends BaseListener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }

        Player player = ((Player) entity);
        if (cancelIfIsNecessary(((Player) entity), event)) {
            return;
        }

        Match match = getMatchByPlayer(player);
        if (match == null) {
            return;
        }

        if (event.getFinalDamage() >= player.getHealth()) {
            event.setCancelled(true);
            match.addSpectator(player);
        }
    }

    @EventHandler
    public void onShootBow(EntityShootBowEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }

        Player player = ((Player) entity);
        if (cancelIfIsNecessary(player, event)) {
            return;
        }
    }
}
