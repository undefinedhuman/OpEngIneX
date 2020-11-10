package de.undefinedhuman.engine.input;

import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.input.Input;
import de.undefinedhuman.core.input.InputManager;
import de.undefinedhuman.core.input.Mouse;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.window.Time;

public class EngineInput implements Input {

    @Override
    public void keyDown(int key) {

    }

    @Override
    public void keyReleased(int key) {

    }

    @Override
    public void textInput(char input) {

    }

    @Override
    public void mouseMoved(float x, float y) {
        if(InputManager.instance.isButtonDown(Mouse.LEFT))
            Camera.instance.getRotation().add(y * Variables.MOUSE_SENSITIVITY * Time.delta, x * Variables.MOUSE_SENSITIVITY * Time.delta, 0);
    }

    @Override
    public void scrolled(int amount) {

    }

}
