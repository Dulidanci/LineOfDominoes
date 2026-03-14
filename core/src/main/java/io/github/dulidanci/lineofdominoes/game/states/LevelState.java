package io.github.dulidanci.lineofdominoes.game.states;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.dulidanci.lineofdominoes.assets.AssetsLoader;
import io.github.dulidanci.lineofdominoes.domino.DominoSide;
import io.github.dulidanci.lineofdominoes.events.InventoryListener;
import io.github.dulidanci.lineofdominoes.game.LineOfDominoes;
import io.github.dulidanci.lineofdominoes.input.InputSystem;
import io.github.dulidanci.lineofdominoes.level.Level;
import io.github.dulidanci.lineofdominoes.level.generator.LevelGenerator;
import io.github.dulidanci.lineofdominoes.level.inventory.Inventory;
import io.github.dulidanci.lineofdominoes.render.DrawContext;
import io.github.dulidanci.lineofdominoes.render.RenderLayer;
import io.github.dulidanci.lineofdominoes.screen.UIManager;
import io.github.dulidanci.lineofdominoes.screen.widget.DominoWidget;
import io.github.dulidanci.lineofdominoes.screen.widget.Widget;
import io.github.dulidanci.lineofdominoes.util.Pair;

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

        this.inventory.setListener(new InventoryListener() {
            @Override
            public void onDominoAdded(Pair<DominoSide, DominoSide> pair, int index) {
                System.out.println("received on domino added event");
                DominoWidget dominoWidget = new DominoWidget.Builder(24, 48, RenderLayer.INVENTORY, pair)
                    .position(index * 48 + 12, 12)
                    .onPress(LevelState.this::onPress)
                    .onRelease(LevelState.this::onRelease)
                    .build();
                uiManager.addDomino(dominoWidget);
            }

            @Override
            public void onDominoRemoved(Pair<DominoSide, DominoSide> pair, int index) {
                System.out.println("received on domino removed event");
                // todo: this
            }
        });
    }

    @Override
    public void enter() {
        // todo: this is where the starting domino height is declared. It shouldn't be random always
        this.uiManager.clear();
        this.level = generator.generateLevel((int) (Math.random() * LineOfDominoes.HEIGHT));
        this.inventory.clear();
        this.paused = false;
    }

    @Override
    public void update(float delta, InputSystem inputSystem) {
        if (paused) return;

        // todo: remove this
        if (inputSystem.getKeyboard().isJustPressed(Input.Keys.R)) {
            this.enter();
        }

        mouse.set(inputSystem.getMouse().x, inputSystem.getMouse().y, 0);
        viewport.unproject(mouse);
        inputSystem.getMouse().projectedMouse(new Vector2(mouse.x, mouse.y));

        inventory.update();
        uiManager.update(delta, inputSystem);

        // todo: remove this
        if (inputSystem.getMouse().leftJustPressed) {
            System.out.println("Mouse at screen (" + inputSystem.getMouse().x + ", " + inputSystem.getMouse().y + ")," +
                " world " + inputSystem.getMouse().worldX + ", " + inputSystem.getMouse().worldY + ")");
        }
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

    public void onPress(Widget widget) {
        System.out.println("LevelState::onPress");
    }

    public void onRelease(Widget widget) {
        System.out.println("LevelState::onRelease");
    }

    public Viewport getViewport() {
        return viewport;
    }
}
