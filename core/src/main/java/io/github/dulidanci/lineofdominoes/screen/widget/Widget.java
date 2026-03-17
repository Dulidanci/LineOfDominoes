package io.github.dulidanci.lineofdominoes.screen.widget;

import com.badlogic.gdx.math.Vector2;
import io.github.dulidanci.lineofdominoes.render.DrawContext;
import io.github.dulidanci.lineofdominoes.render.RenderLayer;

public abstract class Widget {
    protected float x;
    protected float y;
    protected final float width;
    protected final float height;
    protected final RenderLayer layer;
    protected boolean disabled;


    protected Widget(Builder<?> builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.width = builder.width;
        this.height = builder.height;
        this.disabled = builder.disabled;
        this.layer = builder.layer;
    }

    public abstract void update(float delta);

    public abstract void render(float delta, DrawContext drawContext);

    public boolean contains(float mouseX, float mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }

    public void move(Vector2 vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    public void moveTo(Vector2 vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public RenderLayer getLayer() {
        return layer;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public abstract static class Builder<T extends Builder<T>> {
        protected float x = 0;
        protected float y = 0;
        protected final float width;
        protected final float height;
        protected boolean disabled = false;
        protected final RenderLayer layer;

        protected Builder(float width, float height, RenderLayer layer) {
            this.width = width;
            this.height = height;
            this.layer = layer;
        }

        protected abstract T self();

        public T position(float x, float y) {
            this.x = x;
            this.y = y;
            return self();
        }

        public T disabled(boolean disabled) {
            this.disabled = disabled;
            return self();
        }

        public abstract Widget build();
    }
}
