package de.undefinedhuman.core.entity;

import de.undefinedhuman.core.file.FileReader;
import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.file.Serializable;
import de.undefinedhuman.core.entity.ecs.blueprint.Blueprint;
import de.undefinedhuman.core.entity.ecs.component.Component;
import de.undefinedhuman.core.entity.ecs.component.ComponentList;
import de.undefinedhuman.core.entity.ecs.component.ComponentType;
import de.undefinedhuman.core.transform.Transform;

public class Entity implements Serializable {

    private int blueprintID, worldID;
    private EntityType entityType;
    private ComponentList componentList;

    private Transform transform;

    public Entity(Blueprint blueprint) {
        this(blueprint.getID(), blueprint.getType());
    }

    public Entity(int blueprintID, EntityType entityType) {
        this.blueprintID = blueprintID;
        this.entityType = entityType;
        this.transform = new Transform();
        this.componentList = new ComponentList();
    }

    @Override
    public void load(FileReader reader) {
        transform.load(reader);
        int size = reader.getNextInt();
        for(int i = 0; i < size; i++) { componentList.getComponent(ComponentType.valueOf(reader.getNextString())).load(reader);reader.nextLine(); }
    }

    @Override
    public void save(FileWriter writer) {
        transform.save(writer);
        writer.writeInt(componentList.getComponents().size()).nextLine();
        for(Component component : componentList.getComponents()) { writer.writeString(component.getType().name()); component.save(writer); writer.nextLine(); }
    }

    // TODO entity saving
    public void delete() {}

    public Entity setWorldID(int worldID) {
        this.worldID = worldID;
        return this;
    }

    public ComponentList getComponents() {
        return componentList;
    }

    public Component getComponent(ComponentType type) {
        return componentList.getComponent(type);
    }

    public int getWorldID() {
        return worldID;
    }

    public int getBlueprintID() {
        return blueprintID;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Transform getTransform() {
        return transform;
    }

}
