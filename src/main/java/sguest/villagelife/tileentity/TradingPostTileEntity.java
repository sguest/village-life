package sguest.villagelife.tileentity;

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TradingPostTileEntity extends TileEntity {
    private ItemStack displayedItem = ItemStack.EMPTY;
    private final static String NBT_KEY_DISPLAYED = "Displayed";

    public TradingPostTileEntity() {
        super(ModTileEntities.TRADING_POST.get());
    }

    public boolean attemptTrade(MerchantOffers offers) {
        BlockPos inventoryPos = this.pos.down();
        TileEntity inventoryEntity = this.world.getTileEntity(inventoryPos);
        if(inventoryEntity == null) {
            return false;
        }
        Optional<IItemHandler> inventoryCapability = inventoryEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).resolve();
        boolean success = false;
        if(inventoryCapability.isPresent()) {
            IItemHandler itemHandler = inventoryCapability.get();
            for(MerchantOffer offer : offers) {
                if(!success && !offer.hasNoUsesLeft()) {
                    ItemStack sellingStack = offer.getCopyOfSellingStack();
                    if(sellingStack.isItemEqual(this.getDisplayedItem()) && ItemStack.areItemStackTagsEqual(sellingStack, this.getDisplayedItem())) {
                        // Need to allow pulling buying items from multiple slots
                        ItemStack buyingStack1 = offer.getDiscountedBuyingStackFirst();
                        ItemStack buyingStack2 = offer.getBuyingStackSecond().copy();
                        Integer sellingSlot = null;
                        Integer buyingSlot1 = null;
                        Integer buyingSlot2 = null;
                        for(int slot = 0; slot < itemHandler.getSlots(); slot++) {
                            ItemStack testStack;
                            if(sellingSlot == null) {
                                testStack = itemHandler.insertItem(slot, sellingStack, true);
                                if(testStack.isEmpty()) {
                                    sellingSlot = slot;
                                }
                            }
                            if(buyingSlot1 == null) {
                                testStack = itemHandler.extractItem(slot, buyingStack1.getCount(), true);
                                if(testStack.equals(buyingStack1, false)) {
                                    buyingSlot1 = slot;
                                }
                            }
                            if(!buyingStack2.isEmpty() && buyingSlot2 == null) {
                                testStack = itemHandler.extractItem(slot, buyingStack2.getCount(), true);
                                if(testStack.equals(buyingStack2, false)) {
                                    buyingSlot2 = slot;
                                }
                            }
                        }

                        if(sellingSlot != null && buyingSlot1 != null && (buyingSlot2 != null || buyingStack2.isEmpty())) {
                            itemHandler.insertItem(sellingSlot, sellingStack, false);
                            itemHandler.extractItem(buyingSlot1, buyingStack1.getCount(), false);
                            if(!buyingStack2.isEmpty()) {
                                itemHandler.extractItem(buyingSlot2, buyingStack2.getCount(), false);
                            }
                            offer.increaseUses();
                            success = true;
                        }
                    }
                }
            }
        }
        return success;
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
