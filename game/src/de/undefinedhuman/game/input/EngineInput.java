package de.undefinedhuman.game.input;

import de.undefinedhuman.engine.camera.Camera;
import de.undefinedhuman.engine.entity.Entity;
import de.undefinedhuman.engine.entity.EntityManager;
import de.undefinedhuman.engine.entity.ecs.blueprint.BlueprintManager;
import de.undefinedhuman.engine.input.*;
import de.undefinedhuman.core.utils.Maths;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.engine.window.Time;
import de.undefinedhuman.engine.world.TerrainManager;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class EngineInput implements Input {

    private Entity entityToBePlaced;

    private static final int BINARY_ITERATION = 200;
    private static final float RAY_MAX_LENGTH = 600;

    @Override
    public void keyDown(int key) {
    }

    @Override
    public void keyReleased(int key) {
        for(int i = 0; i < 10; i++)
            if(key == GLFW.GLFW_KEY_0 + i) {
                if(entityToBePlaced != null) EntityManager.instance.removeEntity(entityToBePlaced.getWorldID());
                entityToBePlaced = BlueprintManager.instance.getBlueprint(i).createInstance();
                entityToBePlaced.setScale(3f);
                EntityManager.instance.addEntity(entityToBePlaced);
            }

        if(entityToBePlaced != null) {
            if(key == Keys.KEY_R)
                entityToBePlaced.setScale(Maths.clamp(entityToBePlaced.getScale() + 1, 1, 50));
            if(key == Keys.KEY_T)
                entityToBePlaced.setScale(Maths.clamp(entityToBePlaced.getScale() - 1, 1, 50));
        }
    }

    @Override
    public void mouseButtonPressed(int button) {

    }

    @Override
    public void mouseButtonReleased(int button) {
        if(button == Mouse.RIGHT)
            entityToBePlaced = null;
    }

    @Override
    public void textInput(char input) {

    }

    @Override
    public void mousePosition(float x, float y) {
        if(entityToBePlaced != null) {
            Vector3f currentTerrainPoint = calculateCurrentTerrainPoint(MouseRay.calculateMouseRay(x, y));
            if(currentTerrainPoint != null)
                entityToBePlaced.setPosition(currentTerrainPoint);
        }
    }


    @Override
    public void mouseMoved(float velX, float velY) {
        if(InputManager.instance.isButtonDown(Mouse.LEFT))
            Camera.instance.getRotation().add(velY * Variables.MOUSE_SENSITIVITY * (Camera.instance.getFOV()/Variables.FOV_ANGLE) * Time.delta, velX * Variables.MOUSE_SENSITIVITY * (Camera.instance.getFOV()/Variables.FOV_ANGLE) * Time.delta, 0);
    }

    @Override
    public void scrolled(int amount) {
        if(entityToBePlaced != null) entityToBePlaced.getRotation().y = ((entityToBePlaced.getRotation().y + amount * 5) % 360f);
        else Camera.instance.addFOV(amount);
    }

    private Vector3f calculateCurrentTerrainPoint(Vector3f mouseRay) {
        if(intersectionPointInRange(mouseRay, 0, RAY_MAX_LENGTH)) {
            int min = 1, max = 1;
            while(true) {
                max = (int) Math.min(max * 2, RAY_MAX_LENGTH);
                if(isBelowTerrain(calculatePointOnRay(mouseRay, max)))
                    break;
                min = max;
            }
            return binarySearch(0, min, max, mouseRay);
        }
        return null;
    }

    private Vector3f binarySearch(int count, float start, float end, Vector3f ray) {
        float half = start + ((end - start) / 2f);
        if (count >= BINARY_ITERATION) {
            Vector3f endPointOnRay = calculatePointOnRay(ray, half);
            return new Vector3f(endPointOnRay.x, TerrainManager.instance.getHeightAtPosition(endPointOnRay.x, endPointOnRay.z), endPointOnRay.z);
        }
        if (intersectionPointInRange(ray, start, half)) return binarySearch(++count, start, half, ray);
        else return binarySearch(++count, half, end, ray);
    }

    private boolean intersectionPointInRange(Vector3f ray, float start, float end) {
        return !isBelowTerrain(calculatePointOnRay(ray, start)) && isBelowTerrain(calculatePointOnRay(ray, end));
    }

    private boolean isBelowTerrain(Vector3f position) {
        return position.y < TerrainManager.instance.getHeightAtPosition(position.x, position.z);
    }

    private Vector3f calculatePointOnRay(Vector3f ray, float distance) {
        return new Vector3f(Camera.instance.getPosition()).add(ray.x * distance, ray.y * distance, ray.z * distance);
    }

}
