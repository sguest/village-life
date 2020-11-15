package sguest.villagelife.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sguest.villagelife.VillageLife;
import sguest.villagelife.block.ModBlocks;

public class ModTileEntities {
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, VillageLife.MOD_ID);
    public static final RegistryObject<TileEntityType<KegTileEntity>> KEG = TILE_ENTITIES.register("keg", () -> TileEntityType.Builder.create(KegTileEntity::new, ModBlocks.KEG.get()).build(null));

    public static void register() {
        TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}