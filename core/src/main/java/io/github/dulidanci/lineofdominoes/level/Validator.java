package io.github.dulidanci.lineofdominoes.level;

import io.github.dulidanci.lineofdominoes.domino.Domino;
import io.github.dulidanci.lineofdominoes.level.movement.Direction;
import io.github.dulidanci.lineofdominoes.level.movement.Position;

public class Validator {
    private final int height;
    private final int width;

    public Validator(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public boolean validateCoordinates(Position position, Direction direction) {
        return position.x() >= 0 && position.x() < width && position.y() >= 0 && position.y() < height &&
            position.add(direction.getVector()).x() >= 0 && position.add(direction.getVector()).x() < width &&
            position.add(direction.getVector()).y() >= 0 && position.add(direction.getVector()).y() < height;
    }

    public boolean neighbouring(Position first, Position second) {
        Position difference = new Position(second.x() - first.x(), second.y() - first.y());
        for (Direction direction : Direction.values()) {
            if (difference.equals(direction.getVector())) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateIndex(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public static boolean neighbouringDominoes(Domino first, Domino second) {
        return first.getPosition().add(first.getDirection().getVector()).equals(second.getPosition()) &&
            second.getPosition().add(second.getDirection().getVector()).equals(first.getPosition());
    }
}
