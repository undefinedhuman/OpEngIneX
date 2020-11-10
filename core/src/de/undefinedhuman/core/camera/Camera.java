package de.undefinedhuman.core.camera;

import de.undefinedhuman.core.input.InputManager;
import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.core.utils.Maths;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.utils.VectorUtils;
import de.undefinedhuman.core.window.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera extends Manager {

    public static Camera instance;

    protected Vector3f position = new Vector3f(), rotation = new Vector3f();
    protected Matrix4f viewMatrix = new Matrix4f(), projectionMatrix = new Matrix4f();
    private Vector3f cameraDirection = new Vector3f(0, 0, -1f), cameraTarget = new Vector3f(), cameraRight = new Vector3f();

    public Camera() {
        if(instance == null) instance = this;
    }

    @Override
    public void init() {
        rotation.set(0, -90f, 0);
        setViewMatrix();
        setProjectionMatrix();
    }

    @Override
    public void resize(int width, int height) {
        setProjectionMatrix();
    }

    @Override
    public void update(float delta) {
        cameraRight.set(cameraDirection).cross(VectorUtils.Y_AXIS).normalize();
        float cameraSpeed = Variables.CAMERA_MOVE_SPEED * delta;
        if (InputManager.instance.isKeyDown(GLFW.GLFW_KEY_W)) position.add(cameraDirection.x * cameraSpeed, cameraDirection.y * cameraSpeed, cameraDirection.z * cameraSpeed);
        if (InputManager.instance.isKeyDown(GLFW.GLFW_KEY_S)) position.sub(cameraDirection.x * cameraSpeed, cameraDirection.y * cameraSpeed, cameraDirection.z * cameraSpeed);
        if (InputManager.instance.isKeyDown(GLFW.GLFW_KEY_A)) position.sub(cameraRight.x * cameraSpeed, cameraRight.y * cameraSpeed, cameraRight.z * cameraSpeed);
        if (InputManager.instance.isKeyDown(GLFW.GLFW_KEY_D)) position.add(cameraRight.x * cameraSpeed, cameraRight.y * cameraSpeed, cameraRight.z * cameraSpeed);
        position.y = Math.max(position.y, 0);
        rotation.x = Maths.clamp(rotation.x, -89f, 89f);
        rotation.y %= 360f;
        float cosPitch = (float) Math.cos(Math.toRadians(rotation.x));
        cameraDirection.set(Math.cos(Math.toRadians(rotation.y)) * cosPitch, Math.sin(Math.toRadians(rotation.x)), Math.sin(Math.toRadians(rotation.y)) * cosPitch).normalize();
        setViewMatrix();
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    private void setProjectionMatrix() {
        float xScale = (float) (1f / Math.tan(Math.toRadians(Variables.FOV_ANGLE / 2f))), yScale = xScale * Window.instance.getAspectRatio();
        projectionMatrix
                .m00(xScale)
                .m11(yScale)
                .m22(-((Variables.FAR_PLANE + Variables.NEAR_PLANE) / Variables.FRUSTUM_LENGTH))
                .m23(-1)
                .m32(-((2 * Variables.NEAR_PLANE * Variables.FAR_PLANE) / Variables.FRUSTUM_LENGTH))
                .m33(0);
    }

    private void setViewMatrix() {
        viewMatrix.identity().lookAt(position, cameraTarget.set(position).add(cameraDirection), VectorUtils.Y_AXIS);
    }

}
