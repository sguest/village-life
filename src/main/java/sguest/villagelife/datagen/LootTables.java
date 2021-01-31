package sguest.villagelife.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.loot.EmptyLootEntry;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.fml.RegistryObject;
import sguest.villagelife.block.ModBlocks;
import sguest.villagelife.entity.merchant.villager.ModProfessions;
import sguest.villagelife.item.ModItems;

public class LootTables extends BaseLootTableProvider {
    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        addStandardTable(ModBlocks.WOODCUTTER, builder -> copyName(builder));
        addDoor(ModBlocks.REINFORCED_ACACIA_DOOR);
        addDoor(ModBlocks.REINFORCED_BIRCH_DOOR);
        addDoor(ModBlocks.REINFORCED_CRIMSON_DOOR);
        addDoor(ModBlocks.REINFORCED_DARK_OAK_DOOR);
        addDoor(ModBlocks.REINFORCED_JUNGLE_DOOR);
        addDoor(ModBlocks.REINFORCED_OAK_DOOR);
        addDoor(ModBlocks.REINFORCED_SPRUCE_DOOR);
        addDoor(ModBlocks.REINFORCED_WARPED_DOOR);
        addStandardTable(ModBlocks.EMERALD_PRESSURE_PLATE);
        addStandardTable(ModBlocks.KEG, builder -> copyNbt(builder, "Contents", "Contents", CopyNbt.Action.REPLACE));
        for(RegistryObject<Block> tradingPost : ModBlocks.TRADING_POSTS.values()) {
            addStandardTable(tradingPost);
        }
        addStandardTable(ModBlocks.HARVESTER);

        addHeroGift(ModProfessions.CARPENTER.get(),
            Items.ACACIA_LOG,
            Items.BIRCH_LOG,
            Items.DARK_OAK_LOG,
            Items.JUNGLE_LOG,
            Items.OAK_LOG,
            Items.SPRUCE_LOG);
        addHeroGift(ModProfessions.INNKEEPER.get(), Items.MUSHROOM_STEM, Items.RABBIT_STEW, Items.BEETROOT_SOUP);

        addInjectTable("chests/village/village_weaponsmith", builder -> {
            builder.addEntry(ItemLootEntry.builder(ModItems.EMERALD_HORSE_ARMOR.get()).weight(10));
            builder.addEntry(EmptyLootEntry.func_216167_a().weight(90));
        });
        addInjectTable("chests/desert_pyramid", builder -> {
            builder.addEntry(ItemLootEntry.builder(ModItems.EMERALD_HORSE_ARMOR.get()).weight(11));
            builder.addEntry(EmptyLootEntry.func_216167_a().weight(89));
        });
        addInjectTable("chests/end_city_treasure", builder -> {
            builder.addEntry(ItemLootEntry.builder(ModItems.EMERALD_HORSE_ARMOR.get()).weight(4));
            builder.addEntry(EmptyLootEntry.func_216167_a().weight(96));
        });
        addInjectTable("chests/jungle_temple", builder -> {
            builder.addEntry(ItemLootEntry.builder(ModItems.EMERALD_HORSE_ARMOR.get()).weight(4));
            builder.addEntry(EmptyLootEntry.func_216167_a().weight(96));
        });
        addInjectTable("chests/nether_bridge", builder -> {
            builder.addEntry(ItemLootEntry.builder(ModItems.EMERALD_HORSE_ARMOR.get()).weight(20));
            builder.addEntry(EmptyLootEntry.func_216167_a().weight(80));
        });
        addInjectTable("chests/simple_dungeon", builder -> {
            builder.addEntry(ItemLootEntry.builder(ModItems.EMERALD_HORSE_ARMOR.get()).weight(12));
            builder.addEntry(EmptyLootEntry.func_216167_a().weight(88));
        });
        addInjectTable("chests/stronghold_corridor", builder -> {
            builder.addEntry(ItemLootEntry.builder(ModItems.EMERALD_HORSE_ARMOR.get()).weight(3));
            builder.addEntry(EmptyLootEntry.func_216167_a().weight(97));
        });
    }

    private void addDoor(RegistryObject<Block> door) {
        addStandardTable(door, builder -> requireProperty(builder, door.get(), BlockStateProperties.DOUBLE_BLOCK_HALF, "lower"));
    }
}
