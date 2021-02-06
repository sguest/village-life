package sguest.villagelife.datagen;

import java.util.Arrays;

import com.mojang.datafixers.util.Pair;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.LootTableIdCondition;
import sguest.villagelife.VillageLife;
import sguest.villagelife.item.ModItems;
import sguest.villagelife.loot.AddEntriesLootModifier;
import sguest.villagelife.loot.ModLootModifiers;

public class GlobalLootModifierProvider extends net.minecraftforge.common.data.GlobalLootModifierProvider {

    public GlobalLootModifierProvider(DataGenerator gen) {
        super(gen, VillageLife.MOD_ID);
    }

    @Override
    protected void start() {
        add("simple_dungeon_add", ModLootModifiers.ADD_ENTRIES.get(), new AddEntriesLootModifier(
            new ILootCondition[] { LootTableIdCondition.builder(new ResourceLocation("chests/simple_dungeon")).build() },
            Arrays.asList(new Pair<ItemStack, Float>(new ItemStack(ModItems.EMERALD_HORSE_ARMOR.get()), 0.04f))));
        add("desert_pyramid_add", ModLootModifiers.ADD_ENTRIES.get(), new AddEntriesLootModifier(
            new ILootCondition[] { LootTableIdCondition.builder(new ResourceLocation("chests/desert_pyramid")).build() },
            Arrays.asList(new Pair<ItemStack, Float>(new ItemStack(ModItems.EMERALD_HORSE_ARMOR.get()), 0.1f))));
        add("end_city_add", ModLootModifiers.ADD_ENTRIES.get(), new AddEntriesLootModifier(
            new ILootCondition[] { LootTableIdCondition.builder(new ResourceLocation("chests/end_city")).build() },
            Arrays.asList(new Pair<ItemStack, Float>(new ItemStack(ModItems.EMERALD_HORSE_ARMOR.get()), 0.04f))));
        add("jungle_temple_add", ModLootModifiers.ADD_ENTRIES.get(), new AddEntriesLootModifier(
            new ILootCondition[] { LootTableIdCondition.builder(new ResourceLocation("chests/jungle_temple")).build() },
            Arrays.asList(new Pair<ItemStack, Float>(new ItemStack(ModItems.EMERALD_HORSE_ARMOR.get()), 0.04f))));
        add("nether_bridge_add", ModLootModifiers.ADD_ENTRIES.get(), new AddEntriesLootModifier(
            new ILootCondition[] { LootTableIdCondition.builder(new ResourceLocation("chests/nether_bridge")).build() },
            Arrays.asList(new Pair<ItemStack, Float>(new ItemStack(ModItems.EMERALD_HORSE_ARMOR.get()), 0.2f))));
        add("stronghold_corridor_add", ModLootModifiers.ADD_ENTRIES.get(), new AddEntriesLootModifier(
            new ILootCondition[] { LootTableIdCondition.builder(new ResourceLocation("chests/stronghold_corridor")).build() },
            Arrays.asList(new Pair<ItemStack, Float>(new ItemStack(ModItems.EMERALD_HORSE_ARMOR.get()), 0.03f))));
        add("village_weaponsmith_add", ModLootModifiers.ADD_ENTRIES.get(), new AddEntriesLootModifier(
            new ILootCondition[] { LootTableIdCondition.builder(new ResourceLocation("chests/village/village_weaponsmith")).build() },
            Arrays.asList(new Pair<ItemStack, Float>(new ItemStack(ModItems.EMERALD_HORSE_ARMOR.get()), 0.1f))));
    }
}
