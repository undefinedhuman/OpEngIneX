package de.undefinedhuman.core.entity.shader;

import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.light.LightManager;
import de.undefinedhuman.core.light.PointLight;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformDirectional;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformFloat;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformPoint;
import de.undefinedhuman.core.opengl.shader.uniforms.UniformVector3;
import de.undefinedhuman.core.settings.types.mesh.Mesh;
import de.undefinedhuman.core.transform.Transform;
import de.undefinedhuman.core.utils.Variables;

public class LightShader extends EntityShader {

    public UniformVector3
            cameraPosition = new UniformVector3("cameraPosition"),
            fogColor = new UniformVector3("fogColor");

    public UniformFloat
            ambientValue = new UniformFloat("ambientValue"),
            specularStrength = new UniformFloat("specularStrength"),
            shineDamper = new UniformFloat("shineDamper"),
            fogDensity = new UniformFloat("fogDensity"),
            fogPower = new UniformFloat("fogPower");

    public UniformPoint[]
            pointLights = new UniformPoint[Variables.MAX_POINT_LIGHTS];

    public UniformDirectional
            sun = new UniformDirectional("sun");

    public LightShader(String shaderPath, String... attributes) {
        super(shaderPath, attributes);
        super.initUniforms(sun.getUniforms());
        super.initUniforms(ambientValue, cameraPosition, specularStrength, shineDamper, fogDensity, fogPower, fogColor);
        for(int i = 0; i < pointLights.length; i++) {
            pointLights[i] = new UniformPoint("pointLights[" + i + "]");
            super.initUniforms(pointLights[i].getUniforms());
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        ambientValue.loadValue(Variables.AMBIENT_VALUE);
        fogDensity.loadValue(Variables.FOG_DENSITY);
        fogPower.loadValue(Variables.FOG_POWER);
        fogColor.loadValue(Variables.FOG_COLOR);
        for(int i = 0; i < Variables.MAX_POINT_LIGHTS; i++)
            pointLights[i].resizeUniforms();
    }

    @Override
    public void loadUniforms() {
        super.loadUniforms();
        cameraPosition.loadValue(Camera.instance.getPosition());
        sun.loadUniforms(LightManager.instance.getSun());
        for(int i = 0; i < Variables.MAX_POINT_LIGHTS; i++) {
            PointLight pointLight = LightManager.instance.getPointLight(i);
            if(pointLight != null) pointLights[i].loadUniforms(pointLight);
        }
    }

    @Override
    public void loadUniforms(Transform transform) {
        super.loadUniforms(transform);
    }

    @Override
    public void loadUniforms(Mesh mesh) {
        specularStrength.loadValue(mesh.reflectance.getFloat());
        shineDamper.loadValue(mesh.specularPower.getInt());
    }

}
