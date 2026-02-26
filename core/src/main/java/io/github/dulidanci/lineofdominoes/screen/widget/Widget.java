package io.github.dulidanci.lineofdominoes.screen.widget;

import com.badlogic.gdx.math.Vector2;
import io.github.dulidanci.lineofdominoes.input.InputSystem;
import io.github.dulidanci.lineofdominoes.screen.Layer;

import java.util.function.Consumer;

public abstract class Widget {
    protected float x;
    protected float y;
    protected final float width;
    protected final float height;
    protected final Layer layer;
    protected final Consumer<Widget> onPress;
    protected final Consumer<Widget> onRelease;
    protected boolean disabled;
    protected boolean hovered;
    protected boolean pressed;
    protected boolean held;
    protected boolean released;

    protected Widget(Builder<?> builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.width = builder.width;
        this.height = builder.height;
        this.hovered = false;
        this.pressed = false;
        this.held = false;
        this.released = false;
        this.disabled = builder.disabled;
        this.layer = builder.layer;
        this.onPress = builder.onPress;
        this.onRelease = builder.onRelease;
    }

    public void update(float delta) {
    }

    public void handleInput(float delta, InputSystem inputSystem) {
        if (this.disabled) return;

        float mouseX = inputSystem.getMouse().worldX;
        float mouseY = inputSystem.getMouse().worldY;

        this.hovered = this.contains(mouseX, mouseY);
        this.pressed = this.hovered && inputSystem.getMouse().leftJustPressed;
        this.released = this.held && inputSystem.getMouse().leftJustReleased;
        this.held = (this.pressed || this.held) && inputSystem.getMouse().leftPressed;

        if (this.pressed && this.onPress != null) {
            this.onPress.accept(this);
        }

        if (this.held) {
            this.move(new Vector2(inputSystem.getMouse().deltaWorldX, inputSystem.getMouse().deltaWorldY));
        }

        if (this.released && this.onRelease != null) {
            this.onRelease.accept(this);
        }
    }

    public boolean contains(float mouseX, float mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }

    public void move(Vector2 vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public Layer getLayer() {
        return layer;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public abstract static class Builder<T extends Builder<T>> {
        private float x = 0;
        private float y = 0;
        private final float width;
        private final float height;
        private boolean disabled = false;
        private final Layer layer;
        private Consumer<Widget> onPress;
        private Consumer<Widget> onRelease;

        protected Builder(float width, float height, Layer layer) {
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

        public T onPress(Consumer<Widget> onPress) {
            this.onPress = onPress;
            return self();
        }

        public T onRelease(Consumer<Widget> onRelease) {
            this.onRelease = onRelease;
            return self();
        }

        public abstract Widget build();
    }
}
