package sguest.villagelife.stats;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import sguest.villagelife.VillageLife;

import java.text.NumberFormat;
import java.util.Locale;

@ObjectHolder(VillageLife.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModStats {
    public static ResourceLocation INTERACT_WITH_WOODCUTTER = null;

    private static IStatFormatter integerFormatter = NumberFormat.getIntegerInstance(Locale.US)::format;

    @SubscribeEvent
    public static void onStatTypeRegistry(final RegistryEvent.Register<StatType<?>> event) {
        INTERACT_WITH_WOODCUTTER = registerCustom("interact_with_woodcutter", integerFormatter);
    }

    private static ResourceLocation registerCustom(String key, IStatFormatter formatter) {
        ResourceLocation resourcelocation = new ResourceLocation(VillageLife.MOD_ID, key);
        Registry.register(Registry.CUSTOM_STAT, key, resourcelocation);
        Stats.CUSTOM.get(resourcelocation, formatter);
        return resourcelocation;
    }

}