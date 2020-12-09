package de.undefinedhuman.core.input;

import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.window.Window;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MouseRay {

    private static Vector2f
            mousePositionInNormalizedDeviceCoords = new Vector2f();
    private static Vector4f
            mousePositionInClipCoords = new Vector4f(),
            mousePositionInCameraCoords = new Vector4f(),
            mousePositionInWorldCoords = new Vector4f();
    private static Matrix4f
            invertedProjectionMatrix = new Matrix4f(),
            invertedViewMatrix = new Matrix4f();

    public static Vector3f calculateMouseRay(float x, float y) {
        mousePositionInNormalizedDeviceCoords.set(x, y).mul(2).mul(1f / Window.instance.getWidth(), 1f / Window.instance.getHeight()).sub(1f, 1f);
        mousePositionInClipCoords.set(mousePositionInNormalizedDeviceCoords, -1f, 1f);
        invertedProjectionMatrix.set(Camera.instance.getProjectionMatrix()).invert();
        mousePositionInCameraCoords
                .set(invertedProjectionMatrix.transform(mousePositionInClipCoords))
                .set(mousePositionInCameraCoords.x, mousePositionInCameraCoords.y, -1f, 0);
        invertedViewMatrix.set(Camera.instance.getViewMatrix()).invert();
        mousePositionInWorldCoords.set(invertedViewMatrix.transform(mousePositionInCameraCoords));
        return new Vector3f(mousePositionInWorldCoords.x, mousePositionInWorldCoords.y, mousePositionInWorldCoords.z).normalize();
    }

}
