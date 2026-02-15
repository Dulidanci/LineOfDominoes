package io.github.dulidanci.lineofdominoes.render;

import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.dulidanci.lineofdominoes.game.states.LevelState;
import io.github.dulidanci.lineofdominoes.level.Level;
import io.github.dulidanci.lineofdominoes.level.inventory.Inventory;
import io.github.dulidanci.lineofdominoes.screen.UIManager;

public class RenderContext {
    private final Viewport viewport;
    public Level level;
    public Inventory inventory;
    public UIManager uiManager;

    public RenderContext(Viewport viewport) {
        this.viewport = viewport;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public RenderContext fromLevelState(LevelState levelState) {
        this.level = levelState.getLevel();
        this.inventory = levelState.getInventory();
        this.uiManager = levelState.getUiManager();
        return this;
    }
}
