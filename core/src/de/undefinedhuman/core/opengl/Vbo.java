package de.undefinedhuman.core.opengl;

import org.lwjgl.opengl.GL15;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Vbo {

    private int id, target, usage;

    public Vbo(int target, int usage) {
        id = GL15.glGenBuffers();
        this.target = target;
        this.usage = usage;
        bind();
    }

    public void bind() {
        GL15.glBindBuffer(target, id);
    }

    public void unbind() {
        GL15.glBindBuffer(target, 0);
    }

    public void storeData(IntBuffer buffer) {
        GL15.glBufferData(target, buffer, usage);
    }

    public void storeData(FloatBuffer buffer) {
        GL15.glBufferData(target, buffer, usage);
    }

    public void storeData(ByteBuffer buffer) {
        GL15.glBufferData(target, buffer, usage);
    }

    public void delete() {
        GL15.glDeleteBuffers(id);
    }

}
