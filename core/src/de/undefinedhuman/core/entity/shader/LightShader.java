package de.undefinedhuman.core.entity.shader;

import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.light.LightManager;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformFloat;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformVector2;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformVector3;
import de.undefinedhuman.core.transform.Transform;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.window.Time;
import org.joml.Vector2f;

public class LightShader extends EntityShader {

    public UniformVector3
            lightPosition = new UniformVector3("lightPosition"),
            lightColor = new UniformVector3("lightColor"),
            cameraPosition = new UniformVector3("cameraPosition");

    public UniformVector2
            windDirection = new UniformVector2("windDirection");

    public UniformFloat
            ambientValue = new UniformFloat("ambientValue"),
            specularStrength = new UniformFloat("specularStrength"),
            shineDamper = new UniformFloat("shineDamper"),
            time = new UniformFloat("time"),
            windFactor = new UniformFloat("windFactor"),
            windFrequency = new UniformFloat("windFrequency"),
            windGustsDistance = new UniformFloat("windGustsDistance"),
            windStrength = new UniformFloat("windStrength");

    public LightShader(String shaderPath, String... attributes) {
        super(shaderPath, attributes);
        super.initUniforms(lightPosition, lightColor, ambientValue, cameraPosition, specularStrength, shineDamper, time, windDirection, windFrequency, windGustsDistance, windFactor, windStrength);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        lightPosition.loadValue(LightManager.instance.getSun().getPosition());
        lightColor.loadValue(LightManager.instance.getSun().getColor());
        ambientValue.loadValue(Variables.AMBIENT_VALUE);
        specularStrength.loadValue(0.1f);
        shineDamper.loadValue(2);
        windDirection.loadValue(new Vector2f(1, 1));
        windFrequency.loadValue(1);
        windGustsDistance.loadValue(0.25f);
        windStrength.loadValue(0.06f);
    }

    @Override
    public void loadUniforms() {
        super.loadUniforms();
        cameraPosition.loadValue(Camera.instance.getPosition());
        time.loadValue(Time.getElapsedTime());
    }

    @Override
    public void loadUniforms(Transform transform) {
        super.loadUniforms(transform);
        windFactor.loadValue(0.2f);
    }

}
