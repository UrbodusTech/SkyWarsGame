package com.skywars.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.event.player.*;
import com.skywars.GameLoader;
import com.skywars.match.Match;
import com.skywars.session.GameSession;
import com.skywars.utils.LangUtils;

public class GamePlayerListener extends BaseListener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Match match = getMatchByPlayer(player);
        if (match == null) {
            return;
        }

        match.removePlayer(player);
    }

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        Match match = getMatchByPlayer(player);
        if (match == null) {
            return;
        }

        if (event.getMessage().startsWith("/")) {
            player.sendMessage(LangUtils.translate(player, "BLOCKED_COMMANDS"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (cancelIfIsWaiting(player, event)) {
            return;
        }
    }

    @EventHandler
    public void onEatFood(PlayerEatFoodEvent event) {
        Player player = event.getPlayer();
        if (cancelIfIsWaiting(player, event)) {
            return;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (cancelIfIsWaiting(player, event)) {
            //TODO add handler for menu item

            return;
        }
    }

    @EventHandler
    public void onAchievementAwarded(PlayerAchievementAwardedEvent event) {
        cancelFullIfInMatch(event.getPlayer(), event);
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        cancelFullIfInMatch(event.getPlayer(), event);
    }

    @EventHandler
    public void onEditBook(PlayerEditBookEvent event) {
        cancelFullIfInMatch(event.getPlayer(), event);
    }

    @EventHandler
    public void onInventory(InventoryTransactionEvent event) {
        if (cancelIfIsWaiting(event.getTransaction().getSource(), event)) {
            return;
        }
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (message.charAt(0) != '!') {
            return;
        }

        if (!sessionManager.exists(player)) {
            return;
        }

        GameSession session = sessionManager.getSessionByPlayer(player);
        GameLoader.getInstance().getCommandManager().processCommand(session, message);
        event.setCancelled(true);
    }
}
