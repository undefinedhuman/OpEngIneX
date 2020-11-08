package de.undefinedhuman.core.transform;

import de.undefinedhuman.core.file.FileReader;
import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.utils.VectorUtils;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

    private Matrix4f transformationMatrix = new Matrix4f();
    private Matrix3f normalMatrix = new Matrix3f();
    private Vector3f position = new Vector3f(), rotation = new Vector3f(), scale = new Vector3f(1, 1, 1);

    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
    }

    public void setPosition(Vector3f position) {
        setPosition(position.x, position.y, position.z);
    }

    public void addPosition(float x, float y, float z) {
        this.position.add(x, y, z);
    }

    public void addPosition(Vector3f position) {
        addPosition(position.x, position.y, position.z);
    }

    public void addPosition(Vector3f direction, float scalar) {
        addPosition(direction.x * scalar, direction.y * scalar, direction.z * scalar);
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.set(x, y, z);
    }

    public void setRotation(Vector3f rotation) {
        setRotation(rotation.x, rotation.y, rotation.z);
    }

    public void addRotation(float x, float y, float z) {
        this.rotation.add(x, y, z);
    }

    public void addRotation(Vector3f rotation) {
        this.rotation.add(rotation);
    }

    public void setScale(float scale) {
        setScale(scale, scale, scale);
    }

    public void setScale(Vector3f scale) {
        setScale(scale.x, scale.y, scale.z);
    }

    public void setScale(float x, float y, float z) {
        this.scale.set(x, y, z);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void updateMatrices() {
        transformationMatrix
                .identity()
                .translate(position)
                .rotate((float) Math.toRadians(rotation.x), VectorUtils.X_AXIS)
                .rotate((float) Math.toRadians(rotation.y), VectorUtils.Y_AXIS)
                .rotate((float) Math.toRadians(rotation.z), VectorUtils.Z_AXIS)
                .scale(scale);
        normalMatrix
                .set(transformationMatrix)
                .transpose()
                .invert();
    }

    public Matrix4f getTransformationMatrix() {
        return transformationMatrix;
    }

    public Matrix3f getNormalMatrix() {
        return normalMatrix;
    }

    public void load(FileReader reader) {
        this.position = reader.getNextVector3();
        this.rotation = reader.getNextVector3();
        this.scale = reader.getNextVector3();
    }

    public void save(FileWriter writer) {
        writer
                .writeVector3(position)
                .writeVector3(rotation)
                .writeVector3(scale);
    }

}
