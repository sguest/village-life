package sguest.villagelife.recipe;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.google.gson.JsonElement;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.loot.condition.LootConditionManager;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import sguest.villagelife.blocks.ModBlocks;
import sguest.villagelife.items.ModItemTags;

public class RecipesProvider {
    public static Map<Identifier, JsonElement> RECIPES = new HashMap<>();
    public static Map<Identifier, JsonElement> ADVANCEMENTS = new HashMap<>();

    public static void initialize() {
        generate((recipeJsonProvider) -> {
            RECIPES.put(recipeJsonProvider.getRecipeId(), recipeJsonProvider.toJson());
            ADVANCEMENTS.put(recipeJsonProvider.getAdvancementId(), recipeJsonProvider.toAdvancementJson());
        });
    }

    private static void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonFactory.create(ModBlocks.WOODCUTTER)
            .input('I', ModItemTags.IRON_INGOTS)
            .input('#', ItemTags.PLANKS)
            .pattern(" I ")
            .pattern("###")
            .criterion("has_wood", conditionsFromTag(ItemTags.PLANKS))
            .offerTo(exporter);
    }

    private static InventoryChangedCriterion.Conditions conditionsFromTag(Tag<Item> tag) {
        return conditionsFromItemPredicates(ItemPredicate.Builder.create().tag(tag).build());
    }

    private static InventoryChangedCriterion.Conditions conditionsFromItemPredicates(ItemPredicate... items) {
        return new InventoryChangedCriterion.Conditions(EntityPredicate.Extended.EMPTY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, items);
    }
}
