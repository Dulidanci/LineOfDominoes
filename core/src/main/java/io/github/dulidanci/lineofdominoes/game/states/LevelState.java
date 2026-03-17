package io.github.dulidanci.lineofdominoes.game.states;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.dulidanci.lineofdominoes.assets.AssetsLoader;
import io.github.dulidanci.lineofdominoes.domino.Domino;
import io.github.dulidanci.lineofdominoes.game.LineOfDominoes;
import io.github.dulidanci.lineofdominoes.input.InputSystem;
import io.github.dulidanci.lineofdominoes.level.Level;
import io.github.dulidanci.lineofdominoes.level.generator.LevelGenerator;
import io.github.dulidanci.lineofdominoes.level.inventory.Inventory;
import io.github.dulidanci.lineofdominoes.level.movement.Position;
import io.github.dulidanci.lineofdominoes.render.DrawContext;
import io.github.dulidanci.lineofdominoes.render.RenderLayer;
import io.github.dulidanci.lineofdominoes.screen.UIManager;
import io.github.dulidanci.lineofdominoes.screen.widget.DominoWidget;

public class LevelState implements GameState {
    private final FitViewport viewport;
    private final Vector3 mouse = new Vector3();
    private final LevelGenerator generator;
    private final Inventory inventory;
    private final UIManager uiManager;
    private Level level;
    private boolean paused;


    public LevelState() {
        this.generator = new LevelGenerator(LineOfDominoes.WIDTH, LineOfDominoes.HEIGHT);
        this.inventory = new Inventory(10);
        this.uiManager = new UIManager();

        this.viewport = new FitViewport(LineOfDominoes.WIDTH * LineOfDominoes.PIXEL_DENSITY, (LineOfDominoes.HEIGHT + 3) * LineOfDominoes.PIXEL_DENSITY);

        this.inventory.setListener((pair, index) -> {
            DominoWidget dominoWidget = (DominoWidget) new DominoWidget.Builder(24, 48, RenderLayer.INVENTORY, pair, index)
                .position(index * 48 + 12, 12)
                .onPress(widget -> LevelState.this.onPress((DominoWidget) widget))
                .onRelease(widget -> LevelState.this.onRelease((DominoWidget) widget))
                .build();
            uiManager.addDomino(index, dominoWidget);
        });
    }

    @Override
    public void enter() {
        // todo: this is where the starting domino height is declared. It shouldn't be random always
        this.uiManager.clear();
        this.uiManager.unfocusDomino();
        this.level = generator.generateLevel((int) (Math.random() * LineOfDominoes.HEIGHT));
        this.inventory.clear();
        this.paused = false;
    }

    @Override
    public void update(float delta, InputSystem inputSystem) {
        if (paused) return;

        mouse.set(inputSystem.getMouse().x, inputSystem.getMouse().y, 0);
        viewport.unproject(mouse);
        inputSystem.getMouse().projectedMouse(new Vector2(mouse.x, mouse.y));

        // todo: remove this
        if (inputSystem.getKeyboard().isJustPressed(Input.Keys.R)) {
            this.enter();
        }

        if (inputSystem.getMouse().scrolledForward && uiManager.getFocusedDomino() != null) {
                uiManager.getFocusedDomino().rotate(1);
        }

        if (inputSystem.getMouse().scrolledBackward && uiManager.getFocusedDomino() != null) {
            uiManager.getFocusedDomino().rotate(-1);
        }

        inventory.update();
        uiManager.update(delta, inputSystem);
    }

    @Override
    public void render(float delta, DrawContext drawContext) {
        drawContext.draw(AssetsLoader.getSunset(), 0, 0);

        level.render(delta, drawContext);

        uiManager.render(delta, drawContext);
    }

    @Override
    public void pause() {
        this.paused = true;
    }

    @Override
    public void resume() {
        this.paused = false;
    }

    @Override
    public void exit() {

    }

    @Override
    public void dispose() {
        this.inventory.dispose();
        this.uiManager.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void onPress(DominoWidget widget) {
        uiManager.focusDomino(widget.getIndex());
    }

    public void onRelease(DominoWidget widget) {
        Position gridPosition = new Position(
            (int) (widget.getCenterX() + widget.getRotation().getOpposite().getVector().x() * 12) / 24,
            (int) (widget.getCenterY() + widget.getRotation().getOpposite().getVector().y() * 12) / 24 - 3);

        Domino domino = new Domino(gridPosition, widget.getRotation(), inventory.get(widget.getIndex()).getFirst(), inventory.get(widget.getIndex()).getSecond());

        if (level.canPlace(domino)) {
            level.placeDomino(domino);
            uiManager.removeDomino(widget.getIndex());
            inventory.remove(widget.getIndex());
            uiManager.unfocusDomino();
        }

        widget.moveTo(new Vector2(widget.getOriginX(), widget.getOriginY()));
    }

    @Override
    public Viewport getViewport() {
        return viewport;
    }
}
