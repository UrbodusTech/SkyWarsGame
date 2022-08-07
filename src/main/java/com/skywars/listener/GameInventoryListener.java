package com.skywars.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.inventory.InventoryTransactionEvent;

public class GameInventoryListener extends BaseListener {

    @EventHandler
    public void onInventory(InventoryTransactionEvent event) {
        if (cancelIfIsNecessary(event.getTransaction().getSource(), event)) {
            return;
        }
    }
}

