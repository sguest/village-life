package sguest.villagelife.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import sguest.villagelife.VillageLife;
import sguest.villagelife.block.ModBlocks;
import sguest.villagelife.tags.ModTags;

public class BlockTagsProvider extends net.minecraft.data.BlockTagsProvider {

    public BlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, VillageLife.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags()
    {
        getOrCreateBuilder(BlockTags.NON_FLAMMABLE_WOOD)
            .addItemEntry(ModBlocks.REINFORCED_CRIMSON_DOOR.get())
            .addItemEntry(ModBlocks.REINFORCED_WARPED_DOOR.get());

        getOrCreateBuilder(BlockTags.PRESSURE_PLATES)
            .addItemEntry(ModBlocks.EMERALD_PRESSURE_PLATE.get());

        getOrCreateBuilder(BlockTags.WOODEN_DOORS)
            .addItemEntry(ModBlocks.REINFORCED_ACACIA_DOOR.get())
            .addItemEntry(ModBlocks.REINFORCED_BIRCH_DOOR.get())
            .addItemEntry(ModBlocks.REINFORCED_CRIMSON_DOOR.get())
            .addItemEntry(ModBlocks.REINFORCED_DARK_OAK_DOOR.get())
            .addItemEntry(ModBlocks.REINFORCED_JUNGLE_DOOR.get())
            .addItemEntry(ModBlocks.REINFORCED_OAK_DOOR.get())
            .addItemEntry(ModBlocks.REINFORCED_SPRUCE_DOOR.get())
            .addItemEntry(ModBlocks.REINFORCED_WARPED_DOOR.get());

        Builder<Block> tradingPostBuilder = getOrCreateBuilder(ModTags.Blocks.TRADING_POSTS);
        for(RegistryObject<Block> tradingPost : ModBlocks.TRADING_POSTS.values()) {
            tradingPostBuilder.addItemEntry(tradingPost.get());
        }

        getOrCreateBuilder(ModTags.Blocks.HARVESTER_IGNORED);
    }
}
