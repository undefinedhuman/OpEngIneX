package de.undefinedhuman.core.light;

import org.joml.Vector3f;

public class Light {

    private float intensity;
    private Vector3f color;

    public Light(float intensity, Vector3f color) {
        this.intensity = intensity;
        this.color = color;
    }

    public Light setColor(Vector3f color) {
        return this.setColor(color.x, color.y, color.z);
    }

    public Light setColor(float x, float y, float z) {
        this.color.set(x, y, z);
        return this;
    }

    public Light setIntensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public Vector3f getColor() {
        return color;
    }

    public float getIntensity() {
        return intensity;
    }

}
