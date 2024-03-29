package sguest.villagelife.block;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.DyeColor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sguest.villagelife.VillageLife;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, VillageLife.MOD_ID);
    public static final RegistryObject<Block> WOODCUTTER = BLOCKS.register("woodcutter", () -> new WoodcutterBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> REINFORCED_OAK_DOOR = BLOCKS.register("reinforced_oak_door", () -> new ReinforcedDoorBlock(Block.Properties.create(Material.WOOD, Blocks.OAK_PLANKS.getMaterialColor()).hardnessAndResistance(4.0F).sound(SoundType.WOOD).notSolid()));
    public static final RegistryObject<Block> REINFORCED_BIRCH_DOOR = BLOCKS.register("reinforced_birch_door", () -> new ReinforcedDoorBlock(Block.Properties.create(Material.WOOD, Blocks.BIRCH_PLANKS.getMaterialColor()).hardnessAndResistance(4.0F).sound(SoundType.WOOD).notSolid()));
    public static final RegistryObject<Block> REINFORCED_SPRUCE_DOOR = BLOCKS.register("reinforced_spruce_door", () -> new ReinforcedDoorBlock(Block.Properties.create(Material.WOOD, Blocks.SPRUCE_PLANKS.getMaterialColor()).hardnessAndResistance(4.0F).sound(SoundType.WOOD).notSolid()));
    public static final RegistryObject<Block> REINFORCED_JUNGLE_DOOR = BLOCKS.register("reinforced_jungle_door", () -> new ReinforcedDoorBlock(Block.Properties.create(Material.WOOD, Blocks.JUNGLE_PLANKS.getMaterialColor()).hardnessAndResistance(4.0F).sound(SoundType.WOOD).notSolid()));
    public static final RegistryObject<Block> REINFORCED_ACACIA_DOOR = BLOCKS.register("reinforced_acacia_door", () -> new ReinforcedDoorBlock(Block.Properties.create(Material.WOOD, Blocks.ACACIA_PLANKS.getMaterialColor()).hardnessAndResistance(4.0F).sound(SoundType.WOOD).notSolid()));
    public static final RegistryObject<Block> REINFORCED_DARK_OAK_DOOR = BLOCKS.register("reinforced_dark_oak_door", () -> new ReinforcedDoorBlock(Block.Properties.create(Material.WOOD, Blocks.DARK_OAK_PLANKS.getMaterialColor()).hardnessAndResistance(4.0F).sound(SoundType.WOOD).notSolid()));
    public static final RegistryObject<Block> REINFORCED_CRIMSON_DOOR = BLOCKS.register("reinforced_crimson_door", () -> new ReinforcedDoorBlock(Block.Properties.create(Material.WOOD, Blocks.CRIMSON_PLANKS.getMaterialColor()).hardnessAndResistance(4.0F).sound(SoundType.WOOD).notSolid()));
    public static final RegistryObject<Block> REINFORCED_WARPED_DOOR = BLOCKS.register("reinforced_warped_door", () -> new ReinforcedDoorBlock(Block.Properties.create(Material.WOOD, Blocks.WARPED_PLANKS.getMaterialColor()).hardnessAndResistance(4.0F).sound(SoundType.WOOD).notSolid()));
    public static final RegistryObject<Block> EMERALD_PRESSURE_PLATE = BLOCKS.register("emerald_pressure_plate", () -> new EmeraldPressurePlateBlock(Block.Properties.create(Material.IRON, MaterialColor.EMERALD).setRequiresTool().doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> KEG = BLOCKS.register("keg", () -> new KegBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD)));
    public static final Map<DyeColor, RegistryObject<Block>> TRADING_POSTS = new HashMap<>();
    static {
        for(DyeColor colour: DyeColor.values()) {
            TRADING_POSTS.put(colour, BLOCKS.register(colour + "_trading_post", () -> new TradingPostBlock(Block.Properties.create(Material.WOOD, Blocks.OAK_PLANKS.getMaterialColor()).hardnessAndResistance(2.0F).sound(SoundType.WOOD).notSolid())));
        }
    }
    public static final RegistryObject<Block> HARVESTER = BLOCKS.register("harvester", () -> new HarvesterBlock(Block.Properties.create(Material.ROCK, Blocks.DROPPER.getMaterialColor()).hardnessAndResistance(3.5F).sound(SoundType.STONE).setRequiresTool()));
    public static final RegistryObject<Block> GROWING_POPPY = BLOCKS.register("growing_poppy", () -> new GrowingFlowerBlock(Blocks.POPPY.getDefaultState(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));

    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(WOODCUTTER.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(REINFORCED_OAK_DOOR.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(REINFORCED_JUNGLE_DOOR.get(), RenderType.getTranslucent());
        for(RegistryObject<Block> tradingPost: TRADING_POSTS.values()) {
            RenderTypeLookup.setRenderLayer(tradingPost.get(), RenderType.getTranslucent());
        }
        RenderTypeLookup.setRenderLayer(GROWING_POPPY.get(), RenderType.getCutout());
    }

    public static void register() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}