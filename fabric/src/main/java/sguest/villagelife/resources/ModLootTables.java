package sguest.villagelife.resources;

import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.json.loot.JEntry;
import net.devtech.arrp.json.loot.JFunction;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.loot.JPool;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import sguest.villagelife.blocks.ModBlocks;

public class ModLootTables {
    public static void initialize() {
        addStandardBlock(ModBlocks.WOODCUTTER);
    }

    private static void addStandardBlock(Block block) {
        var id = Registry.BLOCK.getId(ModBlocks.WOODCUTTER);
        ModResourcePack.RESOURCE_PACK.addLootTable(new Identifier(id.getNamespace(), "blocks/" + id.getPath()),
            new JLootTable("minecraft:block").pool(
                new JPool().rolls(1)
                    .condition(new JCondition("minecraft:survives_explosion"))
                    .entry(new JEntry().type("minecraft:item")
                        .name(id.toString())
                        .function(new JFunction("minecraft:copy_name").parameter("source", "block_entity"))
                    )
            ));
    }
}
