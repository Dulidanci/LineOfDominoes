package io.github.dulidanci.lineofdominoes.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.dulidanci.lineofdominoes.game.LineOfDominoes;

public class RenderSystem {
    private final SpriteBatch batch;
    private final FitViewport viewport;
    private final BackgroundRenderer backgroundRenderer;
    private final DominoRenderer dominoRenderer;

    public RenderSystem() {
        batch = new SpriteBatch();
        viewport = new FitViewport(LineOfDominoes.WIDTH, LineOfDominoes.HEIGHT + 3);
        backgroundRenderer = new BackgroundRenderer(this);
        dominoRenderer = new DominoRenderer(this);
    }

    public void render(RenderContext context) {

        viewport.getCamera().update();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        backgroundRenderer.render(context);
        dominoRenderer.render(context);

        batch.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public SpriteBatch batch() {
        return batch;
    }

    public void dispose() {
        batch.dispose();
    }

}
