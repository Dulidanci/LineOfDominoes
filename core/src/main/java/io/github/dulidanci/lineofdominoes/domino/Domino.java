package io.github.dulidanci.lineofdominoes.domino;

import io.github.dulidanci.lineofdominoes.level.movement.Position;
import io.github.dulidanci.lineofdominoes.level.movement.Direction;
import io.github.dulidanci.lineofdominoes.util.Pair;

import java.util.Objects;

public class Domino {
    private final Position coordinate;
    private final Direction direction;
    private final DominoSide firstSide;
    private final DominoSide secondSide;

    public Domino(Position position, Direction direction, DominoSide firstSide) {
        this(position, direction, firstSide, null);
    }

    public Domino(Position position, Direction direction, DominoSide firstSide, DominoSide SecondSide) {
        this.coordinate = position;
        this.direction = direction;
        this.firstSide = firstSide;
        this.secondSide = SecondSide;
    }

    public Position getPosition() {
        return coordinate;
    }

    public Direction getDirection() {
        return direction;
    }

    public DominoSide getFirstSide() {
        return firstSide;
    }

    public DominoSide getSecondSide() {
        return secondSide;
    }

    public Pair<Domino, Domino> split() {
        return Pair.of(
            new Domino(this.coordinate, this.direction, this.firstSide),
            new Domino(this.coordinate.add(this.direction.getVector()), this.direction.getOpposite(), this.secondSide)
        );
    }

    public Domino flipped() {
        return new Domino(this.coordinate.add(this.direction.getVector()), this.direction.getOpposite(), this.secondSide, this.firstSide);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (Objects.isNull(obj) || !(obj instanceof Domino domino)) {
            return false;
        }
        return Objects.equals(this.coordinate, domino.coordinate)
            && Objects.equals(this.direction, domino.direction)
            && Objects.equals(this.firstSide, domino.firstSide)
            && Objects.equals(this.secondSide, domino.secondSide);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, direction, firstSide, secondSide);
    }
}
