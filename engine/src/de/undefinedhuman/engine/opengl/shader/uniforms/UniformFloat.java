package de.undefinedhuman.engine.opengl.shader.uniforms;

import org.lwjgl.opengl.GL20;

public class UniformFloat extends Uniform {

    private float currentValue = 0;

    public UniformFloat(String name) {
        super(name);
    }

    public void loadValue(float value) {
        if (loaded && currentValue == value) return;
        currentValue = value;
        GL20.glUniform1f(getLocation(), currentValue);
        loaded = true;
    }

}
