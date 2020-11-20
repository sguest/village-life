package sguest.villagelife.datagen;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.CopyName;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.loot.functions.CopyNbt.Source;
import net.minecraft.state.Property;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import sguest.villagelife.VillageLife;

//Adapted from https://wiki.mcjty.eu/modding/index.php?title=Tut14_Ep7#Loot_Tables
public abstract class BaseLootTableProvider extends LootTableProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    // Filled by subclasses
    protected final Map<Block, LootTable.Builder> blockLootTables = new HashMap<>();
    protected final Map<ResourceLocation, LootTable> lootTables = new HashMap<>();

    private final DataGenerator generator;

    public BaseLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
        this.generator = dataGeneratorIn;
    }

    // Subclasses can override this to fill the 'lootTables' map.
    protected abstract void addTables();

    protected LootTable.Builder addStandardTable(RegistryObject<Block> registry) {
        return addStandardTable(registry.getId().getPath(), registry.get());
    }

    protected LootTable.Builder addStandardTable(RegistryObject<Block> registry, Consumer<ItemLootEntry.Builder<?>> configure) {
        return addStandardTable(registry.getId().getPath(), registry.get(), configure);
    }

    protected LootTable.Builder addStandardTable(String name, Block block) {
        return addStandardTable(name, block, builder -> {});
    }

    // Subclasses can call this if they want a standard loot table. Modify this for your own needs
    protected LootTable.Builder addStandardTable(String name, Block block, Consumer<ItemLootEntry.Builder<?>> configure) {
        ItemLootEntry.Builder<?> itemLootEntryBuilder = ItemLootEntry.builder(block)
            .acceptCondition(SurvivesExplosion.builder());

        configure.accept(itemLootEntryBuilder);

        LootPool.Builder builder = LootPool.builder()
                .name(name)
                .rolls(ConstantRange.of(1))
                .addEntry(itemLootEntryBuilder);

        LootTable.Builder tableBuilder = LootTable.builder().addLootPool(builder);
        blockLootTables.put(block, tableBuilder);
        return tableBuilder;
    }

    protected void addHeroGift(VillagerProfession profession, IItemProvider ... items) {
        LootPool.Builder poolBuilder = LootPool.builder().rolls(new ConstantRange(1));

        for(IItemProvider item : items) {
            poolBuilder = poolBuilder.addEntry(ItemLootEntry.builder(item));
        }

        LootTable.Builder tableBuilder = LootTable.builder().addLootPool(poolBuilder);
        ResourceLocation registryName = profession.getRegistryName();
        ResourceLocation key = new ResourceLocation(registryName.getNamespace(), "gameplay/hero_of_the_village/" + registryName.getPath() + "_gift");
        lootTables.put(key, tableBuilder.setParameterSet(LootParameterSets.GIFT).build());
    }

    protected ItemLootEntry.Builder<?> copyName(ItemLootEntry.Builder<?> builder) {
        return builder.acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY));
    }

    protected ItemLootEntry.Builder<?> requireProperty(ItemLootEntry.Builder<?> builder, Block block, Property<?> property, String value) {
        return builder.acceptCondition(
            BlockStateProperty.builder(block)
                .fromProperties(StatePropertiesPredicate.Builder.newBuilder().withStringProp(property, value)));
    }

    protected ItemLootEntry.Builder<?> copyNbt(ItemLootEntry.Builder<?> builder, String sourcePath, String targetPath, CopyNbt.Action action) {
        return builder.acceptFunction(CopyNbt.builder(Source.BLOCK_ENTITY).addOperation(sourcePath, targetPath, action));
    }

    protected void addInjectTable(String path, Consumer<LootPool.Builder> configure) {
        LootPool.Builder poolBuilder = LootPool.builder().rolls(new ConstantRange(1)).name(VillageLife.MOD_ID + "-main");

        configure.accept(poolBuilder);

        LootTable.Builder tableBuilder = LootTable.builder().addLootPool(poolBuilder);
        ResourceLocation key = new ResourceLocation(VillageLife.MOD_ID, "inject/" + path);
        lootTables.put(key, tableBuilder.build());
    }

    @Override
    // Entry point
    public void act(DirectoryCache cache) {
        addTables();

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<Block, LootTable.Builder> entry : blockLootTables.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParameterSet(LootParameterSets.BLOCK).build());
        }
        tables.putAll(lootTables);
        writeTables(cache, tables);
    }

    // Actually write out the tables in the output folder
    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
            } catch (IOException e) {
                VillageLife.getLogger().error("Couldn't write loot table {}", path, e);
            }
        });
    }

    @Override
    public String getName() {
        return "VillageLife LootTables";
    }
}