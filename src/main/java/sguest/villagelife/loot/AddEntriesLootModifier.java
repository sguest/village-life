package sguest.villagelife.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class AddEntriesLootModifier extends LootModifier {
    private List<Pair<ItemStack, Float>> items;

    public AddEntriesLootModifier(ILootCondition[] conditions, List<Pair<ItemStack, Float>> items) {
        super(conditions);

        this.items = items;
    }

    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        Random random = context.getRandom();
        for(Pair<ItemStack, Float> item : items) {
            if(random.nextFloat() <= item.getSecond()) {
                generatedLoot.add(item.getFirst());
            }
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<AddEntriesLootModifier> {
        private static final String entriesKey = "entries";
        private static final String itemKey = "item";
        private static final String chanceKey = "chance";
        private static final String countKey = "count";

        @Override
        public AddEntriesLootModifier read(ResourceLocation location, JsonObject json,
                ILootCondition[] conditions) {

            List<Pair<ItemStack, Float>> items = new ArrayList<>();
            for(JsonElement item : JSONUtils.getJsonArray(json, entriesKey)) {
                JsonObject entry = (JsonObject)item;
                items.add(new Pair<ItemStack, Float>(
                    new ItemStack(
                        ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getString(entry, itemKey))),
                        JSONUtils.getInt(entry, countKey)
                    ),
                    JSONUtils.getFloat(entry, chanceKey)));
            }
            return new AddEntriesLootModifier(conditions, items);
        }

        @Override
        public JsonObject write(AddEntriesLootModifier instance) {
            final JsonObject json = this.makeConditions(instance.conditions);
            final JsonArray entries = new JsonArray();

            for(Pair<ItemStack, Float> item : instance.items) {
                JsonObject obj = new JsonObject();
                obj.addProperty(itemKey, item.getFirst().getItem().getRegistryName().toString());
                obj.addProperty(countKey, item.getFirst().getCount());
                obj.addProperty(chanceKey, item.getSecond());
                entries.add(obj);
            }
            json.add(entriesKey, entries);
            return json;
        }

    }
}
