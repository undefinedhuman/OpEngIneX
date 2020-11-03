package de.undefinedhuman.core.entity.shader;

import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.opengl.shader.ShaderProgram;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformMatrix3;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformMatrix4;
import de.undefinedhuman.core.transform.Transform;

public class BasicShader extends ShaderProgram {

    public UniformMatrix4
            transformMatrix = new UniformMatrix4("transformMatrix"),
            projectionMatrix = new UniformMatrix4("projectionMatrix"),
            viewMatrix = new UniformMatrix4("viewMatrix");

    public UniformMatrix3 normalMatrix = new UniformMatrix3("normalMatrix");

    public BasicShader(String shaderPath, String... attributes) {
        super(shaderPath, attributes);
        addVertexShader().addFragmentShader().compileShader();
        super.initUniforms(transformMatrix, projectionMatrix, viewMatrix, normalMatrix);
    }

    public void resizeUniforms() {
        this.projectionMatrix.loadValue(Camera.instance.getProjectionMatrix());
    }

    public void loadUniforms() {
        viewMatrix.loadValue(Camera.instance.getViewMatrix());
    }

    public void loadUniforms(Transform transform) {
        transformMatrix.loadValue(transform.getTransformationMatrix());
        normalMatrix.loadValue(transform.getNormalMatrix());
    }

}
