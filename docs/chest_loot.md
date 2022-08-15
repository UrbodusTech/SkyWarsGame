# Chest Loot
With the chest loot api you can customize and create loots.

## Types
- <b>Island</b>: Island type loot will be used to fill chests that are indicated in the map config as island chests.
- <b>Other</b>: The loots of type other are used to fill the chests that are indicated in the map config as type other chest.

## Create Loot
```java
@LootConfig(name = "loot:test", type = LootType.ISLAND)
public class TestLoot extends Loot {

    @Override
    public void init() {
        addItem(Item.get(ItemID.IRON_AXE));
        addItem(Item.get(ItemID.STONE_SWORD));
    }
}
```
To configure the loot we must declare @LootConfig filling the name and type parameters as well as the class must extend Loot. Inside init we must add the items that the loot will contain using addItem()

## Register
```java
public class ExampleExtension extends Extension {

    @Override
    public void install() {
        getLootManager().register(new TestLoot());
    }

    @Override
    public void uninstall() {

    }
}
```

## Unregister
```java
public class ExampleExtension extends Extension {

    @Override
    public void install() {
        getLootManager().unregister("loot:test");
    }

    @Override
    public void uninstall() {

    }
}
```

