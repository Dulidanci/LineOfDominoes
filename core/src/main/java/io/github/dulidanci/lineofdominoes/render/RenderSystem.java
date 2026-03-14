package io.github.dulidanci.lineofdominoes.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.dulidanci.lineofdominoes.game.LineOfDominoes;
import io.github.dulidanci.lineofdominoes.game.states.GameState;

public class RenderSystem {
    public static final int VIRTUAL_WIDTH = LineOfDominoes.WIDTH * LineOfDominoes.PIXEL_DENSITY;
    public static final int VIRTUAL_HEIGHT = (LineOfDominoes.HEIGHT + 3) * LineOfDominoes.PIXEL_DENSITY;
    private final FrameBuffer frameBuffer;
    private final SpriteBatch batch;
    private final FitViewport screenViewport;

    public RenderSystem() {
        this.frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
        this.batch = new SpriteBatch();

        this.screenViewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        this.screenViewport.apply();
    }

    public void render(float delta, GameState[] states) {
        this.frameBuffer.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (GameState state : states) {

            Viewport viewport = state.getViewport();

            this.batch.setProjectionMatrix(viewport.getCamera().combined);
            this.batch.begin();

            DrawContext drawContext = new DrawContext(this.batch);

            state.render(delta, drawContext);

            this.batch.end();
        }

        this.frameBuffer.end();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.screenViewport.apply();

        this.batch.setProjectionMatrix(this.screenViewport.getCamera().combined);
        this.batch.begin();

        Texture bufferTexture = this.frameBuffer.getColorBufferTexture();

        bufferTexture.setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );

        this.batch.draw(bufferTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT, 0, 0, 1, 1);

        this.batch.end();
    }

    public void resize(int width, int height) {
        this.screenViewport.update(width, height, true);
    }

    public void dispose() {
        batch.dispose();
        frameBuffer.dispose();
    }
}
