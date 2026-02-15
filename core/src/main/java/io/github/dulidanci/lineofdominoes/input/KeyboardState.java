package io.github.dulidanci.lineofdominoes.input;

import com.badlogic.gdx.Gdx;

public class KeyboardState {
    private final boolean[] currentKeys = new boolean[256];
    private final boolean[] previousKeys = new boolean[256];

    public void update() {
        for (int i = 0; i < 256; i++) {
            previousKeys[i] = currentKeys[i];
            currentKeys[i] = Gdx.input.isKeyPressed(i);
        }
    }

    public boolean isPressed(int key) {
        return currentKeys[key];
    }

    public boolean isJustPressed(int key) {
        return currentKeys[key] && !previousKeys[key];
    }

    public boolean isJustReleased(int key) {
        return !currentKeys[key] && previousKeys[key];
    }
}
