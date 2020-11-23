package sguest.villagelife.entity.ai;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sguest.villagelife.VillageLife;

public class ModSensorType {
    private static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, VillageLife.MOD_ID);
    public static final RegistryObject<SensorType<NearestTradingPostSensor>> NEAREST_TRADING_POST = SENSOR_TYPES.register("nearest_trading_post", () -> new SensorType<>(NearestTradingPostSensor::new));

    public static void register() {
        SENSOR_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static void setup() {
        VillagerEntity.SENSOR_TYPES = ImmutableList.<SensorType<? extends Sensor<? super VillagerEntity>>>builder()
            .addAll(VillagerEntity.SENSOR_TYPES)
            .add(NEAREST_TRADING_POST.get())
            .build();
    }
}
