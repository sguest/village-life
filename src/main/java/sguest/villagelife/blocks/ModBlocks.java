package sguest.villagelife.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import sguest.villagelife.VillageLife;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(VillageLife.MOD_ID)
public class ModBlocks {
    public static final Block WOODCUTTER = null;

    @SubscribeEvent
    public static void onBlockRegistry(final RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(
            new WoodcutterBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD)).setRegistryName(VillageLife.MOD_ID, "woodcutter")
        );
    }
}
