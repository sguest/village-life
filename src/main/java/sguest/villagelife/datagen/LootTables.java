package sguest.villagelife.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.fml.RegistryObject;
import sguest.villagelife.block.ModBlocks;

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
    }

    private void addDoor(RegistryObject<Block> door) {
        addStandardTable(door, builder -> requireProperty(builder, door.get(), BlockStateProperties.DOUBLE_BLOCK_HALF, "lower"));
    }
}
