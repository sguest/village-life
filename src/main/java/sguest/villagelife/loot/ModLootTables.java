package sguest.villagelife.loot;

import java.util.Arrays;

import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sguest.villagelife.VillageLife;

@Mod.EventBusSubscriber
public class ModLootTables {
    private static String[] injectedPools = new String[] {
        "chests/village/village_weaponsmith",
        "chests/desert_pyramid",
        "chests/end_city_treasure",
        "chests/jungle_temple",
        "chests/nether_bridge",
        "chests/simple_dungeon",
        "chests/stronghold_corridor"
    };

    @SubscribeEvent
    public static void lootTableLoaded(LootTableLoadEvent event) {
        String eventName = event.getName().toString();
        if(eventName.startsWith("minecraft:")) {
            String poolName = eventName.substring("minecraft:".length());
            if(Arrays.stream(injectedPools).anyMatch(poolName::equals)) {
                LootEntry.Builder<?> entry = TableLootEntry.builder(new ResourceLocation(VillageLife.MOD_ID, "inject/" + poolName)).weight(1);
                LootPool injectedPool = LootPool.builder().addEntry(entry).bonusRolls(0, 1).name("villagelife-main").build();

                event.getTable().addPool(injectedPool);
            }
        }
    }
}
