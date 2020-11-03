package de.undefinedhuman.core.input;

import de.undefinedhuman.core.config.SettingsManager;
import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.core.window.Window;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.HashSet;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class InputManager extends Manager {

    public static InputManager instance;

    private HashSet<Integer> keysDown = new HashSet<>(), keysPressed = new HashSet<>(), keysReleased = new HashSet<>(),
            buttonsDown = new HashSet<>(), buttonsPressed = new HashSet<>(), buttonsReleased = new HashSet<>();
    private StringBuilder textInput = new StringBuilder();
    private int scrollAmount = 0;
    private Vector2f mousePosition = new Vector2f(), mouseVelocity = new Vector2f(), oldMousePosition = new Vector2f();
    private Input input;
    private boolean firstInput = true;

    public InputManager() {
        if (instance == null) instance = this;
    }

    @Override
    public void init() {
        GLFW.glfwSetInputMode(Window.instance.getID(), GLFW.GLFW_STICKY_KEYS, GL11.GL_TRUE);
        registerKeyboardEvents();
        registerMouseEvents();
    }

    @Override
    public void update(float delta) {
        updateMouse();
        if (input != null) for (int key : keysDown) input.keyDown(key);
    }

    @Override
    public void delete() {
        clear();
    }

    private void registerKeyboardEvents() {
        GLFW.glfwSetCharCallback(Window.instance.getID(), (window, character) -> inputText((char) character));
        GLFW.glfwSetKeyCallback(Window.instance.getID(), this::keyEvent);
    }

    private void registerMouseEvents() {
        GLFW.glfwSetScrollCallback(Window.instance.getID(), (window, scrollX, scrollY) -> scrollEvent((int) scrollY));
        GLFW.glfwSetMouseButtonCallback(Window.instance.getID(), this::buttonEvent);
        GLFW.glfwSetCursorPosCallback(Window.instance.getID(), this::mouseEvent);
    }

    private void updateMouse() {
        if(firstInput) {
            oldMousePosition.set(mousePosition);
            firstInput = false;
        }
        this.mouseVelocity.set(mousePosition.x - oldMousePosition.x, oldMousePosition.y - mousePosition.y);
        oldMousePosition.set(mousePosition);
        if(input != null) input.mouseMoved(mouseVelocity.x, mouseVelocity.y);
    }

    private void inputText(char c) {
        textInput.append(c);
        if (input != null) input.textInput(c);
    }

    private void keyEvent(long window, int key, int scancode, int action, int mods) {
        switch (action) {
            case GLFW.GLFW_PRESS:
                pressKey(key);
                break;
            case GLFW_RELEASE:
                releaseKey(key);
                if (key == SettingsManager.instance.closeKey.getInt()) glfwSetWindowShouldClose(window, true);
                break;
        }
    }

    private void scrollEvent(int amount) {
        this.scrollAmount = amount;
        if(input != null) input.scrolled(scrollAmount);
    }

    private void buttonEvent(long window, int button, int action, int mods) {
        switch (action) {
            case GLFW.GLFW_PRESS:
                pressButton(button);
                break;
            case GLFW_RELEASE:
                releaseButton(button);
                break;
        }
    }

    private void pressKey(int key) {
        keysDown.add(key);
        keysPressed.add(key);
    }

    private void releaseKey(int key) {
        keysDown.remove(key);
        keysReleased.add(key);
        if (input != null) input.keyReleased(key);
    }

    private void pressButton(int button) {
        buttonsDown.add(button);
        buttonsPressed.add(button);
    }

    private void releaseButton(int button) {
        buttonsDown.remove(button);
        buttonsReleased.add(button);
        if (input != null) input.keyReleased(button);
    }

    public void clear() {
        keysPressed.clear();
        keysReleased.clear();
        textInput.setLength(0);
        buttonsPressed.clear();
        buttonsReleased.clear();
        scrollAmount = 0;
    }

    public boolean isKeyDown(int key) {
        return keysDown.contains(key);
    }

    public boolean isKeyJustPressed(int key) {
        return keysPressed.contains(key);
    }

    public boolean isKeyJustReleased(int key) {
        return keysReleased.contains(key);
    }

    public boolean isButtonDown(int button) {
        return buttonsDown.contains(button);
    }

    public String getTextInput() {
        return textInput.toString();
    }

    public int getScrollAmount() {
        return scrollAmount;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    private void mouseEvent(long window, double x, double y) {
        mousePosition.set((float) x, (float) y);
    }

}
