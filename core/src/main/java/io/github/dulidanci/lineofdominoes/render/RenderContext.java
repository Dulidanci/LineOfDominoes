package io.github.dulidanci.lineofdominoes.render;

import io.github.dulidanci.lineofdominoes.game.states.GameState;
import io.github.dulidanci.lineofdominoes.game.states.LevelState;
import io.github.dulidanci.lineofdominoes.level.Level;
import io.github.dulidanci.lineofdominoes.level.inventory.Inventory;

public class RenderContext {
    public Level level;
    public Inventory inventory;

    public RenderContext from(GameState state) {
        if (state instanceof LevelState) {
            return this.fromLevelState((LevelState) state);
        }
        return new RenderContext();
    }

    private RenderContext fromLevelState(LevelState levelState) {
        this.level = levelState.getLevel();
        this.inventory = levelState.getInventory();
        return this;
    }
}
