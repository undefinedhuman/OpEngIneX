package de.undefinedhuman.engine;

import de.undefinedhuman.core.Application;
import de.undefinedhuman.core.game.Game;
import de.undefinedhuman.engine.input.EngineInput;
import de.undefinedhuman.engine.screen.TestScreen;

public class Main extends Game {

    public static void main(String[] args) {
        new Application(new Main());
    }

    @Override
    public void init() {
        super.init();
        setInput(new EngineInput());
        setScreen(new TestScreen());
    }

}
