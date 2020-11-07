package de.undefinedhuman.engine.camera;

import de.undefinedhuman.core.utils.Maths;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.utils.VectorUtils;
import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.input.InputManager;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class EngineCamera extends Camera {

    public static EngineCamera instance;

    private Vector3f cameraDirection = new Vector3f(0, 0, -1f), cameraTarget = new Vector3f(), cameraRight = new Vector3f();

    public EngineCamera() {
        if(instance == null) instance = this;
    }

    @Override
    public void init() {
        rotation.set(0, -90, 0);
        setViewMatrix();
        setProjectionMatrix();
    }

    @Override
    public void update(float delta) {
        cameraRight.set(cameraDirection).cross(VectorUtils.Y_AXIS).normalize();
        float cameraSpeed = Variables.CAMERA_MOVE_SPEED * delta;
        if (InputManager.instance.isKeyDown(GLFW.GLFW_KEY_W)) position.add(cameraDirection.x * cameraSpeed, cameraDirection.y * cameraSpeed, cameraDirection.z * cameraSpeed);
        if (InputManager.instance.isKeyDown(GLFW.GLFW_KEY_S)) position.sub(cameraDirection.x * cameraSpeed, cameraDirection.y * cameraSpeed, cameraDirection.z * cameraSpeed);
        if (InputManager.instance.isKeyDown(GLFW.GLFW_KEY_D)) position.add(cameraRight.x * cameraSpeed, cameraRight.y * cameraSpeed, cameraRight.z * cameraSpeed);
        if (InputManager.instance.isKeyDown(GLFW.GLFW_KEY_A)) position.sub(cameraRight.x * cameraSpeed, cameraRight.y * cameraSpeed, cameraRight.z * cameraSpeed);
        position.y = Math.max(position.y, 0);
        rotation.x = Maths.clamp(rotation.x, -89f, 89f);
        rotation.y %= 360f;
        float cosPitch = (float) Math.cos(Math.toRadians(rotation.x));
        cameraDirection.set(Math.cos(Math.toRadians(rotation.y)) * cosPitch, Math.sin(Math.toRadians(rotation.x)), Math.sin(Math.toRadians(rotation.y)) * cosPitch).normalize();
        setViewMatrix();
    }

    @Override
    public void setViewMatrix() {
        viewMatrix.identity().lookAt(position, cameraTarget.set(position).add(cameraDirection), VectorUtils.Y_AXIS);
    }

}
