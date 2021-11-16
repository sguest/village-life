package sguest.villagelife.resources;

import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JKeys;
import net.devtech.arrp.json.recipe.JPattern;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JResult;
import net.minecraft.item.Item;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.Tag;
import net.minecraft.util.registry.Registry;
import sguest.villagelife.blocks.ModBlocks;
import sguest.villagelife.items.ModItemTags;
import sguest.villagelife.items.ModItems;

public class ModRecipes {
    public static void initialize() {
        ModResourcePack.RESOURCE_PACK.addRecipe(Registry.BLOCK.getId(ModBlocks.WOODCUTTER),
            JRecipe.shaped(
                JPattern.pattern(
                    " I ",
                    "###"
                ),
                JKeys.keys()
                    .key("I", tagIngredient(ModItemTags.IRON_INGOTS))
                    .key("#", tagIngredient(ItemTags.PLANKS)),
                JResult.item(ModItems.WOODCUTTER)
            ));
    }

    private static JIngredient tagIngredient(Tag<Item> tag) {
        return JIngredient.ingredient().tag(tagPath(tag));
    }

    private static String tagPath(Tag<Item> tag) {
        return ServerTagManagerHolder.getTagManager().getTagId(Registry.ITEM_KEY, tag, () -> {
            return new IllegalStateException("Unknown item tag");
        }).toString();
    }
}
