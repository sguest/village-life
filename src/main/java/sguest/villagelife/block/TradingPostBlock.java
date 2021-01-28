package sguest.villagelife.block;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import sguest.villagelife.tileentity.TradingPostTileEntity;
import sguest.villagelife.util.CubeUtil;
import sguest.villagelife.util.ItemUtil;
import sguest.villagelife.util.TextUtil;
import sguest.villagelife.util.CubeUtil.Cube;

public class TradingPostBlock extends Block {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public static final Cube CANOPY_SHAPE = new Cube(0, 15, 0, 16, 16, 16);
    public static final Cube BASE_SHAPE = new Cube(1, 0, 1, 15, 1, 15);
    public static final Cube FRAME_BACK_SHAPE = new Cube(2, 2, 14, 14, 14, 15);
    public static final Cube[] FRAME_EDGE_SHAPES = new Cube[] {
        new Cube(2, 2, 13, 14, 3, 14),
        new Cube(2, 2, 13, 3, 14, 14),
        new Cube(2, 13, 13, 14, 14, 14),
        new Cube(13, 2, 13, 14, 14, 14),
    };
    public static final Cube[] PILLAR_SHAPES = new Cube[] {
        new Cube(1, 0, 3, 2, 15, 4),
        new Cube(14, 0, 3, 15, 15, 4),
        new Cube(1, 0, 14, 2, 15, 15),
        new Cube(14, 0, 14, 15, 15, 15),
    };

    private static final Map<Direction, VoxelShape> shapes =
        CubeUtil.getHorizontalShapes(Stream.of(
            new Cube[] { CANOPY_SHAPE },
            new Cube[] { BASE_SHAPE },
            new Cube[] { FRAME_BACK_SHAPE },
            FRAME_EDGE_SHAPES,
            PILLAR_SHAPES
        ).flatMap(Arrays::stream).toArray(Cube[]::new));

    public TradingPostBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
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

    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return shapes.get(state.get(FACING));
    }

    @Override
    public boolean isTransparent(BlockState state) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
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
    @Override
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

    @Override
    // Superclass deprecation means don't call this from outside the class. However, overriding is fine and we still want to call the super implementation
    @SuppressWarnings("deprecation")
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.isIn(newState.getBlock())) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if(tileEntity instanceof TradingPostTileEntity) {
                ItemStack displayedItem = ((TradingPostTileEntity)tileEntity).getDisplayedItem();
                if(!displayedItem.isEmpty()) {
                    InventoryHelper.spawnItemStack(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), displayedItem);
                }
            }

            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, IBlockReader world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(itemStack, world, list, flag);
        list.add(TextUtil.styledTranslation("block.villagelife.trading_post.tooltip", TextFormatting.DARK_GRAY));
    }
}
