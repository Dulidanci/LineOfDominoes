package io.github.dulidanci.lineofdominoes.level.movement;

public enum Direction {
    DOWN(0, -1),
    RIGHT(1, 0),
    UP(0, 1),
    LEFT(-1, 0);

    private final Position vector;

    Direction(int x, int y) {
        this.vector = new Position(x, y);
    }

    public Position getVector() {
        return vector;
    }

    public Direction getOpposite() {
        return Direction.values()[(this.ordinal() + 2) % 4];
    }

    public int getTurnDegrees() {
        return this.ordinal() * 90;
    }
}
