package de.undefinedhuman.core.entity.ecs.component.mesh;

import de.undefinedhuman.core.entity.Entity;
import de.undefinedhuman.core.entity.ecs.component.Component;
import de.undefinedhuman.core.entity.ecs.component.ComponentBlueprint;
import de.undefinedhuman.core.entity.ecs.component.ComponentParam;
import de.undefinedhuman.core.entity.ecs.component.ComponentType;
import de.undefinedhuman.core.file.FileReader;
import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.opengl.Vao;

import java.util.HashMap;

public class MeshBlueprint extends ComponentBlueprint {

    private Vao vao;

    public MeshBlueprint() {
        super("Mesh");
    }

    @Override
    public Component createInstance(HashMap<ComponentType, ComponentParam> params) {
        return null;
    }

    @Override
    public void delete() {
        vao.delete();
    }

    @Override
    public void load(FileReader reader) {
        int[] indices = reader.getNextIntegerArray();

        vao = new Vao()
                .bind()
                .storeVertexCount(indices.length)
                .storeIndexData(indices)
                .storeData(0, reader.getNexFloatArray(), 3)
                .storeData(1, reader.getNexFloatArray(), 2)
                .storeData(2, reader.getNexFloatArray(), 3)
                .unbind();
    }

    @Override
    public void save(FileWriter writer) {}

    public int getVertexCount() {
        return vao.getVertexCount();
    }

    public Vao getVao() {
        return vao;
    }

}
