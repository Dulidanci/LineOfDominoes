package io.github.dulidanci.lineofdominoes.screen.widget;

import io.github.dulidanci.lineofdominoes.assets.AssetsLoader;
import io.github.dulidanci.lineofdominoes.domino.DominoSide;
import io.github.dulidanci.lineofdominoes.level.movement.Direction;
import io.github.dulidanci.lineofdominoes.render.DrawContext;
import io.github.dulidanci.lineofdominoes.render.RenderLayer;
import io.github.dulidanci.lineofdominoes.util.Pair;

public class DominoWidget extends Widget {
    private final Pair<DominoSide, DominoSide> sides;

    protected DominoWidget(Builder builder) {
        super(builder);
        this.sides = builder.sides;
    }

    @Override
    public void render(float delta, DrawContext drawContext) {
        drawContext.draw(AssetsLoader.getAtlas().findRegion("domino", this.sides.getFirst().ordinal()),
            x, y, width / 2, height / 4, width, height / 2, 1, 1, Direction.UP.getTurnDegrees());

        drawContext.draw(AssetsLoader.getAtlas().findRegion("domino", this.sides.getSecond().ordinal()),
            x, y + 32, width / 2, height * 3 / 4, width, height / 2, 1, 1, Direction.DOWN.getTurnDegrees());
    }

    public Pair<DominoSide, DominoSide> getSides() {
        return sides;
    }

    public static class Builder extends Widget.Builder<Builder> {
        private final Pair<DominoSide, DominoSide> sides;

        public Builder(float width, float height, RenderLayer layer, Pair<DominoSide, DominoSide> sides) {
            super(width, height, layer);
            this.sides = sides;
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
