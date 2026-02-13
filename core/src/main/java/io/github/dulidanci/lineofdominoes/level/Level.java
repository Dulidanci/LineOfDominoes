package io.github.dulidanci.lineofdominoes.level;

import io.github.dulidanci.lineofdominoes.domino.DominoSide;
import io.github.dulidanci.lineofdominoes.level.movement.Direction;
import io.github.dulidanci.lineofdominoes.domino.Domino;
import io.github.dulidanci.lineofdominoes.util.Pair;
import io.github.dulidanci.lineofdominoes.level.movement.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Level {
    public final int width;
    public final int height;
    private final ArrayList<Pair<Position, Direction>> path;
    private final Map<Position, Domino> dominoes;

    private Level(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.path = builder.path;
        this.dominoes = builder.dominoes;
    }

    public void addDomino(Position position, Direction direction, DominoSide first, DominoSide second) {
        //if (validator.validateCoordinates(position, direction)) {
        dominoes.put(position,
            new Domino(position, direction, first));
        dominoes.put(position.add(direction.getVector()),
            new Domino(position.add(direction.getVector()), direction.getOpposite(), second));
        //}
    }

    public ArrayList<Domino> getDominoes() {
        ArrayList<Domino> dominoes = new ArrayList<>();
        for (Map.Entry<Position, Domino> entry : this.dominoes.entrySet()) {
            dominoes.add(entry.getValue());
        }
        return dominoes;
    }

    public ArrayList<Pair<Position, Direction>> getPath() {
        return new ArrayList<>(this.path);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int width = 0;
        private int height = 0;
        private final ArrayList<Pair<Position, Direction>> path = new ArrayList<>();
        private final Map<Position, Domino> dominoes = new HashMap<>();

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder addDominoList(ArrayList<Pair<Domino, Domino>> dominoes) {
            for (Pair<Domino, Domino> pair : dominoes) {
                if (Validator.neighbouringDominoes(pair.getFirst(), pair.getSecond())) {
                    this.addDomino(pair.getFirst().getPosition(), pair.getFirst().getDirection(), pair.getFirst().getSide(), pair.getSecond().getSide());
                } else {
                    throw new IllegalArgumentException("Tried to add a domino, that has incorrect linking: " + pair.getFirst() + " " + pair.getSecond());
                }
            }
            return this;
        }

        public Builder addDomino(Position position, Direction direction, DominoSide first, DominoSide second) {
            dominoes.put(position, new Domino(position, direction, first));
            dominoes.put(position.add(direction.getVector()), new Domino(position.add(direction.getVector()), direction.getOpposite(), second));
            return this;
        }

        public Builder addPath(Position position, Direction direction) {
            path.add(Pair.of(position, direction));
            path.add(Pair.of(position.add(direction.getVector()), direction.getOpposite()));
            return this;
        }

        public Level build() {
            return new Level(this);
        }
    }
}
