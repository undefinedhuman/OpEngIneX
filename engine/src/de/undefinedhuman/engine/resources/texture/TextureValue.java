package de.undefinedhuman.engine.resources.texture;

public class TextureValue {

    public boolean remove = false;
    private int usages = 1;
    private Texture texture;

    public TextureValue(int id) {
        this.texture = new Texture(id);
    }

    public void add() {
        usages++;
    }

    public Texture getTexture() {
        return texture;
    }

    public void remove() {
        usages--;
        if (--usages > 0) return;
        delete();
        remove = true;
    }

    public void delete() {
        texture.delete();
    }

}
