package io.github.dulidanci.lineofdominoes.game.states;

import io.github.dulidanci.lineofdominoes.render.RenderContext;

public class GameStateManager {
    private GameState currentState;

    public void set(GameState newState) {
        if (currentState != null) {
            currentState.exit();
            currentState.dispose();
        }

        currentState = newState;
        currentState.enter();
    }

    public void update(float delta) {
        if (currentState != null) {
            currentState.update(delta);
        }
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public RenderContext getRenderContext() {
        return new RenderContext().from(currentState);
    }
}
