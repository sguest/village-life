package sguest.villagelife.block;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;

public class ReinforcedDoorBlock extends DoorBlock {

    public ReinforcedDoorBlock(Properties builder) {
        super(builder);
    }

    @Override
    public boolean canEntityDestroy(BlockState state, IBlockReader world, BlockPos pos, Entity entity) {
        if(entity instanceof ZombieEntity) {
            return false;
        }
        return super.canEntityDestroy(state, world, pos, entity);
    }

    @Override
    public void addInformation(ItemStack itemStack, IBlockReader world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(itemStack, world, list, flag);
        TranslationTextComponent text = new TranslationTextComponent("block.villagelife.reinforced_door.tooltip");
        text.setStyle(text.getStyle().applyFormatting(TextFormatting.DARK_GRAY));
        list.add(text);
    }
}
