package sguest.villagelife.client;

import net.fabricmc.api.ClientModInitializer;

public class VillageLifeModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModRenderLayers.initialize();
    }
}
