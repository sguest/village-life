package sguest.villagelife.village;

import java.util.Collection;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;
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
    public static final RegistryObject<PointOfInterestType> INNKEEPER = POI_TYPES.register("innkeeper", () -> createPoiType("inkeeper", ModBlocks.KEG.get()));
    public static final RegistryObject<PointOfInterestType> TRADING_POST = POI_TYPES.register("trading_post", () -> createPoiType("trading_post", 1, 6, ModBlocks.TRADING_POSTS.values().stream().map(x -> x.get()).toArray(Block[]::new)));

    private static PointOfInterestType createPoiType(String name, Collection<BlockState> blockStates, int maxTickets, int validRange) {
        PointOfInterestType poiType = new PointOfInterestType(name, ImmutableSet.copyOf(blockStates), maxTickets, validRange);
        PointOfInterestType.registerBlockStates(poiType);
        return poiType;
    }
    private static PointOfInterestType createPoiType(String name, int maxTickets, int validRange, Block ... blocks) {
        return createPoiType(name, ImmutableSet.copyOf(Stream.of(blocks).map(x -> x.getStateContainer().getValidStates()).flatMap(ImmutableList::stream).toArray(BlockState[]::new)), maxTickets, validRange);
    }

    private static PointOfInterestType createPoiType(String name, Block ... blocks) {
        return createPoiType(name, 1, 1, blocks);
    }

    public static void register() {
        POI_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
