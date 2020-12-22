package de.undefinedhuman.engine.entity;

import de.undefinedhuman.engine.entity.ecs.blueprint.Blueprint;
import de.undefinedhuman.engine.entity.ecs.component.Component;
import de.undefinedhuman.engine.entity.ecs.component.ComponentList;
import de.undefinedhuman.engine.entity.ecs.component.ComponentType;
import de.undefinedhuman.engine.game.GameObject;

public class Entity extends GameObject {

    private int blueprintID, worldID;
    private EntityType type;
    private ComponentList components;

    public Entity(Blueprint blueprint) {
        this.blueprintID = blueprint.getID();
        this.type = blueprint.getEntityType();
        components = new ComponentList();
    }

    @Override
    public void init() {
        for (Component component : components.getComponents()) component.init();
    }

    @Override
    public void delete() {
        components.delete();
    }

    public void setComponents(ComponentList list) {
        this.components = list;
    }

    public void addComponents(Component... components) {
        for(Component component : components) this.components.addComponent(component);
    }

    public Component getComponent(ComponentType type) {
        if(!hasComponent(type)) return null;
        return components.getComponent(type);
    }

    public boolean hasComponent(ComponentType... types) {
        boolean hasComponents = true;
        for (ComponentType type : types) if (!components.hasComponent(type)) hasComponents = false;
        return hasComponents;
    }

    public int getBlueprintID() {
        return blueprintID;
    }

    public EntityType getEntityType() {
        return type;
    }

    public int getWorldID() {
        return worldID;
    }

    public Entity setWorldID(int worldID) {
        this.worldID = worldID;
        return this;
    }

}
