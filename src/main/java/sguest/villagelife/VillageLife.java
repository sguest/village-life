package sguest.villagelife;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sguest.villagelife.block.ModBlocks;
import sguest.villagelife.client.ClientProxy;
import sguest.villagelife.entity.merchant.villager.ModProfessions;
import sguest.villagelife.inventory.container.ModContainerTypes;
import sguest.villagelife.item.ModItems;
import sguest.villagelife.item.crafting.ModRecipeSerializers;
import sguest.villagelife.village.ModPointOfInterestType;
import sguest.villagelife.village.ModVillageBuildings;

import static sguest.villagelife.VillageLife.MOD_ID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MOD_ID)
public class VillageLife {
    public static final String MOD_ID = "villagelife";

    public static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public static Logger getLogger() {
        return LogManager.getLogger(MOD_ID);
    }

    public VillageLife() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        ModBlocks.register();
        ModItems.register();
        ModContainerTypes.register();
        ModRecipeSerializers.register();
        ModPointOfInterestType.register();
        ModProfessions.register();
        ModVillageBuildings.register();
    }

    public void setup(final FMLCommonSetupEvent event) {
        proxy.setup(event);
        ModProfessions.setup();
    }
}
