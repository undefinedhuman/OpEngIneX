package de.undefinedhuman.core.gui;

import de.undefinedhuman.core.opengl.shader.ShaderProgram;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformMatrix4;

public class GuiShader extends ShaderProgram {

    public UniformMatrix4 transformationMatrix = new UniformMatrix4("transformationMatrix");

    public GuiShader() {
        super("gui", "position");
        addVertexShader().addFragmentShader().compileShader();
        super.initUniforms(transformationMatrix);
    }

    public void resize(int width, int height) {}

    public void loadUniforms(GuiTransform transform) {
        transformationMatrix.loadValue(transform.getTransformationMatrix());
    }

}
