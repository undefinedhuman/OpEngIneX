package de.undefinedhuman.game.screen;

import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.opengl.shader.ShaderProgram;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformMatrix4;

public class TestShader extends ShaderProgram {

    public UniformMatrix4
            projectionMatrix = new UniformMatrix4("projectionMatrix"),
            viewMatrix = new UniformMatrix4("viewMatrix");

    public TestShader() {
        super("marching", "position");
        addVertexShader().addFragmentShader().compileShader();
        super.initUniforms(projectionMatrix, viewMatrix);
    }

    public void loadUniforms() {
        projectionMatrix.loadValue(Camera.instance.updateProjectionMatrix());
        viewMatrix.loadValue(Camera.instance.getViewMatrix());
    }

}
