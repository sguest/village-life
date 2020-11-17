package sguest.villagelife.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tileentity.TileEntity;
import sguest.villagelife.item.ModItems;

public class KegTileEntity extends TileEntity {
    public static enum FluidType {
        EMPTY,
        WATER,
        POTION,
        MILK,
        HONEY,
    }

    public static final int CAPACITY = 30;

    private FluidType fluidType = FluidType.EMPTY;
    private int fluidLevel = 0;
    private Potion potionType = Potions.EMPTY;

    public KegTileEntity() {
        super(ModTileEntities.KEG.get());
    }

    public int getFluidLevel() {
        return fluidLevel;
    }

    public FluidType getFluidType() {
        return fluidType;
    }

    public Potion getPotionType() {
        return potionType;
    }

    public boolean addWater(int amount) {
        return addFluid(amount, FluidType.WATER);
    }


    public boolean addMilk(int amount) {
        return addFluid(amount, FluidType.MILK);
    }

    public boolean addHoney(int amount) {
        return addFluid(amount, FluidType.HONEY);
    }

    public boolean addPotion(int amount, Potion potionTypeIn) {
        if(fluidType == FluidType.POTION && this.potionType != potionTypeIn) {
            return false;
        }

        if(addFluid(amount, FluidType.POTION)) {
            this.potionType = potionTypeIn;
            return true;
        }

        return false;
    }

    private boolean addFluid(int amount, FluidType fluidTypeIn) {
        if(this.fluidType != fluidTypeIn && this.fluidType != FluidType.EMPTY) {
            return false;
        }

        if(fluidLevel == CAPACITY) {
            return false;
        }

        this.fluidType = fluidTypeIn;
        fluidLevel = Math.min(fluidLevel + amount, CAPACITY);
        markDirty();
        return true;
    }

    public boolean removeFluid(int amount) {
        if(fluidLevel < amount) {
            return false;
        }

        fluidLevel -= amount;
        if(fluidLevel == 0) {
            fluidType = FluidType.EMPTY;
            potionType = Potions.EMPTY;
        }
        markDirty();
        return true;
    }

    public ItemStack useItem(ItemStack stack) {
        Item item = stack.getItem();
        if(item == Items.BUCKET) {
            switch (fluidType) {
                case MILK:
                    if(removeFluid(3)) {
                        return new ItemStack(Items.MILK_BUCKET);
                    }
                    break;
                case WATER:
                    if(removeFluid(3)) {
                        return new ItemStack(Items.WATER_BUCKET);
                    }
                    break;
                default:
                    break;
            }
        }
        else if(item == Items.GLASS_BOTTLE) {
            switch(fluidType) {
                case WATER:
                    if(removeFluid(1)) {
                        return PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER);
                    }
                    break;
                case HONEY:
                    if(removeFluid(1)) {
                        return new ItemStack(Items.HONEY_BOTTLE);
                    }
                    break;
                case MILK:
                    if(removeFluid(1)) {
                        return new ItemStack(ModItems.MILK_BOTTLE.get());
                    }
                    break;
                case POTION:
                    if(removeFluid(1)) {
                        return PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), potionType);
                    }
                    break;
                default:
                    break;
            }
        }
        else if(item == Items.WATER_BUCKET) {
            if(addWater(3)) {
                return new ItemStack(Items.BUCKET);
            }
        }
        else if(item == Items.MILK_BUCKET) {
            if(addMilk(3)) {
                return new ItemStack(Items.BUCKET);
            }
        }
        else if(item == Items.POTION || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION) {
            Potion potion = PotionUtils.getPotionFromItem(stack);
            if(potion == Potions.WATER) {
                if(addWater(1)) {
                    return new ItemStack(Items.GLASS_BOTTLE);
                }
            }
            else {
                if(addPotion(1, potion)) {
                    return new ItemStack(Items.GLASS_BOTTLE);
                }
            }
        }
        else if(item == Items.HONEY_BOTTLE) {
            if(addHoney(1)) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }
        }
        else if(item == ModItems.MILK_BOTTLE.get()) {
            if(addMilk(1)) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }
        }
        return ItemStack.EMPTY;
    }

    public void readOwnData(CompoundNBT nbt) {
        CompoundNBT kegData = nbt.getCompound("Contents");

        try {
            fluidType = FluidType.valueOf(kegData.getString("Type"));
        }
        catch(IllegalArgumentException ex) {
            fluidType = FluidType.EMPTY;
        }

        if(fluidType == FluidType.EMPTY) {
            fluidLevel = 0;
        }
        else {
            fluidLevel = kegData.getInt("Level");
        }

        if(fluidType == FluidType.POTION) {
            potionType = Potion.getPotionTypeForName(kegData.getString("Potion"));
        }
    }

    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        readOwnData(nbt);
    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        CompoundNBT kegData = new CompoundNBT();

        kegData.putString("Type", fluidType.name());
        kegData.putInt("Level", fluidLevel);

        if(fluidType == FluidType.POTION) {
            kegData.putString("Potion", potionType.getRegistryName().toString());
        }

        compound.put("Contents", kegData);

        return compound;
    }
}
