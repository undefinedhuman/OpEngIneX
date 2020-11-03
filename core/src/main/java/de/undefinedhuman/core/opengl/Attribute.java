package de.undefinedhuman.core.opengl;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class Attribute {

    private int index;

    public Attribute(int index) {
        this.index = index;
    }

    public void enable() {
        GL20.glEnableVertexAttribArray(index);
    }

    public void disable() {
        GL20.glDisableVertexAttribArray(index);
    }

    public Attribute store(int size) {
        return store(size, GL15.GL_FLOAT, false, 0, 0);
    }

    public Attribute store(int size, int type, boolean normalized, int stride, long pointer) {
        GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
        return this;
    }

}
