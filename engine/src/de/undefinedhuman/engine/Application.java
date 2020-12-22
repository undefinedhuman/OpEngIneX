package de.undefinedhuman.engine;

import de.undefinedhuman.engine.game.Game;
import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.engine.window.Time;
import de.undefinedhuman.engine.window.Window;
import org.lwjgl.glfw.GLFW;

public class Application {

    public Application(Game game) {
        new Engine();
        new Time();
        Engine.instance.init();
        if (!Engine.instance.isInitialized()) Log.instance.crash("Engine could not be initialized!");
        Engine.instance.setGame(game);
        Engine.instance.resize(Window.instance.getWidth(), Window.instance.getHeight());
        while (!GLFW.glfwWindowShouldClose(Window.instance.getID())) {
            Time.instance.update();
            Engine.instance.update(Time.getDelta());
            Engine.instance.render();
            Engine.instance.clear();
        }
        Engine.instance.delete();
    }

}
