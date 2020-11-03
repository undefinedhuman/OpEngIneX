package de.undefinedhuman.core;

import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.game.Game;
import de.undefinedhuman.core.window.Time;
import de.undefinedhuman.core.window.Window;
import de.undefinedhuman.log.Log;
import org.lwjgl.glfw.GLFW;

public class Application {

    public Application(Game game, Camera camera) {
        Engine.instance = new Engine(camera);
        Time.instance = new Time();
        Engine.instance.init();
        if (!Engine.instance.isInitialized()) Log.instance.crash("Engine could not be initialized!");
        Engine.instance.setGame(game);
        Engine.instance.resize(Window.instance.getWidth(), Window.instance.getHeight());
        while (!GLFW.glfwWindowShouldClose(Window.instance.getID())) {
            Time.instance.update();
            Engine.instance.update(Time.getDelta());
            Engine.instance.render();
        }
        Engine.instance.delete();
    }

}