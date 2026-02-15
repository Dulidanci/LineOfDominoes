package io.github.dulidanci.lineofdominoes.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.dulidanci.lineofdominoes.render.renderers.BackgroundRenderer;
import io.github.dulidanci.lineofdominoes.render.renderers.DominoRenderer;

public class RenderSystem {
    private final SpriteBatch batch;
    private final BackgroundRenderer backgroundRenderer;
    private final DominoRenderer dominoRenderer;

    public RenderSystem() {
        batch = new SpriteBatch();
        backgroundRenderer = new BackgroundRenderer(this);
        dominoRenderer = new DominoRenderer(this);
    }

    public void render(RenderContext context) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        context.getViewport().apply();
        context.getViewport().getCamera().update();

        batch.setProjectionMatrix(context.getViewport().getCamera().combined);

        batch.begin();

        backgroundRenderer.render(context);
        dominoRenderer.render(context);

        batch.end();
    }

    public SpriteBatch batch() {
        return batch;
    }

    public void dispose() {
        batch.dispose();
    }

}
