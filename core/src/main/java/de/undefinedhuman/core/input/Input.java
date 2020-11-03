package de.undefinedhuman.core.input;

public interface Input {

    void keyDown(int key);
    void keyReleased(int key);
    void textInput(char input);
    void mouseMoved(float x, float y);
    void scrolled(int amount);

}
