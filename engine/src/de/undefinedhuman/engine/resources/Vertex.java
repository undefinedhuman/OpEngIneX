package de.undefinedhuman.engine.resources;

import org.joml.Vector3f;

public class Vertex {

    public int index;
    public Vector3f position = new Vector3f();
    public int textureIndex;
    public int normalIndex;
    public Vertex duplicatedVertex = null;

    public Vertex(int index, Vector3f position) {
        this(index, position, -1, -1);
    }

    public Vertex(int index, Vector3f position, int textureIndex, int normalIndex) {
        this.index = index;
        this.position.set(position);
        this.textureIndex = textureIndex;
        this.normalIndex = normalIndex;
    }

    public boolean compare(int textureIndex, int normalIndex) {
        return this.textureIndex == textureIndex && this.normalIndex == normalIndex;
    }

    public boolean isProcessed() {
        return textureIndex != -1 && normalIndex != -1;
    }

    public Vertex setNormalIndex(int normalIndex) {
        this.normalIndex = normalIndex;
        return this;
    }

    public Vertex setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
        return this;
    }

    public Vertex setDuplicatedVertex(Vertex duplicatedVertex) {
        this.duplicatedVertex = duplicatedVertex;
        return this;
    }

}
