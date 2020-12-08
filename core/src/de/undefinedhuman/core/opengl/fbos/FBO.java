package de.undefinedhuman.core.opengl.fbos;

import de.undefinedhuman.core.window.Window;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.util.HashMap;

public class FBO {

    private final int id, width, height, samples;

    private boolean shadowFBO = false;

    private HashMap<Integer, Attachment> attachments = new HashMap<>();
    private Attachment depthAttachment = null;

    public FBO(int width, int height, int samples) {
        this.id = GL30.glGenFramebuffers();
        this.width = width;
        this.height = height;
        this.samples = samples;
    }

    public FBO bind() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, id);
        return this;
    }

    public FBO unbind() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        return this;
    }

    public FBO addAttachment(int index, Attachment attachment) {
        attachments.put(index, attachment);
        return this;
    }

    public FBO addDepthAttachment(Attachment attachment) {
        this.depthAttachment = attachment;
        attachment.setDepthAttachment();
        return this;
    }

    public FBO setShadowFBO(boolean shadowFBO) {
        this.shadowFBO = shadowFBO;
        return this;
    }

    public FBO init() {
        bind();
        for(Integer id : attachments.keySet())
            attachments.get(id).init(GL30.GL_COLOR_ATTACHMENT0 + id, width, height, samples);
        if(depthAttachment != null)
            depthAttachment.init(GL30.GL_DEPTH_ATTACHMENT, width, height, samples);
        if(shadowFBO) {
            GL11.glDrawBuffer(GL11.GL_NONE);
            GL11.glReadBuffer(GL11.GL_NONE);
        }
        unbind();
        return this;
    }

    public void blitToScreen(int colourIndex) {
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
        GL11.glDrawBuffer(GL11.GL_BACK);
        GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, id);
        GL11.glReadBuffer(GL30.GL_COLOR_ATTACHMENT0 + colourIndex);
        GL30.glBlitFramebuffer(0, 0, width, height, 0, 0, Window.instance.getWidth(), Window.instance.getHeight(),
                GL11.GL_COLOR_BUFFER_BIT, GL11.GL_NEAREST);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    public void blitToFbo(int srcColourIndex, FBO target, int targetColourIndex) {
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, target.id);
        GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0 + targetColourIndex);

        GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, id);
        GL11.glReadBuffer(GL30.GL_COLOR_ATTACHMENT0 + srcColourIndex);

        int bufferBit = depthAttachment != null && target.depthAttachment != null
                ? GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT : GL11.GL_COLOR_BUFFER_BIT;
        GL30.glBlitFramebuffer(0, 0, width, height, 0, 0, target.width, target.height, bufferBit, GL11.GL_NEAREST);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    public int getAttachment(int colorIndex) {
        return attachments.get(colorIndex).getBufferID();
    }

    public int getDepthBuffer() {
        return depthAttachment.getBufferID();
    }

    public void start(int index) {
        GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0 + index);
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, id);
        GL11.glViewport(0, 0, width, height);
    }

    public void stop() {
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
        GL11.glDrawBuffer(GL11.GL_BACK);
        GL11.glViewport(0, 0, Window.instance.getWidth(), Window.instance.getHeight());
    }

    public void startShadow() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, id);
        GL11.glViewport(0, 0, width, height);
    }

    public void stopShadow() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        GL11.glViewport(0, 0, Window.instance.getWidth(), Window.instance.getHeight());
    }

    public void delete() {
        for (Attachment attachment : attachments.values())
            attachment.delete();
        if (depthAttachment != null) depthAttachment.delete();
        GL30.glDeleteFramebuffers(id);
    }

    public boolean isComplete() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, id);
        boolean complete = GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) == GL30.GL_FRAMEBUFFER_COMPLETE;
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        return complete;
    }

}
