package de.undefinedhuman.core.opengl.shader.uniforms;

import de.undefinedhuman.core.light.PointLight;
import org.joml.Vector3f;

public class UniformPoint extends UniformArray {

    private UniformFloat intensity;
    private UniformVector3 position, color, attenuation;

    public UniformPoint(String name) {
        super.setUniforms(intensity = new UniformFloat(name + ".intensity"), position = new UniformVector3(name + ".position"), color = new UniformVector3(name + ".color"), attenuation = new UniformVector3(name + ".attenuation"));
    }

    public void resizeUniforms() {
        this.intensity.loadValue(0);
        this.position.loadValue(new Vector3f());
        this.color.loadValue(new Vector3f());
        this.attenuation.loadValue(new Vector3f());
    }

    public void loadUniforms(PointLight pointLight) {
        this.intensity.loadValue(pointLight.getIntensity());
        this.position.loadValue(pointLight.getPosition());
        this.color.loadValue(pointLight.getColor());
        this.attenuation.loadValue(pointLight.getAttenuation());
    }

}
