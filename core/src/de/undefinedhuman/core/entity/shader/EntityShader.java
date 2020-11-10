package de.undefinedhuman.core.entity.shader;

import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.entity.Entity;
import de.undefinedhuman.core.opengl.shader.ShaderProgram;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformMatrix3;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformMatrix4;

public class EntityShader extends ShaderProgram {

    public UniformMatrix4
            transformMatrix = new UniformMatrix4("transformMatrix"),
            projectionMatrix = new UniformMatrix4("projectionMatrix"),
            viewMatrix = new UniformMatrix4("viewMatrix");

    public UniformMatrix3 normalMatrix = new UniformMatrix3("normalMatrix");

    public EntityShader(String shaderPath, String... attributes) {
        super(shaderPath, attributes);
        addVertexShader().addFragmentShader().compileShader();
        super.initUniforms(transformMatrix, projectionMatrix, viewMatrix, normalMatrix);
    }

    public void resize(int width, int height) {
        projectionMatrix.loadValue(Camera.instance.getProjectionMatrix());
    }

    public void loadUniforms() {
        viewMatrix.loadValue(Camera.instance.getViewMatrix());
    }

    public void loadUniforms(Entity entity) {
        transformMatrix.loadValue(entity.getTransformationMatrix());
        normalMatrix.loadValue(entity.getNormalMatrix());
    }

}
