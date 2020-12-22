package de.undefinedhuman.engine.opengl;

import org.joml.Vector4f;
import org.lwjgl.opengl.*;

public class OpenGLUtils {

    public static Vector4f clipPlane = new Vector4f(0, -1, 0, 10000);

    public static void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL32.glProvokingVertex(GL32.GL_FIRST_VERTEX_CONVENTION);
        GL11.glClearColor(1f, 1f, 1f, 1f);
    }

    public static void enableClipDistance() {
        GL11.glEnable(GL11.GL_CLIP_PLANE0);
    }

    public static void disableClipDistance() {
        GL11.glDisable(GL11.GL_CLIP_PLANE0);
    }

    public static void enableDepth() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void disableDepth() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public static void enableCulling() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL21.GL_BACK);
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

    public static void enableAlphaBlending() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void disableAlphaBlending() {
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void enableSRGB() {
        GL30.glEnable(GL30.GL_FRAMEBUFFER_SRGB);
    }

    public static void disableSRGB() {
        GL30.glDisable(GL30.GL_FRAMEBUFFER_SRGB);
    }

    public static void bindTexture(int unit, int id) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    public static void renderVao(int vertexCount) {
        GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);
    }

    public static void wireframe(boolean enable) {
        if(enable) GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        else GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
    }

}
