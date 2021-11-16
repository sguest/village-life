package sguest.villagelife.resources;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JCriteria {
    private final Map<String, JCriterion> criteria = new HashMap<>();

    public static JCriteria criteria() {
        return new JCriteria();
    }

    public JCriteria var(String name, JCriterion criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public JCriteria clone() {
        try {
            return (JCriteria) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public static class Serializer implements JsonSerializer<JCriteria> {
        @Override
        public JsonElement serialize(JCriteria src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            for(var entry : src.criteria.entrySet()) {
                obj.add(entry.getKey(), context.serialize(entry.getValue()));
            }
            return obj;
        }
    }
}
