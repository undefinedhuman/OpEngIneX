package de.undefinedhuman.core.opengl.shader.uniforms;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

public class UniformVector3 extends Uniform {

    private Vector3f currentValue = new Vector3f();

    public UniformVector3(String name) {
        super(name);
    }

    public void loadValue(Vector3f value) {
        if (loaded && currentValue.equals(value)) return;
        currentValue.set(value);
        GL20.glUniform3f(getLocation(), currentValue.x, currentValue.y, currentValue.z);
        loaded = true;
    }

}
