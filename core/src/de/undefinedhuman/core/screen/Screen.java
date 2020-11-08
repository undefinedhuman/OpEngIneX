package de.undefinedhuman.core.screen;

public interface Screen {

    void init();
    void resize(int width, int height);
    void update(float delta);
    void render();
    void delete();

}
