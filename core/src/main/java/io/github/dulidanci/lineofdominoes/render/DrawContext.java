package io.github.dulidanci.lineofdominoes.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DrawContext {
    private final SpriteBatch batch;

    public DrawContext(SpriteBatch batch) {
        this.batch = batch;
    }

    public void draw(Texture texture, float x, float y) {
        batch.draw(texture, x, y);
    }

    public void draw(Texture texture, float x, float y, float width, float height) {
        batch.draw(texture, x, y, width, height);
    }

    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height,
                     float scaleX, float scaleY, float rotation) {
        batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
    }
}
