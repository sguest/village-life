package sguest.villagelife.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import sguest.villagelife.item.ModItems;
import sguest.villagelife.stats.ModStats;
import sguest.villagelife.tileentity.KegTileEntity;
import sguest.villagelife.tileentity.KegTileEntity.FluidType;
import sguest.villagelife.util.ItemUtil;
import sguest.villagelife.util.TextUtil;

public class KegBlock extends Block {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    protected static final VoxelShape NS_SHAPE = VoxelShapes.or(
        Block.makeCuboidShape(1.0D, 1.0D, 0.0D, 15.0D, 15.0D, 16.0D),
        Block.makeCuboidShape(0.0D, 0.0D, 1.0D, 3.0D, 3.0D, 3.0D),
        Block.makeCuboidShape(13.0D, 0.0D, 1.0D, 16.0D, 3.0D, 3.0D),
        Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 3.0D, 3.0D, 15.0D),
        Block.makeCuboidShape(13.0D, 0.0D, 13.0D, 16.0D, 3.0D, 15.0D)
    );
    protected static final VoxelShape EW_SHAPE = VoxelShapes.or(
        Block.makeCuboidShape(0.0D, 1.0D, 1.0D, 16.0D, 15.0D, 15.0D),
        Block.makeCuboidShape(1.0D, 0.0D, 0.0D, 3.0D, 3.0D, 3.0D),
        Block.makeCuboidShape(1.0D, 0.0D, 13.0D, 3.0D, 3.0D, 16.0D),
        Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 15.0D, 3.0D, 3.0D),
        Block.makeCuboidShape(13.0D, 0.0D, 13.0D, 15.0D, 3.0D, 16.0D)
    );

    public KegBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new KegTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        Direction facing = state.get(FACING);
        if(facing == Direction.NORTH || facing == Direction.SOUTH) {
            return NS_SHAPE;
        }
        return EW_SHAPE;
    }

    public boolean isTransparent(BlockState state) {
        return true;
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    /**
    * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever possible. Implementing/overriding is fine.
    */
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    /**
    * @deprecated call via {@link IBlockState#hasComparatorInputOverride()} whenever possible. Implementing/overriding
    * is fine.
    */
    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    /**
      * @deprecated call via {@link IBlockState#getComparatorInputOverride(World,BlockPos)} whenever possible.
      * Implementing/overriding is fine.
      */
    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(!(tileEntity instanceof KegTileEntity)) {
            return 0;
        }
        KegTileEntity kegTileEntity = (KegTileEntity)tileEntity;
        return Math.floorDiv(kegTileEntity.getFluidLevel() * 15, KegTileEntity.CAPACITY);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasTag()) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if((tileEntity instanceof KegTileEntity)) {
                KegTileEntity kegTileEntity = (KegTileEntity)tileEntity;
                kegTileEntity.read(state, stack.getTag());
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        KegTileEntity tileEntity = new KegTileEntity();
        if(stack.hasTag()) {
            tileEntity.readOwnData(stack.getTag());
            switch(tileEntity.getFluidType()) {
                case WATER:
                    tooltip.add(TextUtil.styledTranslation("block.minecraft.water", TextFormatting.AQUA));
                    break;
                case MILK:
                    tooltip.add(TextUtil.styledTranslation("block.villagelife.keg.tooltip.milk", TextFormatting.WHITE));
                    break;
                case HONEY:
                    tooltip.add(TextUtil.styledTranslation("block.villagelife.keg.tooltip.honey", TextFormatting.YELLOW));
                    break;
                case POTION:
                    tooltip.add(TextUtil.styledTranslation("item.minecraft.potion", TextFormatting.DARK_PURPLE));
                    PotionUtils.addPotionTooltip(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), tileEntity.getPotionType()), tooltip, 1.0F);
                    break;
                default:
                    break;
            }

            if(tileEntity.getFluidType() != FluidType.EMPTY) {
                tooltip.add(TextUtil.styledTranslation("block.villagelife.keg.tooltip.amount", TextFormatting.GRAY, tileEntity.getFluidLevel(), KegTileEntity.CAPACITY));
            }
        }
    }


    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        if(player.isSpectator()) {
            return ActionResultType.CONSUME;
        }
        TileEntity tileEntity = world.getTileEntity(pos);
        if(!(tileEntity instanceof KegTileEntity)) {
            return ActionResultType.PASS;
        }

        KegTileEntity kegTileEntity = (KegTileEntity)tileEntity;
        ItemStack heldStack = player.getHeldItem(hand);
        Item heldItem = heldStack.getItem();

        ItemStack resultItem = ItemStack.EMPTY;

        if(heldItem == Items.BUCKET) {
            switch (kegTileEntity.getFluidType()) {
                case MILK:
                    if(kegTileEntity.removeFluid(3)) {
                        resultItem = new ItemStack(Items.MILK_BUCKET);
                        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                    break;
                case WATER:
                    if(kegTileEntity.removeFluid(3)) {
                        resultItem = new ItemStack(Items.WATER_BUCKET);
                        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                    break;
                default:
                    break;
            }
        }
        else if(heldItem == Items.GLASS_BOTTLE) {
            switch(kegTileEntity.getFluidType()) {
                case WATER:
                    if(kegTileEntity.removeFluid(1)) {
                        resultItem = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER);
                        world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                    break;
                case HONEY:
                    if(kegTileEntity.removeFluid(1)) {
                        resultItem = new ItemStack(Items.HONEY_BOTTLE);
                        world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                    break;
                case MILK:
                    if(kegTileEntity.removeFluid(1)) {
                        resultItem = new ItemStack(ModItems.MILK_BOTTLE.get());
                        world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                    break;
                case POTION:
                    Potion potion = kegTileEntity.getPotionType();
                    if(kegTileEntity.removeFluid(1)) {
                        resultItem = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), potion);
                        world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                    break;
                default:
                    break;
            }
        }
        else if(heldItem == Items.WATER_BUCKET) {
            if(kegTileEntity.addWater(3)) {
                resultItem = new ItemStack(Items.BUCKET);
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
        else if(heldItem == Items.MILK_BUCKET) {
            if(kegTileEntity.addMilk(3)) {
                resultItem = new ItemStack(Items.BUCKET);
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
        else if(heldItem == Items.POTION || heldItem == Items.SPLASH_POTION || heldItem == Items.LINGERING_POTION) {
            Potion potion = PotionUtils.getPotionFromItem(heldStack);
            if(potion == Potions.WATER) {
                if(kegTileEntity.addWater(1)) {
                    resultItem = new ItemStack(Items.GLASS_BOTTLE);
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
            else {
                if(kegTileEntity.addPotion(1, potion)) {
                    resultItem = new ItemStack(Items.GLASS_BOTTLE);
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
        }
        else if(heldItem == Items.HONEY_BOTTLE) {
            if(kegTileEntity.addHoney(1)) {
                resultItem = new ItemStack(Items.GLASS_BOTTLE);
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
        else if(heldItem == ModItems.MILK_BOTTLE.get()) {
            if(kegTileEntity.addMilk(1)) {
                resultItem = new ItemStack(Items.GLASS_BOTTLE);
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
        else {
            switch(kegTileEntity.getFluidType()) {
                case MILK:
                    if(kegTileEntity.removeFluid(1)) {
                        ItemStack cureStack = new ItemStack(Items.MILK_BUCKET);
                        player.curePotionEffects(cureStack);
                        world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                    break;
                case HONEY:
                    if(player.canEat(Foods.HONEY.canEatWhenFull()) && kegTileEntity.removeFluid(1)) {
                        ItemStack eatStack = new ItemStack(Items.HONEY_BOTTLE);
                        player.removePotionEffect(Effects.POISON);
                        player.onFoodEaten(world, eatStack);
                        world.playSound(null, pos, SoundEvents.ITEM_HONEY_BOTTLE_DRINK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                    break;
                case POTION:
                    if(kegTileEntity.removeFluid(1)) {
                        for(EffectInstance effectinstance : kegTileEntity.getPotionType().getEffects()) {
                            if (effectinstance.getPotion().isInstant()) {
                                effectinstance.getPotion().affectEntity(player, player, player, effectinstance.getAmplifier(), 1.0D);
                            } else {
                                player.addPotionEffect(new EffectInstance(effectinstance));
                            }
                        }
                        world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                default:
                    break;
            }
        }

        if(resultItem != ItemStack.EMPTY) {
            heldStack.shrink(1);
            if(heldStack.isEmpty()) {
                player.setHeldItem(hand, resultItem);
            }
            else {
                ItemUtil.giveToPlayer(player, resultItem);
            }
            player.addStat(ModStats.INTERACT_WITH_KEG);
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }
}
