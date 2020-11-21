package sguest.villagelife.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        generator.addProvider(new Recipes(generator));
        generator.addProvider(new LootTables(generator));

        BlockTagsProvider blockTags = new BlockTagsProvider(generator, fileHelper);
        generator.addProvider(blockTags);
        generator.addProvider(new ItemTagsProvider(generator, blockTags, fileHelper));
        generator.addProvider(new ItemModels(generator, fileHelper));
    }
}
