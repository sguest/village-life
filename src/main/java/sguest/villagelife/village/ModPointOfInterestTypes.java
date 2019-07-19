package sguest.villagelife.village;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import sguest.villagelife.VillageLife;
import sguest.villagelife.block.ModBlocks;

import java.util.Set;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(VillageLife.MOD_ID)
public class ModPointOfInterestTypes {
    public static final PointOfInterestType CARPENTER = null;

    @SubscribeEvent
    public static void onItemRegistry(final RegistryEvent.Register<PointOfInterestType> event) {
        event.getRegistry().registerAll(
            new PointOfInterestType("carpenter", toBlockState(ModBlocks.WOODCUTTER), 1, SoundEvents.ENTITY_VILLAGER_WORK_MASON).setRegistryName(VillageLife.MOD_ID, "carpenter")
        );
    }

    private static Set<BlockState> toBlockState(Block block) {
        return ImmutableSet.copyOf(block.getStateContainer().getValidStates());
    }
}
