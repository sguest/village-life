package sguest.villagelife.item.crafting;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sguest.villagelife.VillageLife;

public class ModRecipeSerializers {
    private static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, VillageLife.MOD_ID);
    public static final RegistryObject<IRecipeSerializer<WoodcuttingRecipe>> WOODCUTTING = RECIPE_SERIALIZERS.register(WoodcuttingRecipe.RECIPE_ID, () -> new SingleItemRecipe.Serializer<WoodcuttingRecipe>(WoodcuttingRecipe::new));

    public static void register() {
        RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
