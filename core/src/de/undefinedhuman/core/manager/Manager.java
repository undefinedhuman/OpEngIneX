package de.undefinedhuman.core.manager;

public interface Manager {
    default void init() {}
    default void resize(int width, int height) {}
    default void update(float delta) {}
    default void render() {}
    default void delete() {}
}
