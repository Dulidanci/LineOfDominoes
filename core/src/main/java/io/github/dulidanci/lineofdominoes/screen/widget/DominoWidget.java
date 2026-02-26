package io.github.dulidanci.lineofdominoes.screen.widget;

import io.github.dulidanci.lineofdominoes.domino.DominoSide;
import io.github.dulidanci.lineofdominoes.screen.Layer;
import io.github.dulidanci.lineofdominoes.util.Pair;

public class DominoWidget extends Widget {
    private final Pair<DominoSide, DominoSide> sides;

    protected DominoWidget(Builder builder) {
        super(builder);
        this.sides = builder.sides;
    }

    public Pair<DominoSide, DominoSide> getSides() {
        return sides;
    }

    public static class Builder extends Widget.Builder<Builder> {
        private final Pair<DominoSide, DominoSide> sides;

        public Builder(float width, float height, Layer layer, Pair<DominoSide, DominoSide> sides) {
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
