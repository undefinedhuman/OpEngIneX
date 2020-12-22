package de.undefinedhuman.engine.light;

import org.joml.Vector3f;

public class PointLight extends Light {

    private Vector3f position, attenuation;

    public PointLight(float intensity, Vector3f position, Vector3f color, Vector3f attenuation) {
        super(intensity, color);
        this.position = position;
        this.attenuation = attenuation;
    }

    public PointLight setPosition(Vector3f position) {
        return this.setPosition(position.x, position.y, position.z);
    }

    public PointLight setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
        return this;
    }

    public PointLight setAttenuation(Vector3f attenuation) {
        return this.setAttenuation(attenuation.x, attenuation.y, attenuation.z);
    }

    public PointLight setAttenuation(float x, float y, float z) {
        this.attenuation.set(x, y, z);
        return this;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getAttenuation() {
        return attenuation;
    }

}
