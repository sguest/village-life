package sguest.villagelife.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class TradingPostTileEntity extends TileEntity {
    private ItemStack displayedItem = ItemStack.EMPTY;
    private final static String NBT_KEY_DISPLAYED = "Displayed";

    public TradingPostTileEntity() {
        super(ModTileEntities.TRADING_POST.get());
    }

    public ItemStack getDisplayedItem() {
        return displayedItem;
    }

    public void setDisplayedItem(ItemStack stack) {
        displayedItem = stack;
        world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), Constants.BlockFlags.BLOCK_UPDATE);
        this.markDirty();
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        displayedItem = ItemStack.read(nbt.getCompound(NBT_KEY_DISPLAYED));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        super.write(nbt);
        CompoundNBT displayed = nbt.getCompound(NBT_KEY_DISPLAYED);
        displayedItem.write(displayed);
        nbt.put(NBT_KEY_DISPLAYED, displayed);
        return nbt;
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getPos(), -1, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager network, SUpdateTileEntityPacket packet) {
        super.onDataPacket(network, packet);
        handleUpdateTag(getBlockState(), packet.getNbtCompound());
    }
}
