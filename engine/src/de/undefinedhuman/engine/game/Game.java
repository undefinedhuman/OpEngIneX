package de.undefinedhuman.engine.game;

import de.undefinedhuman.engine.input.Input;
import de.undefinedhuman.engine.input.InputManager;
import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.core.manager.ManagerList;
import de.undefinedhuman.core.screen.Screen;

public abstract class Game {

    private Screen currentScreen = null;
    private ManagerList manager;

    public Game() {
        manager = new ManagerList();
    }

    public void init() {
        manager.init();
    }

    public void resize(int width, int height) {
        manager.resize(width, height);
        if (currentScreen != null) currentScreen.resize(width, height);
    }

    public void update(float delta) {
        manager.update(delta);
        if (currentScreen != null) currentScreen.update(delta);
    }

    public void render() {
        manager.render();
        if (currentScreen != null) currentScreen.render();
    }

    public void delete() {
        manager.delete();
        if (currentScreen != null) currentScreen.delete();
    }

    public Screen getScreen() {
        return currentScreen;
    }

    public void setScreen(Screen screen) {
        if (currentScreen != null) currentScreen.delete();
        currentScreen = screen;
        currentScreen.init();
    }

    public void setInput(Input input) {
        InputManager.instance.setInput(input);
    }

    public void addManager(Manager... managers) {
        manager.addManager(managers);
    }

    public ManagerList getManagerList() {
        return manager;
    }

}
