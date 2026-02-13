package io.github.dulidanci.lineofdominoes.game.states;

public abstract class GameState {
    protected final GameStateManager gsm;

    protected GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void enter();
    public abstract void update(float delta);
    public abstract void exit();
    public abstract void dispose();
}
