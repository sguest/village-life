package sguest.villagelife.inventory.container;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sguest.villagelife.VillageLife;

public class ModContainerTypes {
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, VillageLife.MOD_ID);
    public static final RegistryObject<ContainerType<WoodcutterContainer>> WOODCUTTER = CONTAINERS.register("woodcutter", () -> {
        return IForgeContainerType.create((windowId, inventory, data) -> {
            return new WoodcutterContainer(windowId, inventory);
        });
    });

    public static void register() {
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
