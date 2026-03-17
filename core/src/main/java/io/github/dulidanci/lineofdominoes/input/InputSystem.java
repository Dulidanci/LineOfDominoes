package io.github.dulidanci.lineofdominoes.input;

import com.badlogic.gdx.InputProcessor;

public class InputSystem implements InputProcessor {
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        mouse.scrollAmount += amountY;
        return true;
    }
}
