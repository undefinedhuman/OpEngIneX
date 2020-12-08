package de.undefinedhuman.core.shadows;

import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.utils.VectorUtils;
import de.undefinedhuman.core.window.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class ShadowBox {

	private static final float OFFSET = 10;
	private static final float SHADOW_DISTANCE = 100;

	private Vector3f min = new Vector3f(), max = new Vector3f();

	private Matrix4f lightViewMatrix;

	private float farHeight, farWidth, nearHeight, nearWidth;

	protected ShadowBox(Matrix4f lightViewMatrix) {
		this.lightViewMatrix = lightViewMatrix;
		calculateWidthsAndHeights();
	}

	protected void update() {
		Matrix4f rotation = calculateCameraRotationMatrix();
		Vector3f forwardVector = rotation.transformDirection(new Vector3f(VectorUtils.FORWARD));

		Vector4f[] points = calculateFrustumVertices(rotation, forwardVector, new Vector3f(forwardVector).mul(SHADOW_DISTANCE).add(Camera.instance.getPosition()), new Vector3f(forwardVector).mul(Variables.NEAR_PLANE).add(Camera.instance.getPosition()));

		boolean first = true;
		for (Vector4f point : points) {
			if (first) {
			    min.set(point.x, point.y, point.z);
			    max.set(point.x, point.y, point.z);
				first = false;
				continue;
			}
			if (point.x > max.x) max.x = point.x;
			else if (point.x < min.x) min.x = point.x;
			if (point.y > max.y) max.y = point.y;
			else if (point.y < min.y) min.y = point.y;
			if (point.z > max.z) max.z = point.z;
			else if (point.z < min.z) min.z = point.z;
		}
		max.z += OFFSET;

	}

	protected Vector3f getCenter() {
		Vector4f centerLightInverted = new Matrix4f()
                .set(lightViewMatrix)
                .invert()
                .transform(new Vector4f(new Vector3f(min).add(max).mul(0.5f), 1));
		return new Vector3f(centerLightInverted.x, centerLightInverted.y, centerLightInverted.z);
	}

	protected Vector3f getBounds() {
	    return new Vector3f(max).sub(min);
    }

	private Vector4f[] calculateFrustumVertices(Matrix4f rotation, Vector3f forwardVector, Vector3f centerNear, Vector3f centerFar) {
		Vector3f upVector = rotation.transformDirection(new Vector3f(VectorUtils.Y_AXIS));
		Vector3f rightVector = new Vector3f(forwardVector).cross(upVector);
		Vector3f downVector = new Vector3f(upVector).negate();
		Vector3f leftVector = new Vector3f(rightVector).negate();

		Vector3f farTop = new Vector3f(upVector).mul(farHeight).add(centerFar);
		Vector3f farBottom = new Vector3f(downVector).mul(farHeight).add(centerFar);
		Vector3f nearTop = new Vector3f(upVector).mul(nearHeight).mul(centerNear);
		Vector3f nearBottom = new Vector3f(downVector).mul(nearHeight).add(centerNear);

		Vector4f[] points = new Vector4f[8];
		points[0] = calculateLightSpaceFrustumCorner(farTop, rightVector, farWidth);
		points[1] = calculateLightSpaceFrustumCorner(farTop, leftVector, farWidth);
		points[2] = calculateLightSpaceFrustumCorner(farBottom, rightVector, farWidth);
		points[3] = calculateLightSpaceFrustumCorner(farBottom, leftVector, farWidth);
		points[4] = calculateLightSpaceFrustumCorner(nearTop, rightVector, nearWidth);
		points[5] = calculateLightSpaceFrustumCorner(nearTop, leftVector, nearWidth);
		points[6] = calculateLightSpaceFrustumCorner(nearBottom, rightVector, nearWidth);
		points[7] = calculateLightSpaceFrustumCorner(nearBottom, leftVector, nearWidth);
		return points;
	}

	private Vector4f calculateLightSpaceFrustumCorner(Vector3f startPoint, Vector3f direction, float width) {
		return lightViewMatrix.transform(new Vector4f(new Vector3f(direction).mul(width).add(startPoint), 1f));
	}

	private Matrix4f calculateCameraRotationMatrix() {
	    return new Matrix4f()
                .rotate((float) Math.toRadians(Camera.instance.getRotation().y), VectorUtils.Y_AXIS)
                .rotate((float) Math.toRadians(Camera.instance.getRotation().x), VectorUtils.X_AXIS);
	}

	private void calculateWidthsAndHeights() {
		farWidth = (float) (SHADOW_DISTANCE * Math.tan(Math.toRadians(Camera.instance.getFOV())));
		nearWidth = (float) (Variables.NEAR_PLANE
				* Math.tan(Math.toRadians(Camera.instance.getFOV())));
		farHeight = farWidth / Window.instance.getAspectRatio();
		nearHeight = nearWidth / Window.instance.getAspectRatio();
	}

}
