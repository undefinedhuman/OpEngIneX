package de.undefinedhuman.core.world.generation;

import de.undefinedhuman.core.opengl.Vao;
import de.undefinedhuman.core.utils.Variables;
import org.joml.Vector3f;

public class TerrainGenerator {

    public static Vao generateTerrain(HeightGenerator heightGenerator) {
        int verticesCount = Variables.TERRAIN_VERTEX_COUNT * Variables.TERRAIN_VERTEX_COUNT;
        float[] vertices = new float[verticesCount * 3], textureCoords = new float[verticesCount * 2], normals = new float[verticesCount * 3];
        int[] indices = new int[6 * (Variables.TERRAIN_VERTEX_COUNT - 1) * (Variables.TERRAIN_VERTEX_COUNT - 1)];
        int pointer;
        for(int i = 0; i < Variables.TERRAIN_VERTEX_COUNT; i++)
            for(int j = 0; j < Variables.TERRAIN_VERTEX_COUNT; j++) {
                pointer = i * Variables.TERRAIN_VERTEX_COUNT + j;
                float x = (float) j / ((float) Variables.TERRAIN_VERTEX_COUNT - 1) * Variables.TERRAIN_SIZE,
                        z = (float) i / ((float) Variables.TERRAIN_VERTEX_COUNT - 1) * Variables.TERRAIN_SIZE;
                addValues(vertices, pointer, x, heightGenerator.generateHeight(j, i), z);
                Vector3f normal = generateNormal(j, i, 16, heightGenerator);
                addValues(normals, pointer, normal.x, normal.y, normal.z);
                addValues(textureCoords, pointer, x/Variables.TERRAIN_SIZE, z/Variables.TERRAIN_SIZE);
            }
        pointer = 0;
        for(int i = 0; i < Variables.TERRAIN_VERTEX_COUNT - 1; i++){
            for(int j = 0; j < Variables.TERRAIN_VERTEX_COUNT - 1; j++){
                int topLeft = (i * Variables.TERRAIN_VERTEX_COUNT) + j, topRight = topLeft + 1, bottomLeft = ((i + 1) * Variables.TERRAIN_VERTEX_COUNT) + j, bottomRight = bottomLeft + 1;
                pointer = addValues(indices, pointer, topLeft, bottomLeft, topRight, topRight, bottomLeft, bottomRight);
            }
        }
        return new Vao()
                .bind()
                .storeIndexData(indices)
                .storeData(0, vertices, 3)
                .storeData(1, textureCoords, 2)
                .storeData(2, normals, 3)
                .unbind();
    }

    private static void addValues(float[] array, int pointer, float... values) {
        for(int i = 0, n = values.length; i < n; i++)
            array[pointer*n+i] = values[i];
    }

    private static int addValues(int[] array, int pointer, int... values) {
        for (int value : values)
            array[pointer++] = value;
        return pointer;
    }

    private static Vector3f generateNormal(int x, int z, float strength, HeightGenerator heightGenerator) {
        Vector3f normal = new Vector3f((heightGenerator.generateHeight(x-1, z) - 50f) - (heightGenerator.generateHeight(x+1, z) - 50f), 2f, (heightGenerator.generateHeight(x, z-1) - 50f) - (heightGenerator.generateHeight(x, z+1) - 50f));
        return normal.normalize();
    }

}
