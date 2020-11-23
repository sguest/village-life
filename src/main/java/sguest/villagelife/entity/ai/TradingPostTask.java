package sguest.villagelife.entity.ai;

import java.util.Optional;

import com.google.common.collect.ImmutableMap;

import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import sguest.villagelife.block.ModBlocks;
import sguest.villagelife.util.ModLog;

public class TradingPostTask extends Task<VillagerEntity> {
    private final float speed;
    private BlockPos tradingPostPos;
    private int duration;

    public TradingPostTask(float speed) {
        super(ImmutableMap.of(ModMemoryModuleType.NEAREST_TRADING_POST.get(), MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
        this.speed = speed;
    }

    @Override
    protected boolean shouldExecute(ServerWorld world, VillagerEntity villager) {
        ModLog.info("Trading Post Task shouldExecute");
        ModLog.info(world);
        ModLog.info(villager);
        if(this.getTradingPost(villager).isPresent()) {
            ModLog.info("getTradingPost isPresent");
            return true;
        }
        BlockPos villagerPos = villager.getPosition();
        return isTradingPost(world, villagerPos.east())
            || isTradingPost(world, villagerPos.west())
            || isTradingPost(world, villagerPos.north())
            || isTradingPost(world, villagerPos.south());
    }

    @Override
    protected void startExecuting(ServerWorld world, VillagerEntity villager, long gameTime) {
        super.startExecuting(world, villager, gameTime);
        ModLog.info("Trading Post Task startExecuting");
        ModLog.info(world);
        ModLog.info(villager);
        this.getTradingPost(villager).ifPresent((blockPos) -> {
            this.tradingPostPos = blockPos;
            this.setWalkTarget(villager, blockPos);
            this.duration = 100;
        });
    }

    @Override
    protected void resetTask(ServerWorld world, VillagerEntity villager, long gameTime) {
        super.resetTask(world, villager, gameTime);
        ModLog.info("Trading Post Task resetTask");
        ModLog.info(world);
        ModLog.info(villager);
        this.tradingPostPos = null;
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld world, VillagerEntity villager, long gameTime) {
        ModLog.info("Trading Post Task shouldContinueExecuting");
        ModLog.info(world);
        ModLog.info(villager);
        return this.tradingPostPos != null && this.isTradingPost(world, this.tradingPostPos) && duration >= 0;
    }

    @Override
    protected boolean isTimedOut(long gameTime) {
        return false;
    }

    @Override
    protected void updateTask(ServerWorld world, VillagerEntity villager, long gameTime) {
        ModLog.info("Updating Trading Post Task");
        ModLog.info(world);
        ModLog.info(villager);
        this.duration --;
        ModLog.info(duration);
    }

    private void setWalkTarget(VillagerEntity villager, BlockPos pos) {
        villager.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(pos, this.speed, 0));
    }

    private boolean isTradingPost(ServerWorld world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == ModBlocks.TRADING_POST.get();
    }

    private Optional<BlockPos> getTradingPost(VillagerEntity villager) {
        return villager.getBrain().getMemory(ModMemoryModuleType.NEAREST_TRADING_POST.get());
    }
}
