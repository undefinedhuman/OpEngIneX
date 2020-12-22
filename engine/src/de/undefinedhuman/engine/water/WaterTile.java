package de.undefinedhuman.engine.water;

import de.undefinedhuman.engine.opengl.Vao;
import de.undefinedhuman.core.transform.Transform;
import de.undefinedhuman.core.utils.Variables;
import org.lwjgl.opengl.GL11;

public class WaterTile extends Transform {

    private Vao vao;

    public WaterTile(int x, int z, float height) {
        this.vao = new Vao()
                .bind()
                .storeData(0, new float[] { -1, -1, -1, 1, 1, -1, 1, -1, -1, 1, 1, 1 }, 2)
                .setVertexCount(6)
                .unbind();
        setPosition(x * Variables.TERRAIN_SIZE + Variables.TERRAIN_SIZE/2f, height, z * Variables.TERRAIN_SIZE + Variables.TERRAIN_SIZE/2);
        setScale(Variables.TERRAIN_SIZE/2f);
    }

    public void update(float delta) {
        updateMatrices();
    }

    public void render(WaterShader shader) {
        vao.start();
        shader.loadUniforms(this);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vao.getVertexCount());
        vao.stop();
    }

    public void delete() {
        this.vao.delete();
    }

}
