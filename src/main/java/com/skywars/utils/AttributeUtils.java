package com.skywars.utils;

import cn.nukkit.Player;
import cn.nukkit.potion.Effect;

public final class AttributeUtils {

    public static void sendScreen(Player player) {
        player.addEffect(Effect.getEffect(Effect.BLINDNESS)
                .setAmplifier(5)
                .setDuration((3 * 20))
                .setVisible(false)
        );
    }

    public static void sendDefault(Player player) {
        player.setFoodEnabled(true);
        player.getInventory().clearAll();
        player.getOffhandInventory().clearAll();
        player.getFoodData().setLevel(20);
        player.setMaxHealth(20);
        player.setHealth(20);
        player.getEffects().clear();
        player.setImmobile(false);
        player.setGamemode(Player.SURVIVAL);
    }

    public static void sendInitialJoin(Player player) {
        sendDefault(player);
        player.setFoodEnabled(false);
        player.setImmobile(true);
        player.setCheckMovement(false);
    }

    public static void sendStart(Player player) {
        player.setFoodEnabled(true);
        player.setImmobile(false);
    }

    public static void sendSpectator(Player player) {
        sendDefault(player);
        player.setFoodEnabled(false);
        player.setGamemode(Player.SPECTATOR);
    }
}
