package de.undefinedhuman.engine.world;

import de.undefinedhuman.engine.opengl.OpenGLUtils;
import de.undefinedhuman.engine.opengl.Vao;
import de.undefinedhuman.engine.resources.texture.Texture;
import de.undefinedhuman.engine.resources.texture.TextureManager;
import de.undefinedhuman.core.transform.Transform;
import de.undefinedhuman.core.utils.Maths;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.engine.world.shader.TerrainShader;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

public class Terrain extends Transform {

    private Vao vao;
    private float[][] heights;
    private TerrainTexture terrainTexture;

    private Vector3f a = new Vector3f(), b = new Vector3f(), c = new Vector3f();

    public Terrain(TerrainTexture terrainTexture, Vao vao, int x, int z, float[][] heights) {
        this.terrainTexture = terrainTexture;
        this.vao = vao;
        this.heights = heights;
        setPosition(x * Variables.TERRAIN_SIZE, 0, z * Variables.TERRAIN_SIZE);
    }

    public void update(float delta) {
        updateMatrices();
    }

    public void render(TerrainShader shader) {
        Texture texture = TextureManager.instance.getTexture(terrainTexture.getTextureName());
        texture.bind();
        vao.start();
        shader.loadUniforms(this);
        OpenGLUtils.renderVao(vao.getVertexCount());
        vao.stop();
        texture.unbind();
    }

    public void delete() {
        this.vao.delete();
    }

    public float getHeightAtPosition(float x, float z) {
        Vector2f relativePosition = new Vector2f(x % Variables.TERRAIN_SIZE, z % Variables.TERRAIN_SIZE);
        Vector2i gridPosition = new Vector2i((int) (relativePosition.x / Variables.TERRAIN_GRID_SIZE), (int) (relativePosition.y / Variables.TERRAIN_GRID_SIZE));
        if(!Maths.isInRange(gridPosition.x, 0, heights.length-2) || !Maths.isInRange(gridPosition.y, 0, heights.length-2)) return 0;
        Vector2f gridCoords = new Vector2f((relativePosition.x % Variables.TERRAIN_GRID_SIZE) / Variables.TERRAIN_GRID_SIZE, (relativePosition.y % Variables.TERRAIN_GRID_SIZE) / Variables.TERRAIN_GRID_SIZE);
        if(gridCoords.x <= (1 - gridCoords.y)) return Maths.baryCentricInterpolation(a.set(0, heights[gridPosition.x][gridPosition.y], 0), b.set(1, heights[gridPosition.x+1][gridPosition.y], 0), c.set(0, heights[gridPosition.x][gridPosition.y + 1], 1), gridCoords);
        else return Maths.baryCentricInterpolation(a.set(1, heights[gridPosition.x + 1][gridPosition.y], 0), b.set(1, heights[gridPosition.x + 1][gridPosition.y + 1], 1), c.set(0, heights[gridPosition.x][gridPosition.y + 1], 1), gridCoords);
    }

}
