package io.github.dulidanci.lineofdominoes.game.states;

import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.dulidanci.lineofdominoes.input.InputSystem;
import io.github.dulidanci.lineofdominoes.render.DrawContext;

public interface GameState {
    void enter();
    void update(float delta, InputSystem inputSystem);
    void render(float delta, DrawContext drawContext);
    void pause();
    void resume();
    void exit();
    void dispose();
    void resize(int width, int height);
    Viewport getViewport();
}
