package sguest.villagelife.world.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.blockstateprovider.ForestFlowerBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.PlainFlowerBlockStateProvider;
import sguest.villagelife.block.ModBlocks;

public class ModWorldGen {
    private static void registerFlowers() {
        // registering like this instead of adding a new feature because we want these to show up with bone meal,
        // but bone meal only uses the first flower feature from a biome
        List<BlockState> commonFlowers = new ArrayList<>(Arrays.asList(PlainFlowerBlockStateProvider.COMMON_FLOWERS));
        commonFlowers.add(ModBlocks.CROCUS.get().getDefaultState());
        PlainFlowerBlockStateProvider.COMMON_FLOWERS = commonFlowers.toArray(PlainFlowerBlockStateProvider.COMMON_FLOWERS);

        List<BlockState> forestFlowers = new ArrayList<>(Arrays.asList(ForestFlowerBlockStateProvider.STATES));
        forestFlowers.add(ModBlocks.CROCUS.get().getDefaultState());
        ForestFlowerBlockStateProvider.STATES = forestFlowers.toArray(ForestFlowerBlockStateProvider.STATES);
    }

    public static void setup() {
        registerFlowers();
    }
}
