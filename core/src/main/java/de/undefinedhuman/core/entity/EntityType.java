package de.undefinedhuman.core.entity;

import de.undefinedhuman.core.entity.shader.EntityShader;
import de.undefinedhuman.core.entity.shader.GrowthShader;
import de.undefinedhuman.core.entity.shader.StaticShader;

public enum EntityType {

    STATIC(new StaticShader()),
    DYNAMIC(new StaticShader()),
    GROWTHS(new GrowthShader());

    private EntityShader shader;

    EntityType(EntityShader shader) {
        this.shader = shader;
    }

    public EntityShader getShader() {
        return shader;
    }

    public void resize() {
        shader.bind();
        shader.resizeUniforms();
        shader.unbind();
    }

    public void update() {
        shader.loadUniforms();
    }

    public void update(Entity entity) {
        shader.loadUniforms(entity);
    }

}
