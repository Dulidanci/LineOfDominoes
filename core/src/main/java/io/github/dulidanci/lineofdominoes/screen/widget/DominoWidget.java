package io.github.dulidanci.lineofdominoes.screen.widget;

import com.badlogic.gdx.math.Vector2;
import io.github.dulidanci.lineofdominoes.assets.AtlasIds;
import io.github.dulidanci.lineofdominoes.domino.DominoSide;
import io.github.dulidanci.lineofdominoes.input.InputSystem;
import io.github.dulidanci.lineofdominoes.level.movement.Direction;
import io.github.dulidanci.lineofdominoes.render.DrawContext;
import io.github.dulidanci.lineofdominoes.render.RenderLayer;
import io.github.dulidanci.lineofdominoes.util.Pair;

public class DominoWidget extends ClickableWidget {
    private final Pair<DominoSide, DominoSide> sides;
    private final int index;
    private Direction rotation;
    private final float anchorX;
    private final float anchorY;

    protected DominoWidget(Builder builder) {
        super(builder);
        this.sides = builder.sides;
        this.index = builder.index;
        this.rotation = Direction.UP;
        this.anchorX = builder.x;
        this.anchorY = builder.y;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void handleInput(float delta, InputSystem inputSystem) {
        if (disabled) return;

        super.handleInput(delta, inputSystem);

        if (held) {
            this.move(new Vector2(inputSystem.getMouse().deltaWorldX, inputSystem.getMouse().deltaWorldY));
        }
    }

    @Override
    public void render(float delta, DrawContext drawContext) {
        drawContext.draw(drawContext.getAssetManager().get(AtlasIds.DOMINO.path(), AtlasIds.DOMINO.type()).findRegion(
            "domino_" + sides.getFirst().ordinal() + "_" + rotation.name().toLowerCase()),
            getCenterX() - 12 + rotation.getOpposite().getVector().x() * 12, getCenterY() - 12 + rotation.getOpposite().getVector().y() * 12,
            width / 2, height / 2, width, height / 2, 1, 1, 0);
        drawContext.draw(drawContext.getAssetManager().get(AtlasIds.DOMINO.path(), AtlasIds.DOMINO.type()).findRegion(
            "domino_" + sides.getSecond().ordinal() + "_" + rotation.getOpposite().name().toLowerCase()),
            getCenterX() - 12 + rotation.getVector().x() * 12, getCenterY() - 12 + rotation.getVector().y() * 12,
            width / 2, height / 2, width, height / 2, 1, 1, 0);
    }

    /** Adds a given amount of state rotations to the {@link DominoWidget}.
     * After every 4 rotations, it does a full circle, so it returns to the original place.
      * @param rotations the number of rotations anti-clockwise to be added
     */
    public void rotate(int rotations) {
        this.rotation = Direction.values()[((this.rotation.ordinal() + rotations) % 4 + 4) % 4];
    }

    /** Sets rotation to a given {@link Direction}.
     * @param direction the new direction
     */
    public void rotate(Direction direction) {
        this.rotation = direction;
    }

    @Override
    public boolean contains(float mouseX, float mouseY) {
        if (rotation.isVertical())
            return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
        else {
            float x = this.x + this.width / 2 - this.height / 2;
            float y = this.y + this.height / 2 - this.width / 2;
            return mouseX >= x && mouseX <= x + this.height && mouseY >= y && mouseY <= y + this.width;
        }
    }

    public int getIndex() {
        return index;
    }

    public float getAnchorX() {
        return anchorX;
    }

    public float getAnchorY() {
        return anchorY;
    }

    public float getCenterX() {
        return x + width / 2;
    }

    public float getCenterY() {
        return y + height / 2;
    }

    public Direction getRotation() {
        return rotation;
    }

    public static class Builder extends ClickableWidget.Builder {
        private final Pair<DominoSide, DominoSide> sides;
        private final int index;

        public Builder(float width, float height, RenderLayer layer, Pair<DominoSide, DominoSide> sides, int index) {
            super(width, height, layer);
            this.sides = sides;
            this.index = index;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public DominoWidget build() {
            return new DominoWidget(this);
        }
    }
}
