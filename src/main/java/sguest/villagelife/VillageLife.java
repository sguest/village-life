package sguest.villagelife;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sguest.villagelife.block.DispenserOverrides;
import sguest.villagelife.block.ModBlocks;
import sguest.villagelife.client.ClientProxy;
import sguest.villagelife.entity.merchant.villager.ModProfessions;
import sguest.villagelife.inventory.container.ModContainerTypes;
import sguest.villagelife.item.ModItems;
import sguest.villagelife.item.crafting.ModRecipeSerializers;
import sguest.villagelife.tileentity.ModTileEntities;
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
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::loadComplete);

        ModBlocks.register();
        ModItems.register();
        ModContainerTypes.register();
        ModRecipeSerializers.register();
        ModPointOfInterestType.register();
        ModProfessions.register();
        ModVillageBuildings.register();
        ModTileEntities.register();
    }

    public void setup(final FMLCommonSetupEvent event) {
        proxy.setup(event);
        ModProfessions.setup();
    }

    public void loadComplete(FMLLoadCompleteEvent event) {
        DispenserOverrides.loadComplete(event);
    }
}
