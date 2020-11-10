package sguest.villagelife.entity.merchant.villager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import sguest.villagelife.VillageLife;

@Mod.EventBusSubscriber(modid = VillageLife.MOD_ID, bus = Bus.FORGE)
public class ModTrades {
    @SubscribeEvent
    public static void registerTrades(VillagerTradesEvent event)
    {
        Int2ObjectMap<List<ITrade>> trades = event.getTrades();
        ResourceLocation professionName = event.getType().getRegistryName();

        if(professionName == ModProfessions.CARPENTER.get().getRegistryName()) {
            Map<VillagerType, ItemStack> saplings = new HashMap<>();
            saplings.put(VillagerType.PLAINS, new ItemStack(Items.OAK_SAPLING, 8));
            saplings.put(VillagerType.DESERT, new ItemStack(Items.JUNGLE_SAPLING, 8));
            saplings.put(VillagerType.SAVANNA, new ItemStack(Items.ACACIA_SAPLING, 8));
            saplings.put(VillagerType.SNOW, new ItemStack(Items.SPRUCE_SAPLING, 8));
            saplings.put(VillagerType.TAIGA, new ItemStack(Items.SPRUCE_SAPLING, 8));
            saplings.put(VillagerType.SWAMP, new ItemStack(Items.DARK_OAK_SAPLING, 8));
            saplings.put(VillagerType.JUNGLE, new ItemStack(Items.JUNGLE_SAPLING, 8));
            trades.get(1).add(biomeBuying(saplings, 1, 16, 2, false));
            trades.get(1).add(simpleBuying(new ItemStack(Items.STICK, 32), 1, 16, 2, false));

            Map<VillagerType, ItemStack> planks = new HashMap<>();
            planks.put(VillagerType.PLAINS, new ItemStack(Items.OAK_PLANKS, 12));
            planks.put(VillagerType.DESERT, new ItemStack(Items.JUNGLE_PLANKS, 12));
            planks.put(VillagerType.SAVANNA, new ItemStack(Items.ACACIA_PLANKS, 12));
            planks.put(VillagerType.SNOW, new ItemStack(Items.SPRUCE_PLANKS, 12));
            planks.put(VillagerType.TAIGA, new ItemStack(Items.SPRUCE_PLANKS, 12));
            planks.put(VillagerType.SWAMP, new ItemStack(Items.DARK_OAK_PLANKS, 12));
            planks.put(VillagerType.JUNGLE, new ItemStack(Items.JUNGLE_PLANKS, 12));
            trades.get(2).add(biomeSelling(planks, 1, 15, 5, false));
            Map<VillagerType, ItemStack> logs = new HashMap<>();
            logs.put(VillagerType.PLAINS, new ItemStack(Items.OAK_LOG, 4));
            logs.put(VillagerType.DESERT, new ItemStack(Items.JUNGLE_LOG, 4));
            logs.put(VillagerType.SAVANNA, new ItemStack(Items.ACACIA_LOG, 4));
            logs.put(VillagerType.SNOW, new ItemStack(Items.SPRUCE_LOG, 4));
            logs.put(VillagerType.TAIGA, new ItemStack(Items.SPRUCE_LOG, 4));
            logs.put(VillagerType.SWAMP, new ItemStack(Items.DARK_OAK_LOG, 4));
            logs.put(VillagerType.JUNGLE, new ItemStack(Items.JUNGLE_LOG, 4));
            trades.get(2).add(biomeBuying(logs, 1, 16, 5, false));

            Map<VillagerType, ItemStack> leaves = new HashMap<>();
            leaves.put(VillagerType.PLAINS, new ItemStack(Items.OAK_LEAVES, 14));
            leaves.put(VillagerType.DESERT, new ItemStack(Items.JUNGLE_LEAVES, 14));
            leaves.put(VillagerType.SAVANNA, new ItemStack(Items.ACACIA_LEAVES, 14));
            leaves.put(VillagerType.SNOW, new ItemStack(Items.SPRUCE_LEAVES, 14));
            leaves.put(VillagerType.TAIGA, new ItemStack(Items.SPRUCE_LEAVES, 14));
            leaves.put(VillagerType.SWAMP, new ItemStack(Items.DARK_OAK_LEAVES, 14));
            leaves.put(VillagerType.JUNGLE, new ItemStack(Items.JUNGLE_LEAVES, 14));
            trades.get(3).add(biomeSelling(leaves, 1, 15, 10, false));
            Map<VillagerType, ItemStack> doors = new HashMap<>();
            doors.put(VillagerType.PLAINS, new ItemStack(Items.OAK_DOOR, 1));
            doors.put(VillagerType.DESERT, new ItemStack(Items.JUNGLE_DOOR, 1));
            doors.put(VillagerType.SAVANNA, new ItemStack(Items.ACACIA_DOOR, 1));
            doors.put(VillagerType.SNOW, new ItemStack(Items.SPRUCE_DOOR, 1));
            doors.put(VillagerType.TAIGA, new ItemStack(Items.SPRUCE_DOOR, 1));
            doors.put(VillagerType.SWAMP, new ItemStack(Items.DARK_OAK_DOOR, 1));
            doors.put(VillagerType.JUNGLE, new ItemStack(Items.JUNGLE_DOOR, 1));
            trades.get(3).add(biomeSelling(doors, 1, 12, 15, false));

            Map<VillagerType, ItemStack> strippedLogs = new HashMap<>();
            strippedLogs.put(VillagerType.PLAINS, new ItemStack(Items.STRIPPED_OAK_LOG, 3));
            strippedLogs.put(VillagerType.DESERT, new ItemStack(Items.STRIPPED_JUNGLE_LOG, 3));
            strippedLogs.put(VillagerType.SAVANNA, new ItemStack(Items.STRIPPED_ACACIA_LOG, 3));
            strippedLogs.put(VillagerType.SNOW, new ItemStack(Items.STRIPPED_SPRUCE_LOG, 3));
            strippedLogs.put(VillagerType.TAIGA, new ItemStack(Items.STRIPPED_SPRUCE_LOG, 3));
            strippedLogs.put(VillagerType.SWAMP, new ItemStack(Items.STRIPPED_DARK_OAK_LOG, 3));
            strippedLogs.put(VillagerType.JUNGLE, new ItemStack(Items.STRIPPED_JUNGLE_LOG, 3));
            trades.get(4).add(biomeSelling(strippedLogs, 1, 12, 15, false));
            trades.get(4).add(simpleSelling(new ItemStack(Items.ARMOR_STAND, 2), 1, 12, 20, false));

            trades.get(5).add(simpleSelling(new ItemStack(Items.CRIMSON_STEM, 2), 1, 12, 30, false));
            trades.get(5).add(simpleSelling(new ItemStack(Items.WARPED_STEM, 2), 1, 12, 30, false));
        }
    }

    private static VillagerTrade simpleSelling(ItemStack soldItem, int emeraldPrice, int numTrades, int xpValue, boolean highMultiplier) {
        float priceMultiplier = 0.05F;
        if(highMultiplier) {
            priceMultiplier = 0.2F;
        }
        return new VillagerTrade(soldItem, new ItemStack(Items.EMERALD, emeraldPrice), ItemStack.EMPTY, numTrades, xpValue, priceMultiplier);
    }

    private static VillagerTrade simpleBuying(ItemStack boughtItem, int emeraldPrice, int numTrades, int xpValue, boolean highMultiplier) {
        float priceMultiplier = 0.05F;
        if(highMultiplier) {
            priceMultiplier = 0.2F;
        }
        return new VillagerTrade(new ItemStack(Items.EMERALD, emeraldPrice), boughtItem, ItemStack.EMPTY, numTrades, xpValue, priceMultiplier);
    }

    private static BiomeTrade biomeSelling(Map<VillagerType, ItemStack> soldItems, int emeraldPrice, int numTrades, int xpValue, boolean highMultiplier) {
        float priceMultiplier = 0.05F;
        if(highMultiplier) {
            priceMultiplier = 0.2F;
        }
        Map<VillagerType, ItemStack> emeraldMap = new HashMap<>();
        emeraldMap.put(VillagerType.PLAINS, new ItemStack(Items.EMERALD, emeraldPrice));
        return new BiomeTrade(soldItems, emeraldMap, new HashMap<VillagerType, ItemStack>(), numTrades, xpValue, priceMultiplier);
    }

    private static BiomeTrade biomeBuying(Map<VillagerType, ItemStack> boughtItems, int emeraldPrice, int numTrades, int xpValue, boolean highMultiplier) {
        float priceMultiplier = 0.05F;
        if(highMultiplier) {
            priceMultiplier = 0.2F;
        }
        Map<VillagerType, ItemStack> emeraldMap = new HashMap<>();
        emeraldMap.put(VillagerType.PLAINS, new ItemStack(Items.EMERALD, emeraldPrice));
        return new BiomeTrade(emeraldMap, boughtItems, new HashMap<VillagerType, ItemStack>(), numTrades, xpValue, priceMultiplier);
    }

    private static class VillagerTrade implements ITrade {
        private final ItemStack sellingItem;
        private final ItemStack buyingItem1;
        private final ItemStack buyingItem2;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public VillagerTrade(ItemStack sellingItem, ItemStack buyingItem1, ItemStack buyingItem2, int maxUses, int xpValue, float priceMultiplier) {
            this.sellingItem = sellingItem;
            this.buyingItem1 = buyingItem1;
            this.buyingItem2 = buyingItem2;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = priceMultiplier;
        }

        @Override
        public MerchantOffer getOffer(Entity trader, Random rand) {
            return new MerchantOffer(buyingItem1, buyingItem2, sellingItem, maxUses, xpValue, priceMultiplier);
        }
    }

    private static class BiomeTrade implements ITrade {
        private final Map<VillagerType, ItemStack> sellingMap;
        private final Map<VillagerType, ItemStack> buyingMap1;
        private final Map<VillagerType, ItemStack> buyingMap2;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public BiomeTrade(Map<VillagerType, ItemStack> sellingMap, Map<VillagerType, ItemStack> buyingMap1, Map<VillagerType, ItemStack> buyingMap2, int maxUses, int xpValue, float priceMultiplier) {
            this.sellingMap = sellingMap;
            this.buyingMap1 = buyingMap1;
            this.buyingMap2 = buyingMap2;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = priceMultiplier;
        }

        private static ItemStack getItem(Map<VillagerType, ItemStack> map, VillagerType type) {
            ItemStack stack = map.get(type);
            if(stack == null) {
                stack = map.getOrDefault(VillagerType.PLAINS, ItemStack.EMPTY);
            }
            return stack;
        }

        @Override
        public MerchantOffer getOffer(Entity trader, Random rand) {
            VillagerType villagerType = VillagerType.PLAINS;
            if(trader instanceof VillagerEntity) {
                villagerType = ((VillagerEntity)trader).getVillagerData().getType();
            }
            ItemStack sellingItem = getItem(sellingMap, villagerType);
            ItemStack buyingItem1 = getItem(buyingMap1, villagerType);
            ItemStack buyingItem2 = getItem(buyingMap2, villagerType);
            return new MerchantOffer(buyingItem1, buyingItem2, sellingItem, maxUses, xpValue, priceMultiplier);
        }
    }
}
