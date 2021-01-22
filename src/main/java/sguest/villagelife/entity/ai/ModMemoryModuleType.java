package sguest.villagelife.entity.ai;

import java.util.Optional;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.util.math.GlobalPos;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sguest.villagelife.VillageLife;

public class ModMemoryModuleType {
    private static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, VillageLife.MOD_ID);
    public static final RegistryObject<MemoryModuleType<GlobalPos>> NEAREST_TRADING_POST = MEMORY_MODULE_TYPES.register("nearest_trading_post", () -> new MemoryModuleType<>(Optional.empty()));

    public static void register() {
        MEMORY_MODULE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static void setup() {
        VillagerEntity.MEMORY_TYPES = ImmutableList.<MemoryModuleType<?>>builder()
            .addAll(VillagerEntity.MEMORY_TYPES)
            .add(NEAREST_TRADING_POST.get())
            .build();
    }
}
