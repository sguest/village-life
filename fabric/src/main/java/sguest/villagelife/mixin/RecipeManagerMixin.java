package sguest.villagelife.mixin;

import java.util.Map;

import com.google.gson.JsonElement;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import sguest.villagelife.recipe.RecipesProvider;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    @Inject(method="apply", at = @At("HEAD"))
    public void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
        map.putAll(RecipesProvider.RECIPES);
    }
}
