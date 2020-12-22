package de.undefinedhuman.engine.window;

import de.undefinedhuman.engine.Engine;
import de.undefinedhuman.engine.config.SettingsManager;
import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.engine.opengl.OpenGLUtils;
import de.undefinedhuman.core.utils.Variables;
import org.joml.Vector2f;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    public static Window instance;

    private long id;
    private Vector2f pixelSize = new Vector2f(), screenSize = new Vector2f();
    private Sync sync;

    private float updateTime = 0;

    public Window() {
        if (instance == null) instance = this;
        this.sync = new Sync();
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.out).set();
        if (!glfwInit()) Log.instance.crash("Unable to initialize GLFW!");
        setupWindowHints();
        pixelSize.set(SettingsManager.instance.displayWidth.getInt(), SettingsManager.instance.displayHeight.getInt());
        screenSize.set(SettingsManager.instance.displayWidth.getInt(), SettingsManager.instance.displayHeight.getInt());
        id = glfwCreateWindow((int) pixelSize.x, (int) pixelSize.y, Variables.NAME + " " + Variables.VERSION, NULL, NULL);
        if (id == NULL) Log.instance.crash("Failed to create the GLFW window!");
        glfwMakeContextCurrent(id);
        glfwSwapInterval(1);
        glfwShowWindow(id);

        registerEvents();
    }

    private void setupWindowHints() {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_SAMPLES, SettingsManager.instance.samples.getInt());
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        glfwWindowHint(GLFW_DEPTH_BITS, 24);

        glfwWindowHint(GLFW_SRGB_CAPABLE, GLFW_TRUE);

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    }

    public void update() {
        GLFW.glfwSwapBuffers(id);
        GLFW.glfwPollEvents();
        GLFW.glfwSwapInterval(1);
        if((updateTime += Time.delta) > 1)
            GLFW.glfwSetWindowTitle(id, Variables.NAME + " " + Variables.VERSION + " " + (int) (1f/Time.delta + (updateTime = 0)) + "FPS");
        sync.sync(SettingsManager.instance.fps.getInt());
    }

    public long getID() {
        return id;
    }

    public void render() {
        OpenGLUtils.enableMSAA();
        OpenGLUtils.enableClipDistance();
        OpenGLUtils.enableCulling();
        OpenGLUtils.enableDepth();
        OpenGLUtils.enableSRGB();
        OpenGLUtils.clear();
    }

    public void delete() {
        Callbacks.glfwFreeCallbacks(id);
        glfwDestroyWindow(id);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public float getAspectRatio() {
        return pixelSize.x / pixelSize.y;
    }

    public Vector2f getPixelSize() {
        return pixelSize;
    }

    public Vector2f getScreenSize() {
        return screenSize;
    }

    public int getWidth() {
        return (int) pixelSize.x;
    }

    public int getHeight() {
        return (int) pixelSize.y;
    }

    private void registerEvents() {
        glfwSetFramebufferSizeCallback(id, (window, width, height) -> {
            if (pixelSize.equals(width, height)) return;
            pixelSize.set(width, height);
            Engine.instance.resize(width, height);

        });
        glfwSetWindowSizeCallback(id, (window, width, height) -> {
            if (screenSize.equals(width, height)) return;
            screenSize.set(width, height);
        });
    }

}
