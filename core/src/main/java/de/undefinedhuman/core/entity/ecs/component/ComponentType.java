package de.undefinedhuman.core.entity.ecs.component;

import de.undefinedhuman.core.entity.ecs.component.mesh.MeshBlueprint;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.settings.SettingsObject;

public enum ComponentType {

    MESH(MeshBlueprint.class);

    private Class<? extends ComponentBlueprint> componentBlueprint;

    ComponentType(Class<? extends ComponentBlueprint> componentBlueprint) {
        this.componentBlueprint = componentBlueprint;
    }

    public ComponentBlueprint createInstance(FsFile parentDir, SettingsObject settingsObject) {
        ComponentBlueprint componentBlueprint = null;
        try { componentBlueprint = this.componentBlueprint.newInstance();
        } catch (InstantiationException | IllegalAccessException e) { e.printStackTrace(); }
        if (componentBlueprint != null) componentBlueprint.load(parentDir, settingsObject);
        return componentBlueprint;
    }

    public ComponentBlueprint createInstance() {
        try { return componentBlueprint.newInstance();
        } catch (InstantiationException | IllegalAccessException e) { e.printStackTrace(); }
        return null;
    }

}
