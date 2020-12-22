package de.undefinedhuman.engine.opengl.shader.uniforms;

import org.lwjgl.opengl.GL20;

public class UniformTexture extends Uniform {

    private int currentValue = 0;

    public UniformTexture(String name) {
        super(name);
    }

    public void loadValue(int value) {
        if (loaded && currentValue == value) return;
        currentValue = value;
        GL20.glUniform1i(getLocation(), currentValue);
        loaded = true;
    }

}
