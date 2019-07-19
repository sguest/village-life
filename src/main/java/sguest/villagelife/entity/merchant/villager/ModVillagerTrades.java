package sguest.villagelife.entity.merchant.villager;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;

import java.util.Random;

public class ModVillagerTrades {
    public static void initTrades() {
        register(ModProfessions.CARPENTER, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.OAK_LOG, 12, 8, 2)}, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.ACACIA_WOOD, 10, 8, 4)});
    }

    private static void register(VillagerProfession profession, VillagerTrades.ITrade[] noviceTrades, VillagerTrades.ITrade[] apprenticeTrades) {
        VillagerTrades.field_221239_a.put(profession, tradeMapper(ImmutableMap.of(1, noviceTrades, 2, apprenticeTrades)));
    }

    private static Int2ObjectMap<VillagerTrades.ITrade[]> tradeMapper(ImmutableMap<Integer, VillagerTrades.ITrade[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap(p_221238_0_);
    }

    static class EmeraldForItemsTrade implements VillagerTrades.ITrade {
        private final Item purchasedItem;
        private final int defaultQuantity;
        private final int maxUses;
        private final int givenExp;
        private final float priceMultiplier;

        public EmeraldForItemsTrade(IItemProvider purchasedItem, int defaultQuantity, int maxUses, int givenExp) {
            this.purchasedItem = purchasedItem.asItem();
            this.defaultQuantity = defaultQuantity;
            this.maxUses = maxUses;
            this.givenExp = givenExp;
            this.priceMultiplier = 0.05F;
        }

        public MerchantOffer func_221182_a(Entity entity, Random random) {
            ItemStack itemstack = new ItemStack(this.purchasedItem, this.defaultQuantity);
            return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), this.maxUses, this.givenExp, this.priceMultiplier);
        }
    }
}
