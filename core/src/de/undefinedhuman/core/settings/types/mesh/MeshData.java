package de.undefinedhuman.core.settings.types.mesh;

public class MeshData {

    public float maxDistance = 0;
    public int[] indicies = new int[0];
    public float[] vertices = new float[0], textureCoords = new float[0], normals = new float[0];

    public MeshData() {}

    public MeshData(float maxDistance, int[] indicies, float[] vertices, float[] textureCoords, float[] normals) {
        this.maxDistance = maxDistance;
        this.indicies = indicies;
        this.vertices = vertices;
        this.textureCoords = textureCoords;
        this.normals = normals;
    }

}
