package sguest.villagelife.resources;

import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JElement;
import net.devtech.arrp.json.models.JFace;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import sguest.villagelife.blocks.ModBlocks;
import sguest.villagelife.items.ModItems;

public class ModStatesModels {
    public static void initialize() {
        registerHorizontalRotatable(ModBlocks.WOODCUTTER);
        registerWoodcutterModel();
        registerItemBlock(ModItems.WOODCUTTER, ModBlocks.WOODCUTTER);
    }

    private static void registerItemBlock(Item item, Block block) {
        ModResourcePack.RESOURCE_PACK.addModel(
            new JModel().parent(getBlockIdentifier(block).toString()), getItemIdentifier(item));
    }

    private static void registerHorizontalRotatable(Block block) {
        var blockId = getBlockIdentifier(block);

        ModResourcePack.RESOURCE_PACK.addBlockState(
            JState.state(
                JState.variant().put("facing", "north", JState.model(blockId))
                    .put("facing", "south", JState.model(blockId).y(180))
                    .put("facing", "west", JState.model(blockId).y(270))
                    .put("facing", "east", JState.model(blockId).y(90))
            ),
            Registry.BLOCK.getId(block));
    }

    private static Identifier getBlockIdentifier(Block block) {
        var id = Registry.BLOCK.getId(block);
        return new Identifier(id.getNamespace(), "blocks/" + id.getPath());
    }

    private static Identifier getItemIdentifier(Item item) {
        var id = Registry.ITEM.getId(item);
        return new Identifier(id.getNamespace(), "item/" + id.getPath());
    }

    private static void registerWoodcutterModel() {
        ModResourcePack.RESOURCE_PACK.addModel(new JModel()
            .parent("minecraft:block/block")
            .textures(new JTextures()
                .particle("minecraft:block/oak_planks")
                .var("bottom", "minecraft:block/oak_planks")
                .var("top", "villagelife:block/woodcutter_top")
                .var("side", "villagelife:block/woodcutter_side")
                .var("saw", "minecraft:block/stonecutter_saw"))
            .element(
                new JElement().from(0, 0, 0).to(16, 9, 16).faces(new JFacesCustom()
                    .sidesWithCulling(new JFace("side").uv(0, 7, 16, 16))
                    .down(new JFace("bottom").uv(0, 0, 16, 16).cullface(Direction.DOWN))
                    .up(new JFace("top").uv(0, 0, 16, 16))
                ),
                new JElement().from(1, 9, 8).to(15, 16, 8).faces(new JFacesCustom()
                    .north(new JFace("saw").uv(1, 9, 15, 16).tintIndex(0))
                    .south(new JFace("saw").uv(1, 9, 15, 16).tintIndex(0))
                )
            ),
            getBlockIdentifier(ModBlocks.WOODCUTTER));
    }
}
