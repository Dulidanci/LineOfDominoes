package io.github.dulidanci.lineofdominoes.screen.widget;

import io.github.dulidanci.lineofdominoes.domino.DominoSide;
import io.github.dulidanci.lineofdominoes.util.Pair;

public class DominoWidget extends Widget {
    public final Pair<DominoSide, DominoSide> sides;

    protected DominoWidget(Builder builder) {
        super(builder);
        this.sides = builder.sides;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Widget.Builder {
        public Pair<DominoSide, DominoSide> sides = Pair.of(DominoSide.getRandomSide(), DominoSide.getRandomSide());

        public Builder sides(Pair<DominoSide, DominoSide> sides) {
            this.sides = sides;
            return this;
        }

        @Override
        public DominoWidget build() {
            return new DominoWidget(this);
        }
    }
}
