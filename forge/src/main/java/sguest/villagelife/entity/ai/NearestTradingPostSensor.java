package sguest.villagelife.entity.ai;

import java.util.ArrayList;
import java.util.List;
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
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.pathfinding.Path;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import sguest.villagelife.tileentity.TradingPostTileEntity;
import sguest.villagelife.village.ModPointOfInterestType;

public class NearestTradingPostSensor extends Sensor<MobEntity> {
    private final Long2LongMap positionToTimeMap = new Long2LongOpenHashMap();
    private int numFound;
    private long persistTime;

    public NearestTradingPostSensor() {
        super(20);
    }

    @Override
    protected void update(ServerWorld world, MobEntity entity) {
        if(!(entity instanceof VillagerEntity)) {
            return;
        }
        RegistryKey<World> dimensionKey = world.getDimensionKey();
        AbstractVillagerEntity villager = (AbstractVillagerEntity)entity;
        MerchantOffers offers = villager.getOffers();
        List<Item> soldItems = new ArrayList<>();
        for(MerchantOffer offer : offers) {
            soldItems.add(offer.getSellingStack().getItem());
        }
        this.numFound = 0;
        this.persistTime = world.getGameTime() + (long)world.getRandom().nextInt(20);
        PointOfInterestManager pointOfInterestManager = world.getPointOfInterestManager();
        Predicate<BlockPos> predicate = (pos) -> {
            long i = pos.toLong();
            if (this.positionToTimeMap.containsKey(i)) {
                return false;
            }

            TileEntity tileEntity = world.getTileEntity(pos);
            if(!(tileEntity instanceof TradingPostTileEntity)) {
                return false;
            }
            else if(!soldItems.contains(((TradingPostTileEntity)tileEntity).getDisplayedItem().getItem())) {
                return false;
            }
            else if (++this.numFound >= 5) {
                return false;
            }
            else {
                this.positionToTimeMap.put(i, this.persistTime + 40L);
                return true;
            }
        };
        Stream<BlockPos> stream = pointOfInterestManager.findAll(ModPointOfInterestType.TRADING_POST.get().getPredicate(), predicate, entity.getPosition(), 48, PointOfInterestManager.Status.ANY);
        Path path = entity.getNavigator().pathfind(stream, ModPointOfInterestType.TRADING_POST.get().getValidRange());
        if (path != null && path.reachesTarget()) {
            BlockPos blockPos = path.getTarget();
            Optional<PointOfInterestType> optional = pointOfInterestManager.getType(blockPos);
            if (optional.isPresent()) {
                entity.getBrain().setMemory(ModMemoryModuleType.NEAREST_TRADING_POST.get(), GlobalPos.getPosition(dimensionKey, blockPos));
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
