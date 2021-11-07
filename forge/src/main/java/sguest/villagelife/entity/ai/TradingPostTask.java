package sguest.villagelife.entity.ai;

import java.util.Optional;

import com.google.common.collect.ImmutableMap;

import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import sguest.villagelife.tileentity.TradingPostTileEntity;

public class TradingPostTask extends Task<VillagerEntity> {
    private final float speed;
    private long nextTimeWindow;

    public TradingPostTask(float speed) {
        super(ImmutableMap.of(ModMemoryModuleType.NEAREST_TRADING_POST.get(), MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
        this.speed = speed;
    }

    @Override
    protected boolean shouldExecute(ServerWorld world, VillagerEntity villager) {
        Optional<GlobalPos> optional = villager.getBrain().getMemory(ModMemoryModuleType.NEAREST_TRADING_POST.get());
        return optional.isPresent() && world.getDimensionKey() == optional.get().getDimension() && optional.get().getPos().withinDistance(villager.getPositionVec(), 4);
    }

    @Override
    protected void startExecuting(ServerWorld world, VillagerEntity villager, long gameTime) {
        super.startExecuting(world, villager, gameTime);
        if (gameTime > this.nextTimeWindow) {
            Optional<Vector3d> randomPositionOptional = Optional.ofNullable(RandomPositionGenerator.getLandPos(villager, 8, 6));
            villager.getBrain().setMemory(MemoryModuleType.WALK_TARGET, randomPositionOptional.map((target) -> {
                return new WalkTarget(target, this.speed, 1);
            }));
            Optional<GlobalPos> tradingPosOptional = villager.getBrain().getMemory(ModMemoryModuleType.NEAREST_TRADING_POST.get());
            if(tradingPosOptional.isPresent()) {
                BlockPos tradingPos = tradingPosOptional.get().getPos();
                TileEntity tileEntity = world.getTileEntity(tradingPos);
                if(tileEntity != null && tileEntity instanceof TradingPostTileEntity) {
                    boolean success = ((TradingPostTileEntity)tileEntity).attemptTrade(villager.getOffers());
                    if(success) {
                        world.playSound(null, tradingPos, SoundEvents.ENTITY_VILLAGER_YES, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    }
                    else {
                        world.playSound(null, tradingPos, SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    }
                }
            }
            this.nextTimeWindow = gameTime + 180L;
        }
    }
}
