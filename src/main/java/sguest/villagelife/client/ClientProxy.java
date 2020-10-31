package sguest.villagelife.client;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import sguest.villagelife.CommonProxy;
import sguest.villagelife.client.gui.screen.inventory.WoodcutterScreen;
import sguest.villagelife.inventory.container.ModContainerTypes;

public class ClientProxy extends CommonProxy {
    public void setup(FMLCommonSetupEvent event) {
        registerScreens();
    }

    private void registerScreens() {
        ScreenManager.registerFactory(ModContainerTypes.WOODCUTTER.get(), WoodcutterScreen::new);
    }
}
