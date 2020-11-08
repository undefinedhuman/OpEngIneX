package de.undefinedhuman.core.opengl.shader.uniforms;

import org.joml.Matrix3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

public class UniformMatrix3 extends Uniform {

    private Matrix3f currentValue = new Matrix3f();
    private FloatBuffer buffer = BufferUtils.createFloatBuffer(9);

    public UniformMatrix3(String name) {
        super(name);
    }

    public void loadValue(Matrix3f value) {
        if (loaded && currentValue.equals(value)) return;
        currentValue.set(value);
        currentValue.get(buffer);
        GL20.glUniformMatrix3fv(getLocation(), false, buffer);
        loaded = true;
    }

}
