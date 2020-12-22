package de.undefinedhuman.engine.opengl.shader.uniforms;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

public class UniformMatrix4 extends Uniform {

    private Matrix4f currentValue = new Matrix4f();
    private FloatBuffer buffer = BufferUtils.createFloatBuffer(16);

    public UniformMatrix4(String name) {
        super(name);
    }

    public void loadValue(Matrix4f value) {
        if (loaded && currentValue.equals(value)) return;
        currentValue.set(value);
        currentValue.get(buffer);
        GL20.glUniformMatrix4fv(getLocation(), false, buffer);
        loaded = true;
    }

}
