package de.undefinedhuman.core.light;

import org.joml.Vector3f;

public class Light {

    private Vector3f color = new Vector3f(), position = new Vector3f();

    public Light() { }

    public Light(Vector3f position, Vector3f color) {
        this.position.set(position);
        this.color.set(color);
    }

    public Light setColor(float x, float y, float z) {
        this.color.set(x, y, z);
        return this;
    }

    public Light setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
        return this;
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector3f getPosition() {
        return position;
    }

}
