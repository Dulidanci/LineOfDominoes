package io.github.dulidanci.lineofdominoes.level.movement;

public record Position(int x, int y) {
    public Position add(Position other) {
        return new Position(x + other.x, y + other.y);
    }

    public Position scale(int lambda) {
        return new Position(x * lambda, y * lambda);
    }
}
