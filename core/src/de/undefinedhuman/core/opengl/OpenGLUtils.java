package de.undefinedhuman.core.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

public class OpenGLUtils {

    public static void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(1f, 1f, 1f, 1f);
    }

    public static void enableDepth() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void disableDepth() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public static void enableCulling() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static void disableCulling() {
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public static void enableMSAA() {
        GL11.glEnable(GL13.GL_MULTISAMPLE);
    }

    public static void disableMSAA() {
        GL11.glDisable(GL13.GL_MULTISAMPLE);
    }

    public static void enableSRGB() {
        GL30.glEnable(GL30.GL_FRAMEBUFFER_SRGB);
    }

    public static void disableSRGB() {
        GL30.glDisable(GL30.GL_FRAMEBUFFER_SRGB);
    }

    public static void renderVao(int vertexCount) {
        GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);
    }

}
