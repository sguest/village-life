package sguest.villagelife.resources;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class JRecipeUnlockedConditions extends JConditions {
    private final String recipe;

    public JRecipeUnlockedConditions(String recipe) {
        this.recipe = recipe;
    }

    public JRecipeUnlockedConditions(Item item) {
        this.recipe = Registry.ITEM.getId(item).toString();
    }

    @Override
    protected JRecipeUnlockedConditions clone() {
        return (JRecipeUnlockedConditions) super.clone();
    }
}
