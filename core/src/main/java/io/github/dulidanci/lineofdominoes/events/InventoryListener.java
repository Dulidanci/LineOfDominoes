package io.github.dulidanci.lineofdominoes.events;

import io.github.dulidanci.lineofdominoes.domino.DominoSide;
import io.github.dulidanci.lineofdominoes.util.Pair;

public interface InventoryListener {
    void onDominoAdded(Pair<DominoSide, DominoSide> pair, int index);

    void onDominoRemoved(Pair<DominoSide, DominoSide> pair, int index);
}
