package sguest.villagelife.block;

import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.dispenser.ShulkerBoxDispenseBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import sguest.villagelife.item.ModItems;
import sguest.villagelife.tileentity.KegTileEntity;

public class DispenserOverrides {
    public static void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(DispenserOverrides::applyOverrides);
    }

    private static void applyOverrides() {
        applyKegOverride(KegBlock.getValidItems());

        DispenserBlock.DISPENSE_BEHAVIOR_REGISTRY.put(ModItems.KEG.get(), new ShulkerBoxDispenseBehavior());
    }

    private static void applyKegOverride(Item ... items) {
        for(Item item : items) {
            IDispenseItemBehavior originalBehavior = DispenserBlock.DISPENSE_BEHAVIOR_REGISTRY.get(item);
            IDispenseItemBehavior kegBehavior = new BlockOverrideDispenseItemBehavior(ModBlocks.KEG.get(), originalBehavior) {
                @Override
                public ItemStack customDispense(IBlockSource source, ItemStack stack, BlockPos targetPos) {
                    World world = source.getWorld();
                    TileEntity tileEntity = world.getTileEntity(targetPos);
                    if(!(tileEntity instanceof KegTileEntity)) {
                        return stack;
                    }
                    KegTileEntity kegTileEntity = (KegTileEntity)tileEntity;
                    ItemStack result = kegTileEntity.useItem(stack);

                    if(result.isEmpty()) {
                        return stack;
                    }

                    stack.shrink(1);
                    if (stack.isEmpty()) {
                        return result;
                    }
                    else {
                        if (source.<DispenserTileEntity>getBlockTileEntity().addItemStack(result) < 0) {
                            new DefaultDispenseItemBehavior().dispense(source, result);
                        }
                        return stack;
                    }
                }
            };
            DispenserBlock.DISPENSE_BEHAVIOR_REGISTRY.put(item, kegBehavior);
        }
    }

    public static class BlockOverrideDispenseItemBehavior extends OptionalDispenseBehavior {
        private final Block targetBlock;
        private final IDispenseItemBehavior parentBehavior;

        public BlockOverrideDispenseItemBehavior(Block targetBlock, IDispenseItemBehavior parentBehavior) {
            this.targetBlock = targetBlock;
            this.parentBehavior = parentBehavior;
        }

        public ItemStack customDispense(IBlockSource source, ItemStack stack, BlockPos targetPos) {
            return stack;
        }

        @Override
        public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            BlockPos targetPos = source.getBlockPos().offset(direction);
            Block targetBlock = source.getWorld().getBlockState(targetPos).getBlock();
            if(targetBlock == this.targetBlock) {
                return customDispense(source, stack, targetPos);
            }
            else {
                if(parentBehavior != null) {
                    return parentBehavior.dispense(source, stack);
                }
                return stack;
            }
        }

    }
}
