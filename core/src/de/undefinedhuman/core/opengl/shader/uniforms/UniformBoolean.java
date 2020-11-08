package de.undefinedhuman.core.opengl.shader.uniforms;

import de.undefinedhuman.core.file.FileUtils;
import org.lwjgl.opengl.GL20;

public class UniformBoolean extends Uniform {

    private boolean currentValue = false;

    public UniformBoolean(String name) {
        super(name);
    }

    public void loadValue(boolean value) {
        if (loaded && currentValue == value) return;
        currentValue = value;
        GL20.glUniform1f(getLocation(), FileUtils.booleanToInt(value));
        loaded = true;
    }

}
