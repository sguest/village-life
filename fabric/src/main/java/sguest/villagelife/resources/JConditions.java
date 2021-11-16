package sguest.villagelife.resources;

public abstract class JConditions implements Cloneable {
    @Override
    protected JConditions clone() {
        try {
            return (JConditions) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}
