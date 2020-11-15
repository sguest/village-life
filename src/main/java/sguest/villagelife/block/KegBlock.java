package sguest.villagelife.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import sguest.villagelife.tileentity.KegTileEntity;
import sguest.villagelife.util.ItemUtil;

public class KegBlock extends Block {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    protected static final VoxelShape NS_SHAPE = VoxelShapes.or(
        Block.makeCuboidShape(1.0D, 1.0D, 0.0D, 15.0D, 15.0D, 16.0D),
        Block.makeCuboidShape(0.0D, 0.0D, 1.0D, 3.0D, 3.0D, 3.0D),
        Block.makeCuboidShape(13.0D, 0.0D, 1.0D, 16.0D, 3.0D, 3.0D),
        Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 3.0D, 3.0D, 15.0D),
        Block.makeCuboidShape(13.0D, 0.0D, 13.0D, 16.0D, 3.0D, 15.0D)
    );
    protected static final VoxelShape EW_SHAPE = VoxelShapes.or(
        Block.makeCuboidShape(0.0D, 1.0D, 1.0D, 16.0D, 15.0D, 15.0D),
        Block.makeCuboidShape(1.0D, 0.0D, 0.0D, 3.0D, 3.0D, 3.0D),
        Block.makeCuboidShape(1.0D, 0.0D, 13.0D, 3.0D, 3.0D, 16.0D),
        Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 15.0D, 3.0D, 3.0D),
        Block.makeCuboidShape(13.0D, 0.0D, 13.0D, 15.0D, 3.0D, 16.0D)
    );

    public KegBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new KegTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        Direction facing = state.get(FACING);
        if(facing == Direction.NORTH || facing == Direction.SOUTH) {
            return NS_SHAPE;
        }
        return EW_SHAPE;
    }

    public boolean isTransparent(BlockState state) {
        return true;
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    /**
    * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever possible. Implementing/overriding is fine.
    */
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
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
        if(!(tileEntity instanceof KegTileEntity)) {
            return ActionResultType.PASS;
        }

        KegTileEntity kegTileEntity = (KegTileEntity)tileEntity;
        ItemStack heldStack = player.getHeldItem(hand);
        Item heldItem = heldStack.getItem();

        ItemStack resultItem = ItemStack.EMPTY;

        if(heldItem == Items.BUCKET) {
            switch (kegTileEntity.getFluidType()) {
                case MILK:
                    if(kegTileEntity.removeFluid(3)) {
                        resultItem = new ItemStack(Items.MILK_BUCKET);
                    }
                    break;
                case WATER:
                    if(kegTileEntity.removeFluid(3)) {
                        resultItem = new ItemStack(Items.WATER_BUCKET);
                    }
                    break;
                default:
                    break;
            }
        }
        else if(heldItem == Items.GLASS_BOTTLE) {
            switch(kegTileEntity.getFluidType()) {
                case WATER:
                    if(kegTileEntity.removeFluid(1)) {
                        resultItem = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER);
                    }
                    break;
                case HONEY:
                    if(kegTileEntity.removeFluid(1)) {
                        resultItem = new ItemStack(Items.HONEY_BOTTLE);
                    }
                    break;
                case POTION:
                    Potion potion = kegTileEntity.getPotionType();
                    if(kegTileEntity.removeFluid(1)) {
                        resultItem = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), potion);
                    }
                    break;
                default:
                    break;
            }
        }
        else if(heldItem == Items.WATER_BUCKET) {
            if(kegTileEntity.addWater(3)) {
                resultItem = new ItemStack(Items.BUCKET);
            }
        }
        else if(heldItem == Items.MILK_BUCKET) {
            if(kegTileEntity.addMilk(3)) {
                resultItem = new ItemStack(Items.BUCKET);
            }
        }
        else if(heldItem == Items.POTION || heldItem == Items.SPLASH_POTION || heldItem == Items.LINGERING_POTION) {
            Potion potion = PotionUtils.getPotionFromItem(heldStack);
            if(potion == Potions.WATER) {
                if(kegTileEntity.addWater(1)) {
                    resultItem = new ItemStack(Items.GLASS_BOTTLE);
                }
            }
            else {
                if(kegTileEntity.addPotion(1, potion)) {
                    resultItem = new ItemStack(Items.GLASS_BOTTLE);
                }
            }
        }
        else if(heldItem == Items.HONEY_BOTTLE) {
            if(kegTileEntity.addHoney(1)) {
                resultItem = new ItemStack(Items.GLASS_BOTTLE);
            }
        }

        if(resultItem != ItemStack.EMPTY) {
            heldStack.shrink(1);
            if(heldStack.isEmpty()) {
                player.setHeldItem(hand, resultItem);
            }
            else {
                ItemUtil.giveToPlayer(player, resultItem);
            }
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }
}
