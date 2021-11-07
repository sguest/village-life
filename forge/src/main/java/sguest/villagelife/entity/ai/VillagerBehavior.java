package sguest.villagelife.entity.ai;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.FirstShuffledTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.VillagerTasks;
import net.minecraft.entity.ai.brain.task.WalkTowardsPosTask;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class VillagerBehavior {
    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof VillagerEntity) {
            VillagerEntity villager = (VillagerEntity)event.getEntity();
            VillagerProfession villagerprofession = villager.getVillagerData().getProfession();
            ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> idleTasks = VillagerTasks.idle(villagerprofession, 0.5F);
            idleTasks = ImmutableList.<Pair<Integer, ? extends Task<? super VillagerEntity>>>builder()
                .addAll(idleTasks)
                .add(
                    Pair.of(
                        3, new FirstShuffledTask<>(
                            ImmutableList.of(
                                Pair.of(new TradingPostTask(0.5f), 2),
                                Pair.of(new WalkTowardsPosTask(ModMemoryModuleType.NEAREST_TRADING_POST.get(), 0.4F, 1, 20), 5)
                            )
                        )
                    )
                ).build();
            Brain<VillagerEntity> brain = villager.getBrain();
            brain.registerActivity(Activity.IDLE, idleTasks);
        }
    }
}
