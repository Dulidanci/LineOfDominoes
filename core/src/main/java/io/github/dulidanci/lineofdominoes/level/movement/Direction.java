package io.github.dulidanci.lineofdominoes.level.movement;

public enum Direction {
    DOWN(0, -1, 'D'),
    RIGHT(1, 0, 'R'),
    UP(0, 1, 'U'),
    LEFT(-1, 0, 'L');

    private final Position vector;
    private final char character;

    Direction(int x, int y, char character) {
        this.vector = new Position(x, y);
        this.character = character;
    }

    public Position getVector() {
        return vector;
    }

    public char getCharacter() {
        return character;
    }

    public Direction getOpposite() {
        return Direction.values()[(this.ordinal() + 2) % 4];
    }

    public int getTurnDegrees() {
        return this.ordinal() * 90;
    }
}
