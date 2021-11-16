package sguest.villagelife.resources;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class JRewards implements Cloneable {
    private List<String> recipes;
    private List<String> loot;
    private int experience;
    private String function;

    public JRewards()
    {
    }

    public static JRewards rewards() {
        return new JRewards();
    }

    public JRewards recipe(String recipe) {
        if(this.recipes == null) {
            this.recipes = new ArrayList<String>(1);
        }
        this.recipes.add(recipe);
        return this;
    }

    public JRewards recipe(Item item) {
        return this.recipe(Registry.ITEM.getId(item).toString());
    }

    public JRewards loot(String loot) {
        if(this.loot == null) {
            this.loot = new ArrayList<String>(1);
        }
        this.loot.add(loot);
        return this;
    }

    public JRewards experience(int experience) {
        this.experience = experience;
        return this;
    }

    public JRewards function(String function) {
        this.function = function;
        return this;
    }

    @Override
    public JRewards clone() {
        try {
            return (JRewards) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public static class Serializer implements JsonSerializer<JRewards> {
        @Override
        public JsonElement serialize(JRewards src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            if(src.recipes.size() > 0) {
                obj.add("recipes", context.serialize(src.recipes));
            }
            if(src.experience > 0) {
                obj.add("experience", context.serialize(src.experience));
            }
            if(src.function != null) {
                obj.add("function", context.serialize(src.function));
            }
            return obj;
        }
    }
}
