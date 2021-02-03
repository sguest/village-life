package sguest.villagelife.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import sguest.villagelife.VillageLife;
import sguest.villagelife.block.ModBlocks;
import sguest.villagelife.item.ModItems;

public class ItemModels extends ItemModelProvider {
    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, VillageLife.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        generated(ModItems.MILK_BOTTLE);
        generated(ModItems.MUTTON_SANDWICH);
        generated(ModItems.PORK_SANDWICH);
        generated(ModItems.STEAK_SANDWICH);
        generated(ModItems.REINFORCED_ACACIA_DOOR);
        generated(ModItems.REINFORCED_BIRCH_DOOR);
        generated(ModItems.REINFORCED_CRIMSON_DOOR);
        generated(ModItems.REINFORCED_DARK_OAK_DOOR);
        generated(ModItems.REINFORCED_JUNGLE_DOOR);
        generated(ModItems.REINFORCED_OAK_DOOR);
        generated(ModItems.REINFORCED_SPRUCE_DOOR);
        generated(ModItems.REINFORCED_WARPED_DOOR);
        generated(ModItems.EMERALD_BOOTS);
        generated(ModItems.EMERALD_CHESTPLATE);
        generated(ModItems.EMERALD_HELMET);
        generated(ModItems.EMERALD_LEGGINGS);
        generated(ModItems.EMERALD_HORSE_ARMOR);

        handheld(ModItems.EMERALD_AXE);
        handheld(ModItems.EMERALD_HOE);
        handheld(ModItems.EMERALD_PICKAXE);
        handheld(ModItems.EMERALD_SHOVEL);
        handheld(ModItems.EMERALD_SWORD);

        block(ModBlocks.KEG);
        block(ModBlocks.WOODCUTTER);
        block(ModBlocks.EMERALD_PRESSURE_PLATE);
        for(RegistryObject<Block> tradingPost : ModBlocks.TRADING_POSTS.values()) {
            block(tradingPost);
        }
        block(ModBlocks.HARVESTER);

        generatedBlock(ModBlocks.CROCUS);
    }

    private void generated(RegistryObject<Item> item) {
        simpleParent(item.getId(), "item/", "item/generated");
    }

    private void generatedBlock(RegistryObject<Block> block) {
        simpleParent(block.getId(), "block/", "item/generated");
    }

    private void handheld(RegistryObject<Item> item) {
        simpleParent(item.getId(), "item/", "item/handheld");
    }

    private void simpleParent(ResourceLocation id, String path, String parent) {
        getBuilder(id.getPath())
            .parent(new UncheckedModelFile(parent))
            .texture("layer0", new ResourceLocation(id.getNamespace(), path + id.getPath()));
    }

    private void block(RegistryObject<Block> block) {
        ResourceLocation id = block.getId();
        getBuilder(id.getPath())
            .parent(new UncheckedModelFile(id.getNamespace() + ":block/" + id.getPath()));
    }
}
