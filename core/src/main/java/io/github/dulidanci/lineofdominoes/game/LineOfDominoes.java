package io.github.dulidanci.lineofdominoes.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import io.github.dulidanci.lineofdominoes.game.states.LevelState;
import io.github.dulidanci.lineofdominoes.game.states.GameStateManager;
import io.github.dulidanci.lineofdominoes.input.InputSystem;
import io.github.dulidanci.lineofdominoes.render.RenderSystem;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class LineOfDominoes extends ApplicationAdapter {
    public static final int HEIGHT = 12;
    public static final int WIDTH = 20;
    public static final int PIXEL_DENSITY = 24;

    private InputSystem inputSystem;
    private RenderSystem renderSystem;
    private GameStateManager gsm;

    @Override
    public void create() {
        inputSystem = new InputSystem();
        Gdx.input.setInputProcessor(inputSystem);

        renderSystem = new RenderSystem();
        gsm = new GameStateManager();

        gsm.push(new LevelState());
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        inputSystem.update();
        gsm.update(delta, inputSystem);
        renderSystem.render(delta, gsm.getAllStates());
    }

    @Override
    public void pause() {
        gsm.getCurrentState().pause();
    }

    @Override
    public void resume() {
        gsm.getCurrentState().resume();
    }

    @Override
    public void resize(int width, int height) {
        gsm.getCurrentState().resize(width, height);
        renderSystem.resize(width, height);
    }

    @Override
    public void dispose() {
        renderSystem.dispose();
        gsm.disposeAll();
    }
}
