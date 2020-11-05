package sguest.villagelife.entity.merchant.villager;

import java.util.List;
import java.util.Random;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
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
            trades.get(1).add(simpleBuying(new ItemStack(Items.OAK_SAPLING, 15), 1, 16, 2, false));
            trades.get(1).add(simpleBuying(new ItemStack(Items.BIRCH_SAPLING, 15), 1, 16, 2, false));
            trades.get(1).add(simpleBuying(new ItemStack(Items.JUNGLE_SAPLING, 15), 1, 16, 2, false));
            trades.get(1).add(simpleBuying(new ItemStack(Items.SPRUCE_SAPLING, 15), 1, 16, 2, false));
            trades.get(1).add(simpleBuying(new ItemStack(Items.ACACIA_SAPLING, 15), 1, 16, 2, false));
            trades.get(1).add(simpleBuying(new ItemStack(Items.DARK_OAK_SAPLING, 15), 1, 16, 2, false));
            trades.get(1).add(simpleBuying(new ItemStack(Items.STICK, 32), 1, 16, 2, false));

            trades.get(2).add(simpleSelling(new ItemStack(Items.OAK_PLANKS, 12), 1, 15, 5, false));
            trades.get(2).add(simpleSelling(new ItemStack(Items.BIRCH_PLANKS, 12), 1, 15, 5, false));
            trades.get(2).add(simpleSelling(new ItemStack(Items.JUNGLE_PLANKS, 12), 1, 15, 5, false));
            trades.get(2).add(simpleSelling(new ItemStack(Items.SPRUCE_PLANKS, 12), 1, 15, 5, false));
            trades.get(2).add(simpleSelling(new ItemStack(Items.ACACIA_PLANKS, 12), 1, 15, 5, false));
            trades.get(2).add(simpleSelling(new ItemStack(Items.DARK_OAK_PLANKS, 12), 1, 15, 5, false));
            trades.get(2).add(simpleBuying(new ItemStack(Items.OAK_LOG, 4), 1, 16, 5, false));
            trades.get(2).add(simpleBuying(new ItemStack(Items.BIRCH_LOG, 4), 1, 16, 5, false));
            trades.get(2).add(simpleBuying(new ItemStack(Items.JUNGLE_LOG, 4), 1, 16, 5, false));
            trades.get(2).add(simpleBuying(new ItemStack(Items.SPRUCE_LOG, 4), 1, 16, 5, false));
            trades.get(2).add(simpleBuying(new ItemStack(Items.ACACIA_LOG, 4), 1, 16, 5, false));
            trades.get(2).add(simpleBuying(new ItemStack(Items.DARK_OAK_LOG, 4), 1, 16, 5, false));

            trades.get(3).add(simpleSelling(new ItemStack(Items.OAK_LEAVES, 12), 1, 15, 10, false));
            trades.get(3).add(simpleSelling(new ItemStack(Items.BIRCH_LEAVES, 12), 1, 15, 10, false));
            trades.get(3).add(simpleSelling(new ItemStack(Items.JUNGLE_LEAVES, 12), 1, 15, 10, false));
            trades.get(3).add(simpleSelling(new ItemStack(Items.SPRUCE_LEAVES, 12), 1, 15, 10, false));
            trades.get(3).add(simpleSelling(new ItemStack(Items.ACACIA_LEAVES, 12), 1, 15, 10, false));
            trades.get(3).add(simpleSelling(new ItemStack(Items.DARK_OAK_LEAVES, 12), 1, 15, 10, false));

            trades.get(4).add(simpleSelling(new ItemStack(Items.OAK_DOOR, 1), 1, 12, 15, false));
            trades.get(4).add(simpleSelling(new ItemStack(Items.BIRCH_DOOR, 1), 1, 12, 15, false));
            trades.get(4).add(simpleSelling(new ItemStack(Items.JUNGLE_DOOR, 1), 1, 12, 15, false));
            trades.get(4).add(simpleSelling(new ItemStack(Items.SPRUCE_DOOR, 1), 1, 12, 15, false));
            trades.get(4).add(simpleSelling(new ItemStack(Items.ACACIA_DOOR, 1), 1, 12, 15, false));
            trades.get(4).add(simpleSelling(new ItemStack(Items.DARK_OAK_DOOR, 1), 1, 12, 15, false));
            trades.get(4).add(simpleSelling(new ItemStack(Items.ARMOR_STAND, 1), 2, 12, 20, false));

            trades.get(5).add(simpleSelling(new ItemStack(Items.CRIMSON_PLANKS, 6), 1, 12, 30, false));
            trades.get(5).add(simpleSelling(new ItemStack(Items.WARPED_PLANKS, 6), 1, 12, 30, false));
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
}
