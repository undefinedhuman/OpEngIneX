package de.undefinedhuman.engine.opengl.shader.uniforms;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL20;

public class UniformVector2 extends Uniform {

    private Vector2f currentValue = new Vector2f();

    public UniformVector2(String name) {
        super(name);
    }

    public void loadValue(Vector2f value) {
        if (loaded && currentValue.equals(value)) return;
        currentValue.set(value);
        GL20.glUniform2f(getLocation(), currentValue.x, currentValue.y);
        loaded = true;
    }

}
