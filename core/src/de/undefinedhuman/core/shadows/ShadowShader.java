package de.undefinedhuman.core.shadows;

import de.undefinedhuman.core.opengl.shader.ShaderProgram;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformMatrix4;
import org.joml.Matrix4f;

public class ShadowShader extends ShaderProgram {

    public UniformMatrix4
            tvpMatrix = new UniformMatrix4("tvpMatrix");

	protected ShadowShader() {
		super("shadow", "position", "textureCoords");
        addVertexShader().addFragmentShader().compileShader();
        super.initUniforms(tvpMatrix);
	}


	public void loadUniforms(Matrix4f matrix) {
        tvpMatrix.loadValue(matrix);
    }

}
