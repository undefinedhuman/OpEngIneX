package de.undefinedhuman.engine.opengl.shader.uniforms;

import de.undefinedhuman.engine.light.DirectionalLight;

public class UniformDirectional extends UniformArray {

    private UniformFloat intensity;
    private UniformVector3 direction;
    private UniformVector3 color;

    public UniformDirectional(String name) {
        super.setUniforms(intensity = new UniformFloat(name + ".intensity"), direction = new UniformVector3(name + ".direction"), color = new UniformVector3(name + ".color"));
    }

    public void loadUniforms(DirectionalLight directionalLight) {
        this.intensity.loadValue(directionalLight.getIntensity());
        this.direction.loadValue(directionalLight.getDirection());
        this.color.loadValue(directionalLight.getColor());
    }

}
