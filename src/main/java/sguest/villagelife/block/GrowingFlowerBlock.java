package sguest.villagelife.block;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class GrowingFlowerBlock extends BushBlock implements IGrowable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_2;
    private static final Map<BlockState, GrowingFlowerBlock> byFlower = new HashMap<>();
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
        Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 2.0D, 11.0D),
        Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D),
        Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D),
    };

    private BlockState flowerState;

    public GrowingFlowerBlock(BlockState flowerState, Properties properties) {
        super(properties);
        this.flowerState = flowerState;
        byFlower.put(flowerState, this);
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, 0));
    }

    public static GrowingFlowerBlock getByFlower(BlockState flower) {
        return byFlower.getOrDefault(flower, null);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vector3d vector3d = state.getOffset(worldIn, pos);
        return SHAPE_BY_AGE[state.get(AGE)].withOffset(vector3d.x, vector3d.y, vector3d.z);
    }

    @Override
    public Block.OffsetType getOffsetType() {
        return Block.OffsetType.XZ;
    }

    @Override
    public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
        int age = state.get(AGE);
        if(age >= 2) {
            world.setBlockState(pos, flowerState, 2);
        }
        else {
            world.setBlockState(pos, state.with(AGE, age + 1), 2);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightSubtracted(pos.up(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(world, pos, state,random.nextInt(5) == 0)) {
            grow(world, random, pos, state);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(world, pos, state);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
