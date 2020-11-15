package de.undefinedhuman.core.world.generation;

import de.undefinedhuman.core.opengl.Vao;
import de.undefinedhuman.core.utils.Variables;

public class TerrainGenerator {

    public static Vao generateTerrain(HeightGenerator heightGenerator) {
        int verticesCount = Variables.TERRAIN_VERTEX_COUNT * Variables.TERRAIN_VERTEX_COUNT;
        float[] vertices = new float[verticesCount * 3], textureCoords = new float[verticesCount * 2], normals = new float[verticesCount * 3];
        int[] indices = new int[6 * (Variables.TERRAIN_VERTEX_COUNT - 1) * (Variables.TERRAIN_VERTEX_COUNT - 1)];
        int pointer = 0;
        for(int i = 0; i < Variables.TERRAIN_VERTEX_COUNT; i++)
            for(int j = 0; j < Variables.TERRAIN_VERTEX_COUNT; j++) {
                float x = (float) j / ((float) Variables.TERRAIN_VERTEX_COUNT - 1) * Variables.TERRAIN_SIZE,
                        z = (float) i / ((float) Variables.TERRAIN_VERTEX_COUNT - 1) * Variables.TERRAIN_SIZE;
                addVertices(vertices, pointer, x, heightGenerator.generateHeight(j, i), z);
                addNormalCoords(normals, pointer, 0, 1, 0);
                addTextureCoords(textureCoords, pointer, x/Variables.TERRAIN_SIZE, z/Variables.TERRAIN_SIZE);
                pointer++;
            }
        pointer = 0;
        for(int i = 0; i < Variables.TERRAIN_VERTEX_COUNT - 1; i++){
            for(int j = 0; j < Variables.TERRAIN_VERTEX_COUNT - 1; j++){
                int topLeft = (i * Variables.TERRAIN_VERTEX_COUNT) + j, topRight = topLeft + 1, bottomLeft = ((i + 1) * Variables.TERRAIN_VERTEX_COUNT) + j, bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
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

    private static void addVertices(float[] vertices, int pointer, float x, float y, float z) {
        vertices[pointer*3] = x;
        vertices[pointer*3+1] = y;
        vertices[pointer*3+2] = z;
    }

    private static void addTextureCoords(float[] textureCoords, int pointer, float x, float y) {
        textureCoords[pointer*2] = x;
        textureCoords[pointer*2+1] = y;
    }

    private static void addNormalCoords(float[] normals, int pointer, float x, float y, float z) {
        normals[pointer*3] = x;
        normals[pointer*3+1] = y;
        normals[pointer*3+2] = z;
    }

}
