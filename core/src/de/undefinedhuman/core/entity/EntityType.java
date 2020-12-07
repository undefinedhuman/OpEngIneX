package de.undefinedhuman.core.entity;

import de.undefinedhuman.core.entity.shader.EntityShader;
import de.undefinedhuman.core.entity.shader.GrowthShader;
import de.undefinedhuman.core.entity.shader.StaticShader;

public enum EntityType {

    STATIC(StaticShader.class),
    GROWTH(GrowthShader.class);

    private Class<? extends EntityShader> shaderType;

    EntityType(Class<? extends EntityShader> shaderType) {
        this.shaderType = shaderType;
    }

    public EntityShader createNewInstance() {
        try {
            return this.shaderType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
