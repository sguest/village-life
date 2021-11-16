package sguest.villagelife.resources;

public class JCriterion implements Cloneable {
    private final String trigger;
    private final JConditions conditions;

    protected JCriterion(final String trigger, JConditions conditions) {
        this.trigger = trigger;
        this.conditions = conditions;
    }

    public static JCriterion inventoryChanged(JInventoryChangedConditions conditions) {
        return new JCriterion("minecraft:inventory_changed", conditions);
    }

    public static JCriterion recipeUnlocked(JRecipeUnlockedConditions conditions) {
        return new JCriterion("minecraft:recipe_unlocked", conditions);
    }

    @Override
    public JCriterion clone() {
        try {
            return (JCriterion) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}
