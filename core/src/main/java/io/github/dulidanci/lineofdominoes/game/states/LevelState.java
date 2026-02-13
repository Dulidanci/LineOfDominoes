package io.github.dulidanci.lineofdominoes.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.dulidanci.lineofdominoes.game.LineOfDominoes;
import io.github.dulidanci.lineofdominoes.level.Level;
import io.github.dulidanci.lineofdominoes.level.generator.LevelGenerator;
import io.github.dulidanci.lineofdominoes.level.inventory.Inventory;

public class LevelState extends GameState {
    private final LevelGenerator generator;
    private Level level;
    private final Inventory inventory;

    public LevelState(GameStateManager gsm) {
        super(gsm);
        this.generator = new LevelGenerator(LineOfDominoes.WIDTH, LineOfDominoes.HEIGHT);
        this.level = generator.generateLevel((int) (Math.random() * LineOfDominoes.HEIGHT));
        this.inventory = new Inventory(10);
    }

    @Override
    public void enter() {

    }

    @Override
    public void update(float delta) {
        // todo: remove this
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            this.level = generator.generateLevel((int) (Math.random() * LineOfDominoes.HEIGHT));
        }
    }

    @Override
    public void exit() {

    }

    @Override
    public void dispose() {

    }

    public Level getLevel() {
        return level;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
