package de.undefinedhuman.engine.entity.ecs.system;

public interface System {

    void init();
    void resize(int width, int height);
    void update(float delta);
    void render();
    void delete();

}
