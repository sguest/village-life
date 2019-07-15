package sguest.villagelife.inventory.container;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import sguest.villagelife.VillageLife;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(VillageLife.MOD_ID)
public class ModContainerType {
    public static final ContainerType WOODCUTTER = null;

    @SubscribeEvent
    public static void onContainerTypeRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(
            IForgeContainerType.create((windowId, inv, data) -> {
                return new WoodcutterContainer(windowId, inv);
            }).setRegistryName(VillageLife.MOD_ID, "woodcutter"));
    }
}
