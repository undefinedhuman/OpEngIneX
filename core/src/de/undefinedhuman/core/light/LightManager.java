package de.undefinedhuman.core.light;

import de.undefinedhuman.core.manager.Manager;
import org.joml.Vector3f;

public class LightManager implements Manager {

    public static LightManager instance;

    private DirectionalLight sun;

    private int index = 0;
    private PointLight[] pointLights = new PointLight[5];

    private float angle = 30;

    public LightManager() {
        if(instance == null) instance = this;
    }

    @Override
    public void init() {
        this.sun = new DirectionalLight(1, new Vector3f(1f, 1.0f, 0), new Vector3f(1, 1, 1));
    }

    @Override
    public void update(float delta) {
        // angle += 10f * delta;
        if(angle > 90) {
            sun.setIntensity(0);
            if(angle > 180f) angle = -90;
        } else if(angle <= -80f || angle >= 80f) {
            float intensity = 1 - (Math.abs(angle) - 80f) / 10f;
            sun.setIntensity(intensity).setColor(1, Math.max(intensity, 0.9f), Math.max(intensity, 0.5f));
        } else sun.setIntensity(1).setColor(1, 1, 1);
        float angleInRadians = (float) Math.toRadians(angle);
        sun.setDirection((float) Math.sin(angleInRadians), (float) Math.cos(angleInRadians), 0);
    }

    public DirectionalLight getSun() {
        return sun;
    }

    public void addPointLight(PointLight pointLight) {
        if(index >= pointLights.length) return;
        pointLights[index++] = pointLight;
    }

    public PointLight getPointLight(int index) {
        return pointLights[index];
    }

}
