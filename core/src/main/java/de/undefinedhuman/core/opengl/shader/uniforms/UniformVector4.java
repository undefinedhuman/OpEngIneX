package de.undefinedhuman.core.opengl.shader.uniforms;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;

public class UniformVector4 extends Uniform {

    private Vector4f currentValue = new Vector4f();

    public UniformVector4(String name) {
        super(name);
    }

    public void loadValue(Vector4f value) {
        if (loaded && currentValue.equals(value)) return;
        currentValue.set(value);
        GL20.glUniform4f(getLocation(), currentValue.x, currentValue.y, currentValue.z, currentValue.w);
        loaded = true;
    }

}
