package sguest.villagelife.items;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import sguest.villagelife.VillageLife;
import sguest.villagelife.blocks.ModBlocks;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(VillageLife.MOD_ID)
public class ModItems {
    public static final Item WOODCUTTER = null;

    @SubscribeEvent
    public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
            new BlockItem(ModBlocks.WOODCUTTER, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(VillageLife.MOD_ID, "woodcutter")
        );
    }
}
