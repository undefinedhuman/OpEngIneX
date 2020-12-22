package de.undefinedhuman.engine.entity.ecs.blueprint;

import de.undefinedhuman.engine.entity.EntityType;
import de.undefinedhuman.engine.settings.Setting;
import de.undefinedhuman.engine.settings.SettingType;
import de.undefinedhuman.engine.settings.SettingsList;
import de.undefinedhuman.engine.entity.Entity;
import de.undefinedhuman.engine.entity.ecs.component.ComponentBlueprint;
import de.undefinedhuman.engine.entity.ecs.component.ComponentParam;
import de.undefinedhuman.engine.entity.ecs.component.ComponentType;
import de.undefinedhuman.engine.settings.types.SelectionSetting;

import java.util.HashMap;

public class Blueprint {

    public SettingsList settings = new SettingsList();

    public Setting
            id = new Setting(SettingType.Int, "ID", 0),
            name = new Setting(SettingType.String, "Name", "Temp Name"),
            entityType = new SelectionSetting("Type", EntityType.values());

    private HashMap<ComponentType, ComponentBlueprint> componentBlueprints = new HashMap<>();

    public Blueprint() {
        settings.add(id, name, entityType);
    }

    public Entity createInstance(ComponentParam... param) {
        Entity entity = new Entity(this);
        HashMap<ComponentType, ComponentParam> params = new HashMap<>();
        for (ComponentParam p : param) params.put(p.getType(), p);
        for (ComponentBlueprint blueprint : componentBlueprints.values())
            entity.addComponents(blueprint.createInstance(params));
        entity.init();
        return entity;
    }

    public int getID() {
        return id.getInt();
    }

    public EntityType getEntityType() {
        return entityType.getEntityType();
    }

    public void addComponentBlueprint(ComponentBlueprint blueprint) {
        componentBlueprints.putIfAbsent(blueprint.type, blueprint);
    }

    public ComponentBlueprint getComponentBlueprint(ComponentType type) {
        return componentBlueprints.get(type);
    }

    public boolean hasComponentBlueprints(ComponentType type) {
        return componentBlueprints.containsKey(type);
    }

    public HashMap<ComponentType, ComponentBlueprint> getComponentBlueprints() {
        return componentBlueprints;
    }

    public void delete() {
        for (ComponentBlueprint blueprint : componentBlueprints.values()) blueprint.delete();
    }

}
