package sguest.villagelife.item.crafting;

import net.minecraft.item.crafting.*;
import net.minecraft.item.crafting.SingleItemRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import sguest.villagelife.VillageLife;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(VillageLife.MOD_ID)
public class ModRecipes {
    public static final IRecipeSerializer<WoodcuttingRecipe> WOODCUTTING = null;
    public static IRecipeType<WoodcuttingRecipe> WOODCUTTING_TYPE;

    @SubscribeEvent
    public static void onRecipeSerializerRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        event.getRegistry().registerAll(
            new sguest.villagelife.item.crafting.SingleItemRecipe.Serializer<>(WoodcuttingRecipe::new).setRegistryName(VillageLife.MOD_ID, "woodcutting")
        );

        WOODCUTTING_TYPE = register("woodcutting");
    }

    static <T extends IRecipe<?>> IRecipeType<T> register(final String key) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(VillageLife.MOD_ID, key), new IRecipeType<T>() {
            public String toString() {
            return key;
            }
        });
    }
}
