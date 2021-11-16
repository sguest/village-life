package sguest.villagelife.resources;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.devtech.arrp.util.UnsafeByteArrayOutputStream;
import net.minecraft.item.Item;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import sguest.villagelife.items.ModItems;

public class ModAdvancements {
    public static final Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .registerTypeAdapter(JRewards.class, new JRewards.Serializer())
        .registerTypeAdapter(JInventoryChangedConditions.class, new JInventoryChangedConditions.Serializer())
        .registerTypeAdapter(JCriteria.class, new JCriteria.Serializer())
        .create();

    public static void initialize() {
        standardRecipeAdvancement(ModItems.WOODCUTTER, ItemTags.PLANKS, "has_wood", "decoration");
    }

    private static void standardRecipeAdvancement(Item item, Tag<Item> triggerItem, String triggerName, String category) {
        var itemId = Registry.ITEM.getId(item);
        var advancementId = new Identifier(itemId.getNamespace(), "advancements/recipes/" + category + "/" + itemId.getPath() + ".json");

        addAdvancement(
            advancementId,
            JAdvancement.advancement()
                .parent("minecraft:recipes/root")
                .rewards(JRewards.rewards().recipe(item))
                .requirements(triggerName, "has_the_recipe")
                .criteria(JCriteria.criteria()
                    .var(triggerName, JCriterion.inventoryChanged(new JInventoryChangedConditions().tag(tagPath(triggerItem))))
                    .var("has_the_recipe", JCriterion.recipeUnlocked(new JRecipeUnlockedConditions(item)))
                )
        );
    }

    private static void addAdvancement(Identifier id, JAdvancement advancement) {
        ModResourcePack.RESOURCE_PACK.addData(id, serialize(advancement));
    }

    private static byte[] serialize(Object object) {
        UnsafeByteArrayOutputStream ubaos = new UnsafeByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(ubaos);
        GSON.toJson(object, writer);
        try {
            writer.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return ubaos.getBytes();
    }

    private static String tagPath(Tag<Item> tag) {
        return ServerTagManagerHolder.getTagManager().getTagId(Registry.ITEM_KEY, tag, () -> {
            return new IllegalStateException("Unknown item tag");
        }).toString();
    }

}
