package sguest.villagelife.datagen;

import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import sguest.villagelife.VillageLife;
import sguest.villagelife.block.ModBlocks;

public class BlockStates extends BlockStateProvider {
    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, VillageLife.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleDoor(ModBlocks.REINFORCED_ACACIA_DOOR);
        simpleDoor(ModBlocks.REINFORCED_BIRCH_DOOR);
        simpleDoor(ModBlocks.REINFORCED_CRIMSON_DOOR);
        simpleDoor(ModBlocks.REINFORCED_DARK_OAK_DOOR);
        simpleDoor(ModBlocks.REINFORCED_JUNGLE_DOOR);
        simpleDoor(ModBlocks.REINFORCED_OAK_DOOR);
        simpleDoor(ModBlocks.REINFORCED_SPRUCE_DOOR);
        simpleDoor(ModBlocks.REINFORCED_WARPED_DOOR);

        horizontalBlock(ModBlocks.WOODCUTTER.get(),
            models().withExistingParent(ModBlocks.WOODCUTTER.getId().getPath(), mcLoc("block/stonecutter"))
                .texture("particle", mcLoc("block/oak_planks"))
                .texture("bottom", mcLoc("block/oak_planks"))
                .texture("top", modLoc("block/woodcutter_top"))
                .texture("side", modLoc("block/woodcutter_side"))
        );

        onOffPressurePlateBlock((AbstractPressurePlateBlock)ModBlocks.EMERALD_PRESSURE_PLATE.get(), mcLoc("block/emerald_block"));
    }

    private void onOffPressurePlateBlock(AbstractPressurePlateBlock block, ResourceLocation texture) {
        getVariantBuilder(block)
            .forAllStates(state -> {
                Boolean powered = (state.get(BlockStateProperties.POWERED));
                return ConfiguredModel.builder().modelFile(
                    models().withExistingParent(block.getRegistryName().toString() + (powered ? "_down" : ""),
                        mcLoc("block/pressure_plate_" + (powered ? "down" : "up")))
                    .texture("texture", texture)
                ).build();
            });

    }

    private void simpleDoor(RegistryObject<Block> block) {
        ResourceLocation id = block.getId();
        doorBlock((DoorBlock)block.get(),
            modLoc("block/" + id.getPath() + "_bottom"),
            modLoc("block/" + id.getPath() + "_top"));
    }
}
