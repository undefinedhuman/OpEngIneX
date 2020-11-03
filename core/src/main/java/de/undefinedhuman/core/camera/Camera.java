package de.undefinedhuman.core.camera;

import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.window.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Camera extends Manager {

    public static Camera instance;

    protected Vector3f position = new Vector3f(), rotation = new Vector3f();
    protected Matrix4f viewMatrix = new Matrix4f(), projectionMatrix = new Matrix4f();

    public void init() {
        setProjectionMatrix();
    }

    public void resize(int width, int height) {
        setProjectionMatrix();
    }

    public void setProjectionMatrix() {
        float xScale = (float) (1f / Math.tan(Math.toRadians(Variables.FOV_ANGLE / 2f))), yScale = xScale * Window.instance.getAspectRatio();
        projectionMatrix
                .m00(xScale)
                .m11(yScale)
                .m22(-((Variables.FAR_PLANE + Variables.NEAR_PLANE) / Variables.FRUSTUM_LENGTH))
                .m23(-1)
                .m32(-((2 * Variables.NEAR_PLANE * Variables.FAR_PLANE) / Variables.FRUSTUM_LENGTH))
                .m33(0);
    }

    public void setViewMatrix() {
        viewMatrix.identity();
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void addRotation(float x, float y, float z) {
        rotation.add(x, y, z);
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public abstract void update(float delta);

}
