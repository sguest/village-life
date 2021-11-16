package sguest.villagelife.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JAdvancement implements Cloneable {
    private String parent;
    private JRewards rewards;
    private List<List<String>> requirements;
    private JCriteria criteria;

    public JAdvancement() {
    }

    public static JAdvancement advancement() {
        return new JAdvancement();
    }

    public JAdvancement parent(String parent) {
        this.parent = parent;
        return this;
    }

    public JAdvancement rewards (JRewards rewards) {
        this.rewards = rewards;
        return this;
    }

    public JAdvancement requirements(String... requirements) {
        if (this.requirements == null) {
            this.requirements = new ArrayList<>(1);
        }
        var newRequirements = new ArrayList<String>();
        Collections.addAll(newRequirements, requirements);
        this.requirements.add(newRequirements);
        return this;
    }

    public JAdvancement criteria(JCriteria criteria) {
        this.criteria = criteria;
        return this;
    }

    @Override
    public JAdvancement clone() {
        try {
            return (JAdvancement) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}
