package de.undefinedhuman.engine.opengl.shader.uniforms;

public abstract class UniformArray {

    private Uniform[] uniforms = new Uniform[0];

    protected void setUniforms(Uniform... uniforms) {
        this.uniforms = uniforms;
    }

    public Uniform[] getUniforms() {
        return uniforms;
    }

}
