package sguest.villagelife.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ItemUtil {
    public static void giveToPlayer(PlayerEntity player, ItemStack itemStack) {
        if(!player.inventory.addItemStackToInventory(itemStack)) {
            player.dropItem(itemStack, false);
        }
    }
}
