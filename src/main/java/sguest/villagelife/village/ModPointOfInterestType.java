package sguest.villagelife.village;

import java.util.Collection;

import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sguest.villagelife.VillageLife;
import sguest.villagelife.block.ModBlocks;

public class ModPointOfInterestType {
    private static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, VillageLife.MOD_ID);
    public static final RegistryObject<PointOfInterestType> CARPENTER = POI_TYPES.register("carpenter", () -> createPoiType("carpenter", ModBlocks.WOODCUTTER.get()));

    private static PointOfInterestType createPoiType(String name, Collection<BlockState> blockStates) {
        PointOfInterestType poiType = new PointOfInterestType(name, ImmutableSet.copyOf(blockStates), 1, 1);
        PointOfInterestType.registerBlockStates(poiType);
        return poiType;
    }

    private static PointOfInterestType createPoiType(String name, Block block) {
        return createPoiType(name, ImmutableSet.copyOf(block.getStateContainer().getValidStates()));
    }

    public static void register() {
        POI_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
