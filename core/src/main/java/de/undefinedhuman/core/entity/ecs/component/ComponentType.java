package de.undefinedhuman.core.entity.ecs.component;

import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.settings.SettingsObject;
import de.undefinedhuman.core.entity.ecs.component.mesh.MeshBlueprint;
import de.undefinedhuman.core.entity.ecs.component.texture.TextureBlueprint;

public enum ComponentType {

    MESH(new MeshBlueprint()),
    TEXTURE(new TextureBlueprint());

    private ComponentBlueprint blueprint;

    ComponentType(ComponentBlueprint blueprint) {
        this.blueprint = blueprint;
    }

    public ComponentBlueprint load(FsFile parentDir, SettingsObject settingsObject) {
        ComponentBlueprint componentBlueprint = null;
        if (blueprint != null) {
            try { componentBlueprint = blueprint.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) { e.printStackTrace(); }
            if (componentBlueprint != null) componentBlueprint.load(parentDir, settingsObject);
        }
        return componentBlueprint;
    }

    public ComponentBlueprint createNewInstance() {
        try { return blueprint.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) { e.printStackTrace(); }
        return null;
    }

}
