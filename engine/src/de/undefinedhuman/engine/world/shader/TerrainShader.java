package de.undefinedhuman.engine.world.shader;

import de.undefinedhuman.engine.entity.shader.LightShader;
import de.undefinedhuman.engine.opengl.shader.uniforms.UniformMatrix4;
import de.undefinedhuman.engine.opengl.shader.uniforms.UniformTexture;
import de.undefinedhuman.engine.shadows.ShadowManager;

public class TerrainShader extends LightShader {

    private UniformMatrix4 shadowMapMatrix = new UniformMatrix4("shadowMapMatrix");
    private UniformTexture shadowMapTexture = new UniformTexture("shadowMapTexture");

    public TerrainShader() {
        super("terrain", "position", "textureCoords", "normal");
        super.initUniforms(shadowMapMatrix, shadowMapTexture);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        specularStrength.loadValue(0);
        shineDamper.loadValue(32);
        shadowMapTexture.loadValue(1);
    }

    @Override
    public void loadUniforms() {
        super.loadUniforms();
        shadowMapMatrix.loadValue(ShadowManager.instance.getShadowMapMatrix());
    }

}
