package de.undefinedhuman.core.world.shader;

import de.undefinedhuman.core.entity.shader.LightShader;

public class TerrainShader extends LightShader {

    public TerrainShader() {
        super("terrain", "position", "textureCoords", "normal");
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        specularStrength.loadValue(0);
        shineDamper.loadValue(32);
    }

}
