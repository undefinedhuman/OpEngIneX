package de.undefinedhuman.core.world.shader;

import de.undefinedhuman.core.entity.shader.EntityShader;
import de.undefinedhuman.core.light.LightManager;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformFloat;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformVector3;
import de.undefinedhuman.core.utils.Variables;

public class TerrainShader extends EntityShader {

    public UniformVector3
            lightPosition = new UniformVector3("lightPosition"),
            lightColor = new UniformVector3("lightColor");

    public UniformFloat
            ambientValue = new UniformFloat("ambientValue");

    public TerrainShader() {
        super("terrain", "position", "textureCoords", "normal");
        super.initUniforms(lightPosition, lightColor, ambientValue);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        lightPosition.loadValue(LightManager.instance.getSun().getPosition());
        lightColor.loadValue(LightManager.instance.getSun().getColor());
        ambientValue.loadValue(Variables.AMBIENT_VALUE);
    }

}
