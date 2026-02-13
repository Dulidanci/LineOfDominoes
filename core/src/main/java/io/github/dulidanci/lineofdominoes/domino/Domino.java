package io.github.dulidanci.lineofdominoes.domino;

import io.github.dulidanci.lineofdominoes.level.movement.Position;
import io.github.dulidanci.lineofdominoes.level.movement.Direction;

public class Domino {
    private DominoSide side;
    private Position coordinate;
    private Direction direction;

    public Domino(Position position, Direction direction, DominoSide side) {
        this.coordinate = position;
        this.direction = direction;
        this.side = side;
    }

    public void setPosition(Position position, Direction direction) {
        this.coordinate = position;
        this.direction = direction;
    }

    public Position getPosition() {
        return coordinate;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setSides(DominoSide side) {
        this.side = side;
    }

    public DominoSide getSide() {
        return side;
    }
}
