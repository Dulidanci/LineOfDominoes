package io.github.dulidanci.lineofdominoes.game.states;

import io.github.dulidanci.lineofdominoes.input.InputSystem;
import io.github.dulidanci.lineofdominoes.render.RenderContext;

import java.util.Deque;
import java.util.LinkedList;

public class GameStateManager {
    private final Deque<GameState> stateStack = new LinkedList<>();

    public void push(GameState newState) {
        if (newState != null) {
            if (!stateStack.isEmpty()) {
                stateStack.peek().pause();
            }
            stateStack.push(newState);
            newState.enter();
        }
    }

    public void pop() {
        if (!stateStack.isEmpty()) {
            GameState top = stateStack.pop();
            top.exit();
            top.dispose();
        }

        if (!stateStack.isEmpty()) {
            stateStack.peek().resume();
        }
    }

    public void set(GameState newState) {
        if (!stateStack.isEmpty()) {
            GameState top = stateStack.pop();
            top.exit();
            top.dispose();
        }

        if (newState != null) {
            stateStack.push(newState);
            newState.enter();
        }
    }

    public void update(float delta, InputSystem inputSystem) {
        if (!stateStack.isEmpty()) {
            stateStack.peek().update(delta, inputSystem);
        }
    }

    public GameState getCurrentState() {
        return stateStack.peek();
    }

    public RenderContext getRenderContext() {
        GameState top = stateStack.peek();
        if (top instanceof LevelState levelState) {
            return levelState.getRenderContext();
        }
        return null;
    }

    public void disposeAll() {
        while (!stateStack.isEmpty()) {
            GameState state = stateStack.pop();
            state.exit();
            state.dispose();
        }
    }
}
