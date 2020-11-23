package sguest.villagelife.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TallBlockItem;
import net.minecraft.util.ResourceLocation;
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
    public static final RegistryObject<Item> EMERALD_PRESSURE_PLATE = ITEMS.register("emerald_pressure_plate", () -> new BlockItem(ModBlocks.EMERALD_PRESSURE_PLATE.get(), new Item.Properties().group(ItemGroup.REDSTONE)));
    public static final RegistryObject<Item> EMERALD_SWORD = ITEMS.register("emerald_sword", () -> new SwordItem(ModItemTier.EMERALD, 3, -2.4F, (new Item.Properties()).group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> EMERALD_SHOVEL = ITEMS.register("emerald_shovel", () -> new ShovelItem(ModItemTier.EMERALD, 1.5F, -3.0F, (new Item.Properties()).group(ItemGroup.TOOLS)));
    public static final RegistryObject<Item> EMERALD_PICKAXE = ITEMS.register("emerald_pickaxe", () -> new PickaxeItem(ModItemTier.EMERALD, 1, -2.8F, (new Item.Properties()).group(ItemGroup.TOOLS)));
    public static final RegistryObject<Item> EMERALD_AXE = ITEMS.register("emerald_axe", () -> new AxeItem(ModItemTier.EMERALD, 5.0F, -3.0F, (new Item.Properties()).group(ItemGroup.TOOLS)));
    public static final RegistryObject<Item> EMERALD_HOE = ITEMS.register("emerald_hoe", () -> new HoeItem(ModItemTier.EMERALD, -3, 0.0F, (new Item.Properties()).group(ItemGroup.TOOLS)));
    public static final RegistryObject<Item> EMERALD_HELMET = ITEMS.register("emerald_helmet", () -> new ArmorItem(ModArmorMaterial.EMERALD, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> EMERALD_CHESTPLATE = ITEMS.register("emerald_chestplate", () -> new ArmorItem(ModArmorMaterial.EMERALD, EquipmentSlotType.CHEST, (new Item.Properties()).group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> EMERALD_LEGGINGS = ITEMS.register("emerald_leggings", () -> new ArmorItem(ModArmorMaterial.EMERALD, EquipmentSlotType.LEGS, (new Item.Properties()).group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> EMERALD_BOOTS = ITEMS.register("emerald_boots", () -> new ArmorItem(ModArmorMaterial.EMERALD, EquipmentSlotType.FEET, (new Item.Properties()).group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> EMERALD_HORSE_ARMOR = ITEMS.register("emerald_horse_armor", () -> new HorseArmorItem(8, new ResourceLocation(VillageLife.MOD_ID, "textures/entity/horse/armor/horse_armor_emerald.png"), (new Item.Properties()).maxStackSize(1).group(ItemGroup.MISC)));
    public static final RegistryObject<Item> KEG = ITEMS.register("keg", () -> new BlockItem(ModBlocks.KEG.get(), (new Item.Properties()).group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> MILK_BOTTLE = ITEMS.register("milk_bottle", () -> new MilkBottleItem((new Item.Properties()).containerItem(Items.GLASS_BOTTLE).group(ItemGroup.MISC).maxStackSize(16)));
    public static final RegistryObject<Item> STEAK_SANDWICH = ITEMS.register("steak_sandwich", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).food(ModFoods.STEAK_SANDWICH)));
    public static final RegistryObject<Item> PORK_SANDWICH = ITEMS.register("pork_sandwich", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).food(ModFoods.PORK_SANDWICH)));
    public static final RegistryObject<Item> MUTTON_SANDWICH = ITEMS.register("mutton_sandwich", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).food(ModFoods.MUTTON_SANDWICH)));
    public static final RegistryObject<Item> TRADING_POST = ITEMS.register("trading_post", () -> new BlockItem(ModBlocks.TRADING_POST.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));

    public static void register() {
        ModItemTier.register();
        ModArmorMaterial.register();
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
