package de.undefinedhuman.core.entity.shader;

import de.undefinedhuman.core.opengl.shader.ShaderProgram;

public enum ShaderType {

    STATIC(StaticShader.class),
    GROWTH(GrowthShader.class);

    private Class<? extends ShaderProgram> shaderType;

    ShaderType(Class<? extends ShaderProgram> shaderType) {
        this.shaderType = shaderType;
    }

}
