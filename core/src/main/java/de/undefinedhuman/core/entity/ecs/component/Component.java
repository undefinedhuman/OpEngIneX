package de.undefinedhuman.core.entity.ecs.component;

import de.undefinedhuman.core.file.Serializable;

public abstract class Component implements Serializable {

    protected ComponentType type;

    public Component(ComponentType type) {
        this.type = type;
    }

    public ComponentType getType() {
        return type;
    }

    public void init() {}
    public void delete() {}

}
