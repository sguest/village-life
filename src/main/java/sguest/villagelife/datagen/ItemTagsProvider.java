package sguest.villagelife.datagen;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import sguest.villagelife.VillageLife;
import sguest.villagelife.item.ModItems;
import sguest.villagelife.tags.ModTags;

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

        Builder<Item> tradingPostBuilder = getOrCreateBuilder(ModTags.Items.TRADING_POSTS);
        for(RegistryObject<Item> tradingPost : ModItems.TRADING_POSTS.values()) {
            tradingPostBuilder.addItemEntry(tradingPost.get());
        }
    }
}
