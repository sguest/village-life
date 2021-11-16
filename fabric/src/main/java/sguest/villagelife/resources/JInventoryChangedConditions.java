package sguest.villagelife.resources;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class JInventoryChangedConditions extends JConditions {
    private Map<String, String> items = new HashMap<>();

    public JInventoryChangedConditions item(Item item) {
        this.items.put("item", Registry.ITEM.getId(item).toString());
        return this;
    }

    public JInventoryChangedConditions tag(String tag) {
        this.items.put("tag", tag);
        return this;
    }

    @Override
    protected JInventoryChangedConditions clone() {
        return (JInventoryChangedConditions) super.clone();
    }

    public static class Serializer implements JsonSerializer<JInventoryChangedConditions> {
        @Override
        public JsonElement serialize(JInventoryChangedConditions src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            JsonArray arr = new JsonArray();
            for(var entry : src.items.entrySet()) {
                JsonObject item = new JsonObject();
                item.add(entry.getKey(), context.serialize(entry.getValue()));
                arr.add(item);
            }
            obj.add("items", arr);
            return obj;
        }
    }
}
