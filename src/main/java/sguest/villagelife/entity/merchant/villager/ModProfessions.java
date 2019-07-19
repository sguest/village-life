package sguest.villagelife.entity.merchant.villager;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import sguest.villagelife.VillageLife;
import sguest.villagelife.village.ModPointOfInterestTypes;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(VillageLife.MOD_ID)
public class ModProfessions {
    public static final VillagerProfession CARPENTER = null;

    @SubscribeEvent
    public static void onItemRegistry(final RegistryEvent.Register<VillagerProfession> event) {
        event.getRegistry().registerAll(
            new VillagerProfession("carpenter", ModPointOfInterestTypes.CARPENTER, ImmutableSet.of(), ImmutableSet.of()).setRegistryName(VillageLife.MOD_ID, "carpenter")
        );
    }
}
