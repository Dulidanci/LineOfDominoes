package io.github.dulidanci.lineofdominoes.level.inventory;

import io.github.dulidanci.lineofdominoes.domino.DominoSide;
import io.github.dulidanci.lineofdominoes.events.InventoryListener;
import io.github.dulidanci.lineofdominoes.util.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class Inventory {
    public final int count;
    private final ArrayList<Pair<DominoSide, DominoSide>> inventory;
    private InventoryListener listener;

    public Inventory(int count) {
        this.count = count;
        this.inventory = new ArrayList<>(count);

        while(inventory.size() < 10) {
            addDomino();
        }
    }

    public void update() {
        for (int i = 0; i < count; i++) {
            if (inventory.get(i) == null) {
                generateDomino(i);
            }
        }
    }

    private void addDomino() {
        this.inventory.add(Pair.of(DominoSide.getRandomSide(), DominoSide.getRandomSide()));

        if (this.listener != null) {
            this.listener.onDominoAdded(this.inventory.get(this.inventory.size() - 1), this.inventory.size() - 1);
        }
    }

    private void generateDomino(int index) {
        if (index < 0 || index >= inventory.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + inventory.size());
        }

        this.inventory.set(index, Pair.of(DominoSide.getRandomSide(), DominoSide.getRandomSide()));

        if (this.listener != null) {
            this.listener.onDominoAdded(this.inventory.get(index), index);
        }
    }

    public Pair<DominoSide, DominoSide> get(int index) {
        return this.inventory.get(index);
    }

    public void remove(int index) {
        this.inventory.set(index, null);

        this.generateDomino(index);
    }

    public void clear() {
        Collections.fill(inventory, null);
    }

    public void setListener(InventoryListener listener) {
        this.listener = listener;
    }

    public void dispose() {
        this.clear();
        this.listener = null;
    }
}
