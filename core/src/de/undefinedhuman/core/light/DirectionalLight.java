package de.undefinedhuman.core.light;

import org.joml.Vector3f;

public class DirectionalLight extends Light {

    private Vector3f direction = new Vector3f();

    public DirectionalLight(float intensity, Vector3f direction, Vector3f color) {
        super(intensity, color);
        this.direction.set(direction);
    }

    public DirectionalLight setDirection(float x, float y, float z) {
        this.direction.set(x, y, z);
        return this;
    }

    public Vector3f getDirection() {
        return direction;
    }

}
