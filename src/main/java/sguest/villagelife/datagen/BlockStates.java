package sguest.villagelife.datagen;

import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelBuilder.FaceRotation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import sguest.villagelife.VillageLife;
import sguest.villagelife.block.KegBlock;
import sguest.villagelife.block.ModBlocks;
import sguest.villagelife.block.TradingPostBlock;
import sguest.villagelife.util.CubeUtil;
import sguest.villagelife.util.CubeUtil.Cube;

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

        kegBlock();

        onOffPressurePlateBlock((AbstractPressurePlateBlock)ModBlocks.EMERALD_PRESSURE_PLATE.get(), mcLoc("block/emerald_block"));

        tradingPostBlock();
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

    private void kegBlock() {
        BlockModelBuilder builder = models().withExistingParent(ModBlocks.KEG.getId().getPath(), mcLoc("block/block"))
            .texture("particle", mcLoc("block/barrel_bottom"))
            .texture("front", modLoc("block/keg_front"))
            .texture("back", mcLoc("block/barrel_bottom"))
            .texture("side", modLoc("block/keg_side"));
        
            CubeUtil.modelElement(builder, KegBlock.BODY_SHAPE)
                .allFaces((direction, faceBuilder) -> {
                    switch(direction) {
                        case NORTH:
                            faceBuilder.texture("#front").uvs(1, 0, 15, 14).cullface(Direction.NORTH);
                            break;
                        case SOUTH:
                            faceBuilder.texture("#back").uvs(1, 0, 15, 14).cullface(Direction.SOUTH);
                            break;
                        default:
                            faceBuilder.texture("#side").uvs(0, 0, 13, 16);
                            if(direction == Direction.EAST) {
                                faceBuilder.rotation(FaceRotation.CLOCKWISE_90);
                            }
                            else if(direction == Direction.WEST) {
                                faceBuilder.rotation(FaceRotation.COUNTERCLOCKWISE_90);
                            }
                            break;
                    }
                })
            .end();

        for(Cube foot : KegBlock.FEET_SHAPE) {
            CubeUtil.modelElement(builder, foot).textureAll("#back").end();
        }

        horizontalBlock(ModBlocks.KEG.get(), builder);
    }

    private void tradingPostBlock() {
        BlockModelBuilder builder = models().withExistingParent(ModBlocks.TRADING_POST.getId().getPath(), mcLoc("block/block"))
            .texture("particle", mcLoc("block/oak_planks"))
            .texture("wood", mcLoc("block/oak_planks"))
            .texture("wool", mcLoc("block/white_wool"))
            .texture("frame", mcLoc("block/item_frame"));

        CubeUtil.modelElement(builder, TradingPostBlock.CANOPY_SHAPE).textureAll("#wool").end();
        CubeUtil.modelElement(builder, TradingPostBlock.BASE_SHAPE).textureAll("#wood").end();
        CubeUtil.modelElement(builder, TradingPostBlock.FRAME_BACK_SHAPE)
            .allFaces((direction, faceBuilder) -> {
                if(direction == Direction.NORTH) {
                    faceBuilder.texture("#frame");
                }
                else {
                    faceBuilder.texture("#wood");
                }
            })
        .end();

        for(Cube cube : TradingPostBlock.FRAME_EDGE_SHAPES) {
            CubeUtil.modelElement(builder, cube).textureAll("#wood").end();
        }

        for(Cube pillar : TradingPostBlock.PILLAR_SHAPES) {
            CubeUtil.modelElement(builder, pillar).textureAll("#wood").end();
        }

        horizontalBlock(ModBlocks.TRADING_POST.get(), builder);
    }
}
