package sguest.villagelife.setup;

import net.minecraft.client.gui.ScreenManager;
import sguest.villagelife.client.gui.screen.inventory.WoodcutterScreen;
import sguest.villagelife.inventory.container.ModContainerType;

public class ClientSetup implements Runnable {

    @Override
    public void run() {
        this.registerScreens();
    }

    private void registerScreens() {
        ScreenManager.registerFactory(ModContainerType.WOODCUTTER, WoodcutterScreen::new);
    }
}
