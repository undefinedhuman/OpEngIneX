package de.undefinedhuman.core;

import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.config.ConfigManager;
import de.undefinedhuman.core.config.SettingsManager;
import de.undefinedhuman.core.entity.EntityManager;
import de.undefinedhuman.core.entity.ecs.blueprint.BlueprintManager;
import de.undefinedhuman.core.game.Game;
import de.undefinedhuman.core.input.InputManager;
import de.undefinedhuman.core.language.LanguageManager;
import de.undefinedhuman.core.light.LightManager;
import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.core.manager.ManagerList;
import de.undefinedhuman.core.resources.texture.TextureManager;
import de.undefinedhuman.core.window.Window;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;

public class Engine {

    public static Engine instance;

    private ManagerList managerList, glManagerList;
    private boolean init = false;

    private Game game;

    public Engine() {
        if (instance == null) instance = this;
        managerList = new ManagerList().addManager(new Log(), new SettingsManager(), new ConfigManager(), new LanguageManager());
        glManagerList = new ManagerList().addManager(new InputManager(), new Camera(), new TextureManager(), new LightManager(), new BlueprintManager(), new EntityManager());
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
        managerList.render();
        glManagerList.render();
        game.render();
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
