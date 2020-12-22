package de.undefinedhuman.engine.input;

public interface Input {
    void keyDown(int key);
    void keyReleased(int key);
    void mouseButtonPressed(int button);
    void mouseButtonReleased(int button);
    void textInput(char input);
    void mousePosition(float x, float y);
    void mouseMoved(float velX, float velY);
    void scrolled(int amount);
}
