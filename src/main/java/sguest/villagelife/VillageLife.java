package sguest.villagelife;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sguest.villagelife.setup.ClientSetup;
import sguest.villagelife.setup.ServerSetup;

import static sguest.villagelife.VillageLife.MOD_ID;

@Mod(MOD_ID)
public class VillageLife {
    public static final String MOD_ID = "villagelife";

    public VillageLife() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> new ClientSetup());
        DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> new ServerSetup());
    }
}
