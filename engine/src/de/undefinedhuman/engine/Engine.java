package de.undefinedhuman.engine;

import de.undefinedhuman.engine.camera.Camera;
import de.undefinedhuman.engine.config.ConfigManager;
import de.undefinedhuman.engine.config.SettingsManager;
import de.undefinedhuman.engine.game.Game;
import de.undefinedhuman.engine.input.InputManager;
import de.undefinedhuman.engine.language.LanguageManager;
import de.undefinedhuman.engine.light.LightManager;
import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.core.manager.ManagerList;
import de.undefinedhuman.engine.opengl.OpenGLUtils;
import de.undefinedhuman.engine.resources.texture.TextureManager;
import de.undefinedhuman.engine.window.Window;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL32C;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class Engine {

    public static Engine instance;

    private ManagerList managerList, glManagerList;
    private boolean init = false;

    private Game game;

    public Engine() {
        if (instance == null) instance = this;
        managerList = new ManagerList().addManager(new Log() {
            @Override
            public void close() {
                if (Window.instance != null && Engine.instance.isInitialized())
                    glfwSetWindowShouldClose(Window.instance.getID(), true);
            }
        }, new SettingsManager(), new ConfigManager(), new LanguageManager());
        glManagerList = new ManagerList().addManager(new InputManager(), new Camera(), new TextureManager(), new LightManager());
        Window.instance = new Window();
    }

    public void init() {
        managerList.init();
        Window.instance.init();
        Log.info("Engine initialized and running on LWJGL Version " + Version.getVersion());
        GL.createCapabilities();
        init = true;
        glManagerList.init();
    }

    public void resize(int width, int height) {
        GL32C.glViewport(0, 0, width, height);
        managerList.resize(width, height);
        glManagerList.resize(width, height);
        game.resize(width, height);
    }

    public void update(float delta) {
        Window.instance.update();
        managerList.update(delta);
        glManagerList.update(delta);
        game.update(delta);
    }

    public void render() {
        Window.instance.render();
        OpenGLUtils.clipPlane.set(0, -1, 0, 10000000);
        OpenGLUtils.disableClipDistance();
        managerList.render();
        glManagerList.render();
        game.render();
    }

    public void clear() {
        InputManager.instance.clear();
    }

    public void delete() {
        game.delete();
        glManagerList.delete();
        managerList.delete();
        Window.instance.delete();
        System.exit(0);
    }

    public boolean isInitialized() {
        return init;
    }

    public void setGame(Game game) {
        if (game == null) {
            Log.instance.crash("Game instance can't be null!");
            return;
        }
        this.game = game;
        this.game.init();
        Engine.instance.resize((int) Window.instance.getPixelSize().x, (int) Window.instance.getPixelSize().y);
    }

}
