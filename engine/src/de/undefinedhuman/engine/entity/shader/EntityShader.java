package de.undefinedhuman.engine.entity.shader;

import de.undefinedhuman.engine.camera.Camera;
import de.undefinedhuman.engine.opengl.OpenGLUtils;
import de.undefinedhuman.engine.opengl.shader.ShaderProgram;
import de.undefinedhuman.engine.opengl.shader.uniforms.UniformMatrix3;
import de.undefinedhuman.engine.opengl.shader.uniforms.UniformMatrix4;
import de.undefinedhuman.engine.opengl.shader.uniforms.UniformVector4;
import de.undefinedhuman.engine.settings.types.mesh.Mesh;
import de.undefinedhuman.core.transform.Transform;

public class EntityShader extends ShaderProgram {

    public UniformMatrix4
            transformMatrix = new UniformMatrix4("transformMatrix"),
            projectionMatrix = new UniformMatrix4("projectionMatrix"),
            viewMatrix = new UniformMatrix4("viewMatrix");

    public UniformMatrix3 normalMatrix = new UniformMatrix3("normalMatrix");

    public UniformVector4 clipPlane = new UniformVector4("clipPlane");

    public EntityShader(String shaderPath, String... attributes) {
        super(shaderPath, attributes);
        addVertexShader().addFragmentShader().compileShader();
        super.initUniforms(transformMatrix, projectionMatrix, viewMatrix, normalMatrix, clipPlane);
    }

    public void resize(int width, int height) {}

    public void loadUniforms() {
        projectionMatrix.loadValue(Camera.instance.updateProjectionMatrix());
        viewMatrix.loadValue(Camera.instance.getViewMatrix());
        clipPlane.loadValue(OpenGLUtils.clipPlane);
    }

    public void loadUniforms(Transform transform) {
        transformMatrix.loadValue(transform.getTransformationMatrix());
        normalMatrix.loadValue(transform.getNormalMatrix());
    }

    public void loadUniforms(Mesh mesh) {}

}
