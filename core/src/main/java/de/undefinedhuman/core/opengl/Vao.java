package de.undefinedhuman.core.opengl;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Vao {

    private int id, vertexCount = 0;
    private ArrayList<Vbo> vbos = new ArrayList<>();
    private ArrayList<Attribute> attributes = new ArrayList<>();

    public Vao() {
        this.id = GL30.glGenVertexArrays();
    }

    public Vao bind() {
        GL30.glBindVertexArray(id);
        return this;
    }

    public Vao unbind() {
        GL30.glBindVertexArray(0);
        return this;
    }

    public void enableAttributes() {
        for (Attribute attribute : attributes) attribute.enable();
    }

    public void disableAttributes() {
        for (Attribute attribute : attributes) attribute.disable();
    }

    public void start() {
        bind();
        enableAttributes();
    }

    public void stop() {
        disableAttributes();
        unbind();
    }

    public Vao storeData(int id, float[] data, int size) {
        Vbo vbo = new Vbo(GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        vbo.storeData(buffer);
        attributes.add(new Attribute(id).store(size));
        vbo.unbind();
        vbos.add(vbo);
        return this;
    }

    public Vao storeIndexData(int[] indices) {
        Vbo vbo = new Vbo(GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.GL_STATIC_DRAW);
        IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
        buffer.put(indices);
        buffer.flip();
        vbo.storeData(buffer);
        vbos.add(vbo);
        return this;
    }

    public Vao storeVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
        return this;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void delete() {
        GL30.glDeleteVertexArrays(id);
        for (Vbo vbo : vbos) vbo.delete();
    }

}
