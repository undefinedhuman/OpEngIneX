package de.undefinedhuman.core.entity.shader;

import de.undefinedhuman.core.Engine;
import de.undefinedhuman.core.light.LightManager;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformFloat;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformVector3;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.window.Time;

public class LightShader extends EntityShader {

    public UniformVector3
            lightPosition = new UniformVector3("lightPosition"),
            lightColor = new UniformVector3("lightColor"),
            cameraPosition = new UniformVector3("cameraPosition");

    public UniformFloat
            ambientValue = new UniformFloat("ambientValue"),
            specularStrength = new UniformFloat("specularStrength"),
            shineDamper = new UniformFloat("shineDamper"),
            time = new UniformFloat("time");

    public LightShader(String shaderPath, String... attributes) {
        super(shaderPath, attributes);
        super.initUniforms(lightPosition, lightColor, ambientValue, cameraPosition, specularStrength, shineDamper, time);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        lightPosition.loadValue(LightManager.instance.getSun().getPosition());
        lightColor.loadValue(LightManager.instance.getSun().getColor());
        ambientValue.loadValue(Variables.AMBIENT_VALUE);
        specularStrength.loadValue(0.1f);
        shineDamper.loadValue(2);
    }

    @Override
    public void loadUniforms() {
        super.loadUniforms();
        cameraPosition.loadValue(Engine.camera.getPosition());
        time.loadValue(Time.getElapsedTime());
    }

}
