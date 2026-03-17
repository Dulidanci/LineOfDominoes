package io.github.dulidanci.lineofdominoes.screen.widget;

import io.github.dulidanci.lineofdominoes.input.InputSystem;
import io.github.dulidanci.lineofdominoes.render.RenderLayer;

import java.util.function.Consumer;

public abstract class ClickableWidget extends Widget {
    protected boolean hovered;
    protected boolean pressed;
    protected boolean held;
    protected boolean released;
    protected final Consumer<ClickableWidget> onPress;
    protected final Consumer<ClickableWidget> onRelease;
    protected final float originX;
    protected final float originY;

    protected ClickableWidget(Builder builder) {
        super(builder);
        this.originX = builder.x;
        this.originY = builder.y;
        this.hovered = false;
        this.pressed = false;
        this.held = false;
        this.released = false;
        this.onPress = builder.onPress;
        this.onRelease = builder.onRelease;
    }

    public void handleInput(float delta, InputSystem inputSystem) {
        if (disabled) return;

        float mouseX = inputSystem.getMouse().worldX;
        float mouseY = inputSystem.getMouse().worldY;

        hovered = contains(mouseX, mouseY);
        pressed = hovered && inputSystem.getMouse().leftJustPressed;
        released = held && inputSystem.getMouse().leftJustReleased;
        held = (pressed || held) && inputSystem.getMouse().leftPressed;

        if (pressed && onPress != null) {
            onPress.accept(this);
        }

        if (released && onRelease != null) {
            onRelease.accept(this);
        }
    }

    public float getOriginX() {
        return originX;
    }

    public float getOriginY() {
        return originY;
    }

    public abstract static class Builder extends Widget.Builder<ClickableWidget.Builder> {
        private Consumer<ClickableWidget> onPress;
        private Consumer<ClickableWidget> onRelease;

        public Builder(float width, float height, RenderLayer layer) {
            super(width, height, layer);
        }

        public Builder onPress(Consumer<ClickableWidget> onPress) {
            this.onPress = onPress;
            return self();
        }

        public Builder onRelease(Consumer<ClickableWidget> onRelease) {
            this.onRelease = onRelease;
            return self();
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
