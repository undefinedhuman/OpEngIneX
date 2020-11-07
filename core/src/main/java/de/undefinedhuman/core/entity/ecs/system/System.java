package de.undefinedhuman.core.entity.ecs.system;

public interface System {

    void init();
    void resize(int width, int height);
    void update(float delta);
    void delete();

}
