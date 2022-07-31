package com.skywars.loot;

import cn.nukkit.inventory.ChestInventory;
import cn.nukkit.item.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public abstract class Loot {

    private final List<Item> items;

    public Loot() {
        items = new ArrayList<>();
    }

    protected void addItem(Item item) {
        items.add(item);
    }

    public abstract void init();

    public void fillChest(ChestInventory inventory) {
        inventory.clearAll();

        Random rand = new Random();
        List<Integer> noRepeatItems = new ArrayList<>();

        for (int i = 0; i < inventory.getSize(); i++) {
            if (!rand.nextBoolean()) {
                continue;
            }

            Item item = items.get(rand.nextInt(items.size())).clone();
            if (noRepeatItems.contains(item.getId())) {
                continue;
            }

            int stackSize = item.getMaxStackSize();
            if (stackSize > 1) {
                int randCount = rand.nextInt(stackSize);
                if (item instanceof ItemBlock && randCount < 15) {
                    randCount = 15;
                }

                if (item instanceof ProjectileItem && randCount <  6) {
                    randCount = 6;
                }

                if (item instanceof ItemEnderPearl || item instanceof ItemAppleGold) {
                    randCount = 1;
                }

                if (item instanceof ItemEdible && randCount > 4) {
                    randCount = 4;
                }

                item.setCount(randCount);
            }

            inventory.setItem(i, item);

            if (item instanceof ItemArmor || item instanceof ItemTool) {
                noRepeatItems.add(item.getId());
            }
        }

        noRepeatItems.clear();
    }
}
