package de.undefinedhuman.engine.world.generation;

import de.undefinedhuman.engine.opengl.Vao;
import de.undefinedhuman.core.utils.Maths;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.engine.world.Terrain;
import de.undefinedhuman.engine.world.TerrainTexture;
import org.joml.Vector3f;

public class TerrainGenerator {

    public static Terrain generateTerrain(TerrainTexture terrainTexture, int terrainX, int terrainZ, HeightGenerator heightGenerator) {
        int verticesCount = Variables.TERRAIN_VERTEX_COUNT * Variables.TERRAIN_VERTEX_COUNT;
        float[] vertices = new float[verticesCount * 3], textureCoords = new float[verticesCount * 2], normals = new float[verticesCount * 3];
        int[] indices = new int[6 * (Variables.TERRAIN_VERTEX_COUNT - 1) * (Variables.TERRAIN_VERTEX_COUNT - 1)];
        int pointer;
        float[][] heights = new float[Variables.TERRAIN_VERTEX_COUNT][Variables.TERRAIN_VERTEX_COUNT];
        for(int i = 0; i < Variables.TERRAIN_VERTEX_COUNT; i++)
            for(int j = 0; j < Variables.TERRAIN_VERTEX_COUNT; j++)
                heights[j][i] = heightGenerator.generateHeight((Variables.TERRAIN_VERTEX_COUNT - 1) * terrainX + j, (Variables.TERRAIN_VERTEX_COUNT - 1) * terrainZ + i);

        for(int i = 0; i < Variables.TERRAIN_VERTEX_COUNT; i++)
            for(int j = 0; j < Variables.TERRAIN_VERTEX_COUNT; j++) {
                pointer = i * Variables.TERRAIN_VERTEX_COUNT + j;
                float x = (float) j / ((float) Variables.TERRAIN_VERTEX_COUNT - 1) * Variables.TERRAIN_SIZE,
                        z = (float) i / ((float) Variables.TERRAIN_VERTEX_COUNT - 1) * Variables.TERRAIN_SIZE;
                addValues(vertices, pointer, x, heights[j][i], z);
                Vector3f normal = generateNormal(j, i, heights, (normalX, normalZ) -> heightGenerator.generateHeight((Variables.TERRAIN_VERTEX_COUNT - 1) * terrainX + normalX, (Variables.TERRAIN_VERTEX_COUNT - 1) * terrainZ + normalZ));
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
        Vao vao = new Vao()
                .bind()
                .storeIndexData(indices)
                .storeData(0, vertices, 3)
                .storeData(1, textureCoords, 2)
                .storeData(2, normals, 3)
                .unbind();
        return new Terrain(terrainTexture, vao, terrainX, terrainZ, heights);
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

    private static Vector3f generateNormal(int x, int z, float[][] heights, HeightGenerator heightGenerator) {
        return new Vector3f(getHeight(x-1, z, heights, heightGenerator) - getHeight(x+1, z, heights, heightGenerator), 2f, getHeight(x, z-1, heights, heightGenerator) - getHeight(x, z+1, heights, heightGenerator)).normalize();
    }

    private static float getHeight(int x, int z, float[][] heights, HeightGenerator heightGenerator) {
        if(!Maths.isInRange(x, 0, heights.length-1) || !Maths.isInRange(z, 0, heights.length-1))
            return heightGenerator.generateHeight(x, z);
        return heights[x][z];
    }

}
