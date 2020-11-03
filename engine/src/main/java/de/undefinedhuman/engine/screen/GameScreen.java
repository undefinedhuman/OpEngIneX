package de.undefinedhuman.engine.screen;

import de.undefinedhuman.core.screen.Screen;

public class GameScreen implements Screen {

    public static GameScreen instance;

    public GameScreen() {
        if(instance == null) instance = this;
    }

    @Override
    public void init() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {

    }

    @Override
    public void delete() {

    }

}
