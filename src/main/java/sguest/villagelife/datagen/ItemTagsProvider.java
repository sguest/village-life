package sguest.villagelife.datagen;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import sguest.villagelife.VillageLife;
import sguest.villagelife.item.ModItems;

public class ItemTagsProvider extends net.minecraft.data.ItemTagsProvider {

    public ItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, VillageLife.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        getOrCreateBuilder(ItemTags.NON_FLAMMABLE_WOOD)
            .addItemEntry(ModItems.REINFORCED_CRIMSON_DOOR.get())
            .addItemEntry(ModItems.REINFORCED_WARPED_DOOR.get());

            getOrCreateBuilder(ItemTags.WOODEN_DOORS)
            .addItemEntry(ModItems.REINFORCED_ACACIA_DOOR.get())
            .addItemEntry(ModItems.REINFORCED_BIRCH_DOOR.get())
            .addItemEntry(ModItems.REINFORCED_CRIMSON_DOOR.get())
            .addItemEntry(ModItems.REINFORCED_DARK_OAK_DOOR.get())
            .addItemEntry(ModItems.REINFORCED_JUNGLE_DOOR.get())
            .addItemEntry(ModItems.REINFORCED_OAK_DOOR.get())
            .addItemEntry(ModItems.REINFORCED_SPRUCE_DOOR.get())
            .addItemEntry(ModItems.REINFORCED_WARPED_DOOR.get());
    }
}
