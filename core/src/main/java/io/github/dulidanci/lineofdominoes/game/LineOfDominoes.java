package io.github.dulidanci.lineofdominoes.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import io.github.dulidanci.lineofdominoes.assets.AssetsLoader;
import io.github.dulidanci.lineofdominoes.game.states.LevelState;
import io.github.dulidanci.lineofdominoes.game.states.GameStateManager;
import io.github.dulidanci.lineofdominoes.render.RenderSystem;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class LineOfDominoes extends Game {
    public static final int HEIGHT = 12;
    public static final int WIDTH = 20;

    private RenderSystem renderSystem;
    private GameStateManager gsm;

    @Override
    public void create() {
        AssetsLoader.init();

        gsm = new GameStateManager();
        renderSystem = new RenderSystem();

        gsm.set(new LevelState(gsm));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float delta = Gdx.graphics.getDeltaTime();

        gsm.update(delta);
        renderSystem.render(gsm.getRenderContext());

        super.render();
    }

    @Override
    public void resize(int width, int height) {
        renderSystem.resize(width, height);
    }

    @Override
    public void dispose() {
        renderSystem.dispose();
        AssetsLoader.dispose();
    }
}
