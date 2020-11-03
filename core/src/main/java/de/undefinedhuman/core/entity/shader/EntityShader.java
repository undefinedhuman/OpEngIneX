package de.undefinedhuman.core.entity.shader;

import de.undefinedhuman.core.opengl.shader.uniforms.UniformFloat;
import de.undefinedhuman.core.window.Time;

public class EntityShader extends LightShader {

    public UniformFloat
            time = new UniformFloat("time");

    public EntityShader(String shaderPath, String... attributes) {
        super(shaderPath, attributes);
        super.initUniforms(time);
    }

    @Override
    public void loadUniforms() {
        super.loadUniforms();
        time.loadValue(Time.getElapsedTime());
    }

}
