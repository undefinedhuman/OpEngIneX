package de.undefinedhuman.core.entity.ecs.system;

import de.undefinedhuman.core.entity.Entity;

public interface System {

    void resize(int width, int height);
    void update(Entity entity, float delta);
    void delete();

}
