package sguest.villagelife.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import sguest.villagelife.util.ItemUtil;

public class MilkBottleItem extends Item {

    public MilkBottleItem(Properties builder) {
        super(builder);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entityLiving) {
        // This should cure anything that a milk bucket would
        ItemStack cureStack = new ItemStack(Items.MILK_BUCKET);
        if (!world.isRemote) entityLiving.curePotionEffects(cureStack);

        if (entityLiving instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
            serverplayerentity.addStat(Stats.ITEM_USED.get(this));
        }

        if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).abilities.isCreativeMode) {
            stack.shrink(1);
            if(stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }
            ItemUtil.giveToPlayer((PlayerEntity)entityLiving, new ItemStack(Items.GLASS_BOTTLE));
        }
        return stack;
    }

    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        return DrinkHelper.startDrinking(world, player, hand);
    }
}
