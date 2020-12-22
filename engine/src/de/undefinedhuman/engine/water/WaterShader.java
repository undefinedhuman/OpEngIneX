package de.undefinedhuman.engine.water;

import de.undefinedhuman.engine.camera.Camera;
import de.undefinedhuman.engine.entity.shader.EntityShader;
import de.undefinedhuman.engine.light.LightManager;
import de.undefinedhuman.engine.opengl.shader.uniforms.UniformDirectional;
import de.undefinedhuman.engine.opengl.shader.uniforms.UniformFloat;
import de.undefinedhuman.engine.opengl.shader.uniforms.UniformTexture;
import de.undefinedhuman.engine.opengl.shader.uniforms.UniformVector3;
import de.undefinedhuman.core.utils.Variables;

public class WaterShader extends EntityShader {

    public UniformFloat
            waveFactor = new UniformFloat("waveFactor"),
            specularStrength = new UniformFloat("specularStrength"),
            shineDamper = new UniformFloat("shineDamper"),
            fogDensity = new UniformFloat("fogDensity"),
            fogPower = new UniformFloat("fogPower");

    public UniformTexture
            reflectionTexture = new UniformTexture("reflectionTexture"),
            refractionTexture = new UniformTexture("refractionTexture"),
            dudvTexture = new UniformTexture("dudvTexture"),
            normalTexture = new UniformTexture("normalTexture"),
            depthTexture = new UniformTexture("depthTexture");

    public UniformVector3
            cameraPosition = new UniformVector3("cameraPosition"),
            fogColor = new UniformVector3("fogColor");

    public UniformDirectional
            sun = new UniformDirectional("sun");

    public WaterShader() {
        super("water", "position");
        super.initUniforms(sun);
        super.initUniforms(waveFactor, reflectionTexture, refractionTexture, dudvTexture, cameraPosition, normalTexture, depthTexture, specularStrength, shineDamper, fogDensity, fogPower, fogColor);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        specularStrength.loadValue(Variables.WATER_SPECULAR_STRENGTH);
        shineDamper.loadValue(Variables.WATER_SHINE_DAMPER);
        fogDensity.loadValue(Variables.FOG_DENSITY);
        fogPower.loadValue(Variables.FOG_POWER);
        fogColor.loadValue(Variables.FOG_COLOR);
        reflectionTexture.loadValue(0);
        refractionTexture.loadValue(1);
        dudvTexture.loadValue(2);
        normalTexture.loadValue(3);
        depthTexture.loadValue(4);
    }

    public void loadUniforms(float factor) {
        waveFactor.loadValue(factor);
        cameraPosition.loadValue(Camera.instance.getPosition());
        sun.loadUniforms(LightManager.instance.getSun());
    }

}
