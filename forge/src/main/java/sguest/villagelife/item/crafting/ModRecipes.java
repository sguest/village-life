package sguest.villagelife.item.crafting;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRecipes {
    public static IRecipeType<WoodcuttingRecipe> WOODCUTTING;

    @SubscribeEvent
    public static void onRecipeSerializerRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        WOODCUTTING = IRecipeType.register(WoodcuttingRecipe.RECIPE_ID);
    }
}
