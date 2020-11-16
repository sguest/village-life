package sguest.villagelife.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.tileentity.TileEntity;

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
