package sguest.villagelife.stat;

import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import sguest.villagelife.VillageLifeMod;

public class ModStats {
    public static final Identifier INTERACT_WITH_WOODCUTTER = register("interact_with_woodcutter", StatFormatter.DEFAULT);

    private static Identifier register(String id, StatFormatter formatter) {
        Identifier identifier = new Identifier(VillageLifeMod.MODID, id);
        Registry.register(Registry.CUSTOM_STAT, (String)id, identifier);
        Stats.CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }
}
