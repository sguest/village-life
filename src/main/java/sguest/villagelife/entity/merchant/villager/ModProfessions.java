package sguest.villagelife.entity.merchant.villager;

import com.google.common.collect.ImmutableSet;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sguest.villagelife.VillageLife;
import sguest.villagelife.village.ModPointOfInterestType;

public class ModProfessions {
    private static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, VillageLife.MOD_ID);
    public static final RegistryObject<VillagerProfession> CARPENTER = PROFESSIONS.register("carpenter", () -> new VillagerProfession("carpenter", ModPointOfInterestType.CARPENTER.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.UI_STONECUTTER_TAKE_RESULT));

    public static void register() {
        PROFESSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
