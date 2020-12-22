package de.undefinedhuman.engine.opengl.fbos.attachments;

import de.undefinedhuman.engine.opengl.fbos.Attachment;
import org.lwjgl.opengl.GL30;

public class RenderBufferAttachment extends Attachment {

    private final int format;

    public RenderBufferAttachment(int format) {
        this.format = format;
    }

    @Override
    public void init(int attachment, int width, int height, int samples) {
        int buffer = GL30.glGenRenderbuffers();
        super.setBufferID(buffer);
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, buffer);
        GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, samples, format, width, height);
        GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, attachment, GL30.GL_RENDERBUFFER, buffer);
    }

    @Override
    public void delete() {
        GL30.glDeleteRenderbuffers(super.getBufferID());
    }

}
