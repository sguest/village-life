package sguest.villagelife.item;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.TallBlockItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sguest.villagelife.VillageLife;
import sguest.villagelife.block.ModBlocks;

public class ModItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, VillageLife.MOD_ID);
    public static final RegistryObject<Item> WOODCUTTER = ITEMS.register("woodcutter", () -> new BlockItem(ModBlocks.WOODCUTTER.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> REINFORCED_OAK_DOOR = ITEMS.register("reinforced_oak_door", () -> new TallBlockItem(ModBlocks.REINFORCED_OAK_DOOR.get(), (new Item.Properties()).group(ItemGroup.REDSTONE)));
    public static final RegistryObject<Item> REINFORCED_BIRCH_DOOR = ITEMS.register("reinforced_birch_door", () -> new TallBlockItem(ModBlocks.REINFORCED_BIRCH_DOOR.get(), (new Item.Properties()).group(ItemGroup.REDSTONE)));
    public static final RegistryObject<Item> REINFORCED_SPRUCE_DOOR = ITEMS.register("reinforced_spruce_door", () -> new TallBlockItem(ModBlocks.REINFORCED_SPRUCE_DOOR.get(), (new Item.Properties()).group(ItemGroup.REDSTONE)));
    public static final RegistryObject<Item> REINFORCED_JUNGLE_DOOR = ITEMS.register("reinforced_jungle_door", () -> new TallBlockItem(ModBlocks.REINFORCED_JUNGLE_DOOR.get(), (new Item.Properties()).group(ItemGroup.REDSTONE)));
    public static final RegistryObject<Item> REINFORCED_ACACIA_DOOR = ITEMS.register("reinforced_acacia_door", () -> new TallBlockItem(ModBlocks.REINFORCED_ACACIA_DOOR.get(), (new Item.Properties()).group(ItemGroup.REDSTONE)));
    public static final RegistryObject<Item> REINFORCED_DARK_OAK_DOOR = ITEMS.register("reinforced_dark_oak_door", () -> new TallBlockItem(ModBlocks.REINFORCED_DARK_OAK_DOOR.get(), (new Item.Properties()).group(ItemGroup.REDSTONE)));
    public static final RegistryObject<Item> REINFORCED_CRIMSON_DOOR = ITEMS.register("reinforced_crimson_door", () -> new TallBlockItem(ModBlocks.REINFORCED_CRIMSON_DOOR.get(), (new Item.Properties()).group(ItemGroup.REDSTONE)));
    public static final RegistryObject<Item> REINFORCED_WARPED_DOOR = ITEMS.register("reinforced_warped_door", () -> new TallBlockItem(ModBlocks.REINFORCED_WARPED_DOOR.get(), (new Item.Properties()).group(ItemGroup.REDSTONE)));

    public static void register() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
