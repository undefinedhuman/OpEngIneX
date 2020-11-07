package de.undefinedhuman.core.entity.ecs.component.mesh;

import de.undefinedhuman.core.Engine;
import de.undefinedhuman.core.entity.ecs.component.Component;
import de.undefinedhuman.core.entity.ecs.component.ComponentBlueprint;
import de.undefinedhuman.core.entity.ecs.component.ComponentParam;
import de.undefinedhuman.core.entity.ecs.component.ComponentType;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.opengl.Vao;
import de.undefinedhuman.core.resources.texture.TextureManager;
import de.undefinedhuman.core.settings.SettingsObject;
import de.undefinedhuman.core.settings.panels.Panel;
import de.undefinedhuman.core.settings.panels.PanelObject;
import de.undefinedhuman.core.settings.panels.StringPanel;
import de.undefinedhuman.core.settings.types.mesh.Mesh;

import java.util.ArrayList;
import java.util.HashMap;

public class MeshBlueprint extends ComponentBlueprint {

    private Panel meshes = new StringPanel("Meshes", new Mesh());

    private Vao[] vaos = new Vao[0];
    private String[] textures = new String[0];

    public MeshBlueprint() {
        super(ComponentType.MESH);
        settings.add(meshes);
    }

    @Override
    public Component createInstance(HashMap<ComponentType, ComponentParam> params) {
        return new MeshComponent(vaos, textures);
    }

    @Override
    public void load(FsFile parentDir, SettingsObject settingsObject) {
        super.load(parentDir, settingsObject);
        // TODO Maybe Refactor
        if(Engine.instance == null) return;
        ArrayList<PanelObject> objects = meshes.values();
        vaos = new Vao[meshes.getPanelObjects().size()];
        for(int i = 0; i < vaos.length; i++) {
            Mesh mesh = (Mesh) objects.get(i);
            vaos[i] = mesh.generateVao();
            textures[i] = mesh.texture.getString();
            if(TextureManager.instance != null) TextureManager.instance.getTexture(textures[i]).setCulling(mesh.culling.getBoolean());
        }
    }

    @Override
    public void delete() {
        for(Vao vao : vaos)
            vao.delete();
    }

}
