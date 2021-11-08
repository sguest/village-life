package sguest.villagelife.items;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class ItemTags {
    public static final Tag<Item> IRON_INGOTS = TagFactory.ITEM.create(new Identifier("c", "iron_ingots"));
}
