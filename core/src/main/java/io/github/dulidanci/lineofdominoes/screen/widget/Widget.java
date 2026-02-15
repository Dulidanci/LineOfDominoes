package io.github.dulidanci.lineofdominoes.screen.widget;

import com.badlogic.gdx.math.Vector2;
import io.github.dulidanci.lineofdominoes.input.InputSystem;
import io.github.dulidanci.lineofdominoes.screen.Layer;

import java.util.function.Consumer;

public class Widget {
    public float x;
    public float y;
    public float width;
    public float height;
    public boolean hovered;
    public boolean pressed;
    public boolean held;
    public boolean released;
    public boolean disabled;
    public final Layer layer;
    protected String text;
    protected Consumer<Widget> onPress;
    protected Consumer<Widget> onRelease;

    protected Widget(Builder builder) {
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
        this.text = builder.text;
        this.onPress = builder.onPress;
        this.onRelease = builder.onRelease;
    }

    public void update(float delta, InputSystem inputSystem) {
        if (this.disabled) return;

        this.hovered = this.contains(inputSystem.getMouse().worldX, inputSystem.getMouse().worldY);
        this.pressed = this.hovered && inputSystem.getMouse().leftJustPressed;
        this.released = this.held && inputSystem.getMouse().leftJustReleased;
        this.held = this.pressed || this.held && inputSystem.getMouse().leftPressed;

        if (this.pressed) onPress.accept(this);
        if (this.released) onRelease.accept(this);
    }

    public boolean contains(float mouseX, float mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }

    public void move(Vector2 vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private float x = 0;
        private float y = 0;
        private float width = 0;
        private float height = 0;
        private boolean disabled = false;
        private Layer layer = Layer.BACKGROUND;
        private String text = "";
        private Consumer<Widget> onPress;
        private Consumer<Widget> onRelease;

        public Builder width(float width) {
            this.width = width;
            return this;
        }

        public Builder height(float height) {
            this.height = height;
            return this;
        }

        public Builder position(float x, float y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder disabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public Builder layer(Layer layer) {
            this.layer = layer;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder onPress(Consumer<Widget> onPress) {
            this.onPress = onPress;
            return this;
        }

        public Builder onRelease(Consumer<Widget> onRelease) {
            this.onRelease = onRelease;
            return this;
        }

        public Widget build() {
            return new Widget(this);
        }
    }
}
