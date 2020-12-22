package de.undefinedhuman.engine.window;

import de.undefinedhuman.core.utils.Variables;
import org.lwjgl.glfw.GLFW;

public class Time {

    public static Time instance;

    public static float delta = 0;
    public static float elapsedTime = 0;

    private long lastFrameTime;

    public Time() {
        if(instance == null) instance = this;
        lastFrameTime = getCurrentTime();
    }

    public void update() {
        long currentTime = getCurrentTime();
        delta = Math.min(Variables.MAX_DELTA, (float) (currentTime - lastFrameTime) / Variables.NANOS_IN_SECOND);
        elapsedTime += delta;
        delta *= Variables.DELTA_MULTIPLIER;
        lastFrameTime = currentTime;
    }

    public static float getDelta() {
        return delta;
    }

    public static float getElapsedTime() { return elapsedTime; }

    private long getCurrentTime() {
        return (long) (GLFW.glfwGetTime() * Variables.NANOS_IN_SECOND);
    }

}
