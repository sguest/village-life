package sguest.villagelife;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sguest.villagelife.block.ModBlocks;
import sguest.villagelife.client.ClientProxy;
import sguest.villagelife.inventory.container.ModContainerTypes;
import sguest.villagelife.item.ModItems;
import sguest.villagelife.item.crafting.ModRecipeSerializers;

import static sguest.villagelife.VillageLife.MOD_ID;

@Mod(MOD_ID)
public class VillageLife {
    public static final String MOD_ID = "villagelife";

    public static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public VillageLife() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        ModBlocks.register();
        ModItems.register();
        ModContainerTypes.register();
        ModRecipeSerializers.register();
    }

    public void setup(final FMLCommonSetupEvent event) {
        proxy.setup(event);
    }
}
