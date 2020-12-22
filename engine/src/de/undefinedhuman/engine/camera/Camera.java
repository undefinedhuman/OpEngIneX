package de.undefinedhuman.engine.camera;

import de.undefinedhuman.engine.input.InputManager;
import de.undefinedhuman.engine.input.Keys;
import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.core.utils.Maths;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.utils.VectorUtils;
import de.undefinedhuman.engine.window.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera implements Manager {

    public static Camera instance;

    private Vector3f position = new Vector3f(), rotation = new Vector3f();
    private Matrix4f viewMatrix = new Matrix4f(), reflectedViewMatrix = new Matrix4f(), projectionMatrix = new Matrix4f();
    private Vector3f cameraDirection = new Vector3f(0, 0, -1f), cameraTarget = new Vector3f(), cameraRight = new Vector3f();
    private float FOV_ANGLE = Variables.FOV_ANGLE;
    private boolean reflected = false;

    public Camera() {
        if(instance == null) instance = this;
    }

    @Override
    public void init() {
        rotation.set(0, -90f, 0);
        setViewMatrix();
        updateProjectionMatrix();
    }

    @Override
    public void resize(int width, int height) {
        updateProjectionMatrix();
    }

    @Override
    public void update(float delta) {
        cameraRight.set(cameraDirection).cross(VectorUtils.Y_AXIS).normalize();
        float cameraSpeed = (Variables.CAMERA_MOVE_SPEED * (InputManager.instance.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL) ? 10 : 1)) * delta;
        if (InputManager.instance.isKeyDown(Keys.KEY_SPACE)) position.add(0, cameraSpeed, 0);
        if (InputManager.instance.isKeyDown(Keys.KEY_SHIFT)) position.add(0, -cameraSpeed, 0);
        if (InputManager.instance.isKeyDown(Keys.KEY_W)) position.add(cameraDirection.x * cameraSpeed, cameraDirection.y * cameraSpeed, cameraDirection.z * cameraSpeed);
        if (InputManager.instance.isKeyDown(Keys.KEY_S)) position.sub(cameraDirection.x * cameraSpeed, cameraDirection.y * cameraSpeed, cameraDirection.z * cameraSpeed);
        if (InputManager.instance.isKeyDown(Keys.KEY_A)) position.sub(cameraRight.x * cameraSpeed, cameraRight.y * cameraSpeed, cameraRight.z * cameraSpeed);
        if (InputManager.instance.isKeyDown(Keys.KEY_D)) position.add(cameraRight.x * cameraSpeed, cameraRight.y * cameraSpeed, cameraRight.z * cameraSpeed);
        // position.y = Math.max(position.y, TerrainManager.instance.getHeightAtPosition(position.x, position.z) + 0.5f);
        rotation.x = Maths.clamp(rotation.x, -89f, 89f);
        rotation.y %= 360f;
        float cosPitch = (float) Math.cos(Math.toRadians(rotation.x));
        cameraDirection.set(Math.cos(Math.toRadians(rotation.y)) * cosPitch, Math.sin(Math.toRadians(rotation.x)), Math.sin(Math.toRadians(rotation.y)) * cosPitch).normalize();
        setViewMatrix();
    }

    public Matrix4f getViewMatrix() {
        return reflected ? reflectedViewMatrix : viewMatrix;
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

    public Matrix4f updateProjectionMatrix() {
        float yScale = (float) (1f / Math.tan(Math.toRadians(FOV_ANGLE / 2f))), xScale = yScale / Window.instance.getAspectRatio();
        return projectionMatrix
                .m00(xScale)
                .m11(yScale)
                .m22(-((Variables.FAR_PLANE + Variables.NEAR_PLANE) / Variables.FRUSTUM_LENGTH))
                .m23(-1)
                .m32(-((2 * Variables.NEAR_PLANE * Variables.FAR_PLANE) / Variables.FRUSTUM_LENGTH))
                .m33(0);
    }

    public void addFOV(int amount) {
        this.FOV_ANGLE = Maths.clamp(FOV_ANGLE - amount, 1, Variables.FOV_ANGLE);
    }

    public float getFOV() {
        return FOV_ANGLE;
    }

    private Vector3f reflectedPosition = new Vector3f(), reflectedDirection = new Vector3f();

    public void setViewMatrix() {
        viewMatrix
                .identity()
                .lookAt(position, cameraTarget.set(position).add(cameraDirection), VectorUtils.Y_AXIS);
        reflectedPosition.set(position.x, position.y - (2 * (position.y - Variables.WATER_HEIGHT)), position.z);
        float cosPitch = (float) Math.cos(Math.toRadians(-rotation.x));
        reflectedDirection.set(Math.cos(Math.toRadians(rotation.y)) * cosPitch, Math.sin(Math.toRadians(-rotation.x)), Math.sin(Math.toRadians(rotation.y)) * cosPitch).normalize();
        reflectedViewMatrix
                .identity()
                .lookAt(reflectedPosition, cameraTarget.set(reflectedPosition).add(reflectedDirection), VectorUtils.Y_AXIS);
    }

    public void setPosition(Vector3f position) {
        this.setPosition(position.x, position.y, position.z);
    }

    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
    }

    public void setReflected(boolean reflected) {
        this.reflected = reflected;
    }

}
