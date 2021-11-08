package sguest.villagelife;

import net.fabricmc.api.ModInitializer;
import sguest.villagelife.blocks.ModBlocks;
import sguest.villagelife.items.ModItems;
import sguest.villagelife.recipe.RecipesProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VillageLifeMod implements ModInitializer {
    public static final String MODID = "villagelife";
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Override
    public void onInitialize() {
        ModBlocks.initialize();
        ModItems.initialize();
        RecipesProvider.initialize();
    }
}
