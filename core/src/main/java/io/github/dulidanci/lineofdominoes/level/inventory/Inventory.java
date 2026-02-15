package io.github.dulidanci.lineofdominoes.level.inventory;

import io.github.dulidanci.lineofdominoes.domino.DominoSide;
import io.github.dulidanci.lineofdominoes.events.InventoryListener;
import io.github.dulidanci.lineofdominoes.util.Pair;

import java.util.ArrayList;

public class Inventory {
    public final int count;
    private final ArrayList<Pair<DominoSide, DominoSide>> inventory = new ArrayList<>();
    private InventoryListener listener;

    public Inventory(int count) {
        this.count = count;
    }

    public void update() {
        if (this.inventory.size() < this.count) {
            fillUp();
        }
    }

    public void fillUp() {
        while (this.inventory.size() < count) {
            generateDomino();
        }
    }

    private void generateDomino() {
        this.inventory.add(Pair.of(DominoSide.getRandomSide(), DominoSide.getRandomSide()));

        if (this.listener != null) {
            this.listener.onDominoAdded(this.inventory.get(this.inventory.size() - 1), this.inventory.size() - 1);
            System.out.println("sent on domino added event");
        }
    }

    public Pair<DominoSide, DominoSide> get(int index) {
        return this.inventory.get(index);
    }

    public Pair<DominoSide, DominoSide> takeAndRemove(int index) {
        Pair<DominoSide, DominoSide> removed = this.inventory.remove(index);
        if (this.listener != null) {
            listener.onDominoRemoved(removed, index);
            System.out.println("sent on domino removed event");
        }
        this.fillUp();
        return removed;
    }

    public void clear() {
        this.inventory.clear();
    }

    public ArrayList<Pair<DominoSide, DominoSide>> getInventory() {
        return new ArrayList<>(this.inventory);
    }

    public void setListener(InventoryListener listener) {
        this.listener = listener;
    }

    public void dispose() {
        this.clear();
        this.listener = null;
    }
}
