package de.undefinedhuman.core.entity.ecs.blueprint;

import de.undefinedhuman.core.entity.EntityType;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.settings.SettingType;
import de.undefinedhuman.core.settings.SettingsList;
import de.undefinedhuman.core.entity.Entity;
import de.undefinedhuman.core.entity.ecs.component.ComponentBlueprint;
import de.undefinedhuman.core.entity.ecs.component.ComponentParam;
import de.undefinedhuman.core.entity.ecs.component.ComponentType;
import de.undefinedhuman.core.settings.types.SelectionSetting;

import java.util.HashMap;

public class Blueprint {

    public SettingsList settings = new SettingsList();

    public Setting
            id = new Setting(SettingType.Int, "ID", 0),
            entityType = new SelectionSetting("Type", EntityType.values());

    private HashMap<ComponentType, ComponentBlueprint> componentBlueprints = new HashMap<>();

    public Blueprint() {
        settings.add(id);
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
        componentBlueprints.putIfAbsent(blueprint.getType(), blueprint);
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
