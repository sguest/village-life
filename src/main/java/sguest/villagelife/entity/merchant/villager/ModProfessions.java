package sguest.villagelife.entity.merchant.villager;

import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.task.GiveHeroGiftsTask;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.ResourceLocation;
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
    public static final RegistryObject<VillagerProfession> INNKEEPER = PROFESSIONS.register("innkeeper", () -> new VillagerProfession("innkeeper", ModPointOfInterestType.INNKEEPER.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ITEM_BOTTLE_EMPTY));
    public static final RegistryObject<VillagerProfession> GARDENER = PROFESSIONS.register("gardener", () -> new VillagerProfession("gardener", ModPointOfInterestType.GARDENER.get(), ImmutableSet.of(), ImmutableSet.of(), Blocks.WHEAT.getDefaultState().getSoundType().getBreakSound()));

    public static void register() {
        PROFESSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static void setup() {
        for (RegistryObject<VillagerProfession> profession : PROFESSIONS.getEntries()) {
            GiveHeroGiftsTask.GIFTS.put(profession.get(), new ResourceLocation(VillageLife.MOD_ID, "gameplay/hero_of_the_village/" + profession.getId().getPath() + "_gift"));
        }
    }
}
