package de.undefinedhuman.core.entity.ecs.component;

public class ComponentParam {

    private ComponentType type;
    public ComponentParam(ComponentType type) {
        this.type = type;
    }
    public ComponentType getType() {
        return type;
    }

}
