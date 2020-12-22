package de.undefinedhuman.game;

import de.undefinedhuman.engine.Application;
import de.undefinedhuman.engine.game.Game;
import de.undefinedhuman.game.input.EngineInput;
import de.undefinedhuman.game.screen.TestScreen;

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
