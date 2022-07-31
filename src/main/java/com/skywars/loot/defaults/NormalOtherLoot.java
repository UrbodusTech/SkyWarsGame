package com.skywars.loot.defaults;

import cn.nukkit.block.BlockID;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import com.skywars.loot.Loot;
import com.skywars.loot.LootConfig;
import com.skywars.loot.LootType;

@LootConfig(name = "loot:normal_other", type = LootType.OTHER)
public class NormalOtherLoot extends Loot {

    @Override
    public void init() {
        addItem(Item.get(BlockID.WOOD));
        addItem(Item.get(BlockID.STONE));
        addItem(Item.get(BlockID.COBBLESTONE));

        addItem(Item.get(ItemID.DIAMOND_AXE));
        addItem(Item.get(ItemID.DIAMOND_SWORD));
        addItem(Item.get(ItemID.DIAMOND_HELMET));
        addItem(Item.get(ItemID.DIAMOND_CHESTPLATE));
        addItem(Item.get(ItemID.DIAMOND_LEGGINGS));
        addItem(Item.get(ItemID.DIAMOND_BOOTS));
        addItem(Item.get(ItemID.IRON_HELMET));
        addItem(Item.get(ItemID.IRON_CHESTPLATE));
        addItem(Item.get(ItemID.IRON_LEGGINGS));
        addItem(Item.get(ItemID.IRON_BOOTS));
        addItem(Item.get(ItemID.IRON_SWORD));
        addItem(Item.get(ItemID.IRON_AXE));

        addItem(Item.get(ItemID.SNOWBALL));
        addItem(Item.get(ItemID.EGG));
        addItem(Item.get(ItemID.ENDER_PEARL));
        addItem(Item.get(ItemID.GOLDEN_APPLE));

        addItem(Item.get(ItemID.COOKED_CHICKEN));
        addItem(Item.get(ItemID.COOKED_PORKCHOP));
        addItem(Item.get(ItemID.APPLE));
    }
}
