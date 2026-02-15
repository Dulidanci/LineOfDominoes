package io.github.dulidanci.lineofdominoes.game.states;

import io.github.dulidanci.lineofdominoes.input.InputSystem;

public interface GameState {
    void enter();
    void update(float delta, InputSystem inputSystem);
    void pause();
    void resume();
    void exit();
    void dispose();
    void resize(int width, int height);
}
