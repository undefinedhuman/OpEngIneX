package de.undefinedhuman.core.resources.texture;

import de.undefinedhuman.core.opengl.OpenGLUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Texture {

    private int id;
    private boolean enableCulling = true;

    public Texture(int id) {
        this.id = id;
    }

    public void bind() {
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
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
