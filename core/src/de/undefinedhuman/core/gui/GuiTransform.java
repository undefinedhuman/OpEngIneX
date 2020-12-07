package de.undefinedhuman.core.gui;

import de.undefinedhuman.core.opengl.OpenGLUtils;
import de.undefinedhuman.core.opengl.Vao;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

public abstract class GuiTransform {

    private Vao vao;
    private Matrix4f transformationMatrix = new Matrix4f();
    private Vector2f position = new Vector2f(), scale = new Vector2f();

    public GuiTransform() {
        this(0, 0, 1, 1);
    }

    public GuiTransform(float x, float y) {
        this(x, y, 1, 1);
    }

    public GuiTransform(float x, float y, float scaleX, float scaleY) {
        this.position.set(x, y);
        this.scale.set(scaleX, scaleY);
        this.vao = new Vao()
                .bind()
                .setVertexCount(4)
                .storeData(0, new float[] { -1, 1, -1, -1, 1, 1, 1, -1 }, 2)
                .unbind();
    }

    public void resize(int width, int height) { }

    public void update(float delta) {
        transformationMatrix
                .identity()
                .translate(position.x, position.y, 0)
                .scale(scale.x, scale.y, 1f);
    }

    public void render(GuiShader shader) {
        vao.start();
        bindTexture();
        shader.loadUniforms(this);
        OpenGLUtils.disableCulling();
        OpenGLUtils.disableDepth();
        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, vao.getVertexCount());
        OpenGLUtils.enableCulling();
        OpenGLUtils.enableDepth();
        unbindTexture();
        vao.stop();
    }

    public void delete() {
        vao.delete();
    }

    public abstract void bindTexture();
    public abstract void unbindTexture();

    public Matrix4f getTransformationMatrix() {
        return transformationMatrix;
    }

    public GuiTransform setScale(float x, float y) {
        this.scale.set(x, y);
        transformationMatrix
                .identity()
                .translate(position.x, position.y, 0)
                .scale(scale.x, scale.y, 1f);
         return this;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }

}
