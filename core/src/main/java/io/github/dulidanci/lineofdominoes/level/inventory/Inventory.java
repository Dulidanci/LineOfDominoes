package io.github.dulidanci.lineofdominoes.level.inventory;

import io.github.dulidanci.lineofdominoes.domino.Domino;
import io.github.dulidanci.lineofdominoes.domino.DominoSide;
import io.github.dulidanci.lineofdominoes.level.movement.Direction;
import io.github.dulidanci.lineofdominoes.level.movement.Position;
import io.github.dulidanci.lineofdominoes.util.Pair;

import java.util.ArrayList;

public class Inventory {
    public final int count;
    private final ArrayList<Pair<Domino, Domino>> inventory = new ArrayList<>();

    public Inventory(int count) {
        this.count = count;
        this.fillUp();
    }

    public void fillUp() {
        while (this.inventory.size() < count) {
            this.inventory.add(Pair.of(
                new Domino(Position.origo, Direction.UP, DominoSide.getRandomSide()),
                new Domino(Position.origo.add(Direction.UP.getVector()), Direction.DOWN, DominoSide.getRandomSide())
            ));
        }
    }

    public ArrayList<Pair<Domino, Domino>> getInventory() {
        return new ArrayList<>(this.inventory);
    }

    public Pair<Domino, Domino> get(int index) {
        return this.inventory.get(index);
    }

    public Pair<Domino, Domino> takeAndRemove(int index) {
        return this.inventory.remove(index);
    }
}
