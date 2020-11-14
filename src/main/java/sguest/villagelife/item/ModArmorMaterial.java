package sguest.villagelife.item;

import java.util.function.Supplier;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sguest.villagelife.VillageLife;
import sguest.villagelife.util.TagUtil;

public class ModArmorMaterial implements IArmorMaterial {
    public static IArmorMaterial EMERALD;

    public static void register() {
        EMERALD = new ModArmorMaterial("emerald", 25, new int[]{2, 5, 6, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, () -> Ingredient.fromTag(TagUtil.getItemTag(new ResourceLocation("forge", "gems/emerald"))), 1.0F, 0.0F);
    }

    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final LazyValue<Ingredient> repairMaterial;
    private final float toughness;
    private final float knockbackResistance;

    public ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, Supplier<Ingredient> repairMaterial, float toughness, float knockbackResistance) {
        this.name = VillageLife.MOD_ID + ":" + name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.repairMaterial = new LazyValue<>(repairMaterial);
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
    }

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * maxDamageFactor;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return damageReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return repairMaterial.getValue();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }
}
