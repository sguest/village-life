package sguest.villagelife.datagen;

import java.util.function.Consumer;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import sguest.villagelife.VillageLife;
import sguest.villagelife.block.ModBlocks;
import sguest.villagelife.item.ModItems;
import sguest.villagelife.item.crafting.ModRecipeSerializers;
import sguest.villagelife.util.ItemUtil;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.WOODCUTTER.get())
            .patternLine(" I ")
            .patternLine("###")
            .key('I', Tags.Items.INGOTS_IRON)
            .key('#', ItemTags.PLANKS)
            .setGroup(ItemGroup.DECORATIONS.getPath())
            .addCriterion("has_wood", hasItem(ItemTags.PLANKS))
            .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_AXE.get())
            .patternLine("EE")
            .patternLine("ES")
            .patternLine(" S")
            .key('E', Tags.Items.GEMS_EMERALD)
            .key('S', Tags.Items.RODS_WOODEN)
            .setGroup(ItemGroup.TOOLS.getPath())
            .addCriterion("has_emerald", hasItem(Tags.Items.GEMS_EMERALD))
            .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_HOE.get())
            .patternLine("EE")
            .patternLine(" S")
            .patternLine(" S")
            .key('E', Tags.Items.GEMS_EMERALD)
            .key('S', Tags.Items.RODS_WOODEN)
            .setGroup(ItemGroup.TOOLS.getPath())
            .addCriterion("has_emerald", hasItem(Tags.Items.GEMS_EMERALD))
            .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_PICKAXE.get())
            .patternLine("EEE")
            .patternLine(" S ")
            .patternLine(" S ")
            .key('E', Tags.Items.GEMS_EMERALD)
            .key('S', Tags.Items.RODS_WOODEN)
            .setGroup(ItemGroup.TOOLS.getPath())
            .addCriterion("has_emerald", hasItem(Tags.Items.GEMS_EMERALD))
            .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_SHOVEL.get())
            .patternLine("E")
            .patternLine("S")
            .patternLine("S")
            .key('E', Tags.Items.GEMS_EMERALD)
            .key('S', Tags.Items.RODS_WOODEN)
            .setGroup(ItemGroup.TOOLS.getPath())
            .addCriterion("has_emerald", hasItem(Tags.Items.GEMS_EMERALD))
            .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_SWORD.get())
            .patternLine("E")
            .patternLine("E")
            .patternLine("S")
            .key('E', Tags.Items.GEMS_EMERALD)
            .key('S', Tags.Items.RODS_WOODEN)
            .setGroup(ItemGroup.COMBAT.getPath())
            .addCriterion("has_emerald", hasItem(Tags.Items.GEMS_EMERALD))
            .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_BOOTS.get())
            .patternLine("E E")
            .patternLine("E E")
            .key('E', Tags.Items.GEMS_EMERALD)
            .setGroup(ItemGroup.COMBAT.getPath())
            .addCriterion("has_emerald", hasItem(Tags.Items.GEMS_EMERALD))
            .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_CHESTPLATE.get())
            .patternLine("E E")
            .patternLine("EEE")
            .patternLine("EEE")
            .key('E', Tags.Items.GEMS_EMERALD)
            .setGroup(ItemGroup.COMBAT.getPath())
            .addCriterion("has_emerald", hasItem(Tags.Items.GEMS_EMERALD))
            .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_HELMET.get())
            .patternLine("EEE")
            .patternLine("E E")
            .key('E', Tags.Items.GEMS_EMERALD)
            .setGroup(ItemGroup.COMBAT.getPath())
            .addCriterion("has_emerald", hasItem(Tags.Items.GEMS_EMERALD))
            .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_LEGGINGS.get())
            .patternLine("EEE")
            .patternLine("E E")
            .patternLine("E E")
            .key('E', Tags.Items.GEMS_EMERALD)
            .setGroup(ItemGroup.COMBAT.getPath())
            .addCriterion("has_emerald", hasItem(Tags.Items.GEMS_EMERALD))
            .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.EMERALD_PRESSURE_PLATE.get())
            .patternLine("EE")
            .key('E', Tags.Items.GEMS_EMERALD)
            .setGroup(ItemGroup.REDSTONE.getPath())
            .addCriterion("has_emerald", hasItem(Tags.Items.GEMS_EMERALD))
            .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.KEG.get())
            .patternLine("PPP")
            .patternLine("P P")
            .patternLine("SPS")
            .key('P', ItemTags.PLANKS)
            .key('S', ItemTags.WOODEN_STAIRS)
            .setGroup(ItemGroup.DECORATIONS.getPath())
            .addCriterion("has_wood", hasItem(ItemTags.PLANKS))
            .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.KEG.get())
            .addIngredient(ModBlocks.KEG.get())
            .addCriterion("has_keg", hasItem(ModBlocks.KEG.get()))
            .build(consumer, new ResourceLocation(VillageLife.MOD_ID, "empty_keg"));

        for(String colour : ItemUtil.listDyeColours()) {
            ShapedRecipeBuilder.shapedRecipe(ModBlocks.TRADING_POSTS.get(colour).get())
                .patternLine("CCC")
                .patternLine("SFS")
                .patternLine("WWW")
                .key('C', ItemUtil.getCarpet(colour))
                .key('S', Tags.Items.RODS_WOODEN)
                .key('F', Items.ITEM_FRAME)
                .key('W', Items.OAK_SLAB)
                .setGroup(ItemGroup.DECORATIONS.getPath())
                .addCriterion("has_frame", hasItem(Items.ITEM_FRAME))
                .build(consumer);
        }

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.HARVESTER.get())
            .patternLine("III")
            .patternLine("CRC")
            .patternLine("CCC")
            .key('I', Items.IRON_BARS)
            .key('C', Tags.Items.COBBLESTONE)
            .key('R', Tags.Items.DUSTS_REDSTONE)
            .setGroup(ItemGroup.DECORATIONS.getPath())
            .addCriterion("has_redstone", hasItem(Items.REDSTONE))
            .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ModItems.MUTTON_SANDWICH.get())
            .addIngredient(Items.BREAD)
            .addIngredient(Items.COOKED_MUTTON)
            .setGroup(ItemGroup.FOOD.getPath())
            .addCriterion("has_mutton", hasItem(Items.COOKED_MUTTON))
            .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ModItems.PORK_SANDWICH.get())
            .addIngredient(Items.BREAD)
            .addIngredient(Items.COOKED_PORKCHOP)
            .setGroup(ItemGroup.FOOD.getPath())
            .addCriterion("has_porkchop", hasItem(Items.COOKED_PORKCHOP))
            .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ModItems.STEAK_SANDWICH.get())
            .addIngredient(Items.BREAD)
            .addIngredient(Items.COOKED_BEEF)
            .setGroup(ItemGroup.FOOD.getPath())
            .addCriterion("has_beef", hasItem(Items.COOKED_BEEF))
            .build(consumer);

        reinforcedDoor(ModBlocks.REINFORCED_ACACIA_DOOR.get(), Blocks.ACACIA_DOOR, consumer);
        reinforcedDoor(ModBlocks.REINFORCED_BIRCH_DOOR.get(), Blocks.BIRCH_DOOR, consumer);
        reinforcedDoor(ModBlocks.REINFORCED_CRIMSON_DOOR.get(), Blocks.CRIMSON_DOOR, consumer);
        reinforcedDoor(ModBlocks.REINFORCED_DARK_OAK_DOOR.get(), Blocks.DARK_OAK_DOOR, consumer);
        reinforcedDoor(ModBlocks.REINFORCED_JUNGLE_DOOR.get(), Blocks.JUNGLE_DOOR, consumer);
        reinforcedDoor(ModBlocks.REINFORCED_OAK_DOOR.get(), Blocks.OAK_DOOR, consumer);
        reinforcedDoor(ModBlocks.REINFORCED_SPRUCE_DOOR.get(), Blocks.SPRUCE_DOOR, consumer);
        reinforcedDoor(ModBlocks.REINFORCED_WARPED_DOOR.get(), Blocks.WARPED_DOOR, consumer);

        addWoodcutting(ItemTags.ACACIA_LOGS, Items.ACACIA_LOG, Items.ACACIA_WOOD, Items.STRIPPED_ACACIA_LOG, Items.STRIPPED_ACACIA_WOOD,
            Items.ACACIA_PLANKS, Items.ACACIA_FENCE, Items.ACACIA_SLAB, Items.ACACIA_STAIRS, consumer);
        addWoodcutting(ItemTags.BIRCH_LOGS, Items.BIRCH_LOG, Items.BIRCH_WOOD, Items.STRIPPED_BIRCH_LOG, Items.STRIPPED_BIRCH_WOOD,
            Items.BIRCH_PLANKS, Items.BIRCH_FENCE, Items.BIRCH_SLAB, Items.BIRCH_STAIRS, consumer);
        addWoodcutting(ItemTags.CRIMSON_STEMS, Items.CRIMSON_STEM, Items.CRIMSON_HYPHAE, Items.STRIPPED_CRIMSON_STEM, Items.STRIPPED_CRIMSON_HYPHAE,
            Items.CRIMSON_PLANKS, Items.CRIMSON_FENCE, Items.CRIMSON_SLAB, Items.CRIMSON_STAIRS, consumer);
        addWoodcutting(ItemTags.DARK_OAK_LOGS, Items.DARK_OAK_LOG, Items.DARK_OAK_WOOD, Items.STRIPPED_DARK_OAK_LOG, Items.STRIPPED_DARK_OAK_WOOD,
            Items.DARK_OAK_PLANKS, Items.DARK_OAK_FENCE, Items.DARK_OAK_SLAB, Items.DARK_OAK_STAIRS, consumer);
        addWoodcutting(ItemTags.JUNGLE_LOGS, Items.JUNGLE_LOG, Items.JUNGLE_WOOD, Items.STRIPPED_JUNGLE_LOG, Items.STRIPPED_JUNGLE_WOOD,
            Items.JUNGLE_PLANKS, Items.JUNGLE_FENCE, Items.JUNGLE_SLAB, Items.JUNGLE_STAIRS, consumer);
        addWoodcutting(ItemTags.OAK_LOGS, Items.OAK_LOG, Items.OAK_WOOD, Items.STRIPPED_OAK_LOG, Items.STRIPPED_OAK_WOOD,
            Items.OAK_PLANKS, Items.OAK_FENCE, Items.OAK_SLAB, Items.OAK_STAIRS, consumer);
        addWoodcutting(ItemTags.SPRUCE_LOGS, Items.SPRUCE_LOG, Items.SPRUCE_WOOD, Items.STRIPPED_SPRUCE_LOG, Items.STRIPPED_SPRUCE_WOOD,
            Items.SPRUCE_PLANKS, Items.SPRUCE_FENCE, Items.SPRUCE_SLAB, Items.SPRUCE_STAIRS, consumer);
        addWoodcutting(ItemTags.WARPED_STEMS, Items.WARPED_STEM, Items.WARPED_HYPHAE, Items.STRIPPED_WARPED_STEM, Items.STRIPPED_WARPED_HYPHAE,
            Items.WARPED_PLANKS, Items.WARPED_FENCE, Items.WARPED_SLAB, Items.WARPED_STAIRS, consumer);
    }

    private void reinforcedDoor(Block reinforcedDoor, Block regularDoor, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(reinforcedDoor)
            .patternLine("I")
            .patternLine("D")
            .patternLine("I")
            .key('I', Items.IRON_BARS)
            .key('D', regularDoor)
            .setGroup(ItemGroup.REDSTONE.getPath())
            .addCriterion("has_door", hasItem(regularDoor))
            .build(consumer);
    }

    private void addWoodcutting(INamedTag<Item> logTag, Item log, Item wood, Item strippedLog, Item strippedWood, Item planks, Item fence, Item slab, Item stairs, Consumer<IFinishedRecipe> consumer) {
        woodcuttingRecipe(logTag, strippedLog, 1, consumer);
        woodcuttingRecipe(logTag, strippedWood, 1, consumer);
        woodcuttingRecipe(planks, fence, 1, consumer);
        woodcuttingRecipe(wood, log, 1, consumer);
        woodcuttingRecipe(log, wood, 1, consumer);
        woodcuttingRecipe(logTag, planks, 4, consumer);
        woodcuttingRecipe(planks, slab, 2, consumer);
        woodcuttingRecipe(planks, stairs, 1, consumer);
    }

    private void woodcuttingRecipe(INamedTag<Item> input, Item output, int count, Consumer<IFinishedRecipe> consumer) {
        new SingleItemRecipeBuilder(ModRecipeSerializers.WOODCUTTING.get(), Ingredient.fromTag(input), output, count)
            .addCriterion("has_wood", hasItem(input))
            .build(consumer, woodcuttingName(input.getName(), output.getRegistryName()));
    }

    private void woodcuttingRecipe(Item input, Item output, int count, Consumer<IFinishedRecipe> consumer) {
        new SingleItemRecipeBuilder(ModRecipeSerializers.WOODCUTTING.get(), Ingredient.fromItems(input), output, count)
            .addCriterion("has_wood", hasItem(input))
            .build(consumer, woodcuttingName(input.getRegistryName(), output.getRegistryName()));
    }

    private ResourceLocation woodcuttingName(ResourceLocation first, ResourceLocation second) {
        return new ResourceLocation(VillageLife.MOD_ID, String.format("%s_from_%s_woodcutting", first.getPath(), second.getPath()));
    }
}
