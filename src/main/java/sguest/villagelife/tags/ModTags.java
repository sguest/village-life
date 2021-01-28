package sguest.villagelife.tags;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.ITag.INamedTag;
import sguest.villagelife.VillageLife;

public class ModTags {
    public static class Blocks {
        public static final INamedTag<Block> TRADING_POSTS = makeTag("trading_posts");

        private static INamedTag<Block> makeTag(String id) {
            return BlockTags.makeWrapperTag(VillageLife.MOD_ID + ":" + id);
        }
    }

    public static class Items {
        public static final INamedTag<Item> TRADING_POSTS = makeTag("trading_posts");

        private static INamedTag<Item> makeTag(String id) {
            return ItemTags.makeWrapperTag(VillageLife.MOD_ID + ":" + id);
        }
    }
}
