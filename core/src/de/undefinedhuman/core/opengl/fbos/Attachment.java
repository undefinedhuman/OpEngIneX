package de.undefinedhuman.core.opengl.fbos;

public abstract class Attachment {

    private int bufferID;
    private boolean isDepthAttachment = false;

    public abstract void init(int attachment, int width, int height, int samples);

    public abstract void delete();

    public int getBufferID() {
        return bufferID;
    }

    protected void setBufferID(int bufferID) {
        this.bufferID = bufferID;
    }

    protected void setDepthAttachment() {
        this.isDepthAttachment = true;
    }

    protected boolean isDepthAttachment() {
        return isDepthAttachment;
    }

}
