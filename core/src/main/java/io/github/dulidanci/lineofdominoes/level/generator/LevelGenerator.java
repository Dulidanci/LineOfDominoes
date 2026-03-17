package io.github.dulidanci.lineofdominoes.level.generator;

import io.github.dulidanci.lineofdominoes.domino.Domino;
import io.github.dulidanci.lineofdominoes.domino.DominoSide;
import io.github.dulidanci.lineofdominoes.level.Level;
import io.github.dulidanci.lineofdominoes.level.movement.Direction;
import io.github.dulidanci.lineofdominoes.level.movement.Position;
import io.github.dulidanci.lineofdominoes.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class LevelGenerator {
    private final int width;
    private final int height;
    private boolean[][] visited;
    private final ArrayList<Pair<Position, Direction>> emptySpaces = new ArrayList<>();
    public final float dominoPercentage = 0.2f;
    private final ArrayList<Domino> dominoes = new ArrayList<>();

    public LevelGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.reset();
    }

    public void reset() {
        this.visited = new boolean[width + 1][height];
        this.emptySpaces.clear();
        this.dominoes.clear();
    }

    public Level generateLevel(int startY) {
        this.reset();
        this.generatePath(startY);

        ArrayList<Pair<Position, Direction>> path = new ArrayList<>();
        emptySpaces.forEach(pair -> {
            path.add(Pair.of(pair.getFirst().add(Direction.RIGHT.getVector()), pair.getSecond()));
            path.add(Pair.of(pair.getFirst().add(pair.getSecond().getVector()).add(Direction.RIGHT.getVector()), pair.getSecond().getOpposite()));
        });
        Level.Builder builder = new Level.Builder(path);

        this.generateDominoes();
        builder.addDominoList(this.dominoes);

        return builder.build();
    }

    private void generatePath(int startY) {
        if (Validator.validateIndex(0, startY, width, height)) {
            emptySpaces.add(Pair.of(new Position(-2, startY), Direction.RIGHT));
            visit(0, startY);
        } else {
            throw new IllegalStateException("Invalid index when generating level: " + startY + " - " + height);
        }
    }

    private boolean visit(int x, int y) {
        visited[x][y] = true;
        while(true) {
            ArrayList<Direction> neighbours = new ArrayList<>();
            Arrays.stream(Direction.values()).forEach(direction -> {
                Position target = new Position(x, y).add(direction.getVector().scale(2));
                if (Validator.validateIndex(target.x(), target.y(), width + 1, height)) {
                    if (!visited[target.x()][target.y()]) {
                        neighbours.add(direction);
                    }
                }
            });

            if (neighbours.isEmpty()) {
                emptySpaces.remove(emptySpaces.size() - 1);
                return false;
            }

            Direction chosenDirection = neighbours.get((int) (Math.random() * neighbours.size()));

            emptySpaces.add(Pair.of(new Position(x, y), chosenDirection));

            if (x + chosenDirection.getVector().scale(2).x() >= width - 1 ||
                visit(x + chosenDirection.getVector().scale(2).x(), y + chosenDirection.getVector().scale(2).y())) {
                return true;
            }
        }
    }

    private void generateDominoes() {
        for (int i = 0; i < emptySpaces.size(); i++) {
            dominoes.add(i, null);
        }

        int targetNumber = Math.round(emptySpaces.size() * dominoPercentage);
        if (targetNumber > dominoes.size()) {
            throw new ArrayIndexOutOfBoundsException("Tried to generate more dominoes than there are spaces for them!" + targetNumber + " > " + dominoes.size());
        }

        this.createDominoIgnoringNeighbours(0);
        this.createDominoIgnoringNeighbours(emptySpaces.size() - 1);

        int generated = 2;
        while (generated < targetNumber) {
            if (createDomino((int) (Math.random() * emptySpaces.size()))) {
                generated++;
            }
        }

        dominoes.removeIf(Objects::isNull);
    }

    private boolean createDomino(int indexInPath) {
        if (dominoes.get(indexInPath) == null) {

            dominoes.set(indexInPath, new Domino(
                emptySpaces.get(indexInPath).getFirst().add(Direction.RIGHT.getVector()),
                emptySpaces.get(indexInPath).getSecond(),
                dominoes.get(indexInPath - 1) != null ? dominoes.get(indexInPath - 1).getSecondSide() : DominoSide.getRandomSide(),
                dominoes.get(indexInPath + 1) != null ? dominoes.get(indexInPath + 1).getFirstSide() : DominoSide.getRandomSide()));
            return true;
        }
        return false;
    }

    private void createDominoIgnoringNeighbours(int indexInPath) {
        dominoes.set(indexInPath, new Domino(
            emptySpaces.get(indexInPath).getFirst().add(Direction.RIGHT.getVector()),
            emptySpaces.get(indexInPath).getSecond(),
            DominoSide.getRandomSide(),
            DominoSide.getRandomSide()));
    }

    public static class Validator {
        public static boolean validateIndex(int x, int y, int width, int height) {
            return x >= 0 && x < width && y >= 0 && y < height;
        }
    }
}
