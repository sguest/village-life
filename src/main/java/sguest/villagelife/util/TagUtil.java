package sguest.villagelife.util;

import java.util.Optional;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.util.ResourceLocation;

public class TagUtil {
    public static INamedTag<Item> getItemTag(ResourceLocation name) {
        Optional<? extends INamedTag<Item>> optional = ItemTags.getAllTags()
            .stream().filter(tag -> tag.getName().equals(name)).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }
}
