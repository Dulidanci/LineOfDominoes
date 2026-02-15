package io.github.dulidanci.lineofdominoes.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import io.github.dulidanci.lineofdominoes.assets.AssetsLoader;
import io.github.dulidanci.lineofdominoes.game.states.LevelState;
import io.github.dulidanci.lineofdominoes.game.states.GameStateManager;
import io.github.dulidanci.lineofdominoes.input.InputSystem;
import io.github.dulidanci.lineofdominoes.render.RenderSystem;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class LineOfDominoes extends Game {
    public static final int HEIGHT = 12;
    public static final int WIDTH = 20;

    private InputSystem inputSystem;
    private RenderSystem renderSystem;
    private GameStateManager gsm;

    @Override
    public void create() {
        AssetsLoader.init();

        inputSystem = new InputSystem();
        renderSystem = new RenderSystem();
        gsm = new GameStateManager();

        gsm.push(new LevelState());
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        inputSystem.update();
        gsm.update(delta, inputSystem);
        renderSystem.render(gsm.getRenderContext());

        super.render();
    }

    @Override
    public void resize(int width, int height) {
        gsm.getCurrentState().resize(width, height);
    }

    @Override
    public void dispose() {
        renderSystem.dispose();
        AssetsLoader.dispose();
        gsm.disposeAll();
    }
}
