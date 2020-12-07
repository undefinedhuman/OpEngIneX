package de.undefinedhuman.core.entity.ecs.component.mesh;

import de.undefinedhuman.core.Engine;
import de.undefinedhuman.core.entity.ecs.component.Component;
import de.undefinedhuman.core.entity.ecs.component.ComponentBlueprint;
import de.undefinedhuman.core.entity.ecs.component.ComponentParam;
import de.undefinedhuman.core.entity.ecs.component.ComponentType;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.resources.texture.TextureManager;
import de.undefinedhuman.core.settings.SettingsObject;
import de.undefinedhuman.core.settings.panels.Panel;
import de.undefinedhuman.core.settings.panels.PanelObject;
import de.undefinedhuman.core.settings.panels.StringPanel;
import de.undefinedhuman.core.settings.types.mesh.Mesh;

import java.util.Collection;
import java.util.HashMap;

public class MeshBlueprint extends ComponentBlueprint {

    private Panel meshes = new StringPanel("Meshes", new Mesh());

    public MeshBlueprint() {
        settings.add(meshes);
        this.type = ComponentType.MESH;
    }

    @Override
    public Component createInstance(HashMap<ComponentType, ComponentParam> params) {
        return new MeshComponent(this);
    }

    @Override
    public void load(FsFile parentDir, SettingsObject settingsObject) {
        super.load(parentDir, settingsObject);
        if(Engine.instance == null) return;
        for(PanelObject object : meshes.getPanelObjects().values()) {
            if(!(object instanceof Mesh)) continue;
            Mesh mesh = (Mesh) object;
            mesh.generateVao();
            if(TextureManager.instance != null)
                TextureManager.instance.getTexture(mesh.texture.getString()).setCulling(mesh.culling.getBoolean());
        }
    }

    @Override
    public void delete() {
        for(PanelObject object : meshes.values()) {
            if(!(object instanceof Mesh)) continue;
            Mesh mesh = (Mesh) object;
            mesh.getVao().delete();
        }
    }

    public Collection<PanelObject> getMeshes() {
        return meshes.getPanelObjects().values();
    }

}
