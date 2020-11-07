package de.undefinedhuman.engine;

import de.undefinedhuman.core.Application;
import de.undefinedhuman.core.game.Game;
import de.undefinedhuman.engine.camera.EngineCamera;
import de.undefinedhuman.engine.input.EngineInput;
import de.undefinedhuman.engine.screen.GameScreen;

public class Main extends Game {

    public static void main(String[] args) {
        new Application(new Main(), new EngineCamera());
    }

    @Override
    public void init() {
        super.init();
        setInput(new EngineInput());
        setScreen(new GameScreen());
    }

}
