package de.undefinedhuman.core.world.shader;

import de.undefinedhuman.core.entity.shader.LightShader;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformMatrix4;

public class TerrainShader extends LightShader {

    private UniformMatrix4 shadowMapMatrix = new UniformMatrix4("shadowMapMatrix");

    public TerrainShader() {
        super("terrain", "position", "textureCoords", "normal");
        super.initUniforms(shadowMapMatrix);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        specularStrength.loadValue(0);
        shineDamper.loadValue(32);
    }

}
