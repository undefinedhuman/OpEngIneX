package de.undefinedhuman.engine.opengl.models;

import de.undefinedhuman.engine.opengl.Vao;

public class QuadGenerator {

    private static final int VERTEX_COUNT = 4;
    private static final float[] VERTICES = { 0, 0, 1, 0, 1, 1, 0, 1 };
    private static final int[] INDICES = { 0, 3, 1, 1, 3, 2 };

    public static Vao generateQuad() {
        return new Vao()
                .bind()
                .storeIndexData(INDICES)
                .storeData(0, VERTICES, 2)
                .unbind();
    }

}
