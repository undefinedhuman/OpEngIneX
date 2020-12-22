package de.undefinedhuman.engine.resources.texture;

import de.undefinedhuman.engine.opengl.OpenGLUtils;
import org.lwjgl.opengl.GL11;

public class Texture {

    private int id;
    private boolean enableCulling = true;

    public Texture(int id) {
        this.id = id;
    }

    public void bind() {
        OpenGLUtils.bindTexture(0, id);
        if(!enableCulling) OpenGLUtils.disableCulling();
    }

    public void unbind() {
        OpenGLUtils.enableCulling();
    }

    public Texture setCulling(boolean culling) {
        this.enableCulling = culling;
        return this;
    }

    public int getID() {
        return id;
    }

    public void delete() {
        GL11.glDeleteTextures(id);
    }

}
