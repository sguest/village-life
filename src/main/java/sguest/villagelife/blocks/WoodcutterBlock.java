package sguest.villagelife.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class WoodcutterBlock extends Block {
    public static final DirectionProperty directionProperty;
    protected static final VoxelShape shape;

    public WoodcutterBlock(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(directionProperty, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return shape;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(directionProperty, context.getPlacementHorizontalFacing().getOpposite());
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(new IProperty[]{directionProperty});
    }

    static {
        directionProperty = HorizontalBlock.HORIZONTAL_FACING;
        shape = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    }
}
