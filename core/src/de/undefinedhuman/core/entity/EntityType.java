package de.undefinedhuman.core.entity;

import de.undefinedhuman.core.entity.shader.EntityShader;
import de.undefinedhuman.core.entity.shader.GrowthShader;
import de.undefinedhuman.core.entity.shader.StaticShader;
import de.undefinedhuman.core.log.Log;

import java.lang.reflect.InvocationTargetException;

public enum EntityType {

    STATIC(StaticShader.class),
    GROWTH(GrowthShader.class);

    private final Class<? extends EntityShader> shaderType;

    EntityType(Class<? extends EntityShader> shaderType) {
        this.shaderType = shaderType;
    }

    public EntityShader createNewInstance() {
        try {
            return this.shaderType.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException |NoSuchMethodException | InstantiationException | IllegalAccessException ex) {
            Log.info("Can't create new object instance of shader: " + shaderType.getName() + "\n" + ex.getMessage());
        }
        return null;
    }

}
