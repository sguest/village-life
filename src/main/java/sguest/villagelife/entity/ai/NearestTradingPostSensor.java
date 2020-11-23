package sguest.villagelife.entity.ai;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;

import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;
import sguest.villagelife.village.ModPointOfInterestType;

public class NearestTradingPostSensor extends Sensor<MobEntity> {
    private final Long2LongMap positionToTimeMap = new Long2LongOpenHashMap();
    private int numFound;
    private long persistTime;

    public NearestTradingPostSensor() {
        super(20);
    }

    @Override
    protected void update(ServerWorld worldIn, MobEntity entityIn) {
        this.numFound = 0;
        this.persistTime = worldIn.getGameTime() + (long)worldIn.getRandom().nextInt(20);
        PointOfInterestManager pointOfInterestManager = worldIn.getPointOfInterestManager();
        Predicate<BlockPos> predicate = (pos) -> {
            long i = pos.toLong();
            if (this.positionToTimeMap.containsKey(i)) {
                return false;
            } else if (++this.numFound >= 5) {
                return false;
            } else {
                this.positionToTimeMap.put(i, this.persistTime + 40L);
                return true;
            }
        };
        Stream<BlockPos> stream = pointOfInterestManager.findAll(ModPointOfInterestType.TRADING_POST.get().getPredicate(), predicate, entityIn.getPosition(), 48, PointOfInterestManager.Status.ANY);
        Path path = entityIn.getNavigator().pathfind(stream, ModPointOfInterestType.TRADING_POST.get().getValidRange());
        if (path != null && path.reachesTarget()) {
            BlockPos blockpos = path.getTarget();
            Optional<PointOfInterestType> optional = pointOfInterestManager.getType(blockpos);
            if (optional.isPresent()) {
                entityIn.getBrain().setMemory(ModMemoryModuleType.NEAREST_TRADING_POST.get(), blockpos);
            }
        } else if (this.numFound < 5) {
            this.positionToTimeMap.long2LongEntrySet().removeIf((locatedTime) -> {
                return locatedTime.getLongValue() < this.persistTime;
            });
        }
    }

    @Override
    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(ModMemoryModuleType.NEAREST_TRADING_POST.get());
    }
}
