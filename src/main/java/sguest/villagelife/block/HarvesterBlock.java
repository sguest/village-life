package sguest.villagelife.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;
import sguest.villagelife.tags.ModTags;

public class HarvesterBlock extends Block {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public HarvesterBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(TRIGGERED, Boolean.valueOf(false)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, TRIGGERED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        boolean isPowered = world.isBlockPowered(pos) || world.isBlockPowered(pos.up());
        boolean isTriggered = state.get(TRIGGERED);
        if (isPowered && !isTriggered) {
            world.getPendingBlockTicks().scheduleTick(pos, this, 4);
            world.setBlockState(pos, state.with(TRIGGERED, Boolean.valueOf(true)), 2);
        } else if (!isPowered && isTriggered) {
            world.setBlockState(pos, state.with(TRIGGERED, Boolean.valueOf(false)), 2);
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        BlockPos targetPos = pos.offset(state.get(FACING));
        BlockState targetState = world.getBlockState(targetPos);
        Block targetBlock = targetState.getBlock();
        if(ModTags.Blocks.HARVESTER_TARGETS.contains(targetBlock)) {
            IntegerProperty ageProperty = null;
            for(Property<?> prop : targetState.getProperties()) {
                if(prop instanceof IntegerProperty && prop.getName() == "age") {
                    ageProperty = (IntegerProperty)prop;
                }
            }
            
            if(ageProperty != null && targetState.get(ageProperty) > 0) {
                BlockRayTraceResult raytrace =
                    new BlockRayTraceResult(new Vector3d(targetPos.getX(), targetPos.getY(), targetPos.getZ()), state.get(FACING), pos, false);
                ItemStack seedStack = targetBlock.getPickBlock(state, raytrace, world, targetPos, FakePlayerFactory.getMinecraft(world));
                List<ItemStack> drops = Block.getDrops(targetState, world, targetPos, null);

                boolean canHarvest = false;
                boolean foundSeed = false;
                for(ItemStack drop : drops) {
                    if(!foundSeed &&  drop.isItemEqual(seedStack)) {
                        drop.shrink(1);
                        foundSeed = true;
                    }
                    if(!drop.isEmpty()) {
                        canHarvest = true;
                    }
                }

                if(canHarvest) {
                    world.setBlockState(targetPos, targetState.with(ageProperty, 0));
                    for(ItemStack drop : drops) {
                        InventoryHelper.spawnItemStack(world, targetPos.getX(), targetPos.getY(), targetPos.getZ(), drop);
                    }
                    world.playSound(null, targetPos, targetState.getSoundType().getBreakSound(), SoundCategory.BLOCKS, 1.5F, 1.0F);
                    world.spawnParticle(new BlockParticleData(ParticleTypes.BLOCK, targetState), targetPos.getX(), targetPos.getY(), targetPos.getZ(), 10, 0, 0.5, 0, 0.4);
                }
            }
        }
    }
}