package io.github.dulidanci.lineofdominoes.input;

public class InputSystem {
    private final MouseState mouse = new MouseState();
    private final KeyboardState keyboard = new KeyboardState();

    public void update() {
        mouse.update();
        keyboard.update();
    }

    public MouseState getMouse() {
        return mouse;
    }

    public KeyboardState getKeyboard() {
        return keyboard;
    }
}
