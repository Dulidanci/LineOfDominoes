package io.github.dulidanci.lineofdominoes.domino;

public enum DominoSide {
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE;

    public static DominoSide getRandomSide() {
        return DominoSide.values()[(int) (Math.random() * DominoSide.values().length)];
    }
}
