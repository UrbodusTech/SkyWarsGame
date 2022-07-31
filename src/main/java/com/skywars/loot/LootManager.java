package com.skywars.loot;

import cn.nukkit.plugin.PluginLogger;
import com.skywars.GameLoader;
import com.skywars.loot.defaults.NormalIslandLoot;
import com.skywars.loot.defaults.NormalOtherLoot;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Getter
public class LootManager {

    private final Map<String, Loot> islandLoots;
    private final Map<String, Loot> otherLoots;

    public LootManager() {
        islandLoots = new HashMap<>();
        otherLoots = new HashMap<>();
    }

    public void init() {
        register(new NormalIslandLoot());
        register(new NormalOtherLoot());
    }

    public void register(Loot loot) {
        PluginLogger logger = GameLoader.getInstance().getLogger();

        LootConfig config = loot.getClass().getAnnotation(LootConfig.class);
        if (config == null) {
            logger.warning("Skipping unknown loot, loots must implement @LootConfig");

            return;
        }

        if (!config.name().contains("loot:")) {
            logger.warning("Skipping unknown loot, loots name must start with loot:etc example loot:normal");

            return;
        }

        loot.init();

        if (config.type() == LootType.ISLAND) {
            islandLoots.put(config.name(), loot);
        } else {
            otherLoots.put(config.name(), loot);
        }

        logger.info("Loot [" + config.name() + "] registered");
    }

    public void unregister(String name) {
        islandLoots.remove(name);
        otherLoots.remove(name);
    }

    public Loot getRandomLootIsland() {
        Random rand = new Random();
        Loot[] loots = islandLoots.values().toArray(new Loot[0]);

        return loots[rand.nextInt(loots.length)];
    }

    public Loot getRandomLootOther() {
        Random rand = new Random();
        Loot[] loots = otherLoots.values().toArray(new Loot[0]);

        return loots[rand.nextInt(loots.length)];
    }
}
