package de.undefinedhuman.core.settings.types.mesh;

import de.undefinedhuman.core.opengl.Vao;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.settings.SettingType;
import de.undefinedhuman.core.settings.panels.PanelObject;
import de.undefinedhuman.core.settings.types.BooleanSetting;
import de.undefinedhuman.core.settings.types.TextureSetting;
import de.undefinedhuman.core.settings.types.array.FloatArraySetting;
import de.undefinedhuman.core.settings.types.array.IntArraySetting;

public class Mesh extends PanelObject {

    public Setting
            indiciesSetting = new IntArraySetting("Indicies", new int[0]).setEditable(false),
            verticesSetting = new FloatArraySetting("Vertices", new float[0]).setEditable(false),
            textureCoordsSetting = new FloatArraySetting("Texture Coords", new float[0]).setEditable(false),
            normalsSetting = new FloatArraySetting("Normals", new float[0]).setEditable(false),
            maxDistanceSetting = new Setting(SettingType.Float, "Max Distance", 0),
            loadMesh = new MeshSetting("Load") {
                @Override
                public void updateMesh(float maxDistance, int[] indicies, float[] vertices, float[] textureCoords, float[] normals) {
                    maxDistanceSetting.setValue(maxDistance);
                    indiciesSetting.setValue(indicies);
                    verticesSetting.setValue(vertices);
                    textureCoordsSetting.setValue(textureCoords);
                    normalsSetting.setValue(normals);
                }
            },
            texture = new TextureSetting("Texture", "Unknown.png"),
            culling = new BooleanSetting("Culling", true),
            windFactor = new Setting(SettingType.Float, "Wind Factor", 0.1f),
            reflectance = new Setting(SettingType.Float, "reflectance", 0f),
            specularPower = new Setting(SettingType.Int, "Specular Power", 32);

    private Vao vao;

    public Mesh() {
        settings.add(loadMesh, maxDistanceSetting, indiciesSetting, verticesSetting, textureCoordsSetting, normalsSetting, texture, culling, windFactor, reflectance, specularPower);
    }

    public void generateVao() {
        vao = new Vao()
                .bind()
                .storeIndexData(indiciesSetting.getIntegerArray())
                .storeData(0, verticesSetting.getFloatArray(), 3)
                .storeData(1, textureCoordsSetting.getFloatArray(), 2)
                .storeData(2, normalsSetting.getFloatArray(), 3)
                .unbind();
    }

    public Vao getVao() {
        return vao;
    }

}
