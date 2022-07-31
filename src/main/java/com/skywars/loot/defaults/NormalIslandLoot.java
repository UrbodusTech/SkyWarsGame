package com.skywars.loot.defaults;

import cn.nukkit.block.BlockID;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import com.skywars.loot.Loot;
import com.skywars.loot.LootConfig;
import com.skywars.loot.LootType;

@LootConfig(name = "loot:normal", type = LootType.ISLAND)
public class NormalIslandLoot extends Loot {

    @Override
    public void init() {
        addItem(Item.get(ItemID.IRON_AXE));
        addItem(Item.get(ItemID.STONE_SWORD));

        addItem(Item.get(BlockID.WOOD));
        addItem(Item.get(BlockID.STONE));
        addItem(Item.get(BlockID.COBBLESTONE));

        addItem(Item.get(ItemID.SNOWBALL));
        addItem(Item.get(ItemID.EGG));

        addItem(Item.get(ItemID.COOKED_CHICKEN));
        addItem(Item.get(ItemID.COOKED_PORKCHOP));
        addItem(Item.get(ItemID.APPLE));

        addItem(Item.get(ItemID.CHAIN_HELMET));
        addItem(Item.get(ItemID.CHAIN_CHESTPLATE));
        addItem(Item.get(ItemID.CHAIN_LEGGINGS));
        addItem(Item.get(ItemID.CHAIN_BOOTS));
    }
}
