package sguest.villagelife.resources;

import org.spongepowered.include.com.google.gson.JsonArray;
import org.spongepowered.include.com.google.gson.JsonObject;
import org.spongepowered.include.com.google.gson.JsonPrimitive;

import net.minecraft.item.Item;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import sguest.villagelife.items.ModItems;

public class ModAdvancements {
    public static void initialize() {
        standardRecipeAdvancement(ModItems.WOODCUTTER, ItemTags.PLANKS, "has_wood", "decoration");
    }

    private static void standardRecipeAdvancement(Item item, Tag<Item> triggerItem, String triggerName, String category) {
        var itemId = Registry.ITEM.getId(item);

        var rewardArray = new JsonArray();
        rewardArray.add(new JsonPrimitive(itemId.toString()));
        var rewards = new JsonObject();
        rewards.add("recipes", rewardArray);

        var requirements = new JsonArray();
        requirements.add(new JsonPrimitive(triggerName));
        requirements.add(new JsonPrimitive("has_the_recipe"));
        var requirementsWrapper = new JsonArray();
        requirementsWrapper.add(requirements);

        var itemObj = new JsonObject();
        itemObj.add("tag", new JsonPrimitive(tagPath(triggerItem)));
        var itemArray = new JsonArray();
        itemArray.add(itemObj);
        var conditions = new JsonObject();
        conditions.add("items", itemArray);
        var hasItemCondition = new JsonObject();
        hasItemCondition.add("trigger", new JsonPrimitive("minecraft:inventory_changed"));
        hasItemCondition.add("conditions", conditions);

        var recipeCondition = new JsonObject();
        recipeCondition.add("recipe", new JsonPrimitive(Registry.ITEM.getId(item).toString()));
        var hasRecipeCondition = new JsonObject();
        hasRecipeCondition.add("trigger", new JsonPrimitive("minecraft:recipe_unlocked"));
        hasRecipeCondition.add("conditions", recipeCondition);

        var criteria = new JsonObject();
        criteria.add(triggerName, hasItemCondition);
        criteria.add("has_the_recipe", hasRecipeCondition);

        var advancement = new JsonObject();
        advancement.add("rewards", rewards);
        advancement.add("requirements", requirementsWrapper);
        advancement.add("parent", new JsonPrimitive("minecraft:recipes/root"));
        advancement.add("criteria", criteria);

        var advancementId = new Identifier(itemId.getNamespace(), "advancements/recipes/" + category + "/" + itemId.getPath() + ".json");
        ModResourcePack.RESOURCE_PACK.addData(advancementId, advancement.toString().getBytes());
    }

    private static String tagPath(Tag<Item> tag) {
        return ServerTagManagerHolder.getTagManager().getTagId(Registry.ITEM_KEY, tag, () -> {
            return new IllegalStateException("Unknown item tag");
        }).toString();
    }

}
