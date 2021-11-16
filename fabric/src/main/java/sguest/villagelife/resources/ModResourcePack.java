package sguest.villagelife.resources;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import sguest.villagelife.VillageLifeMod;

public class ModResourcePack {
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(VillageLifeMod.MODID + ":resource");

    public static void initialize() {
        ModStatesModels.initialize();
        ModLootTables.initialize();
        ModTags.initialize();
        ModRecipes.initialize();
        ModAdvancements.initialize();

        RRPCallback.BEFORE_VANILLA.register(a -> a.add(RESOURCE_PACK));
    }
}
