package sguest.villagelife.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemUtil {
    public static void giveToPlayer(PlayerEntity player, ItemStack itemStack) {
        if(!player.inventory.addItemStackToInventory(itemStack)) {
            player.dropItem(itemStack, false);
        }
    }

    public static Item getCarpet(String colour) {
        switch(colour) {
            case "white": return Items.WHITE_CARPET;
            case "orange": return Items.ORANGE_CARPET;
            case "magenta": return Items.MAGENTA_CARPET;
            case "light_blue": return Items.LIGHT_BLUE_CARPET;
            case "yellow": return Items.YELLOW_CARPET;
            case "lime": return Items.LIME_CARPET;
            case "pink": return Items.PINK_CARPET;
            case "light_gray": return Items.LIGHT_GRAY_CARPET;
            case "cyan": return Items.CYAN_CARPET;
            case "purple": return Items.PURPLE_CARPET;
            case "blue": return Items.BLUE_CARPET;
            case "brown": return Items.BROWN_CARPET;
            case "green": return Items.GREEN_CARPET;
            case "red": return Items.RED_CARPET;
            case "black": return Items.BLACK_CARPET;
            default: return null;
        }
    }

    public static String[] listDyeColours() {
        return new String[] {
            "white",
            "orange",
            "magenta",
            "light_blue",
            "yellow",
            "lime",
            "pink",
            "gray",
            "light_gray",
            "cyan",
            "purple",
            "blue",
            "brown",
            "green",
            "red",
            "black",
        };
    }
}
