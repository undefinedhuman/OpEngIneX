package de.undefinedhuman.core.settings.types.mesh;

import de.undefinedhuman.core.opengl.Vao;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.settings.panels.PanelObject;
import de.undefinedhuman.core.settings.types.FloatArraySetting;
import de.undefinedhuman.core.settings.types.IntArraySetting;

public class Mesh extends PanelObject {

    public Setting
            indiciesSetting = new IntArraySetting("Indicies", new int[0]).setEditable(false),
            verticesSetting = new FloatArraySetting("Vertices", new float[0]).setEditable(false),
            textureCoordsSetting = new FloatArraySetting("Texture Coords", new float[0]).setEditable(false),
            normalsSetting = new FloatArraySetting("Normals", new float[0]).setEditable(false),
            loadMesh = new MeshSetting("Load") {
                @Override
                public void updateMesh(int[] indicies, float[] vertices, float[] textureCoords, float[] normals) {
                    indiciesSetting.setValue(indicies);
                    verticesSetting.setValue(vertices);
                    textureCoordsSetting.setValue(textureCoords);
                    normalsSetting.setValue(normals);
                }
            };

    public Mesh() {
        settings.add(loadMesh, indiciesSetting, verticesSetting, textureCoordsSetting, normalsSetting);
    }

    public Vao generateVao() {
        return new Vao()
                .bind()
                .storeIndexData(indiciesSetting.getIntegerArray())
                .storeData(0, verticesSetting.getFloatArray(), 3)
                .storeData(1, textureCoordsSetting.getFloatArray(), 2)
                .storeData(2, normalsSetting.getFloatArray(), 3)
                .unbind();
    }

}
