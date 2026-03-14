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
        drawContext.draw(AssetsLoader.getAtlas().findRegion("domino_" + sides.getFirst().ordinal() + "_" + Direction.UP.name().toLowerCase()),
            x, y, width / 2, height / 4, width, height / 2, 1, 1, 0);

        drawContext.draw(AssetsLoader.getAtlas().findRegion("domino_" + sides.getSecond().ordinal() + "_" + Direction.DOWN.name().toLowerCase()),
            x, y + 24, width / 2, height * 3 / 4, width, height / 2, 1, 1, 0);
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
