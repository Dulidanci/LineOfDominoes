package io.github.dulidanci.lineofdominoes.level.movement;

public record Position(int x, int y) {
    public static final Position origo = new Position(0, 0);

    public Position add(Position other) {
        return new Position(x + other.x, y + other.y);
    }

    public Position scale(int lambda) {
        return new Position(x * lambda, y * lambda);
    }

    public Position copy() {
        return new Position(x, y);
    }
}
