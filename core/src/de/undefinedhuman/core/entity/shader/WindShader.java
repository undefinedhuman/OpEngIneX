package de.undefinedhuman.core.entity.shader;

import de.undefinedhuman.core.opengl.shader.uniforms.UniformFloat;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformVector2;
import de.undefinedhuman.core.settings.types.mesh.Mesh;
import de.undefinedhuman.core.transform.Transform;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.window.Time;

public class WindShader extends LightShader {

    public UniformVector2
            windDirection = new UniformVector2("windDirection");

    public UniformFloat
            time = new UniformFloat("time"),
            windFactor = new UniformFloat("windFactor"),
            windFrequency = new UniformFloat("windFrequency"),
            windGustsDistance = new UniformFloat("windGustsDistance"),
            windStrength = new UniformFloat("windStrength");

    public WindShader(String shaderPath, String... attributes) {
        super(shaderPath, attributes);
        super.initUniforms(time, windDirection, windFrequency, windGustsDistance, windFactor, windStrength);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        windDirection.loadValue(Variables.WIND_DIRECTION);
        windFrequency.loadValue(Variables.WIND_FREQUENCY);
        windGustsDistance.loadValue(Variables.WIND_GUSTS_DISTANCE);
        windStrength.loadValue(Variables.WIND_STRENGTH);
    }

    @Override
    public void loadUniforms() {
        super.loadUniforms();
        time.loadValue(Time.getElapsedTime());
    }

    @Override
    public void loadUniforms(Transform transform) {
        super.loadUniforms(transform);
    }

    @Override
    public void loadUniforms(Mesh mesh) {
        super.loadUniforms(mesh);
        windFactor.loadValue(mesh.windFactor.getFloat());
    }

}
