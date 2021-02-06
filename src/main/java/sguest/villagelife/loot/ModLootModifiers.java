package sguest.villagelife.loot;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sguest.villagelife.VillageLife;

public class ModLootModifiers {
    private static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, VillageLife.MOD_ID);
    public static final RegistryObject<AddEntriesLootModifier.Serializer> ADD_ENTRIES = LOOT_MODIFIER_SERIALIZERS.register("add_entries", AddEntriesLootModifier.Serializer::new);

    public static void register(){
        LOOT_MODIFIER_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
