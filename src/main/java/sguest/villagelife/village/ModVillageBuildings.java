package sguest.villagelife.village;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.LegacySingleJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern.PlacementBehaviour;
import net.minecraft.world.gen.feature.template.ProcessorLists;
import sguest.villagelife.VillageLife;

public class ModVillageBuildings {
    // Most profession buildings seem to be weight 2
    private static int JIGSAW_WEIGHT = 2;
    // Big thanks to Immersive Engineering for being open source
    // never would have figured this out on my own
    public static void register() {
        for (String villageType : new String[] {"plains", "savanna", "desert", "snowy", "taiga"}) {
            ResourceLocation villagePoolName = new ResourceLocation("village/" + villageType + "/houses");
            JigsawPattern oldPool = WorldGenRegistries.JIGSAW_POOL.getOrDefault(villagePoolName);
            List<JigsawPiece> oldBuildings = ImmutableList.of();
            if(oldPool != null) {
                oldBuildings = oldPool.getShuffledPieces(new Random(0));
            }
            Map<JigsawPiece, Integer> buildings = new HashMap<JigsawPiece, Integer>();
            // Jigsaw pool get shuffled pieces returns a list of pieces, and each piece will have a number of entries equal to its weight
            // For example, a piece with weight 5 will appear 5 times in the resulting list
            // This is counting the number of occurrences of each piece to get their respective weights
            for(JigsawPiece piece : oldBuildings) {
                buildings.compute(piece, (p, i) -> (i == null ? 0 : i) + 1);
            }

            for (String villager : new String[] {"carpenter", "innkeeper"}) {
                ResourceLocation buildingLocation = new ResourceLocation(VillageLife.MOD_ID, "village/" + villageType + "/houses/" + villageType + "_" + villager + "_house_1");
                JigsawPiece newPiece = new LegacySingleJigsawPiece(Either.left(buildingLocation), () -> ProcessorLists.field_244101_a, PlacementBehaviour.RIGID);
                buildings.put(newPiece, JIGSAW_WEIGHT);
            }

            List<Pair<JigsawPiece, Integer>> newPieces = buildings.entrySet().stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue())).collect(Collectors.toList());

            Registry.register(WorldGenRegistries.JIGSAW_POOL, villagePoolName, new JigsawPattern(villagePoolName, oldPool.getName(), newPieces));
        }
    }
}
