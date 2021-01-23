package sguest.villagelife.tileentity;

import java.util.ArrayList;
import java.util.List;
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
                        ItemStack buyingStack1 = offer.getDiscountedBuyingStackFirst();
                        ItemStack buyingStack2 = offer.getBuyingStackSecond().copy();
                        List<Integer> sellingSlots = new ArrayList<>();
                        List<Integer> buyingSlots1 = new ArrayList<>();
                        List<Integer> buyingSlots2 = new ArrayList<>();
                        ItemStack testStack;
                        for(int slot = 0; slot < itemHandler.getSlots(); slot++) {
                            if(!sellingStack.isEmpty()) {
                                sellingStack = itemHandler.insertItem(slot, sellingStack, true);
                                sellingSlots.add(slot);
                            }
                            if(!buyingStack1.isEmpty()) {
                                testStack = itemHandler.extractItem(slot, buyingStack1.getCount(), true);
                                if(testStack.isItemEqual(buyingStack1) && ItemStack.areItemStackTagsEqual(testStack, buyingStack1)) {
                                    buyingStack1.shrink(testStack.getCount());
                                    buyingSlots1.add(slot);
                                }
                            }
                            if(!buyingStack2.isEmpty()) {
                                testStack = itemHandler.extractItem(slot, buyingStack2.getCount(), true);
                                if(testStack.isItemEqual(buyingStack2) && ItemStack.areItemStackTagsEqual(testStack, buyingStack2)) {
                                    buyingStack2.shrink(testStack.getCount());
                                    buyingSlots2.add(slot);
                                }
                            }
                        }

                        if(sellingStack.isEmpty() && buyingStack1.isEmpty() && buyingStack2.isEmpty()) {
                            sellingStack = offer.getCopyOfSellingStack();
                            buyingStack1 = offer.getDiscountedBuyingStackFirst();
                            buyingStack2 = offer.getBuyingStackSecond().copy();
                            for(Integer slot: sellingSlots) {
                                sellingStack = itemHandler.insertItem(slot, sellingStack, false);
                            }
                            for(Integer slot: buyingSlots1) {
                                testStack = itemHandler.extractItem(slot, buyingStack1.getCount(), false);
                                buyingStack1.shrink(testStack.getCount());
                            }
                            for(Integer slot: buyingSlots2) {
                                testStack = itemHandler.extractItem(slot, buyingStack2.getCount(), false);
                                buyingStack2.shrink(testStack.getCount());
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
