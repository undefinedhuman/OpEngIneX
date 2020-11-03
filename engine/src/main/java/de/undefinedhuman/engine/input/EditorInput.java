package de.undefinedhuman.engine.input;

import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.input.Input;
import de.undefinedhuman.core.input.InputManager;
import de.undefinedhuman.core.input.Mouse;
import de.undefinedhuman.core.window.Time;
import de.undefinedhuman.engine.camera.EditorCamera;

public class EditorInput implements Input {

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
            EditorCamera.instance.addRotation(y * Variables.MOUSE_SENSITIVITY * Time.delta, x * Variables.MOUSE_SENSITIVITY * Time.delta, 0);
    }

    @Override
    public void scrolled(int amount) {

    }

}
