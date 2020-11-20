package sguest.villagelife.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.fml.RegistryObject;
import sguest.villagelife.block.ModBlocks;
import sguest.villagelife.entity.merchant.villager.ModProfessions;

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

        addHeroGift(ModProfessions.CARPENTER.get(),
            Items.ACACIA_LOG,
            Items.BIRCH_LOG,
            Items.DARK_OAK_LOG,
            Items.JUNGLE_LOG,
            Items.OAK_LOG,
            Items.SPRUCE_LOG);
        addHeroGift(ModProfessions.INNKEEPER.get(), Items.MUSHROOM_STEM, Items.RABBIT_STEW, Items.BEETROOT_SOUP);
    }

    private void addDoor(RegistryObject<Block> door) {
        addStandardTable(door, builder -> requireProperty(builder, door.get(), BlockStateProperties.DOUBLE_BLOCK_HALF, "lower"));
    }
}
