package io.github.dulidanci.lineofdominoes.level.generator;

import io.github.dulidanci.lineofdominoes.domino.Domino;
import io.github.dulidanci.lineofdominoes.domino.DominoSide;
import io.github.dulidanci.lineofdominoes.level.Level;
import io.github.dulidanci.lineofdominoes.level.Validator;
import io.github.dulidanci.lineofdominoes.level.movement.Direction;
import io.github.dulidanci.lineofdominoes.level.movement.Position;
import io.github.dulidanci.lineofdominoes.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class LevelGenerator {
    public final int width;
    public final int height;
    public boolean[][] visited;
    public final ArrayList<Pair<Position, Direction>> path = new ArrayList<>();
    public final float dominoPercentage = 0.2f;
    public final ArrayList<Pair<Domino, Domino>> dominoes = new ArrayList<>();

    public LevelGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.reset();
    }

    public void reset() {
        this.visited = new boolean[width + 1][height];
        this.path.clear();
        this.dominoes.clear();
    }

    public Level generateLevel(int startY) {
        this.reset();
        Level.Builder builder = Level.builder().width(width).height(height);
        this.generatePath(startY);
        this.generateDominoes();
        dominoes.forEach(dominoPair -> path.remove(Pair.of(dominoPair.getFirst().getPosition().add(Direction.LEFT.getVector()), dominoPair.getFirst().getDirection())));
        path.forEach(pair -> builder.addPath(pair.getFirst().add(Direction.RIGHT.getVector()), pair.getSecond()));
        dominoes.forEach(domino -> builder.addDominoList(this.dominoes));
        return builder.build();
    }

    private void generatePath(int startY) {
        if (Validator.validateIndex(0, startY, width, height)) {
            path.add(Pair.of(new Position(-2, startY), Direction.RIGHT));
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
                path.remove(path.size() - 1);
                return false;
            }

            Direction chosenDirection = neighbours.get((int) (Math.random() * neighbours.size()));

            path.add(Pair.of(new Position(x, y), chosenDirection));

            if (x + chosenDirection.getVector().scale(2).x() >= width - 1 ||
                visit(x + chosenDirection.getVector().scale(2).x(), y + chosenDirection.getVector().scale(2).y())) {
                return true;
            }
        }
    }

    private void generateDominoes() {
        for (int i = 0; i < path.size(); i++) {
            dominoes.add(i, null);
        }

        int targetNumber = Math.round(path.size() * dominoPercentage);
        if (targetNumber > dominoes.size()) {
            throw new ArrayIndexOutOfBoundsException("Tried to generate more dominoes than there are spaces for them!" + targetNumber + " > " + dominoes.size());
        }

        this.createDominoIgnoringNeighbours(0);
        this.createDominoIgnoringNeighbours(path.size() - 1);

        int generated = 2;
        while (generated < targetNumber) {
            if (createDomino((int) (Math.random() * path.size()))) {
                generated++;
            }
        }

        dominoes.removeIf(Objects::isNull);
    }

    private boolean createDomino(int indexInPath) {
        if (dominoes.get(indexInPath) == null) {

            dominoes.set(indexInPath, Pair.of(
                new Domino(path.get(indexInPath).getFirst().add(Direction.RIGHT.getVector()), path.get(indexInPath).getSecond(),
                    dominoes.get(indexInPath - 1) != null ? dominoes.get(indexInPath - 1).getSecond().getSide() : DominoSide.getRandomSide()),
                new Domino(path.get(indexInPath).getFirst().add(path.get(indexInPath).getSecond().getVector()).add(Direction.RIGHT.getVector()), path.get(indexInPath).getSecond().getOpposite(),
                    dominoes.get(indexInPath + 1) != null ? dominoes.get(indexInPath + 1).getFirst().getSide() : DominoSide.getRandomSide())));

            return true;
        }
        return false;
    }

    private void createDominoIgnoringNeighbours(int indexInPath) {
        dominoes.set(indexInPath, Pair.of(
            new Domino(path.get(indexInPath).getFirst().add(Direction.RIGHT.getVector()), path.get(indexInPath).getSecond(), DominoSide.getRandomSide()),
            new Domino(path.get(indexInPath).getFirst().add(path.get(indexInPath).getSecond().getVector()).add(Direction.RIGHT.getVector()), path.get(indexInPath).getSecond().getOpposite(), DominoSide.getRandomSide())));
    }


}
