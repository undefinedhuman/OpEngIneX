package de.undefinedhuman.engine.entity.ecs.component;

import de.undefinedhuman.core.file.Serializable;
import de.undefinedhuman.core.network.NetworkComponent;

public abstract class Component implements Serializable, NetworkComponent {

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
