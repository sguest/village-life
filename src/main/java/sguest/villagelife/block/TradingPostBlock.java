package sguest.villagelife.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import sguest.villagelife.tileentity.TradingPostTileEntity;
import sguest.villagelife.util.ItemUtil;

public class TradingPostBlock extends Block {

    public TradingPostBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TradingPostTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        if(player.isSpectator()) {
            return ActionResultType.CONSUME;
        }

        TileEntity tileEntity = world.getTileEntity(pos);
        if(!(tileEntity instanceof TradingPostTileEntity)) {
            return ActionResultType.PASS;
        }
        TradingPostTileEntity tradingPostTileEntity = (TradingPostTileEntity)tileEntity;

        ItemStack displayedItem = tradingPostTileEntity.getDisplayedItem();
        if(!displayedItem.isEmpty()) {
            return ActionResultType.PASS;
        }

        ItemStack heldItem = player.getHeldItem(hand);
        if(heldItem.isEmpty()) {
            return ActionResultType.PASS;
        }

        ItemStack toDisplay = heldItem.split(1);
        tradingPostTileEntity.setDisplayedItem(toDisplay);

        return ActionResultType.SUCCESS;
    }

    @Override
    public void onBlockClicked(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if(world.isRemote) {
            return;
        }
        if(player.isSpectator()) {
            return;
        }

        TileEntity tileEntity = world.getTileEntity(pos);
        if(!(tileEntity instanceof TradingPostTileEntity)) {
            return;
        }
        TradingPostTileEntity tradingPostTileEntity = (TradingPostTileEntity)tileEntity;

        ItemStack displayedItem = tradingPostTileEntity.getDisplayedItem();
        if(!displayedItem.isEmpty()) {
            tradingPostTileEntity.setDisplayedItem(ItemStack.EMPTY);

            if(player.getHeldItem(Hand.MAIN_HAND).isEmpty()) {
                player.setHeldItem(Hand.MAIN_HAND, displayedItem);
            }
            else {
                ItemUtil.giveToPlayer(player, displayedItem);
            }
        }
    }
}
