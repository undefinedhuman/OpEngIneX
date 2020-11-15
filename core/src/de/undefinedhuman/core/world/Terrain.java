package de.undefinedhuman.core.world;

import de.undefinedhuman.core.opengl.OpenGLUtils;
import de.undefinedhuman.core.opengl.Vao;
import de.undefinedhuman.core.resources.texture.Texture;
import de.undefinedhuman.core.resources.texture.TextureManager;
import de.undefinedhuman.core.transform.Transform;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.world.generation.HeightGenerator;
import de.undefinedhuman.core.world.generation.TerrainGenerator;
import de.undefinedhuman.core.world.shader.TerrainShader;

public class Terrain extends Transform {

    private Vao vao;
    private TerrainTexture terrainTexture;

    public Terrain(TerrainTexture terrainTexture, int x, int z, HeightGenerator heightGenerator) {
        this.terrainTexture = terrainTexture;
        this.vao = TerrainGenerator.generateTerrain(heightGenerator);
        setPosition(x - Variables.TERRAIN_SIZE/2, 0, z - Variables.TERRAIN_SIZE/2);
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

}
