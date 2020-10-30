package sguest.villagelife;

import net.minecraftforge.fml.common.Mod;
import sguest.villagelife.block.ModBlocks;
import sguest.villagelife.items.ModItems;

import static sguest.villagelife.VillageLife.MOD_ID;

@Mod(MOD_ID)
public class VillageLife {
    public static final String MOD_ID = "villagelife";

    public VillageLife() {
        ModBlocks.register();
        ModItems.register();
    }
}
